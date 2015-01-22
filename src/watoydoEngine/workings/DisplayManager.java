package watoydoEngine.workings;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import watoydoEngine.workings.displayActivity.ActivePane;


public class DisplayManager{
	
	private static DisplayManager instance;
	
	public static DisplayManager getInstance(){
		if(instance == null){
			instance = new DisplayManager();
		}
		return instance;
	}
	
	private DisplayMode dm;
	private GraphicsDevice mainDevice;
	private int[] resolution;
	
	private DisplayManager(){
		
		resolution = new int[2];
	
		// sets mainDevice to contain the default screen
		mainDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		dm = mainDevice.getDisplayMode();
	}
	
	// Sets up active pane to be fullscreen/windowed at correct resolution
	public void setFrameToScreen(ActivePane window, int windowed, int[] resolution){
		
		this.resolution[0] = resolution[0];
		this.resolution[1] = resolution[1];
		
		dm = new DisplayMode(this.resolution[0],this.resolution[1],32,DisplayMode.REFRESH_RATE_UNKNOWN);
		
		if(windowed == 2 && mainDevice.isFullScreenSupported()){
				mainDevice.setFullScreenWindow(window);
				// Try and change settings
				if(mainDevice.isDisplayChangeSupported()){
					mainDevice.setDisplayMode(dm);
				}
				else{
					window.setMode(resolution, windowed);
				}
		}
		else{
			
			if(windowed == 2)
				window.setMode(resolution, 0);
			else
				window.setMode(resolution, windowed);
			
			window.setPreferredSize(new Dimension(resolution[0],resolution[1]));
			window.pack();
		}
	}
	
	// if not windowed will close fullscreen
	public void endDisplay(){
		if(!ActivePane.getInstance().isWindowed()){
			mainDevice.getFullScreenWindow().dispose();
			mainDevice.setFullScreenWindow(null);
		}
	}
	
	public int[] getResolution(){
		return this.resolution;
	}

}