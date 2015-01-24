package game.scenarios;

import game.GameChoiceMenu;
import game.God;
import game.TurnProcess;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import watoydoEngine.io.ReadWriter;

public class StoreRobbery extends GameChoiceMenu {

	private TurnProcess turnProcess;

	public StoreRobbery() {
		turnProcess = new TurnProcess("dummySolution.txt");
	}
	
	@Override
	public ChoiceButton getOptionOne() throws FileNotFoundException, IOException {
		return new ChoiceButton("Option One", turnProcess, 0, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
	}

	@Override
	public ChoiceButton getOptionTwo() throws FileNotFoundException, IOException {
		return new ChoiceButton("Option Two", turnProcess, 1, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
	}

	@Override
	public ChoiceButton getOptionThree() throws FileNotFoundException, IOException {
		return new ChoiceButton("Option Three", turnProcess, 2, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
	}

	@Override
	public ChoiceButton getOptionFour() throws FileNotFoundException, IOException {
		return new ChoiceButton("Option Four", turnProcess, 3, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
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
	protected TurnProcess getTurnProcess() {
		return turnProcess;
	}

}
