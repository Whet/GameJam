package watoydoEngine.designObjects.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import watoydoEngine.display.tweens.TweenDefinable;
import watoydoEngine.fonts.FontList;

public class Text implements Displayable {

	private Font font;
	private double[] location;
	private boolean visible;
	private double scale;
	private String message;
	private Color colour;
	private TweenDefinable tween;
	private int z;
	private float alpha;
	protected int pointInText;
	protected double lineY;

	public Text() {
		location = new double[2];
		location[0] = 0;
		location[1] = 0;
		this.visible = true;
		this.scale = 1;
		this.message = "";
		this.colour = Color.white;
//		font = FontList.AUD14;
	}

	public Text(double x, double y) {
		location = new double[2];
		location[0] = x;
		location[1] = y;
		this.visible = true;
		this.scale = 1;
		this.message = "";
		this.colour = Color.white;
//		font = FontList.AUD14;
	}

	public Text(double x, double y, String message) {
		location = new double[2];
		location[0] = x;
		location[1] = y;
		this.visible = true;
		this.scale = 1;
		this.message = message;
		this.colour = Color.white;
//		font = FontList.AUD14;
	}

	public Text(double x, double y, String message, Color colour) {
		location = new double[2];
		location[0] = x;
		location[1] = y;
		this.visible = true;
		this.scale = 1;
		this.message = message;
		this.colour = colour;
//		font = FontList.AUD14;
	}

	@Override
	public void drawMethod(Graphics2D drawShape) {
		pointInText = 0;
		lineY = location[1];
		drawShape.setColor(colour);
		if(this.font != null)
			drawShape.setFont(font);
		while (pointInText < this.getText().length()) {
			drawShape.drawString(computeLines(), (int) location[0], (int) lineY);
		}
	}

	protected final String computeLines() {
		StringBuilder sb = new StringBuilder();

		// newline has been reached, update position
		if (pointInText != 0) {
			lineY += 18;
		}
		for (int i = pointInText; i < this.getText().length(); i++) {
			if (this.getText().charAt(i) == '@'
					&& i + 1 < this.getText().length()
					&& this.getText().charAt(i + 1) == 'n') {
				pointInText = i + 2;
				return sb.toString();
			}
			sb.append(this.getText().charAt(i));
		}
		pointInText += sb.length();
		return sb.toString();
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	@Override
	public double getScale() {
		return this.scale;
	}

	@Override
	public double[] getSize() {
		double[] size = new double[2];
		size[0] = this.getText().length() * 8;
		if (size[0] == 0) {
			size[0] = 6;
		}
		size[1] = 16;
		return size;
	}

	@Override
	public double[] getLocation() {
		return this.location;
	}

	@Override
	public void kickTween() {
		if (this.tween != null) {
			double[] movePos = this.tween.getCord(this.location);
			this.move(movePos[0], movePos[1]);
			if (this.tween.isEnded()) {
				this.tween = null;
			}
		}
	}

	@Override
	public int getZ() {
		return this.z;
	}

	public String getText() {
		return this.message;
	}

	public Color getColour() {
		return this.colour;
	}

	@Override
	public int compareTo(Displayable otherDisplayable) {
		if (otherDisplayable.getZ() > this.getZ()) {
			return -1;
		} else if (otherDisplayable.getZ() < this.getZ()) {
			return 1;
		}
		return 0;
	}

	public float getAlpha() {
		return this.alpha;
	}

	public Font getFont() {
		return this.font;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public void setScale(double scale) {
		this.scale = scale;
	}

	@Override
	public void setLocation(double x, double y) {
		this.location[0] = x;
		this.location[1] = y;
	}

	public void setLocation(double[] location) {
		this.location[0] = location[0];
		this.location[1] = location[1];
	}

	public void setText(String message) {
		this.message = message;
	}

	public void appendText(String message) {
		this.message = this.message + message;
	}

	public void appendText(char message) {
		this.message = this.message + message;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	@Override
	public void move(double x, double y) {
		this.location[0] += x;
		this.location[1] += y;
	}

	@Override
	public void setTween(TweenDefinable tween) {
		this.tween = tween;
	}

	@Override
	public void setZ(int z) {
		this.z = z;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public void setFont(Font font) {
		this.font = font;
	}

}
