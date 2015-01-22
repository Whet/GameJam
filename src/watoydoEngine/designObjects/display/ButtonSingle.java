package watoydoEngine.designObjects.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import watoydoEngine.utils.GraphicsFunctions;

public abstract class ButtonSingle extends AbstractButton implements ImageDisplayable{

	private BufferedImage image;
	
	public ButtonSingle(BufferedImage image){
		this.image = image;
	}
	
	public ButtonSingle(BufferedImage image, double x, double y){
		super(x,y);
		this.image = image;
	}
	
	public ButtonSingle(BufferedImage image, boolean visible){
		super(visible);
		this.image = image;
	}
	
	public ButtonSingle(BufferedImage image, boolean visible, boolean active){
		super(visible,active);
		this.image = image;
	}
	
	public ButtonSingle(BufferedImage image, double x, double y, boolean visible){
		super(x,y,visible);
		this.image = image;
	}
	
	public ButtonSingle(BufferedImage image, double x, double y, boolean visible, boolean active, int z){
		super(x,y,visible,active,z);
		this.image = image;
	}
	
	@Override
	public void drawMethod(Graphics2D drawShape){
		drawShape.setComposite(GraphicsFunctions.makeComposite(this.getAlpha()));
		drawShape.drawImage(this.image,this.getTransformationForDrawing(),null);
		drawShape.setComposite(GraphicsFunctions.makeComposite(1));
	}
	
	@Override
	public boolean isInBounds(double x, double y){
		if(x >= (this.getLocation()[0]) && x <= (this.getLocation()[0] + (image.getWidth() * this.getScale())) &&
		   y >= (this.getLocation()[1]) && y <= (this.getLocation()[1] + (image.getHeight() * this.getScale()))){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public double[] getLocationCentre(){
		double editedLocation[] = {this.getLocation()[0] + this.getSize()[0] * 0.5, this.getLocation()[1] + this.getSize()[1] * 0.5};
		return editedLocation;
	}


	@Override
	public double[] getSize(){
		double[] size = new double[2];
		if(image != null){
			size[0] = this.image.getWidth();
			size[1] = this.image.getHeight();
		}
		else{
			size[0] = 0;
			size[1] = 0;
		}
		return size;
	}
	
	@Override
	public BufferedImage getImage(){
		return this.image;
	}
	
	@Override
	public void setImage(BufferedImage newImage){
		this.image = newImage;
	}
	
}
