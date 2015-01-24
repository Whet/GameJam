package game;

import game.parser.Parser;

import java.io.File;
import java.util.HashSet;
import java.util.Set;


public class TurnProcess {

	private Set<God> takenTurns;
	
	private God currentGod;
	private int stage;
	private String story;
	private Parser parser;
	
	public TurnProcess(String file) {
		this.stage = 0;
		currentGod = God.unselected;
		
		takenTurns = new HashSet<God>();
		
		parser = new Parser(new File(file));
	}

	public God getCurrentGod() {
		return currentGod;
	}

	public boolean setCurrentGod(God currentGod) {
		
		if(!this.takenTurns.contains(currentGod)) {
			this.currentGod = currentGod;
			return true;
		}
		
		return false;
	}
	
	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public void applyChoice(int choiceNumber) {
		
		updateStory(choiceNumber);
		
		takenTurns.add(currentGod);
		
		// Or auto choose next god
		currentGod = God.unselected;
	}

	private void updateStory(int choiceNumber) {
		this.story = ""+stage;
		
		this.story = this.parser.getStoryLine(currentGod, choiceNumber);
	}

	public String getStory() {
		return story;
	}
	
}
