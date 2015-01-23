package game;

import game.scenarios.BombChoice;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.display.ButtonSingle;
import watoydoEngine.designObjects.display.Crowd;
import watoydoEngine.designObjects.display.ImageSingle;
import watoydoEngine.hardPanes.HardPaneDefineable;
import watoydoEngine.io.ReadWriter;
import watoydoEngine.workings.displayActivity.ActivePane;

public class MainMenu implements HardPaneDefineable {

	@Override
	public void load(Crowd crowd) {
		
		BufferedImage titleImageFile = null;
		BufferedImage startButtonImage = null;
		BufferedImage bioButtonImage = null;
		BufferedImage exitButtonImage = null;
		
		try {
			titleImageFile = ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png"));
			startButtonImage = ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png"));
			bioButtonImage = ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png"));
			exitButtonImage = ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		ImageSingle titleImage = new ImageSingle(titleImageFile);
		
		ButtonSingle startGameButton = new ButtonSingle(startButtonImage) {
			
			@Override
			public boolean mD(java.awt.Point mousePosition, java.awt.event.MouseEvent e) {
				ActivePane.getInstance().changeRootCrowd(new Crowd(new BombChoice()));
				return true;
			};
			
		};
		
		ButtonSingle bioButton = new ButtonSingle(bioButtonImage) {
			
			@Override
			public boolean mD(java.awt.Point mousePosition, java.awt.event.MouseEvent e) {
				return true;
			};
			
		};
		
		ButtonSingle exitButton = new ButtonSingle(exitButtonImage) {
			
			@Override
			public boolean mD(java.awt.Point mousePosition, java.awt.event.MouseEvent e) {
				System.exit(0);
				return true;
			};
			
		};
		
		int width = ActivePane.getInstance().getWidth();
		int height = ActivePane.getInstance().getHeight();
		
		titleImage.setLocation(width / 2 - titleImage.getSize()[0] / 2, 40);
		
		int btnStart = 200;
		startGameButton.setLocation(40, btnStart + 80);
		bioButton.setLocation(40, btnStart + 140);
		exitButton.setLocation(40, btnStart + 200);
		
		crowd.addDisplayItem(titleImage);
		crowd.addButton(startGameButton);
		crowd.addButton(bioButton);
		crowd.addButton(exitButton);
	}

}
