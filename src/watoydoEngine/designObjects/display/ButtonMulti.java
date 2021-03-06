package watoydoEngine.designObjects.display;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import watoydoEngine.utils.GraphicsFunctions;

public abstract class ButtonMulti extends AbstractButton implements ImageDisplayable {
	
	private int frame;
	private boolean playing;
	private BufferedImage[] image;

	public ButtonMulti(BufferedImage image[]){
		this.image = image;
		this.playing = false;
		this.frame = 0;
	}
	
	public ButtonMulti(BufferedImage image[], double x, double y){
		super(x,y);
		this.image = image;
		this.playing = false;
		this.frame = 0;
	}
	
	public ButtonMulti(BufferedImage image[], boolean visible){
		super(visible);
		this.image = image;
		this.playing = false;
		this.frame = 0;
	}
	
	public ButtonMulti(BufferedImage image[], boolean visible, boolean active){
		super(visible,active);
		this.image = image;
		this.playing = false;
		this.frame = 0;
	}
	
	public ButtonMulti(BufferedImage image[], double x, double y, boolean visible){
		super(x,y,visible);
		this.image = image;
		this.playing = false;
		this.frame = 0;
	}
	
	public ButtonMulti(BufferedImage image[], double x, double y, boolean visible, boolean active, int z){
		super(x,y,visible,active,z);
		this.image = image;
		this.playing = false;
		this.frame = 0;
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
	
	public void nextFrame(boolean ignorePlaying){
		if(playing || ignorePlaying){
			this.frame++;
			if(frame > image.length - 1){
				frame = 0;
			}
		}
	}
	
	public void previousFrame(boolean ignorePlaying){
		if(playing || ignorePlaying){
			this.frame--;
			if(frame < 0){
				frame = image.length - 1;
			}
		}
	}
	
	public void setPlaying(boolean playing){
		this.playing = playing;
	}
	
	public void setFrame(int frame){
		if(frame <= image.length - 1){
			this.frame = frame;
		}
	}


	@Override
	public boolean isInBounds(double x, double y){
		if(x >= (this.getLocation()[0]) && x <= (this.getLocation()[0] + (image[frame].getWidth() * this.getScale())) && y >= (this.getLocation()[1]) && y <= (this.getLocation()[1] + (image[frame].getHeight() * this.getScale()))){
			return true;
		}
		return false;
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

	public void setImage(BufferedImage[] image){
		this.image = image;
	}
	
	@Override
	public void setImage(BufferedImage image){
		this.image[0] = image;
	}
	
	@Override
	public void mI(Point mousePosition) {
		super.mI(mousePosition);
		this.setFrame(1);
	}
	
	@Override
	public void mO(Point mousePosition) {
		super.mO(mousePosition);
		this.setFrame(0);
	}

}
