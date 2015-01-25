package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import watoydoEngine.designObjects.actions.ActionRegion;
import watoydoEngine.designObjects.display.ButtonMulti;
import watoydoEngine.designObjects.display.Crowd;
import watoydoEngine.designObjects.display.ImageSingle;
import watoydoEngine.designObjects.display.Text;
import watoydoEngine.hardPanes.HardPaneDefineable;
import watoydoEngine.io.ReadWriter;
import watoydoEngine.workings.displayActivity.ActivePane;

public class Biography implements HardPaneDefineable {

	@Override
	public void load(Crowd crowd) throws FileNotFoundException, IOException {

		final List<God> gods = new ArrayList<>();
		
		gods.add(God.butter);
		gods.add(God.debauchery);
		gods.add(God.fire);
		gods.add(God.motion);
		
		final ImageSingle godImage = new ImageSingle(getGodImage(gods.get(0)));
		crowd.addDisplayItem(godImage);
		
		BufferedImage buttonUpImage = null;
		BufferedImage buttonDownImage = null;
		
		ImageSingle background = null;
		
		try {
			buttonUpImage = ImageIO.read(ReadWriter.getResourceAsInputStream("btnUp.png"));
			buttonDownImage = ImageIO.read(ReadWriter.getResourceAsInputStream("btnDown.png"));
			background = new ImageSingle(ImageIO.read(ReadWriter.getResourceAsInputStream("backgroundPlaceholder.png")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		crowd.addDisplayItem(background);
		
		ButtonMulti menuButton = new ButtonMulti(new BufferedImage[]{buttonUpImage, buttonDownImage}) {
			
			Text text = new Text(this.getLocation()[0] + 20, this.getLocation()[1], "Menu");

			{
				text.setAlpha(1);
				text.setColour(Color.black);
			}

			@Override
			public void drawMethod(Graphics2D drawShape) {
				super.drawMethod(drawShape);
				text.setLocation(this.getLocation()[0] + 22, this.getLocation()[1] + 32);
				text.drawMethod(drawShape);
			}
			
			@Override
			public boolean mD(java.awt.Point mousePosition, java.awt.event.MouseEvent e) {
				ActivePane.getInstance().changeRootCrowd(new Crowd(new MainMenu()));
				return true;
			};
			
		};
		
		menuButton.setLocation(40, ActivePane.getInstance().getHeight() - 100);
		crowd.addButton(menuButton);
		
		final Text godText = new Text(ActivePane.getInstance().getWidth() * 0.75, 50) {
			
			private String fullMessage;
			private StringBuffer sb = new StringBuffer();
			
			{
				this.setColour(Color.red);
				this.setText("aaaa");
			}
			
			@Override
			public void setText(String message) {
				this.fullMessage = message;
				this.sb = new StringBuffer();
			}
			
			@Override
			public void drawMethod(Graphics2D drawShape) {
				super.setText(sb.toString());
				super.drawMethod(drawShape);
				
				if(sb.length() < fullMessage.length()) {
					sb.append(fullMessage.toCharArray()[sb.length()]);
				}
			}
			
		};
		godText.setZ(1);
		godText.setAlpha(1);
		godText.setLocation(650, 90);
		crowd.addDisplayItem(godText);
		
		godText.setText(getGodText(God.butter));
		
		ActionRegion actionRegion = new ActionRegion(0,0,0,0) {
			
			int index = 0;
			
			@Override
			public boolean mU(Point mousePosition, MouseEvent e) {
				index++;
				
				if(index > 3)
					index = 0;
				
				godImage.setImage(getGodImage(gods.get(index)));
				godText.setText(getGodText(gods.get(index)));
				return true;
			}
			
			@Override
			public boolean isInBounds(double x, double y) {
				return true;
			}
			
		};
		crowd.addMouseActionItem(actionRegion);
		
		crowd.addDisplayItem(godImage);
		
	}
	
	private String getGodText(God god) {
		
		switch(god) {
		case butter:
			return "BUTTER";
		case debauchery:
			return "DEBAUCHERY";
		case fire:
			return "FIRE";
		case motion:
			return "MOTION";
		case unselected:
		break;
		
		}
		
		return "ANUS";
		
	}
	
	private BufferedImage getGodImage(God god) {
		
		String filename = "";
		
		switch(god) {
		case butter:
			filename = "butter.png";
			break;
		case debauchery:
			filename = "debauchery.png";
			break;
		case fire:
			filename = "fire.png";
			break;
		case motion:
			filename = "motion.png";
			break;
		case unselected:
			break;
		
		}
		
		try {
			BufferedImage read = ImageIO.read(ReadWriter.getResourceAsInputStream(filename));

			Image tmp = read.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
		    BufferedImage dimg = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);

		    Graphics2D g2d = dimg.createGraphics();
		    g2d.drawImage(tmp, 0, 0, null);
		    g2d.dispose();
		    
		    return dimg;
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
