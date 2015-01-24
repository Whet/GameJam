package game.scenarios;

import game.GameChoiceMenu;
import game.God;
import game.TurnProcess;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.display.Displayable;
import watoydoEngine.designObjects.display.Text;
import watoydoEngine.io.ReadWriter;

public class StoreRobbery extends GameChoiceMenu {

	private TurnProcess turnProcess;
	private ChoiceButton choiceButton1;
	private ChoiceButton choiceButton2;
	private ChoiceButton choiceButton3;
	private ChoiceButton choiceButton4;

	public StoreRobbery() {
		super();
		try{
		choiceButton1 = new ChoiceButton("Trolley", 1, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
		choiceButton2 = new ChoiceButton("Robber", 0, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
		choiceButton3 = new ChoiceButton("Clerk", 2, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
		choiceButton4 = new ChoiceButton("Alchohol", 3, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		HashSet<Displayable> others = new HashSet<>();
		
		others.add(choiceButton1);
		others.add(choiceButton2);
		others.add(choiceButton3);
		others.add(choiceButton4);
		
		turnProcess = new TurnProcess(backgroundText, this.getGods().length, others, "solutions.txt");
		choiceButton1.setTurnProcess(turnProcess);
		choiceButton2.setTurnProcess(turnProcess);
		choiceButton3.setTurnProcess(turnProcess);
		choiceButton4.setTurnProcess(turnProcess);
	}
	
	@Override
	public ChoiceButton getOptionOne() throws FileNotFoundException, IOException {
		return choiceButton1;
	}

	@Override
	public ChoiceButton getOptionTwo() throws FileNotFoundException, IOException {
		return choiceButton2;
	}

	@Override
	public ChoiceButton getOptionThree() throws FileNotFoundException, IOException {
		return choiceButton3;
	}

	@Override
	public ChoiceButton getOptionFour() throws FileNotFoundException, IOException {
		return choiceButton4;
	}

	@Override
	protected String getScenarioName() {
		return "Sandra Store Scenario";
	}

	@Override
	protected BufferedImage getBackgroundImage() {
		try {
			return ImageIO.read(ReadWriter.getResourceAsInputStream("backgroundPlaceholder.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected God[] getGods() {
		return new God[]{God.butter, God.debauchery, God.fire, God.motion};
	}

	@Override
	protected TurnProcess getTurnProcess(Text storyText) {
		turnProcess.setText(storyText);
		return turnProcess;
	}

	@Override
	public String getStartText() {
		return "";
	}

}
