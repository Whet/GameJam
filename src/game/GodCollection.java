package game;

public class GodCollection {

	private static GodCollection instance;
	
	public static GodCollection getInstance() {
		if(instance == null) {
			instance = new GodCollection();
		}
		return instance;
	}

	public God getGod(int godNumber) {
		return null;
	}
	
}
