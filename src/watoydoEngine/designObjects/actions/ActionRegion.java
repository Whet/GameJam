package watoydoEngine.designObjects.actions;

import java.awt.Point;
import java.awt.event.MouseEvent;

public abstract class ActionRegion implements MouseRespondable{
	
	private boolean active;
	private double[] location;
	private double[] size;
	private int actionZ;
	
	public ActionRegion(double x, double y, double width, double height){
		this.active = true;
		
		location = new double[2];
		location[0] = x;
		location[1] = y;
		
		size = new double[2];
		size[0] = width;
		size[1] = height;
		
		actionZ = 0;
	}
	
	@Override
	public boolean mD(Point mousePosition, MouseEvent e) {
		return false;
	}
	
	@Override
	public boolean mU(Point mousePosition, MouseEvent e) {
		return false;
	}
	
	@Override
	public boolean mC(Point mousePosition, MouseEvent e) {
		return false;
	}
	
	@Override
	public boolean rMD(Point mousePosition, MouseEvent e) {
		return false;
	}
	
	@Override
	public boolean rMU(Point mousePosition, MouseEvent e) {
		return false;
	}
	
	@Override
	public boolean rMC(Point mousePosition, MouseEvent e) {
		return false;
	}
	
	@Override
	public void mI(Point mousePosition) {
	}
	
	@Override
	public void mO(Point mousePosition) {
	}
	
	@Override
	public boolean mMC(Point mousePosition, MouseEvent e) {
		return false;
	}
	
	@Override
	public boolean isActive() {
		return active;
	}
	
	@Override
	public double[] getLocation() {
		return this.location;
	}
	
	public double[] getSize(){
		return this.size;
	}

	@Override
	public boolean isInBounds(double x, double y) {
		if(x >= location[0] && x <= location[0] + size[0] && y >= location[1] && y <= location[1] + size[1]){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public void setLocation(double x, double y) {
		this.location[0] = x;
		this.location[1] = y;
	}
	
	public void setLocation(double[] location) {
		this.location = location;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int getActionZ() {
		return actionZ;
	}

	@Override
	public void setActionZ(int actionZ) {
		this.actionZ = actionZ;
	}

}
