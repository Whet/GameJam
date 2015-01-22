/*
 * 
 */
package watoydoEngine.designObjects.actions;

import java.awt.event.KeyEvent;

// TODO: Auto-generated Javadoc
/**
 * The Interface KeyboardRespondable.
 */
public interface KeyboardRespondable{
	
	// The Void
	
	/**
	 * K d.
	 *
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean kD(KeyEvent e);
	
	/**
	 * K u.
	 *
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean kU(KeyEvent e);
	
	/**
	 * K p.
	 *
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean kP(KeyEvent e);
	
	// Getters
	/**
	 * Checks if is focused.
	 *
	 * @return true, if is focused
	 */
	public boolean isFocused();
	
	// Setters
	/**
	 * Sets the focus.
	 *
	 * @param active the new focus
	 */
	public void setFocus(boolean active);

	public int getActionZ();
	
}
