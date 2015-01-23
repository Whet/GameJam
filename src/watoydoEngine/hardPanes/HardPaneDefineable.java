/*
 * 
 */
package watoydoEngine.hardPanes;

import java.io.FileNotFoundException;
import java.io.IOException;

import watoydoEngine.designObjects.display.Crowd;

// TODO: Auto-generated Javadoc
// Allows a class to set up a crowd in java code
/**
 * The Interface HardPaneDefineable.
 */
public interface HardPaneDefineable {
	
	/**
	 * Load.
	 *
	 * @param tag the tag
	 * @param crowd the crowd
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void load(Crowd crowd) throws FileNotFoundException, IOException;
}
