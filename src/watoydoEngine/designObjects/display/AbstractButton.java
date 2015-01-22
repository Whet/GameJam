package watoydoEngine.designObjects.display;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import watoydoEngine.designObjects.actions.MouseRespondable;
import watoydoEngine.display.tweens.TweenDefinable;

public abstract class AbstractButton implements Displayable, MouseRespondable{
	
	private double[] location;
	private boolean visible;
	private double scale;
	private double rotation;
	private AffineTransform transformation;
	private TweenDefinable tween;
	private int z;
	private boolean active;
	private float alpha;
	private int actionZ;

	public AbstractButton(){
		this.scale = 1;
		this.rotation = 0;
		this.transformation = new AffineTransform();
		
		this.visible = true;
		this.active = true;
		
		this.location = new double[2];
		this.setLocation(0, 0);
		
		z = 0;
		actionZ = 0;
		
		alpha = 1;
	}
	
	public AbstractButton(double x, double y){
		this.scale = 1;
		this.rotation = 0;
		this.transformation = new AffineTransform();
		
		this.visible = true;
		this.active = true;
		
		this.location = new double[2];
		this.setLocation(x, y);
	
		z = 0;
		
		alpha = 1;
	}
	
	public AbstractButton(boolean visible){
		this.scale = 1;
		this.rotation = 0;
		this.transformation = new AffineTransform();
		
		this.visible = visible;
		this.active = visible;
		
		this.location = new double[2];
		this.setLocation(0, 0);
		
		z = 0;
		actionZ = 0;
		
		alpha = 1;
	}
	
	public AbstractButton(boolean visible, boolean active){
		this.scale = 1;
		this.rotation = 0;
		this.transformation = new AffineTransform();
		
		this.visible = visible;
		this.active = active;
		
		this.location = new double[2];
		this.setLocation(0, 0);
		
		z = 0;
		actionZ = 0;
		
		alpha = 1;
	}
	
	public AbstractButton(double x, double y, boolean visible){
		this.scale = 1;
		this.rotation = 0;
		this.transformation = new AffineTransform();
		
		this.visible = visible;
		this.active = visible;
		
		this.location = new double[2];
		this.setLocation(x, y);
		
		z = 0;
		actionZ = 0;
		
		alpha = 1;
	}
	
	public AbstractButton(double x, double y, boolean visible, boolean active, int z){
		this.scale = 1;
		this.rotation = 0;
		this.transformation = new AffineTransform();
		
		this.visible = visible;
		this.active = active;
		
		this.location = new double[2];
		this.setLocation(x, y);
		
		this.z = z;
		actionZ = 0;
		
		alpha = 1;
	}
	
	@Override
	public boolean mD(Point mousePosition, MouseEvent e){return false;};
	
	@Override
	public boolean mU(Point mousePosition, MouseEvent e){return false;};
	
	@Override
	public boolean mC(Point mousePosition, MouseEvent e){return false;};

	@Override
	public void mI(Point mousePosition){};
	
	@Override
	public void mO(Point mousePosition){};
	
	@Override
	public boolean rMD(Point mousePosition, MouseEvent e){return false;};

	@Override
	public boolean rMU(Point mousePosition, MouseEvent e){return false;};
	
	@Override
	public boolean rMC(Point mousePosition, MouseEvent e){return false;};
	
	@Override
	public boolean mMC(Point mousePosition, MouseEvent e) {return false;}
	
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
	
	public TweenDefinable getTween() {
		return tween;
	}

	@Override
	public boolean isVisible(){
		return this.visible;
	}
	
	@Override
	public double getScale(){
		return this.scale;
	}
	
	public AffineTransform getTransformation(){
		return this.transformation;
	}
	
	public AffineTransform getTransformationForDrawing(){
		this.transformation.setToTranslation(location[0], location[1]);
		this.transformation.scale(scale,scale);
		this.transformation.rotate(this.getRotation());
		return this.transformation;
	}
	
	public void setActive(Boolean active){
		this.active = active;
	}
	
	@Override
	public boolean isActive(){
		return this.active;
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
		this.active = visible;
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
	
	@Override
	public void setActive(boolean active){
		this.active = active;
	}
	
	public void setAlpha(float alpha){
		this.alpha = alpha;
	}
	
	@Override
	public int getActionZ() {
		return actionZ;
	}

	@Override
	public void setActionZ(int actionZ) {
		this.actionZ = actionZ;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
}
