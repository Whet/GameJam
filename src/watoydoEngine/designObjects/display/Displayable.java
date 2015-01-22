package watoydoEngine.designObjects.display;

import java.awt.Graphics2D;

import watoydoEngine.display.tweens.TweenDefinable;

public interface Displayable extends Comparable<Displayable>{

	public void move(double x, double y);

	public void kickTween();

	public void drawMethod(Graphics2D drawShape);
	
	public boolean isVisible();

	public double getScale();

	public double[] getLocation();

	public int getZ();
	
	public double[] getSize();
	
	@Override
	public int compareTo(Displayable otherDisplay);
	
	public void setVisible(boolean visible);
	
	public void setScale(double scale);
	
	public void setLocation(double x, double y);

	public void setZ(int z);

	public void setTween(TweenDefinable tween);
	
}
