package watoydoEngine.designObjects.display;

import java.awt.geom.AffineTransform;

import watoydoEngine.display.tweens.TweenDefinable;

public abstract class AbstractImage implements ImageDisplayable{
	
	private double[] location;
	private boolean visible;
	private double scale;
	private AffineTransform transformation;
	private TweenDefinable tween;
	private int z;
	private float alpha;
	
	public AbstractImage(){
		this.scale = 1;
		this.transformation = new AffineTransform();
		
		this.visible = true;
		
		this.location = new double[2];
		this.setLocation(0, 0);
		
		z = 0;
		
		alpha = 1;
	}
	
	public AbstractImage(double x, double y){
		this.scale = 1;
		this.transformation = new AffineTransform();
		
		this.visible = true;
		
		this.location = new double[2];
		this.setLocation(x, y);
		
		z = 0;
		
		alpha = 1;
	}
	
	public AbstractImage(boolean visible){
		this.scale = 1;
		this.transformation = new AffineTransform();
		
		this.visible = visible;
		
		this.location = new double[2];
		this.setLocation(0, 0);
		
		z = 0;
		
		alpha = 1;
	}
	
	public AbstractImage(double x, double y, boolean visible, int z){
		this.scale = 1;
		this.transformation = new AffineTransform();
		
		this.visible = visible;
		
		this.location = new double[2];
		this.setLocation(x, y);
		
		this.z = z;
		
		alpha = 1;
	}
	
	@Override
	public void kickTween(){
		if(this.tween != null){
			double[] movePos = this.tween.getCord(this.location);
			this.move(movePos[0],movePos[1]);
			if(this.tween.isEnded()){
				this.tween = null;
			}
		}
	}

	@Override
	public boolean isVisible(){
		if(this.alpha == 0){
			return false;
		}
		return this.visible;
	}
	
	@Override
	public double getScale(){
		return this.scale;
	}
	
	@Override
	public AffineTransform getTransformation(){
		return this.transformation;
	}
	
	@Override
	public AffineTransform getTransformationForDrawing(){
		this.transformation.setToTranslation(location[0], location[1]);
		this.transformation.scale(scale,scale);
		this.transformation.rotate(this.getRotation());
		return this.transformation;
	}
	
	@Override
	public double[] getLocation(){
		return this.location;
	}
	
	@Override
	public int getZ(){
		return this.z;
	}
	
	@Override
	public int compareTo(Displayable otherDisplay){
		if(otherDisplay.getZ() > this.getZ()){
			 return -1;
		}
		else if(otherDisplay.getZ() < this.getZ()){
			return 1;
		}
		return 0;
	}
	
	public float getAlpha(){
		return this.alpha;
	}
	
	@Override
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	@Override
	public void setScale(double scale){
		this.scale = scale;
	}
	
	@Override
	public void setLocation(double x, double y){
		this.location[0] = x;
		this.location[1] = y;
	}
	
	@Override
	public void move(double x, double y){
		this.location[0] += x;
		this.location[1] += y;
	}
	
	@Override
	public void setTween(TweenDefinable tween){
		this.tween = tween;
	}
	
	@Override
	public void setZ(int z){
		this.z = z;
	}
	
	public void setAlpha(float alpha){
		this.alpha = alpha;
	}
	
}
