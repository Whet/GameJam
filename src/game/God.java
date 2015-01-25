package game;

public enum God {

	debauchery(0), butter(1), fire(2), motion(3), unselected(-1), chaosvegetables(4), watertreachery(5), chaos(6), vegetables(7), water(8), treachery(9);
	
	int code;
	
	God(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static God getGod(int godID) {
		for(God god:values()) {
			if(god.getCode() == godID)
				return god;
		}
		return null;
	}
	
}
