package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.actions.ActionRegion;
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

		God[] gods = getGods();
		
		final ImageSingle backgroundImage = new ImageSingle(getBackgroundImage());

		int screenWidth = ActivePane.getInstance().getWidth();
		int screenHeight = ActivePane.getInstance().getHeight();
		
		final Text storyText = new Text(screenWidth/2 - 200, 200);
		final TurnProcess turnProcess = getTurnProcess(storyText);
		
		final GodButton god1Image = new GodButton(turnProcess, gods[0], getGodImage(gods[0]));
		final GodButton god2Image = new GodButton(turnProcess, gods[1], getGodImage(gods[1]));
		final GodButton god3Image = new GodButton(turnProcess, gods[2], getGodImage(gods[2]));
		final GodButton god4Image = new GodButton(turnProcess, gods[3], getGodImage(gods[3]));
		
		final Text titleText = new Text(screenWidth/2 - 200, 40, getScenarioName());
		
		titleText.setColour(Color.black);
		storyText.setColour(Color.black);
		
		god1Image.setLocation(GOD_ICON_SPACING, GOD_ICON_SPACING);
		god2Image.setLocation(screenWidth - god2Image.getImage().getWidth() - GOD_ICON_SPACING, GOD_ICON_SPACING);
		god3Image.setLocation(GOD_ICON_SPACING, screenHeight - god3Image.getImage().getHeight() - GOD_ICON_SPACING);
		god4Image.setLocation(screenWidth - god4Image.getImage().getWidth() - GOD_ICON_SPACING, screenHeight - god4Image.getImage().getHeight() - GOD_ICON_SPACING);
		
		final ChoiceButton optionOne = getOptionOne();
		final ChoiceButton optionTwo = getOptionTwo();
		final ChoiceButton optionThree = getOptionThree();
		final ChoiceButton optionFour = getOptionFour();
		
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
		
		crowd.addButton(god1Image);
		crowd.addButton(god2Image);
		crowd.addButton(god3Image);
		crowd.addButton(god4Image);
		
		crowd.addButton(optionOne);
		crowd.addButton(optionTwo);
		crowd.addButton(optionThree);
		crowd.addButton(optionFour);
		
		crowd.addDisplayItem(titleText);
		crowd.addDisplayItem(storyText);
		
		crowd.addMouseActionItem(new ActionRegion(0,0,0,0) {
			
			
			@Override
			public boolean isActive() {
				return turnProcess.isChoicesMade();
			}
			
			@Override
			public boolean isInBounds(double x, double y) {
				return true;
			}
			
			@Override
			public boolean mD(Point mousePosition, MouseEvent e) {
				turnProcess.incrementStage();
				return true;
			}
			
		});
		
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
				
				storyText.setAlpha(option4Alpha);
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
				
				if(optionFour.getAlpha() >= 1) {
					animationTimer.cancel();
					
					god1Image.setActive(true);
					god2Image.setActive(true);
					god3Image.setActive(true);
					god4Image.setActive(true);
					
					optionOne.setActive(true);
					optionTwo.setActive(true);
					optionThree.setActive(true);
					optionFour.setActive(true);
				}
			}
			
		}, 0, 1);
	}
	
	protected abstract TurnProcess getTurnProcess(Text storyText);

	protected abstract God[] getGods();

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

	public abstract ChoiceButton getOptionOne() throws FileNotFoundException, IOException;
	
	public abstract ChoiceButton getOptionTwo() throws FileNotFoundException, IOException;
	
	public abstract ChoiceButton getOptionThree() throws FileNotFoundException, IOException;
	
	public abstract ChoiceButton getOptionFour() throws FileNotFoundException, IOException;

	private static class GodButton extends ButtonSingle {

		private TurnProcess turnProcess;
		private God god;
		
		public GodButton(TurnProcess turnProcess, God god, BufferedImage image) {
			super(image);
			this.god = god;
			this.turnProcess = turnProcess;
		}
		
		@Override
		public boolean mD(Point mousePosition, MouseEvent e) {
			if(turnProcess.setCurrentGod(god)) {
				System.out.println("CURRENT GOD " + god.toString());
			}
			else {
				System.out.println("ALREADY CHOSE " + god.toString());
			}
			return true;
		}
		
		@Override
		public void mI(Point mousePosition) {
			this.setScale(1.2);
		}
		
		@Override
		public void mO(Point mousePosition) {
			this.setScale(1);
		}
	}
	
	public static class ChoiceButton extends ButtonSingle {
		
		private Text text;
		private TurnProcess turnProcess;
		private int choice;
		
		public ChoiceButton(String text, int choice, BufferedImage image) {
			super(image);
			this.choice = choice;
			this.text = new Text(this.getLocation()[0] + 5, this.getLocation()[1] + this.getSize()[1] - 10, text);
			this.text.setColour(Color.black);
		}
		
		@Override
		public void drawMethod(Graphics2D drawShape) {
			super.drawMethod(drawShape);
			text.setAlpha(this.getAlpha());
			text.drawMethod(drawShape);
		}
		
		@Override
		public boolean mD(Point mousePosition, MouseEvent e) {
			if(turnProcess.applyChoice(choice)) {
				this.setActive(false);
			}
			return true;
		}
		
		@Override
		public void setLocation(double x, double y) {
			super.setLocation(x, y);
			if(text != null)
				this.text.setLocation(x + 5, y + this.getSize()[1] - 10);
		}

		public void setTurnProcess(TurnProcess turnProcess) {
			this.turnProcess = turnProcess;
		}
		
	}
	
}
