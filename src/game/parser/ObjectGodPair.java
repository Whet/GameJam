package game.parser;

import game.God;

public class ObjectGodPair {
	private int objectID;
	private God godID;
	
	public ObjectGodPair(int objectID, int godID) {
		this.objectID = objectID;
		this.godID = God.values()[godID];
	}
	
	public boolean equals(ObjectGodPair other) {
		if(!(other instanceof ObjectGodPair))
			return false;
		
		if(this.toString().equals(other.toString()))
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + godID.name() + " " + objectID + ")";
	}

	public int getObjectID() {
		return objectID;
	}

	public God getGodID() {
		return godID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}

	public void setGodID(God godID) {
		this.godID = godID;
	}
	
}
