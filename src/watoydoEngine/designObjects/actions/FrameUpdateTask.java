package watoydoEngine.designObjects.actions;

import java.util.TimerTask;

import watoydoEngine.workings.displayActivity.ActivePane;

public class FrameUpdateTask extends TimerTask{
	
	private double tweenTimer;
	private double frameTime;

	public FrameUpdateTask(double frameTime){
		tweenTimer = 0;
		
		this.frameTime = frameTime;
	}
	
	@Override
	public void run(){
		
		tweenTimer += frameTime;
		// the tween timer loops so that numbers don't get too big,'railway' tweens are therefore limited to 59 seconds but this is acceptable
		// to animate for longer than 59 seconds a 'free' tween can be used that just adds x,y every frame rather than trying to reach a destination
		if(tweenTimer > 59000){
			tweenTimer = 0;
		}
		
		// check for mouse location dependent events and repaint the screen
		ActivePane.getInstance().mouseEntered();
		ActivePane.getInstance().reloadDisplay();
	}
	
	public double getTime(){
		return tweenTimer;
	}
	
}
