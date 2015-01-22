/*
 * 
 */
package watoydoEngine.designObjects.actions;

import java.awt.Point;
import java.awt.event.MouseEvent;

// TODO: Auto-generated Javadoc
/**
 * The Interface MouseRespondable.
 */
public interface MouseRespondable {
	
	// The Void
	
	/**
	 * M d.
	 *
	 * @param mousePosition the mouse position
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean mD(Point mousePosition, MouseEvent e);
	
	/**
	 * M u.
	 *
	 * @param mousePosition the mouse position
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean mU(Point mousePosition, MouseEvent e);
	
	/**
	 * M c.
	 *
	 * @param mousePosition the mouse position
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean mC(Point mousePosition, MouseEvent e);
	
	/**
	 * R md.
	 *
	 * @param mousePosition the mouse position
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean rMD(Point mousePosition, MouseEvent e);
	
	/**
	 * R mu.
	 *
	 * @param mousePosition the mouse position
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean rMU(Point mousePosition, MouseEvent e);
	
	/**
	 * R mc.
	 *
	 * @param mousePosition the mouse position
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean rMC(Point mousePosition, MouseEvent e);
	
	/**
	 * M mc.
	 *
	 * @param mousePosition the mouse position
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean mMC(Point mousePosition, MouseEvent e);
	
	/**
	 * M i.
	 *
	 * @param mousePosition the mouse position
	 */
	public void mI(Point mousePosition);
	
	/**
	 * M o.
	 *
	 * @param mousePosition the mouse position
	 */
	public void mO(Point mousePosition);
	
	// Getters
	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive();
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public double[] getLocation();
	
	/**
	 * Checks if is in bounds.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if is in bounds
	 */
	public boolean isInBounds(double x, double y);

	// Setters
	/**
	 * Sets the location.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setLocation(double x, double y);
	
	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(boolean active);
	
	/**
	 * Gets the action z.
	 *
	 * @return the action z
	 */
	public int getActionZ();
	
	/**
	 * Sets the action z.
	 *
	 * @param actionZ the new action z
	 */
	public void setActionZ(int actionZ);
}
