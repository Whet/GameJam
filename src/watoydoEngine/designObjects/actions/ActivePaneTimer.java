/*
 * 
 */
package watoydoEngine.designObjects.actions;

import java.util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class ActivePaneTimer.
 */
public class ActivePaneTimer {
	
	/**
	 * The redraw rate.
	 */
	public static int redrawRate = 16;
	
	/**
	 * The instance.
	 */
	private static ActivePaneTimer instance;
	
	/**
	 * Gets the single instance of ActivePaneTimer.
	 *
	 * @return single instance of ActivePaneTimer
	 */
	public static ActivePaneTimer getInstance(){
		if(instance == null){
			instance = new ActivePaneTimer(redrawRate);
		}
		return instance;
	}
	
	/**
	 * The frame time.
	 */
	private int frameTime;
	
	/**
	 * The upd timer.
	 */
	private Timer updTimer;
	
	/**
	 * The playing.
	 */
	private boolean playing;
	
	/**
	 * The frame upd.
	 */
	private FrameUpdateTask frameUpd;
	
	/**
	 * Instantiates a new active pane timer.
	 *
	 * @param frameTime the frame time
	 */
	private ActivePaneTimer(int frameTime){
		
		this.frameTime = frameTime;
		
		frameUpd = new FrameUpdateTask(this.frameTime);
		
		updTimer = new Timer();
		
		playing = false;
	}
	
	// The Void
	/**
	 * Start.
	 */
	public void start(){
		if(!playing){
			updTimer.scheduleAtFixedRate(frameUpd, 0, frameTime);
			playing = true;
		}
	}
	
	// Getters
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public double getTime(){
		return this.frameUpd.getTime();
	}
	
	/**
	 * Gets the duration.
	 *
	 * @param startTime the start time
	 * @return the duration
	 */
	public double getDuration(double startTime){
		// the the main timer has looped
		if(this.getTime() < startTime){
			// timer goes 0-59
			return ((59000 - startTime) + this.getTime());
		}
		return this.getTime() - startTime;
	}

	/**
	 * Checks if is playing.
	 *
	 * @return true, if is playing
	 */
	public boolean isPlaying() {
		return playing;
	}
	
	
}
