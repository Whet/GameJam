package watoydoEngine.designObjects.display;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import watoydoEngine.designObjects.actions.KeyboardRespondable;
import watoydoEngine.designObjects.actions.MouseRespondable;

public class TextInput extends Text implements MouseRespondable,KeyboardRespondable{
	
	private boolean active;
	private int actionZ;
	private boolean focus;
	private int maxLetters;
	private int minLetters;
	private Rectangle2D boundBox;
	private boolean drawBox;
	private Color onColour;
	private Color offColour;
	private boolean updateHitbox;

	public void setDrawBox(boolean drawBox) {
		this.drawBox = drawBox;
		this.onColour = TextButton.DEFAULT_ON_COLOUR;
		this.offColour = TextButton.DEFAULT_OFF_COLOUR;
		this.setColour(offColour);
	}

	public TextInput(double x, double y, String message) {
		super(x,y,message);
		
		active = true;
		focus = false;
		this.drawBox = true;
		this.onColour = TextButton.DEFAULT_ON_COLOUR;
		this.offColour = TextButton.DEFAULT_OFF_COLOUR;
		this.setColour(offColour);
		updateHitbox = false;
	}

	@Override
	public final void drawMethod(Graphics2D drawShape){
		
		if(drawBox && this.boundBox != null) {
			drawShape.setColor(this.getColour().darker().darker());
			drawShape.fillRect((int)this.getLocation()[0] - 5, (int)this.getLocation()[1] - (int)this.boundBox.getHeight() + ((int)this.boundBox.getHeight() / 4), (int)this.boundBox.getWidth() + 10, (int)(this.boundBox.getHeight() * 1.25));
			drawShape.setColor(this.getColour().darker());
			drawShape.drawRect((int)this.getLocation()[0] - 5, (int)this.getLocation()[1] - (int)this.boundBox.getHeight() + ((int)this.boundBox.getHeight() / 4), (int)this.boundBox.getWidth() + 10, (int)(this.boundBox.getHeight() * 1.25));
		}
		
		updateText();
		
		
		if(updateHitbox){
			if(this.getText().isEmpty()) {
				FontMetrics metric = drawShape.getFontMetrics(this.getFont());
				this.boundBox = metric.getStringBounds(" ", drawShape);
			}
			else {
				FontMetrics metric = drawShape.getFontMetrics(this.getFont());
				
				if(!this.isFocused() || metric.getStringBounds(this.getText(), drawShape).getWidth() > this.boundBox.getWidth())
					this.boundBox = metric.getStringBounds(this.getText(), drawShape);
			}
			
			
			updateHitbox = false;
		}
		
		super.drawMethod(drawShape);
	}
	
	// If a sublass wants to define it's text with a string variable, this method gets called before drawing
	// so a user doesn't have to call setText every time they update the variable
	protected void updateText(){};

	@Override
	public boolean kD(KeyEvent e){
		return true;
	}

	@Override
	public boolean kU(KeyEvent e){
		if((e.getKeyCode() == 32 || Character.isAlphabetic(e.getKeyChar()) || Character.isDigit(e.getKeyChar())) && (maxLetters == 0 || maxLetters > this.getText().length())){
			this.appendText(e.getKeyChar());
			updateHitbox = true;
		}
		return true;
	}

	@Override
	public boolean kP(KeyEvent e){
		// if backspace, delete last letter
		// doesn't work with getKeyCode
		if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE && this.getText().length() > minLetters){
			this.setText(this.getText().substring(0, this.getText().length() - 1));
		}
		// always return true to stop other things running
		return true;
	}

	@Override
	public boolean mD(Point mousePosition, MouseEvent e){
		this.setFocus(true);
		return true;
	}

	@Override
	public boolean mU(Point mousePosition, MouseEvent e){
		this.setFocus(true);
		return true;
	}

	@Override
	public boolean rMD(Point mousePosition, MouseEvent e){
		return false;
	}

	@Override
	public boolean rMU(Point mousePosition, MouseEvent e){
		return false;
	}

	@Override
	public void setFocus(boolean focus) {
		if(focus){
			this.setColour(Color.white);
		}
		else{
			this.setColour(onColour);
		}
		this.focus = focus;
	}
	
	@Override
	public void mI(Point mousePosition) {
		if(!this.isFocused())
			this.setColour(onColour);
	}
	
	@Override
	public void mO(Point mousePosition) {
		this.setFocus(false);
		this.setColour(offColour);
		updateHitbox = true;
	}
	
	@Override
	public boolean mMC(Point mousePosition, MouseEvent e) {return false;}
	
	@Override
	public boolean isActive(){
		return active;
	}

	@Override
	public boolean isFocused(){
		return focus;
	}

	@Override
	public boolean isInBounds(double x, double y){
		if(this.boundBox == null){
			return false;
		}
		return boundBox.contains(x - this.getLocation()[0], y - this.getLocation()[1]);
	}

	public int getMaxLetters(){
		return this.maxLetters;
	}

	@Override
	public void setActive(boolean active){
		this.active = active;
	}

	public void setMaxLetters(int maxLetters){
		this.maxLetters = maxLetters;
	}

	public int getMinLetters() {
		return minLetters;
	}

	public void setMinLetters(int minLetters) {
		this.minLetters = minLetters;
	}

	@Override
	public boolean mC(Point mousePosition, MouseEvent e) {
		return false;
	}

	@Override
	public boolean rMC(Point mousePosition, MouseEvent e) {
		return false;
	}

	@Override
	public int getActionZ() {
		return this.actionZ;
	}

	@Override
	public void setActionZ(int actionZ) {
		this.actionZ = actionZ;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.setActive(visible);
	}

	public Color getOnColour() {
		return onColour;
	}

	public void setOnColour(Color onColour) {
		this.onColour = onColour;
	}

	public Color getOffColour() {
		return offColour;
	}

	public void setOffColour(Color offColour) {
		this.offColour = offColour;
		this.setColour(offColour);
	}
	
}
