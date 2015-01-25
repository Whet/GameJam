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
		gods.add(God.chaos);
		gods.add(God.treachery);
		gods.add(God.vegetables);
		gods.add(God.water);
		
		final ImageSingle godImage = new ImageSingle(getGodImage(gods.get(0)));
		crowd.addDisplayItem(godImage);
		
		BufferedImage buttonUpImage = null;
		BufferedImage buttonDownImage = null;
		
		ImageSingle background = null;
		
		try {
			buttonUpImage = ImageIO.read(ReadWriter.getResourceAsInputStream("btnUp.png"));
			buttonDownImage = ImageIO.read(ReadWriter.getResourceAsInputStream("btnDown.png"));
			background = new ImageSingle(ImageIO.read(ReadWriter.getResourceAsInputStream("mainmenu.png")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		background.setAlpha(0.3f);
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
			private int lineLength = 0;
			private int charPos = 0;
			
			{
				this.setColour(Color.white);
				this.setText("aaaa");
			}
			
			@Override
			public void setText(String message) {
				this.fullMessage = message;
				this.sb = new StringBuffer();
				this.lineLength = 0;
				charPos = 0;
			}
			
			@Override
			public void drawMethod(Graphics2D drawShape) {
				super.setText(sb.toString());
				super.drawMethod(drawShape);
				
				if(sb.length() < fullMessage.length()) {
					
					if(lineLength > 40 && fullMessage.toCharArray()[charPos] == ' ') {
						sb.append("@n");
						lineLength = 0;
						charPos++;
					}
					else {
						sb.append(fullMessage.toCharArray()[charPos]);
						lineLength++;
						charPos++;
					}
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
				
				if(index > 7)
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
			return "Some consider Bertius to be a mere human but they couldn’t be more wrong. Most gods like to embellish stories about their grandiose powers to wow their peers and incite jealousy. Bertius does not need to have power of the elements nor control over emotions to assert his dominance. All he needs is his divine stick of butter to smite down his foes with its greasy wrath. Regular folk cower in fear and awe at his lard wielding prowess. He is said to be second to none in butter-based combat.\r\n" + 
					"People worship his ability to slip and slide (butter aided) out of any situation. They pray to him so that he may bless them with his greasy guile to aid them in their endeavours.";
		case debauchery:
			return "Redonkulus, self-proclaimed king of the gods: picture of perfect health and idol of beauty, gluts on the affairs of mortals, implementing his intoxicating seductive influence on all the waking world. With a sip of wine here and a ham leg there, he exerts his powers not when people need him but as he sees fit, leaving the recipients left in a lustful and greedy daze. He can be found lying on his plinth, devouring and drinking in an endless orgy of pleasure.";
		case fire:
			return "Birthed in the raging infernos of a dying star, Savina is revered even among the gods for her fighting ability and her control over the rampant flame. Commonly found in the pantheons swoly temple of iron, she prefers to shoulder her worshippers’ problems, squatting, benching and lifting them repeatedly till her figure is as bulging and radiant as the flames that comprise her flaming hair.\r\n" + 
					"She is often left with the responsibility of the pantheons annual fireworks display due to her excellence in pyrotechnics, and is a great help when it comes to barbeques in the rain.";
		case motion:
			return "His stinted growth has always left him the butt of the Pantheon’s jokes, yet Grabnibus uses his talents to move whole world’s, sunder great mountains and tickle the feet of the unworthy. With a prod from the finger of his Omnigloves, the result can be a gentle breeze or a sonic boom and he’s ready to repay the teasing that his divine colleagues have dogged him with for eons.";
		case chaos:
			return "Having his name misspelt when he was young, S’Tan was forced to join the pantheon of gods unlike his younger brother who is king of the underworld. S’Tan is forced to aid humanity using his chaotic powers whether he likes it or not. He still finds time to satiate his thirst for suffering of others by only helping those who pray for his divine assistance.\r\n" + 
					"	If the suffering of many, aids the situation of one, you can guarantee that S’Tan will be there with his influence, his pride still maimed by the sabotage of his name.";
		case treachery:
			return "At the brunch of Bertius Chudley’s 10 billionth birthday, Ridonkulus and Reeki-Teeki longed to steal the recipe for Chudley’s divine butter. Their scheming resulted in the creation of the blackened Brussel Sprout, a devilish veggie conceived in treachery and deceit.\r\n" + 
					"S’Tan, eager to preserve his muscular form, ate the sprout as he was on his eonly diet. The result was a flatulent cloud of such evil, that Orful was born unto the world,, but his treachery bound  in the heavens to cause help and harm wherever he may please.";
		case vegetables:
			return "Being the god of vegetables; Reeki Teeki is considered by some to be a nuisance, particularly by Ridonkulus who has not eaten a vegetable intentionally in all his godly life. Rikki formed himself of the collective (and often overlooked) conscience of the entire vegetative world.\r\n" + 
					"A parsnip, a carrot and a throng of parsley later and formed was Reeki-Teeki: defender of fibre, lord of the unintentional bowel-movement, protector of digestive health.  Despite his non-threatening powers, Reeki-Teeki will do all he can to blight the ‘Meat-Walkers’ that roam the land that rightfully belongs to the veggies.";
		case water:
			return "There’s a lot of water in the world, and when Aquaria came to be she was forced to take form of the entire world’s water. She couldn’t stand being so spread out that nobody could see her there, until one day she was assisted by Ridonkulus.\r\n" + 
					"Ridonkulus crafted a corset of holy gold and said it would finally contain her liquid form to something more manageable… At a cost.";
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
		case chaos:
			filename = "chaos.png";
			break;
		case treachery:
			filename = "treachery.png";
			break;
		case vegetables:
			filename = "vegetables.png";
			break;
		case water:
			filename = "water.png";
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
