package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.actions.ActionRegion;
import watoydoEngine.designObjects.actions.KeyboardHandler;
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
	private static final double CHOICE_SCREEN_PERCENT = 0.8;
	
	protected ImageSingle textBackdrop;
	
	public GameChoiceMenu() {
		
		int screenWidth = ActivePane.getInstance().getWidth();
		
		try {
			textBackdrop = new ImageSingle(ImageIO.read(ReadWriter.getResourceAsInputStream("pinkRectangle.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		textBackdrop.setLocation(screenWidth/2 - 300 - 5, 0);
		textBackdrop.setVisible(false);
		textBackdrop.setAlpha(0.85f);
	}
	
	@Override
	public void load(Crowd crowd) throws FileNotFoundException, IOException {

		final God[] gods = getGods();

		final ImageSingle backgroundImage = new ImageSingle(getBackgroundImage());

		int screenWidth = ActivePane.getInstance().getWidth();
		int screenHeight = ActivePane.getInstance().getHeight();
		
		final Text storyText = new Text(screenWidth/2 - 280, 140);
		
		storyText.setText(getStartText());
		
		final TurnProcess turnProcess = getTurnProcess(storyText);
		
		final GodButton god1Image = getGodButton(turnProcess, gods, 0);
		final GodButton god2Image = getGodButton(turnProcess, gods, 1);
		final GodButton god3Image = getGodButton(turnProcess, gods, 2);
		final GodButton god4Image = getGodButton(turnProcess, gods, 3);
		
		final Text titleText = new Text(screenWidth/2 - 180, 60, getScenarioName());
		
		titleText.setColour(Color.black);
		storyText.setColour(Color.black);
		
		god1Image.setLocation(GOD_ICON_SPACING, GOD_ICON_SPACING);
		
		if(god2Image != null)
			god2Image.setLocation(screenWidth - god2Image.getImage().getWidth() - GOD_ICON_SPACING, GOD_ICON_SPACING);
		
		if(god3Image != null)
			god3Image.setLocation(GOD_ICON_SPACING, screenHeight - god3Image.getImage().getHeight() - GOD_ICON_SPACING);
		
		if(god4Image != null)
			god4Image.setLocation(screenWidth - god4Image.getImage().getWidth() - GOD_ICON_SPACING, screenHeight - god4Image.getImage().getHeight() - GOD_ICON_SPACING);
		
		final ChoiceButton optionOne = getOptionOne();
		final ChoiceButton optionTwo = getOptionTwo();
		final ChoiceButton optionThree = getOptionThree();
		final ChoiceButton optionFour = getOptionFour();
		
		KeyboardHandler handler = new KeyboardHandler() {
			
			@Override
			public boolean kD(KeyEvent e) {
				switch(e.getKeyCode()) {
					// Q
					case 81:
						turnProcess.setCurrentGod(gods[0]);
						if(turnProcess.applyChoice(0))
							optionOne.setVisible(false);
					break;
					// W
					case 87:
						turnProcess.setCurrentGod(gods[0]);
						if(turnProcess.applyChoice(1))
							optionTwo.setVisible(false);
					break;
					// E
					case 69:
						turnProcess.setCurrentGod(gods[0]);
						if(turnProcess.applyChoice(2))
							optionThree.setVisible(false);
					break;
					// R
					case 82:
						turnProcess.setCurrentGod(gods[0]);
						if(turnProcess.applyChoice(3))
							optionFour.setVisible(false);
					break;
					
					// Z
					case 90:
						turnProcess.setCurrentGod(gods[1]);
						if(turnProcess.applyChoice(0))
							optionOne.setVisible(false);
					break;
					// X
					case 88:
						turnProcess.setCurrentGod(gods[1]);
						if(turnProcess.applyChoice(1))
							optionTwo.setVisible(false);
					break;
					// C
					case 67:
						turnProcess.setCurrentGod(gods[1]);
						if(turnProcess.applyChoice(2))
							optionThree.setVisible(false);
					break;
					// V
					case 86:
						turnProcess.setCurrentGod(gods[1]);
						if(turnProcess.applyChoice(3))
							optionFour.setVisible(false);
					break;
					
					// O
					case 79:
						turnProcess.setCurrentGod(gods[2]);
						if(turnProcess.applyChoice(0))
							optionOne.setVisible(false);
					break;
					// P
					case 80:
						turnProcess.setCurrentGod(gods[2]);
						if(turnProcess.applyChoice(1))
							optionTwo.setVisible(false);
					break;
					// [
					case 91:
						turnProcess.setCurrentGod(gods[2]);
						if(turnProcess.applyChoice(2))
							optionThree.setVisible(false);
					break;
					// ]
					case 93:
						turnProcess.setCurrentGod(gods[2]);
						if(turnProcess.applyChoice(3))
							optionFour.setVisible(false);
					break;
					
					// M
					case 77:
						turnProcess.setCurrentGod(gods[3]);
						if(turnProcess.applyChoice(0))
							optionOne.setVisible(false);
					break;
					// ,
					case 44:
						turnProcess.setCurrentGod(gods[3]);
						if(turnProcess.applyChoice(1))
							optionTwo.setVisible(false);
					break;
					// .
					case 46:
						turnProcess.setCurrentGod(gods[3]);
						if(turnProcess.applyChoice(2))
							optionThree.setVisible(false);
					break;
					// /
					case 47:
						turnProcess.setCurrentGod(gods[3]);
						if(turnProcess.applyChoice(3))
							optionFour.setVisible(false);
					break;
				}
				return true;
			}
			
		};
		crowd.addKeyListener(handler);
		
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
		
		if(god2Image != null)
			god2Image.setAlpha(startAlpha);
		
		if(god3Image != null)
			god3Image.setAlpha(startAlpha);
		
		if(god4Image != null)
			god4Image.setAlpha(startAlpha);
		
		optionOne.setAlpha(startAlpha);
		optionTwo.setAlpha(startAlpha);
		optionThree.setAlpha(startAlpha);
		optionFour.setAlpha(startAlpha);
		
		titleText.setAlpha(startAlpha);
		
		crowd.addDisplayItem(backgroundImage);
		
		crowd.addButton(god1Image);
		
		if(god2Image != null)
			crowd.addButton(god2Image);
		
		if(god3Image != null)
			crowd.addButton(god3Image);
		
		if(god4Image != null)
			crowd.addButton(god4Image);

		crowd.addDisplayItem(textBackdrop);
		
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
				
//				titleText.setAlpha(titleAlpha);
				
				backgroundImage.setAlpha(titleAlpha);
			
				god1Image.setAlpha(god1Alpha);
				
				if(god2Image != null)
					god2Image.setAlpha(god2Alpha);
				
				if(god3Image != null)
					god3Image.setAlpha(god3Alpha);
				
				if(god4Image != null)
					god4Image.setAlpha(god4Alpha);
				
				optionOne.setAlpha(option1Alpha);
				optionTwo.setAlpha(option2Alpha);
				optionThree.setAlpha(option3Alpha);
				optionFour.setAlpha(option4Alpha);
				
//				storyText.setAlpha(option4Alpha);
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

					titleText.setAlpha(1f);
					storyText.setAlpha(1f);
					textBackdrop.setVisible(true);
					
					god1Image.setActive(true);
					
					if(god2Image != null)
						god2Image.setActive(true);
					
					if(god3Image != null)
						god3Image.setActive(true);
					
					if(god4Image != null)
						god4Image.setActive(true);
					
					optionOne.setActive(true);
					optionTwo.setActive(true);
					optionThree.setActive(true);
					optionFour.setActive(true);
				}
			}
			
		}, 0, 1);
	}
	
	private GodButton getGodButton(TurnProcess turnProcess, God[] gods, int index) {
		
		if(gods.length <= index)
			return null;
		
		return new GodButton(turnProcess, gods[index], getGodImage(gods[index]));
	}

	protected abstract TurnProcess getTurnProcess(Text storyText);

	protected abstract God[] getGods();

	protected abstract BufferedImage getBackgroundImage();

	protected abstract String getScenarioName();
	
	private BufferedImage getGodImage(God god) {
		
		String filename = "";
		
		switch(god) {
		case butter:
			filename = "butter.png";
			break;
		case debauchery:
			filename = "debauchery.png";
			break;
		case fire:
			filename = "fire.png";
			break;
		case motion:
			filename = "motion.png";
			break;
		case unselected:
			break;
		
		}
		
		try {
			BufferedImage read = ImageIO.read(ReadWriter.getResourceAsInputStream(filename));

			Image tmp = read.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		    BufferedImage dimg = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

		    Graphics2D g2d = dimg.createGraphics();
		    g2d.drawImage(tmp, 0, 0, null);
		    g2d.dispose();
		    
		    return dimg;
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public abstract String getStartText();
	
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
				this.setVisible(false);
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
