package game;

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

public abstract class GameChoiceMenu implements HardPaneDefineable {

	private static final int GOD_ICON_SPACING = 100;
	private static final int CHOICE_WIDTH_SPACING = 10;
	private static final int CHOICE_HEIGHT_SPACING = 60;
	private static final double CHOICE_SCREEN_PERCENT = 0.7;
	
	@Override
	public void load(Crowd crowd) {

		GodCollection godCollection = GodCollection.getInstance();
		
		ImageSingle god1Image = new ImageSingle(getGodImage(godCollection.getGod(0)));
		ImageSingle god2Image = new ImageSingle(getGodImage(godCollection.getGod(1)));
		ImageSingle god3Image = new ImageSingle(getGodImage(godCollection.getGod(2)));
		ImageSingle god4Image = new ImageSingle(getGodImage(godCollection.getGod(3)));
		
		int screenWidth = ActivePane.getInstance().getWidth();
		int screenHeight = ActivePane.getInstance().getHeight();
		
		god1Image.setLocation(GOD_ICON_SPACING, GOD_ICON_SPACING);
		god2Image.setLocation(screenWidth - god2Image.getImage().getWidth() - GOD_ICON_SPACING, GOD_ICON_SPACING);
		god3Image.setLocation(GOD_ICON_SPACING, screenHeight - god3Image.getImage().getHeight() - GOD_ICON_SPACING);
		god4Image.setLocation(screenWidth - god4Image.getImage().getWidth() - GOD_ICON_SPACING, screenHeight - god4Image.getImage().getHeight() - GOD_ICON_SPACING);
		
		ButtonSingle optionOne = null;
		ButtonSingle optionTwo = null;
		ButtonSingle optionThree = null;
		ButtonSingle optionFour = null;
		
		try{
			optionOne = getOptionOne();
			optionTwo = getOptionTwo();
			optionThree = getOptionThree();
			optionFour = getOptionFour();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		int screenMidX = screenWidth / 2;
		int buttonWidth = optionOne.getImage().getWidth();
		
		optionOne.setLocation(screenMidX - buttonWidth - CHOICE_WIDTH_SPACING, screenHeight * CHOICE_SCREEN_PERCENT);
		optionTwo.setLocation(screenMidX + CHOICE_WIDTH_SPACING, screenHeight * CHOICE_SCREEN_PERCENT);
		optionThree.setLocation(screenMidX - buttonWidth - CHOICE_WIDTH_SPACING, screenHeight * CHOICE_SCREEN_PERCENT + CHOICE_HEIGHT_SPACING);
		optionFour.setLocation(screenMidX + CHOICE_WIDTH_SPACING, screenHeight * CHOICE_SCREEN_PERCENT + CHOICE_HEIGHT_SPACING);
		
		crowd.addDisplayItem(god1Image);
		crowd.addDisplayItem(god2Image);
		crowd.addDisplayItem(god3Image);
		crowd.addDisplayItem(god4Image);
		
		crowd.addButton(optionOne);
		crowd.addButton(optionTwo);
		crowd.addButton(optionThree);
		crowd.addButton(optionFour);
		
	}
	
	private BufferedImage getGodImage(God god) {
		try {
			return ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public abstract ButtonSingle getOptionOne() throws FileNotFoundException, IOException;
	
	public abstract ButtonSingle getOptionTwo() throws FileNotFoundException, IOException;
	
	public abstract ButtonSingle getOptionThree() throws FileNotFoundException, IOException;
	
	public abstract ButtonSingle getOptionFour() throws FileNotFoundException, IOException;

}
