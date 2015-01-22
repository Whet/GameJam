package watoydoEngine.designObjects.display;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import watoydoEngine.designObjects.actions.KeyboardRespondable;
import watoydoEngine.designObjects.actions.MouseRespondable;
import watoydoEngine.display.tweens.TweenDefinable;
import watoydoEngine.hardPanes.HardPaneDefineable;
import watoydoEngine.workings.displayActivity.ActivePane;

public class Crowd implements Displayable, MouseRespondable, KeyboardRespondable{


	private ArrayList<Displayable> displayList;
	private ArrayList<MouseRespondable> mouseActionList;
	private ArrayList<KeyboardRespondable> keyboardActionList;
	
	private Double scale;
	private boolean visible;
	private boolean active;
	private boolean focus;
	private double[] location;
	private AffineTransform transformation;
	private TweenDefinable tween;
	private int z;
	private int actionZ;
	
	private boolean useLocalCord;
	
	public Crowd(HardPaneDefineable pane){
		
		displayList = new ArrayList<Displayable>();
		mouseActionList = new ArrayList<MouseRespondable>();
		keyboardActionList = new ArrayList<KeyboardRespondable>();
		
		scale = 1.0;
		location = new double[2];
		location[0] = 0;
		location[1] = 0;
		transformation = new AffineTransform();
		
		this.useLocalCord = false;
		
		focus = true;
		active = true;
		visible = true;
		
		z = 0;
		actionZ = 0;
		
		pane.load(this);
		
		computeZOrder();
	}
	
	public Crowd(HardPaneDefineable pane, boolean useLocalCord, int z){

		displayList = new ArrayList<Displayable>();
		mouseActionList = new ArrayList<MouseRespondable>();
		keyboardActionList = new ArrayList<KeyboardRespondable>();
		
		scale = 1.0;
		location = new double[2];
		location[0] = 0;
		location[1] = 0;
		transformation = new AffineTransform();
		
		this.useLocalCord = useLocalCord;
		
		focus = true;
		active = true;
		visible = true;
		
		this.z = z;
		actionZ = 0;
		
		pane.load(this);
		
		computeZOrder();
	}
	
	public Crowd(boolean visible){
		
		displayList = new ArrayList<Displayable>();
		mouseActionList = new ArrayList<MouseRespondable>();
		keyboardActionList = new ArrayList<KeyboardRespondable>();
		
		scale = 1.0;
		location = new double[2];
		location[0] = 0;
		location[1] = 0;
		transformation = new AffineTransform();
		
		this.useLocalCord = false;
		
		focus = true;
		active = true;
		this.visible = visible;
		
		z = 0;
		actionZ = 0;
		
		computeZOrder();
	}
	
	public Crowd(boolean visible,boolean useLocalCord, int z){

		displayList = new ArrayList<Displayable>();
		mouseActionList = new ArrayList<MouseRespondable>();
		keyboardActionList = new ArrayList<KeyboardRespondable>();
		
		scale = 1.0;
		location = new double[2];
		location[0] = 0;
		location[1] = 0;
		transformation = new AffineTransform();
		
		this.z = z;
		actionZ = 0;
		
		this.useLocalCord = useLocalCord;
		
		focus = true;
		active = true;
		this.visible = visible;
		
		computeZOrder();
	}
	
	@Override
	public void drawMethod(Graphics2D drawShape){
		ActivePane.getInstance().drawCrowd(this);
	}

	private void convertToLocal(){
		for(int i = 0; i < displayList.size(); i++){
			displayList.get(i).move(location[0],location[1]);
		}
		
	}
	
	private void convertToGlobal(){
		for(int i = 0; i < displayList.size(); i++){
			displayList.get(i).move(- location[0],- location[1]);
		}
	}

	public void computeZOrder(){
		Collections.sort(displayList);
		Collections.sort(mouseActionList, new Comparator<MouseRespondable>() {

			@Override
			public int compare(MouseRespondable o1, MouseRespondable o2) {
				if(o2.getActionZ() > o1.getActionZ()){
					 return -1;
				}
				else if(o2.getActionZ() < o1.getActionZ()){
					return 1;
				}
				return 0;
			}
		});
		Collections.sort(keyboardActionList, new Comparator<KeyboardRespondable>() {

			@Override
			public int compare(KeyboardRespondable o1, KeyboardRespondable o2) {
				if(o2.getActionZ() > o1.getActionZ()){
					 return -1;
				}
				else if(o2.getActionZ() < o1.getActionZ()){
					return 1;
				}
				return 0;
			}
		});
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
	public boolean kU(KeyEvent e) {
		if(this.focus){
			for(int i = 0; i < keyboardActionList.size(); i++){
				if(keyboardActionList.get(i).isFocused()){
					if(keyboardActionList.get(i).kU(e))
						return true;
				}
	    	}
		}
		return false;
	}
	
	@Override
	public boolean kP(KeyEvent e) {
		if(this.focus){
			for(int i = 0; i < keyboardActionList.size(); i++){
				if(keyboardActionList.get(i).isFocused()){
					if(keyboardActionList.get(i).kP(e))
						return true;
				}
	    	}
		}
		return false;
	}
	
	@Override
	public boolean kD(KeyEvent e) {
		return false;
	}
	
	@Override
	public boolean mD(Point mousePosition, MouseEvent e) {
		if(this.active){
			for(int i = 0; i < mouseActionList.size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(mouseActionList.get(i).isActive() && mouseActionList.get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(mouseActionList.get(i).mD(mousePosition, e))
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean mU(Point mousePosition, MouseEvent e) {
		if(this.active){
			for(int i = 0; i < mouseActionList.size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(mouseActionList.get(i).isActive() && mouseActionList.get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(mouseActionList.get(i).mU(mousePosition, e))
						return true;
				}
			}
		}
		return mC(mousePosition, e);
	}
	
	@Override
	public boolean mC(Point mousePosition, MouseEvent e) {
		if(this.active){
			for(int i = 0; i < mouseActionList.size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(mouseActionList.get(i).isActive() && mouseActionList.get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(mouseActionList.get(i).mC(mousePosition, e))
						return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean rMD(Point mousePosition, MouseEvent e) {
		if(this.active){
			for(int i = 0; i < mouseActionList.size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(mouseActionList.get(i).isActive() && mouseActionList.get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(mouseActionList.get(i).rMD(mousePosition, e))
						return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean rMU(Point mousePosition, MouseEvent e) {
		if(this.active){
			for(int i = 0; i < mouseActionList.size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(mouseActionList.get(i).isActive() && mouseActionList.get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(mouseActionList.get(i).rMU(mousePosition, e))
						return true;
				}
			}
		}
		return rMC(mousePosition, e);
	}
	
	@Override
	public boolean rMC(Point mousePosition, MouseEvent e) {
		if(this.active){
			for(int i = 0; i < mouseActionList.size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(mouseActionList.get(i).isActive() && mouseActionList.get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(mouseActionList.get(i).rMC(mousePosition, e))
						return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean mMC(Point mousePosition, MouseEvent e) {
		if(this.active){
			for(int i = 0; i < mouseActionList.size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(mouseActionList.get(i).isActive() && mouseActionList.get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(mouseActionList.get(i).mMC(mousePosition, e))
						return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void mI(Point mousePosition) {
		if(this.active){
			for(int i = 0; i < mouseActionList.size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(mouseActionList.get(i).isActive() && mouseActionList.get(i).isInBounds(mousePosition.x,mousePosition.y)){
					mouseActionList.get(i).mI(mousePosition);
				}
				// active and not in area
				else if(mouseActionList.get(i).isActive()){
					mouseActionList.get(i).mO(mousePosition);
				}
			}
		}
	}
	
	@Override
	public void mO(Point mousePosition) {}
	
	
    public void addDisplayItem(Displayable item){
 		displayList.add(item);
 	}
 	
	public void addButton(AbstractButton item){
 		displayList.add(item);
 		mouseActionList.add(item);
 	}

	public void addInputText(TextInput item){
 		displayList.add(item);
 		mouseActionList.add(item);
 		keyboardActionList.add(item);
 	}
 	
	 public void addCrowd(Crowd item){
 		displayList.add(item);
 		mouseActionList.add(item);
 		keyboardActionList.add(item);
 	}
 	
	 public void addMouseActionItem(MouseRespondable item){
 		mouseActionList.add(item);
 	}
 	
	 public void addKeyListener(KeyboardRespondable item){
 		keyboardActionList.add(item);
 	}
	
	@Override
	public boolean isVisible(){
		return this.visible;
	}
	
	@Override
	public double getScale(){
		return this.scale;
	}
	
	@Override
	public double[] getSize(){
		double[] returnInt = new double[2];
		return returnInt;
	}

	public AffineTransform transformation(){
		this.transformation.setToTranslation(location[0], location[1]);
		this.transformation.scale(scale,scale);
		return this.transformation;
	}
	
	@Override
	public double[] getLocation(){
		return this.location;
	}
	
	public ArrayList<Displayable> getDisplayList(){
		return this.displayList;
	}
	
	public ArrayList<MouseRespondable> getMouseActionList(){
		return this.mouseActionList;
	}
	
	public ArrayList<KeyboardRespondable> getKeyboardActionList(){
		return this.keyboardActionList;
	}
	
	public boolean getLocal(){
		return this.useLocalCord;
	}
	
	@Override
	public int getZ() {
		return this.z;
	}
	
	@Override
	public boolean isFocused() {
		return this.focus;
	}

	@Override
	public boolean isInBounds(double x, double y) {
		return true;
	}
	
	@Override
	public boolean isActive() {
		return this.active;
	}
	
	@Override
	public int compareTo(Displayable otherDisplayable){
		if(otherDisplayable.getZ() > this.getZ()){
			 return -1;
		}
		else if(otherDisplayable.getZ() < this.getZ()){
			return 1;
		}
		return 0;
	}
	
	public AffineTransform getTransformation(){
		return this.transformation;
	}
	
	public AffineTransform getTransformationForDrawing(){
		this.transformation.setToTranslation(location[0], location[1]);
		this.transformation.scale(scale,scale);
		return this.transformation;
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
	
	public void setLocal(boolean useLocal){
		if(useLocal != this.useLocalCord){
			this.useLocalCord = useLocal;
			if(useLocalCord){
				convertToLocal();
			}
			else{
				convertToGlobal();
			}
		}
	}
	
	@Override
	public void setZ(int z){
		this.z = z;
	}
	
	@Override
	public void setTween(TweenDefinable tween){
		this.tween = tween;
	}
	
	@Override
	public void setFocus(boolean active) {
		this.focus = active;
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

	public void removeItem(Displayable item) {
		this.displayList.remove(item);
		this.keyboardActionList.remove(item);
		this.mouseActionList.remove(item);
	}

}