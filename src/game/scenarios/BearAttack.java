package game.scenarios;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.display.Displayable;
import watoydoEngine.designObjects.display.Text;
import watoydoEngine.io.ReadWriter;
import watoydoEngine.workings.displayActivity.ActivePane;
import game.GameChoiceMenu;
import game.God;
import game.TurnProcess;
import game.GameChoiceMenu.ChoiceButton;

public class BearAttack extends GameChoiceMenu {
	
	private TurnProcess turnProcess;
	private ChoiceButton choiceButton1;
	private ChoiceButton choiceButton2;
	private ChoiceButton choiceButton3;
	private ChoiceButton choiceButton4;

	public BearAttack() {
		super();
		try{
			
			BufferedImage buttonUpImage = ImageIO.read(ReadWriter.getResourceAsInputStream("btnUp.png"));
			BufferedImage buttonDownImage = ImageIO.read(ReadWriter.getResourceAsInputStream("btnDown.png"));
			
		choiceButton1 = new ChoiceButton("Campfire", 0, new BufferedImage[]{buttonUpImage, buttonDownImage});
		choiceButton2 = new ChoiceButton("Bear", 1, new BufferedImage[]{buttonUpImage, buttonDownImage});
		choiceButton3 = new ChoiceButton("Tent", 2, new BufferedImage[]{buttonUpImage, buttonDownImage});
		choiceButton4 = new ChoiceButton("Wife", 3, new BufferedImage[]{buttonUpImage, buttonDownImage});
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		HashSet<Displayable> others = new HashSet<>();
		
		others.add(choiceButton1);
		others.add(choiceButton2);
		others.add(choiceButton3);
		others.add(choiceButton4);
		
		turnProcess = new TurnProcess(this.getGods(), textBackdrop, this.getGods().length, others, "bearsolutions.txt");
		choiceButton1.setTurnProcess(turnProcess);
		choiceButton2.setTurnProcess(turnProcess);
		choiceButton3.setTurnProcess(turnProcess);
		choiceButton4.setTurnProcess(turnProcess);
	}
	
	@Override
	public ChoiceButton getOptionOne() throws FileNotFoundException, IOException {
		return choiceButton1;
	}

	@Override
	public ChoiceButton getOptionTwo() throws FileNotFoundException, IOException {
		return choiceButton2;
	}

	@Override
	public ChoiceButton getOptionThree() throws FileNotFoundException, IOException {
		return choiceButton3;
	}

	@Override
	public ChoiceButton getOptionFour() throws FileNotFoundException, IOException {
		return choiceButton4;
	}

	@Override
	protected String getScenarioName() {
		return "Jerry Bear Scenario";
	}

	@Override
	protected BufferedImage getBackgroundImage() {
		try {
			BufferedImage read = ImageIO.read(ReadWriter.getResourceAsInputStream("campsite.png"));

			Image tmp = read.getScaledInstance(ActivePane.getInstance().getWidth(), ActivePane.getInstance().getHeight(), Image.SCALE_SMOOTH);
		    BufferedImage dimg = new BufferedImage(ActivePane.getInstance().getWidth(), ActivePane.getInstance().getHeight(), BufferedImage.TYPE_INT_ARGB);

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

	@Override
	protected God[] getGods() {
		return new God[]{God.chaosvegetables, God.watertreachery};
	}

	@Override
	protected TurnProcess getTurnProcess(Text storyText) {
		turnProcess.setText(storyText);
		return turnProcess;
	}

	@Override
	public String getStartText() {
		return "Needing a break away from the hustle and bustle of his busy life, Jerry and his wife are on a camping trip to celebrate their anniversary.@nUnfortunately for Jerry, a ravenous grizzly bear is slowly making its way towards his camp with hunger in its eyes.@n\"Oh god, my job as a holistic synergy officer didn't prepare me for this!\"@nDeperate for a solution, he searches his surroundings, then looks to the heavens for divine inspiration.";
	}

}
