package watoydoEngine.designObjects.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import watoydoEngine.utils.GraphicsFunctions;


public class ImageMulti extends AbstractImage{
	
	private int frame;
	private boolean playing;
	private BufferedImage[] image;
	private double rotation;
	
	public ImageMulti(BufferedImage[] image){
		super();
		
		this.image = image;
		
		this.frame = 0;
		this.playing = true;
		
		this.rotation = 0;
	}

	public ImageMulti(BufferedImage image[], double x, double y){
		super(x,y);
		
		this.image = image;
		
		this.frame = 0;
		this.playing = true;
		
		this.rotation = 0;
	}

	public ImageMulti(BufferedImage image[], boolean visible){
		super(visible);
		
		this.image = image;
		
		this.frame = 0;
		this.playing = true;
		
		this.rotation = 0;
	}

	public ImageMulti(BufferedImage image[], double x, double y, boolean visible,int z){
		super(x,y,visible,z);
		
		this.image = image;
		
		this.frame = 0;
		this.playing = true;
		
		this.rotation = 0;
	}
	
	@Override
	public void drawMethod(Graphics2D drawShape){
		nextFrame();
		drawShape.setComposite(GraphicsFunctions.makeComposite(this.getAlpha()));
		drawShape.drawImage(this.image[frame],this.getTransformationForDrawing(),null);
		drawShape.setComposite(GraphicsFunctions.makeComposite(1));
	}
	
	public void nextFrame(){
		if(playing){
			this.frame++;
			if(frame > image.length - 1){
				frame = 0;
			}
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
			size[0] = this.image[frame].getWidth();
			size[1] = this.image[frame].getHeight();
		}
		else{
			size[0] = 0;
			size[1] = 0;
		}
		return size;
	}
	
	public boolean isPlaying(){
		return playing;
	}
	
	@Override
	public BufferedImage getImage(){
		return this.image[frame];
	}
	
	public int getFrame(){
		return this.frame;
	}
	
	public void setFrame(int frame){
		if(frame < image.length - 1){
			this.frame = frame;
		}
	}
	
	public void setPlaying(boolean playing){
		this.playing = playing;
	}
	
	@Override
	public void setImage(BufferedImage image){
		this.image[frame] = image;
	}
	
	@Override
	public double getRotation() {
		return rotation;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
}
