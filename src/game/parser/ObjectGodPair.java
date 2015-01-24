package game.parser;

import game.God;

public class ObjectGodPair {
	public int objectID;
	public God godID;
	public ObjectGodPair(int objectID, int godID) {
		this.objectID = objectID;
		this.godID = God.values()[godID];
	}
	
	public boolean equals(ObjectGodPair other) {
		if(!(other instanceof ObjectGodPair))
			return false;
		
		ObjectGodPair otherPair = (ObjectGodPair) other;
		
		if(this.objectID == otherPair.objectID && this.godID.equals(otherPair.godID))
			return true;
		
		return false;
	}
	
	public String toString() {
		return "(" + godID.name() + " " + objectID + ")";
	}
}
