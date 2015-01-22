/*
 * 
 */
package watoydoEngine.display.tweens;

// TODO: Auto-generated Javadoc
// Allows a class to describe a tween
/**
 * The Interface TweenDefinable.
 */
public interface TweenDefinable {
	
	/**
	 * Stop.
	 */
	public void stop();
	
	/**
	 * Start.
	 */
	public void start();
	
	/**
	 * Checks if is ended.
	 *
	 * @return true, if is ended
	 */
	public boolean isEnded();
	// Gets where the object should be at this point in time
	/**
	 * Gets the cord.
	 *
	 * @param parentPos the parent pos
	 * @return the cord
	 */
	public double[] getCord(double[] parentPos);
	
	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public double getDuration();
	
	// Allows tween control in motion
	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(double duration);
}
