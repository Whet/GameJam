package game;

import java.io.File;

public class TurnProcess {

	private File file;
	private God currentGod;
	private int stage;
	private String story;
	
	public TurnProcess(String file) {
		this.file = new File(file);
		this.stage = 0;
		currentGod = God.unselected;
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
	}

	public String getStory() {
		return story;
	}
	
}
