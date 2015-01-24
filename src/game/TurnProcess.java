package game;

import game.parser.ObjectGodPair;
import game.parser.Parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import watoydoEngine.designObjects.display.Displayable;
import watoydoEngine.designObjects.display.Text;


public class TurnProcess {

	private List<God> takenTurns;
	private List<Integer> takenChoices;
	
	private God currentGod;
	private int stage;
	private final int PLAYER_COUNT;
	private String story;
	private Parser parser;
	
	private Text storyText;
	
	private Set<Displayable> others;
	private int storyLength;
	
	public TurnProcess(int playerCount, Set<Displayable> others, String file) {
		
		this.PLAYER_COUNT = playerCount;
		
		this.others = others;
		
		this.storyLength = 0;
		this.stage = 0;
		currentGod = God.unselected;
		
		takenChoices = new ArrayList<>();
		takenTurns = new ArrayList<>();
		
		parser = new Parser(new File(file));
	}

	public God getCurrentGod() {
		return currentGod;
	}

	public boolean setCurrentGod(God currentGod) {
		
		if(!this.takenTurns.contains(currentGod)) {
			this.currentGod = currentGod;
			return true;
		}
		
		return false;
	}
	
	public boolean applyChoice(int choiceNumber) {
		
		if(this.currentGod == God.unselected) {
			System.out.println("GOD UNSELECTED");
			return false;
		}

		System.out.println("GOD " + currentGod.toString() + " CHOSE " + choiceNumber);
		
		takenTurns.add(currentGod);
		takenChoices.add(choiceNumber);
		
		// Or auto choose next god
		currentGod = God.unselected;

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
		
//		this.story = this.parser.getStoryLine(pairs);
		this.story = "Roses are red@cViolets are blue@cAnus";
		
		// Work out how many clicks needed to read story
		char[] charArray = this.story.toCharArray();
		for(int i = 0; i < charArray.length; i++) {
			if(charArray[i] == '@' && charArray[i + 1] == 'c') {
				storyLength++;
			}
		}
		
		storyText.setText(this.getStory());
	}

	private void hideAll() {
		for(Displayable btn:this.others) {
			btn.setVisible(false);
		}
	}

	private String getStory() {

		StringBuffer sb = new StringBuffer();
		
		char[] charArray = this.story.toCharArray();
		
		int counter = 0;
		
		for(int i = 0; i < charArray.length; i++) {
			if(!(charArray[i] == '@' && charArray[i + 1] == 'c'))
				sb.append(charArray[i]);
			else if(charArray[i] == '@' && charArray[i + 1] == 'c' && counter < this.stage) {
				i++;
				sb.append("@n");
				counter++;
			}
			else
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
		
		if(this.stage > storyLength)
			System.out.println("STORY DONE");
	}

	public void setText(Text storyText) {
		this.storyText = storyText;
	}
	
}
