package game;

public enum God {

	butter(0), debauchery(1), fire(2), motion(3), unselected(-1);
	
	int code;
	
	God(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
}
