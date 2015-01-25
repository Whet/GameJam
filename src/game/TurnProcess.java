package game;

import game.GameChoiceMenu.GodButton;
import game.audio.AudioHandler;
import game.parser.ObjectGodPair;
import game.parser.Parser;
import game.scenarios.BearAttack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import watoydoEngine.designObjects.display.Crowd;
import watoydoEngine.designObjects.display.Displayable;
import watoydoEngine.designObjects.display.ImageSingle;
import watoydoEngine.designObjects.display.Text;
import watoydoEngine.workings.displayActivity.ActivePane;


public class TurnProcess {

	private List<God> takenTurns;
	private List<Integer> takenChoices;

	private God[] gods;

	private God currentGod;
	private int stage;
	private final int PLAYER_COUNT;
	private String story;
	private Parser parser;

	private Text storyText;

	private Set<Displayable> others;
	private int storyLength;
	private ImageSingle backGround;
	private List<GodButton> godImages;
	
	private AudioHandler audioHandler;

	public TurnProcess(God[] gods, ImageSingle background, int playerCount, Set<Displayable> others, String file) {

		this.gods = gods;

		this.backGround = background;

		this.PLAYER_COUNT = playerCount;

		this.others = others;

		this.storyLength = 0;
		this.stage = 0;
		this.currentGod = gods[0];

		takenChoices = new ArrayList<>();
		takenTurns = new ArrayList<>();

		parser = new Parser(new File(file));
		audioHandler = new AudioHandler();
	}

	public boolean applyChoice(int choiceNumber) {

		if(this.currentGod == God.unselected) {
			System.out.println("GOD UNSELECTED");
			return false;
		}


		for(Integer choiceMade:this.takenChoices) {
			if(choiceNumber == choiceMade) {
				System.out.println("COULDNT CHOOSE NUMBER");
				return false;
			}
		}

		System.out.println("GOD " + currentGod.toString() + " CHOSE " + choiceNumber);

		takenTurns.add(currentGod);
		takenChoices.add(choiceNumber);

		for(int i = 0; i < gods.length; i++) {
			godImages.get(i).setScale(0.3);
		}
		for(int i = 0; i < gods.length; i++) {
			if(PLAYER_COUNT == 1 && gods[i] == currentGod && i + 1 < gods.length) {
				currentGod = gods[i + 1];

				godImages.get(i + 1).setScale(godImages.get(i).getScale() + 0.1);

				break;
			}
			else if(PLAYER_COUNT == 2 && takenChoices.size() == 0) {
				currentGod = God.chaosvegetables;
				godImages.get(0).setScale(0.4);
				godImages.get(1).setScale(0.4);
			}
			else if(PLAYER_COUNT == 2 && takenChoices.size() > 0) {
				currentGod = God.watertreachery;
				godImages.get(2).setScale(0.4);
				godImages.get(3).setScale(0.4);
			}
		}

		if(this.takenChoices.size() == PLAYER_COUNT) {
			updateStory();
		}

		return true;
	}

	private void updateStory() {

		hideAll();

		ObjectGodPair[] pairs = new ObjectGodPair[PLAYER_COUNT];

		for(int i = 0; i < pairs.length; i++) {
			pairs[i] = new ObjectGodPair(takenChoices.get(i), takenTurns.get(i).getCode());
		}

		this.story = this.parser.getStoryLine(pairs);
		//		this.story = "Roses are red@cViolets are blue@cAnus";

		// Work out how many clicks needed to read story
		char[] charArray = this.story.toCharArray();
		for(int i = 0; i < charArray.length; i++) {
			if(charArray[i] == '@' && charArray[i + 1] == 'c') {
				storyLength++;
			}
		}

		storyText.setText(this.getStory());
		storyText.setAlpha(1);
		storyText.setVisible(true);
	}

	private void hideAll() {
		for(Displayable btn:this.others) {
			btn.setVisible(false);
		}
	}

	private String getStory() {
		backGround.setVisible(true);
		final int MAX_CHARS_PER_LINE = 25;
		int chars = 0;

		StringBuffer sb = new StringBuffer();

		char[] charArray = this.story.toCharArray();

		int counter = 0;

		for(int i = 0; i < charArray.length; i++) {
			if(counter >= this.stage && !(charArray[i] == '@' && charArray[i + 1] == 'c')) {
				sb.append(charArray[i]);
				chars++;
			}
			else if(charArray[i] == '@' && charArray[i + 1] == 'c') {
				i++;
				//				sb.append("@n");
				counter++;
				chars = 0;

				//Any @letter pair besides "@c" can represent an audio clip.
				String audioPath = "";
				switch(charArray[i+1]){
				case '1':
					i++;
					audioPath = "audio/explosion.wav";
					break;
				case '2':
					i++;
					audioPath = "audio/gunshot.wav";
					break;
				case '3':
					i++;
					audioPath = "audio/inferno.wav";
					break;
				case '4':
					i++;
					audioPath = "audio/moreglassbreaking.wav";
					break;
				case '5':
					i++;
					audioPath = "audio/singleglassbreaking.wav";
					break;
				case '6':
					i++;
					audioPath = "audio/splat.wav";
					break;
				case '7':
					i++;
					audioPath = "audio/thud.wav";
					break;
				case '8':
					i++;
					audioPath = "audio/wolfwhistle.wav";
					break;
				}
				if(!audioPath.equals("")) {
					audioHandler.playAudio(audioPath);
				}
				
			}

			if(chars > MAX_CHARS_PER_LINE && i+1 < charArray.length && charArray[i + 1] == ' ') {
				sb.append("@n");
				chars = 0;
			}

			if(counter > this.stage)
				break;
		}

		return sb.toString();
	}

	public boolean isChoicesMade() {
		return this.takenChoices.size() == PLAYER_COUNT;
	}

	public void incrementStage() {
		System.out.println(stage);
		this.stage++;
		storyText.setText(this.getStory());

		if(this.stage > storyLength && PLAYER_COUNT == 4)
			ActivePane.getInstance().changeRootCrowd(new Crowd(new BearAttack()));
		else if(this.stage > storyLength && PLAYER_COUNT == 2)
			ActivePane.getInstance().changeRootCrowd(new Crowd(new MainMenu()));
	}

	public void setText(Text storyText) {
		this.storyText = storyText;
	}

	public void setImages(List<GodButton> godImages) {
		this.godImages = godImages;
	}

}
