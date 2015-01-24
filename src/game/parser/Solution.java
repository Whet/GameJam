package game.parser;

public class Solution {
	private String story;
	private IdHolder[] pairs;
	private String scenarioName;
	
	public Solution(String scenarioName, String story, IdHolder[] pairs) {
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

	public IdHolder[] getPairs() {
		return pairs;
	}
}
