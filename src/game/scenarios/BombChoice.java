package game.scenarios;

import game.GameChoiceMenu;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.display.ButtonSingle;
import watoydoEngine.io.ReadWriter;

public class BombChoice extends GameChoiceMenu {

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

}
