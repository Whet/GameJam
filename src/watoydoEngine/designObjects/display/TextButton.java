package watoydoEngine.designObjects.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import watoydoEngine.designObjects.actions.MouseRespondable;

public abstract class TextButton extends Text implements MouseRespondable{

	public static Color DEFAULT_ON_COLOUR = new Color(240,198,18);
	public static Color DEFAULT_OFF_COLOUR = new Color(0,120,120);
	
	private Color onColour;
	private Color offColour;
	private boolean active;
	private int actionZ;
	private Rectangle2D boundBox;
	private boolean drawBox;
	
	public TextButton(Color onColour, Color offColour) {
		this.active = true;
		this.setColour(offColour);
		this.onColour = onColour;
		this.offColour = offColour;
		this.drawBox = true;
		boundBox = null;
	}
	
	public void setDrawBox(boolean drawBox) {
		this.drawBox = drawBox;
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
		this.setColour(onColour);
	}

	@Override
	public void mO(Point mousePosition) {
		this.setColour(offColour);
	}

	@Override
	public boolean mMC(Point mousePosition, MouseEvent e) {return false;}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public boolean isInBounds(double x, double y) {
		synchronized(this){
			if(this.boundBox == null || this.getText().isEmpty()){
				return false;
			}
			
			boolean contains = boundBox.contains(x - this.getLocation()[0], y - this.getLocation()[1]);
	
			return contains;
		}
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
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
	public void setText(String message) {
		super.setText(message);
		synchronized(this){
			// Causes flickering in menus
			this.boundBox = null;
		}
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		synchronized(this){
			this.boundBox = null;
		}
	}
	
	public Color getOnColour() {
		return onColour;
	}

	public Color getOffColour() {
		return offColour;
	}

	public void setOnColour(Color onColour) {
		this.onColour = onColour;
	}

	public void setOffColour(Color offColour) {
		this.offColour = offColour;
	}

	@Override
	public void drawMethod(Graphics2D drawShape) {
		if(drawBox && this.boundBox != null && this.getText().length() > 0) {
			drawShape.setColor(this.getColour().darker().darker());
			drawShape.fillRect((int)this.getLocation()[0] - 5, (int)this.getLocation()[1] - (int)this.boundBox.getHeight() + ((int)this.boundBox.getHeight() / 4), (int)this.boundBox.getWidth() + 10, (int)(this.boundBox.getHeight() * 1.25));
		}
		
		super.drawMethod(drawShape);
		
		if(this.boundBox == null){
			FontMetrics metric = drawShape.getFontMetrics(this.getFont());
			this.boundBox = metric.getStringBounds(this.getText(), drawShape);
		}
		
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.setActive(visible);
	}

}
