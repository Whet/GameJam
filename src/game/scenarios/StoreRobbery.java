package game.scenarios;

import game.GameChoiceMenu;
import game.God;
import game.TurnProcess;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.display.ButtonSingle;
import watoydoEngine.io.ReadWriter;

public class StoreRobbery extends GameChoiceMenu {

	@Override
	public ButtonSingle getOptionOne() throws FileNotFoundException, IOException {
		return new ButtonSingle(ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png"))) {
		};
	}

	@Override
	public ButtonSingle getOptionTwo() throws FileNotFoundException, IOException {
		return new ButtonSingle(ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png"))) {
		};
	}

	@Override
	public ButtonSingle getOptionThree() throws FileNotFoundException, IOException {
		return new ButtonSingle(ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png"))) {
		};
	}

	@Override
	public ButtonSingle getOptionFour() throws FileNotFoundException, IOException {
		return new ButtonSingle(ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png"))) {
		};
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
		return new TurnProcess("scenarios/01");
	}

}
