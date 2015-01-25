package game;

import game.audio.AudioHandler;
import game.scenarios.StoreRobbery;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.display.ButtonMulti;
import watoydoEngine.designObjects.display.Crowd;
import watoydoEngine.designObjects.display.ImageSingle;
import watoydoEngine.designObjects.display.Text;
import watoydoEngine.hardPanes.HardPaneDefineable;
import watoydoEngine.io.ReadWriter;
import watoydoEngine.workings.displayActivity.ActivePane;

public class MainMenu implements HardPaneDefineable {
	
	private AudioHandler audioHandler;

	@Override
	public void load(Crowd crowd) {
		
		audioHandler = new AudioHandler();
		
		BufferedImage titleImageFile = null;
		BufferedImage buttonUpImage = null;
		BufferedImage buttonDownImage = null;
		
		ImageSingle background = null;
		
		try {
			titleImageFile = ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png"));
			buttonUpImage = ImageIO.read(ReadWriter.getResourceAsInputStream("btnUp.png"));
			buttonDownImage = ImageIO.read(ReadWriter.getResourceAsInputStream("btnDown.png"));
			background = new ImageSingle(ImageIO.read(ReadWriter.getResourceAsInputStream("mainmenu.png")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		ImageSingle titleImage = new ImageSingle(titleImageFile);
		
		ButtonMulti startGameButton = new ButtonMulti(new BufferedImage[]{buttonUpImage, buttonDownImage}) {
			
			Text text = new Text(this.getLocation()[0] + 20, this.getLocation()[1], "Start");

			{
				text.setAlpha(1);
				text.setColour(Color.black);
			}

			@Override
			public void drawMethod(Graphics2D drawShape) {
				super.drawMethod(drawShape);
				text.setLocation(this.getLocation()[0] + 22, this.getLocation()[1] + 32);
				text.drawMethod(drawShape);
			}
			
			@Override
			public boolean mD(java.awt.Point mousePosition, java.awt.event.MouseEvent e) {
				ActivePane.getInstance().changeRootCrowd(new Crowd(new StoreRobbery()));
				return true;
			};
			
		};
		
		ButtonMulti bioButton = new ButtonMulti(new BufferedImage[]{buttonUpImage, buttonDownImage}) {
			
			Text text = new Text(this.getLocation()[0] + 20, this.getLocation()[1], "Bios");

			{
				text.setAlpha(1);
				text.setColour(Color.black);
			}

			@Override
			public void drawMethod(Graphics2D drawShape) {
				super.drawMethod(drawShape);
				text.setLocation(this.getLocation()[0] + 22, this.getLocation()[1] + 32);
				text.drawMethod(drawShape);
			}
			
			@Override
			public boolean mD(java.awt.Point mousePosition, java.awt.event.MouseEvent e) {
				ActivePane.getInstance().changeRootCrowd(new Crowd(new Biography()));
				return true;
			};
			
		};
		
		ButtonMulti exitButton = new ButtonMulti(new BufferedImage[]{buttonUpImage, buttonDownImage}) {
			
			Text text = new Text(this.getLocation()[0] + 20, this.getLocation()[1], "Exit");

			{
				text.setAlpha(1);
				text.setColour(Color.black);
			}

			@Override
			public void drawMethod(Graphics2D drawShape) {
				super.drawMethod(drawShape);
				text.setLocation(this.getLocation()[0] + 22, this.getLocation()[1] + 32);
				text.drawMethod(drawShape);
			}
			
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
		crowd.addDisplayItem(background);
		crowd.addButton(startGameButton);
		crowd.addButton(bioButton);
		crowd.addButton(exitButton);
		
		//Switch with title announcement sound clip
		audioHandler.playAudio("audio/title.wav");
	}
}
