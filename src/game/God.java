package game;

public enum God {

	butter(-1), debauchery(1), fire(2), motion(3), unselected(0);
	
	int code;
	
	God(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
}
