package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.actions.ActionRegion;
import watoydoEngine.designObjects.display.ButtonMulti;
import watoydoEngine.designObjects.display.ButtonSingle;
import watoydoEngine.designObjects.display.Crowd;
import watoydoEngine.designObjects.display.ImageSingle;
import watoydoEngine.designObjects.display.Text;
import watoydoEngine.display.tweens.MotionTween;
import watoydoEngine.hardPanes.HardPaneDefineable;
import watoydoEngine.io.ReadWriter;
import watoydoEngine.utils.Maths;
import watoydoEngine.workings.displayActivity.ActivePane;

public abstract class GameChoiceMenu implements HardPaneDefineable {

	private static final int CHOICE_WIDTH_SPACING = 10;
	private static final int CHOICE_HEIGHT_SPACING = 60;
	private static final double CHOICE_SCREEN_PERCENT = 0.8;
	
	protected ImageSingle textBackdrop;
	
	public int choicesMade = 0;
	
	GodButton god1Image;
	GodButton god2Image;
	GodButton god3Image;
	GodButton god4Image;
	
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
		textBackdrop.setVisible(true);
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
		this.god1Image = god1Image;
		final GodButton god2Image = getGodButton(turnProcess, gods, 1);
		this.god2Image = god2Image;
		final GodButton god3Image = getGodButton(turnProcess, gods, 2);
		this.god3Image = god3Image;
		final GodButton god4Image = getGodButton(turnProcess, gods, 3);
		this.god4Image = god4Image;
		
		List<GodButton> godImages = new ArrayList<>();
		godImages.add(god1Image);
		godImages.add(god2Image);
		godImages.add(god3Image);
		godImages.add(god4Image);
		
		turnProcess.setImages(godImages);
		
//		final Text titleText = new Text(screenWidth/2 - 180, 60, getScenarioName());
		
//		titleText.setColour(Color.black);
		storyText.setColour(Color.black);
		
		god1Image.setLocation(screenWidth / 2 - god1Image.getSize()[0] / 2, screenHeight / 2 - god1Image.getSize()[1] / 2);
		
		if(god2Image != null)
			god2Image.setLocation(screenWidth / 2 - god1Image.getSize()[0] / 2, screenHeight / 2 - god1Image.getSize()[1] / 2);
		
		if(god3Image != null)
			god3Image.setLocation(screenWidth / 2 - god1Image.getSize()[0] / 2, screenHeight / 2 - god1Image.getSize()[1] / 2);
		
		if(god4Image != null)
			god4Image.setLocation(screenWidth / 2 - god1Image.getSize()[0] / 2, screenHeight / 2 - god1Image.getSize()[1] / 2);
		
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
		
//		titleText.setAlpha(startAlpha);
		
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
		
//		crowd.addDisplayItem(titleText);
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
		
		final ActionRegion advanceAnimation = new ActionRegion(0,0,0,0) {
			
			@Override
			public boolean isInBounds(double x, double y) {
				return true;
			}
			
			@Override
			public boolean mD(Point mousePosition, MouseEvent e) {
			
				this.setActive(false);
				
				storyText.setVisible(false);
				textBackdrop.setVisible(false);
				
				final Timer god1Timer = new Timer();
				god1Timer.schedule(new TimerTask() {

					private int milliseconds = 0;
					private float godAlpha = 0f;
					private float godIconAlpha = 0f;
					private float godScale = 1f;
					
					@Override
					public void run() {
						milliseconds++;
						computeAnimation();
						
						god1Image.setAlpha(godAlpha);
						god1Image.setScale(godScale);
					}

					private void computeAnimation() {
						if(milliseconds == 2000)
							god1Image.setTween(new MotionTween(god1Image, -ActivePane.getInstance().getWidth() / 100 + 50, +50, 10000, true));
						
						if(milliseconds > 2000)
							godScale -= 0.001;
						
						if(godScale <= 0.3) {
							godScale = 0.3f;
						}
						
						if(Maths.getDistance(god1Image.getLocation()[0], god1Image.getLocation()[1], 38, 43) < 10) {
							god1Image.setImage(getGodIcon(gods[0]));
							god1Timer.cancel();
							startTimer2();
						}
						else {
							if(godAlpha < 1)
								godAlpha += 0.1;
							
							if(godAlpha > 1)
								godAlpha = 1;
						}
					}
					
				}, 0, 1);
				return true;
			}
			
			public void startTimer2() {
				final Timer godtimer = new Timer();
				godtimer.schedule(new TimerTask() {

					private int milliseconds = 0;
					private float godAlpha = 0f;
					private float godScale = 1f;
					
					@Override
					public void run() {
						milliseconds++;
						computeAnimation();
						
						god2Image.setAlpha(godAlpha);
						god2Image.setScale(godScale);
					}

					private void computeAnimation() {
						if(milliseconds == 2000)
							god2Image.setTween(new MotionTween(god2Image, +ActivePane.getInstance().getWidth()- 200, +50, 10000, true));
						
						if(milliseconds > 2000)
							godScale -= 0.001;
						
						if(godScale <= 0.3) {
							godScale = 0.3f;
						}
						
						if(Maths.getDistance(god2Image.getLocation()[0], god2Image.getLocation()[1], ActivePane.getInstance().getWidth()- 200, 43) < 10) {
							god2Image.setImage(getGodIcon(gods[1]));
//							god2Image.setActive(true);
							godtimer.cancel();
							startTimer3();
						}
						else {
							if(godAlpha < 1)
								godAlpha += 0.1;
							
							if(godAlpha > 1)
								godAlpha = 1;
						}
					}
					
				}, 0, 1);
			}
			
			public void startTimer3() {
				
				if(gods.length == 2) {
					showOptions();
					return;
				}
				
				final Timer godtimer = new Timer();
				godtimer.schedule(new TimerTask() {

					private int milliseconds = 0;
					private float godAlpha = 0f;
					private float godScale = 1f;
					
					@Override
					public void run() {
						milliseconds++;
						computeAnimation();
						
						god3Image.setAlpha(godAlpha);
						god3Image.setScale(godScale);
					}

					private void computeAnimation() {
						if(milliseconds == 2000)
							god3Image.setTween(new MotionTween(god3Image, -ActivePane.getInstance().getWidth() / 100 + 50, ActivePane.getInstance().getHeight() - 180, 10000, true));
						
						if(milliseconds > 2000)
							godScale -= 0.001;
						
						if(godScale <= 0.3) {
							godScale = 0.3f;
						}
						
						if(Maths.getDistance(god3Image.getLocation()[0], god3Image.getLocation()[1], 38,540) < 10) {
							god3Image.setImage(getGodIcon(gods[2]));
//							god3Image.setActive(true);
							godtimer.cancel();
							startTimer4();
						}
						else {
							if(godAlpha < 1)
								godAlpha += 0.1;
							
							if(godAlpha > 1)
								godAlpha = 1;
						}
					}
					
				}, 0, 1);
			}
			
			public void startTimer4() {
				
				if(gods.length == 3) {
					showOptions();
					return;
				}
				
				final Timer godtimer = new Timer();
				godtimer.schedule(new TimerTask() {

					private int milliseconds = 0;
					private float godAlpha = 0f;
					private float godScale = 1f;
					
					@Override
					public void run() {
						milliseconds++;
						computeAnimation();
						
						god4Image.setAlpha(godAlpha);
						god4Image.setScale(godScale);
					}

					private void computeAnimation() {
						if(milliseconds == 2000)
							god4Image.setTween(new MotionTween(god4Image, +ActivePane.getInstance().getWidth()- 200, ActivePane.getInstance().getHeight() - 180, 10000, true));
						
						if(milliseconds > 2000)
							godScale -= 0.001;
						
						if(godScale <= 0.3) {
							godScale = 0.3f;
						}
						
						if(Maths.getDistance(god4Image.getLocation()[0], god4Image.getLocation()[1], 1080, 540) < 10) {
							god4Image.setImage(getGodIcon(gods[3]));
//							god4Image.setActive(true);
							godtimer.cancel();
							showOptions();
						}
						else {
							if(godAlpha < 1)
								godAlpha += 0.1;
							
							if(godAlpha > 1)
								godAlpha = 1;
						}
					}
					
				}, 0, 1);
			}
			
			private void showOptions() {
				
				god1Image.setScale(0.4);
				
				System.out.println("SHOWING OPTIONS");
				final Timer animationTimer = new Timer();
				
				animationTimer.scheduleAtFixedRate(new TimerTask() {
					
					private int milliseconds = 0;
					
					private float optionAlpha = 0;
					
					@Override
					public void run() {
						milliseconds++;
						computeAnimation();
						
						optionOne.setAlpha(optionAlpha);
						optionTwo.setAlpha(optionAlpha);
						optionThree.setAlpha(optionAlpha);
						optionFour.setAlpha(optionAlpha);
						
					}

					private final float fadeInRate = 0.001f;
					
					private void computeAnimation() {
						if(milliseconds > 1000) {
							if(optionAlpha < 1)
								optionAlpha +=fadeInRate;
							if(optionAlpha > 1)
								optionAlpha = 1;
							
							optionAlpha += 0.0008f;
						}
						
						if(optionAlpha >= 1) {
							animationTimer.cancel();
//							titleText.setAlpha(1f);
//							titleText.setVisible(true);
							storyText.setAlpha(1f);
//							storyText.setVisible(true);
						}
					}
					
				}, 0, 1);
			}
			
		};
		advanceAnimation.setActive(false);
		crowd.addMouseActionItem(advanceAnimation);
		
		final Timer animationTimer = new Timer();
		
		animationTimer.scheduleAtFixedRate(new TimerTask() {
			
			private int milliseconds = 0;
			
			private float titleAlpha = 0;
			private float backdropAlpha = 0;
			
			@Override
			public void run() {
				milliseconds++;
				computeAnimation();
				
//				titleText.setAlpha(titleAlpha);
				backgroundImage.setAlpha(titleAlpha);
				storyText.setAlpha(titleAlpha);
				textBackdrop.setAlpha(backdropAlpha);
			}

			private final float fadeInRate = 0.001f;
			
			private void computeAnimation() {
				if(milliseconds > 1000) {
					if(titleAlpha < 1)
						titleAlpha +=fadeInRate;
					if(titleAlpha > 1)
						titleAlpha = 1;
					
					backdropAlpha += 0.0008f;
				}
				
				if(storyText.getAlpha() >= 1) {
					animationTimer.cancel();
					storyText.setAlpha(1f);
					advanceAnimation.setActive(true);
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

			Image tmp = read.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
		    BufferedImage dimg = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);

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
	
	private BufferedImage getGodIcon(God god) {
		
		String filename = "";
		
		switch(god) {
		case butter:
			filename = "butterIcon.png";
			break;
		case debauchery:
			filename = "debaucheryIcon.png";
			break;
		case fire:
			filename = "fireIcon.png";
			break;
		case motion:
			filename = "motionIcon.png";
			break;
		case unselected:
			break;
		
		}
		
		try {
			BufferedImage read = ImageIO.read(ReadWriter.getResourceAsInputStream(filename));

		    return read;
		
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
	
	public static class GodButton extends ButtonSingle {

		private TurnProcess turnProcess;
		private God god;
		
		public GodButton(TurnProcess turnProcess, God god, BufferedImage image) {
			super(image);
			this.god = god;
			this.turnProcess = turnProcess;
			this.setActive(false);
		}
		
		@Override
		public boolean mD(Point mousePosition, MouseEvent e) {
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
	
	public static class ChoiceButton extends ButtonMulti {
		
		private Text text;
		private TurnProcess turnProcess;
		private int choice;
		
		public ChoiceButton(String text, int choice, BufferedImage[] image) {
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
