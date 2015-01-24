package game.scenarios;

import game.GameChoiceMenu;
import game.God;
import game.TurnProcess;

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

public class StoreRobbery extends GameChoiceMenu {

	private TurnProcess turnProcess;
	private ChoiceButton choiceButton1;
	private ChoiceButton choiceButton2;
	private ChoiceButton choiceButton3;
	private ChoiceButton choiceButton4;

	public StoreRobbery() {
		super();
		try{
		choiceButton1 = new ChoiceButton("Trolley", 3, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
		choiceButton2 = new ChoiceButton("Robber", 0, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
		choiceButton3 = new ChoiceButton("Clerk", 2, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
		choiceButton4 = new ChoiceButton("Alchohol", 1, ImageIO.read(ReadWriter.getResourceAsInputStream("buttonPlaceholder.png")));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		HashSet<Displayable> others = new HashSet<>();
		
		others.add(choiceButton1);
		others.add(choiceButton2);
		others.add(choiceButton3);
		others.add(choiceButton4);
		
		turnProcess = new TurnProcess(backgroundText, this.getGods().length, others, "solutions.txt");
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
		return "Sandra Store Scenario";
	}

	@Override
	protected BufferedImage getBackgroundImage() {
		try {
			BufferedImage read = ImageIO.read(ReadWriter.getResourceAsInputStream("storeRobbery.png"));

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
		return new God[]{God.butter, God.debauchery, God.fire, God.motion};
	}

	@Override
	protected TurnProcess getTurnProcess(Text storyText) {
		turnProcess.setText(storyText);
		return turnProcess;
	}

	@Override
	public String getStartText() {
		return "Sandra is an average person.@nShe has lived an average life, of little significance.@nHowever,one night, when Sandra is shopping at her local convenience store, her life is changed forever. @nThe store is empty, but for a sole clerk shifting restlessly behind the counter.@nAs she makes her way to the checkout to pay, an armed robber bursts into the store.@nFrozen in place, she can't help but think to herself �Oh God, Oh God. Why me? God help me!�.@nWell... sometimes you ought to be careful what you ask for...";
	}

}
