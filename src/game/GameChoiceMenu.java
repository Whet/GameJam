package game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.display.ButtonSingle;
import watoydoEngine.designObjects.display.Crowd;
import watoydoEngine.designObjects.display.ImageSingle;
import watoydoEngine.designObjects.display.Text;
import watoydoEngine.hardPanes.HardPaneDefineable;
import watoydoEngine.io.ReadWriter;
import watoydoEngine.workings.displayActivity.ActivePane;

public abstract class GameChoiceMenu implements HardPaneDefineable {

	private static final int GOD_ICON_SPACING = 100;
	private static final int CHOICE_WIDTH_SPACING = 10;
	private static final int CHOICE_HEIGHT_SPACING = 60;
	private static final double CHOICE_SCREEN_PERCENT = 0.7;
	
	@Override
	public void load(Crowd crowd) throws FileNotFoundException, IOException {

		GodCollection godCollection = GodCollection.getInstance();
		
		final ImageSingle backgroundImage = new ImageSingle(getBackgroundImage());
		
		final ImageSingle god1Image = new ImageSingle(getGodImage(godCollection.getGod(0)));
		final ImageSingle god2Image = new ImageSingle(getGodImage(godCollection.getGod(1)));
		final ImageSingle god3Image = new ImageSingle(getGodImage(godCollection.getGod(2)));
		final ImageSingle god4Image = new ImageSingle(getGodImage(godCollection.getGod(3)));
		
		int screenWidth = ActivePane.getInstance().getWidth();
		int screenHeight = ActivePane.getInstance().getHeight();

		final Text titleText = new Text(screenWidth/2 - 200, 40, getScenarioName());

		titleText.setColour(Color.black);
		
		god1Image.setLocation(GOD_ICON_SPACING, GOD_ICON_SPACING);
		god2Image.setLocation(screenWidth - god2Image.getImage().getWidth() - GOD_ICON_SPACING, GOD_ICON_SPACING);
		god3Image.setLocation(GOD_ICON_SPACING, screenHeight - god3Image.getImage().getHeight() - GOD_ICON_SPACING);
		god4Image.setLocation(screenWidth - god4Image.getImage().getWidth() - GOD_ICON_SPACING, screenHeight - god4Image.getImage().getHeight() - GOD_ICON_SPACING);
		
		final ButtonSingle optionOne = getOptionOne();
		final ButtonSingle optionTwo = getOptionTwo();
		final ButtonSingle optionThree = getOptionThree();
		final ButtonSingle optionFour = getOptionFour();
		
		int screenMidX = screenWidth / 2;
		int buttonWidth = optionOne.getImage().getWidth();

		backgroundImage.setLocation(screenMidX - backgroundImage.getSize()[0] / 2, screenHeight/2 - backgroundImage.getSize()[1] / 2);
		
		optionOne.setLocation(screenMidX - buttonWidth - CHOICE_WIDTH_SPACING, screenHeight * CHOICE_SCREEN_PERCENT);
		optionTwo.setLocation(screenMidX + CHOICE_WIDTH_SPACING, screenHeight * CHOICE_SCREEN_PERCENT);
		optionThree.setLocation(screenMidX - buttonWidth - CHOICE_WIDTH_SPACING, screenHeight * CHOICE_SCREEN_PERCENT + CHOICE_HEIGHT_SPACING);
		optionFour.setLocation(screenMidX + CHOICE_WIDTH_SPACING, screenHeight * CHOICE_SCREEN_PERCENT + CHOICE_HEIGHT_SPACING);
		
		int startAlpha = 0;

		backgroundImage.setAlpha(startAlpha);
		
		god1Image.setAlpha(startAlpha);
		god2Image.setAlpha(startAlpha);
		god3Image.setAlpha(startAlpha);
		god4Image.setAlpha(startAlpha);
		
		optionOne.setAlpha(startAlpha);
		optionTwo.setAlpha(startAlpha);
		optionThree.setAlpha(startAlpha);
		optionFour.setAlpha(startAlpha);
		
		titleText.setAlpha(startAlpha);
		
		crowd.addDisplayItem(backgroundImage);
		
		crowd.addDisplayItem(god1Image);
		crowd.addDisplayItem(god2Image);
		crowd.addDisplayItem(god3Image);
		crowd.addDisplayItem(god4Image);
		
		crowd.addButton(optionOne);
		crowd.addButton(optionTwo);
		crowd.addButton(optionThree);
		crowd.addButton(optionFour);
		
		crowd.addDisplayItem(titleText);
		
		final Timer animationTimer = new Timer();
		
		animationTimer.scheduleAtFixedRate(new TimerTask() {
			
			private int milliseconds = 0;
			
			private float god1Alpha = 0;
			private float god2Alpha = 0;
			private float god3Alpha = 0;
			private float god4Alpha = 0;
			
			private float option1Alpha = 0;
			private float option2Alpha = 0;
			private float option3Alpha = 0;
			private float option4Alpha = 0;
			
			private float titleAlpha = 0;
			
			@Override
			public void run() {
				milliseconds++;
				computeAnimation();
				
				titleText.setAlpha(titleAlpha);
				
				backgroundImage.setAlpha(titleAlpha);
				
				god1Image.setAlpha(god1Alpha);
				god2Image.setAlpha(god2Alpha);
				god3Image.setAlpha(god3Alpha);
				god4Image.setAlpha(god4Alpha);
				
				optionOne.setAlpha(option1Alpha);
				optionTwo.setAlpha(option2Alpha);
				optionThree.setAlpha(option3Alpha);
				optionFour.setAlpha(option4Alpha);
			}

			private final float fadeInRate = 0.001f;
			
			private void computeAnimation() {
				if(milliseconds > 1000) {
					if(titleAlpha < 1)
						titleAlpha +=fadeInRate;
					if(titleAlpha > 1)
						titleAlpha = 1;
				}
				if(milliseconds > 3000) {
					if(god1Alpha < 1)
						god1Alpha +=fadeInRate;
					if(god1Alpha > 1)
						god1Alpha = 1;
				}
				if(milliseconds > 3500) {
					if(god2Alpha < 1)
						god2Alpha +=fadeInRate;
					if(god2Alpha > 1)
						god2Alpha = 1;
				}
				if(milliseconds > 4000) {
					if(god3Alpha < 1)
						god3Alpha +=fadeInRate;
					if(god3Alpha > 1)
						god3Alpha = 1;
				}
				if(milliseconds > 4500) {
					if(god4Alpha < 1)
						god4Alpha +=fadeInRate;
					if(god4Alpha > 1)
						god4Alpha = 1;
				}
				if(milliseconds > 5000) {
					if(option1Alpha < 1)
						option1Alpha +=fadeInRate;
					if(option1Alpha > 1)
						option1Alpha = 1;
				}
				if(milliseconds > 5200) {
					if(option2Alpha < 1)
						option2Alpha +=fadeInRate;
					if(option2Alpha > 1)
						option2Alpha = 1;
				}
				if(milliseconds > 5400) {
					if(option3Alpha < 1)
						option3Alpha +=fadeInRate;
					if(option3Alpha > 1)
						option3Alpha = 1;
				}
				if(milliseconds > 5600) {
					if(option4Alpha < 1)
						option4Alpha +=fadeInRate;
					if(option4Alpha > 1)
						option4Alpha = 1;
				}
				
				if(milliseconds > 10000)
					animationTimer.cancel();
			}
			
		}, 0, 1);
	}
	
	protected abstract BufferedImage getBackgroundImage();

	protected abstract String getScenarioName();
	
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
