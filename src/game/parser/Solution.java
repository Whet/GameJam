package game.parser;

import java.util.ArrayList;

public class Solution {
	private String story;
	private ArrayList<ObjectGodPair> pairs;
	private String scenarioName;
	
	public Solution(String scenarioName, String story, ArrayList<ObjectGodPair> pairs) {
		this.story = story;
		this.pairs = pairs;
		this.scenarioName = scenarioName;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public String getStory() {
		return story;
	}

	public ArrayList<ObjectGodPair> getPairs() {
		return pairs;
	}
}
