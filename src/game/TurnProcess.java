package game;

import game.parser.Parser;

import java.io.File;


public class TurnProcess {

	private God currentGod;
	private int stage;
	private String story;
	private Parser parser;
	
	public TurnProcess(String file) {
		this.stage = 0;
		currentGod = God.unselected;
		
		parser = new Parser(new File(file));
	}

	public God getCurrentGod() {
		return currentGod;
	}

	public void setCurrentGod(God currentGod) {
		this.currentGod = currentGod;
	}
	
	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public void applyChoice(God god) {
		
		// Assign god to choice number
		int choiceNumber = 0;
		
		updateStory(choiceNumber);
	}

	private void updateStory(int choiceNumber) {
		this.story = ""+stage;
		
		this.story = this.parser.getStoryLine(currentGod, choiceNumber);
	}

	public String getStory() {
		return story;
	}
	
}
