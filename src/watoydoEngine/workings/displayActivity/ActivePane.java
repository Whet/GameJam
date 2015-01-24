package watoydoEngine.workings.displayActivity;
import game.MainMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import watoydoEngine.designObjects.actions.ActivePaneTimer;
import watoydoEngine.designObjects.display.Crowd;
import watoydoEngine.designObjects.display.Displayable;
import watoydoEngine.workings.DisplayManager;

public class ActivePane extends JFrame implements MouseListener, KeyListener, WindowListener{
	
	private static ActivePane instance;
	
	public static ActivePane getInstance(){
		if(instance == null){
			instance = new ActivePane();
		}
		return instance;
	}

	private Crowd rootCrowd;
	
	private int windowed;
	private int[] resolution;
	
	private boolean rootCrowdLoaded;
	
	private BufferStrategy bufferS;
	private Graphics2D dBuffer;
	
	private Color backgroundColour;
	
	private static final Semaphore loadedMutex = new Semaphore(1);
	
	public static boolean L_MOUSE_DOWN = false;
	
	private ActivePane(){
		
		// Display settings
		resolution = new int[2];
		resolution[0] = 1280;
		resolution[1] = 720;
		windowed = 0;

		
		// Although the program can run windowed we position buttons by resolution sometimes
		// To save effort of resizing every time the user does the window, it is kept constant
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		backgroundColour = new Color(0,0,0);
		
		// Stops unneeded looping before displaylist has been populated
		rootCrowdLoaded = false;
		
	}
	
	
	public void setMode(int[] resolution, int windowed){
		
		this.resolution[0] = resolution[0];
		this.resolution[1] = resolution[1];
		
		this.windowed = windowed;
		
		if(windowed != 1)
			this.setUndecorated(false);
		else
			this.setUndecorated(true);
	}
	
	// Set display up when config menu closes, run once
	public void setDisplay(){
		
		// Sets window icon to watoydo logo
//		Image watLogo = null;
		this.setTitle("GameJam");
		
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		
//		Image cursorImage = null;
//		Point cursorHotSpot = new Point(0,0);	
//		
//		try{
//			watLogo = ImageIO.read(ReadWriter.getResourceAsInputStream("images/watoydo/watoydoLogo.jpg"));
//			cursorImage = ImageIO.read(ReadWriter.getResourceAsInputStream("images/atrophy/combat/icons/mouseWV.png"));
//		}
//		catch(IOException ioexcept){
//			ioexcept.printStackTrace();
//			System.err.println("watoydo window logo not found");
//		}
//		
//		this.setIconImage(watLogo);
//		
//		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
//		this.setCursor(customCursor);
		
		DisplayManager.getInstance().setFrameToScreen(this, this.windowed,this.resolution);
		
		this.setLocation((int)(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().x - resolution[0] * 0.5)
						,(int)(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().y - resolution[1] * 0.5));
		
		this.setVisible(true);
		
		// Stops flickering with screen redraws
		this.createBufferStrategy(2);
		bufferS = this.getBufferStrategy();
	}
	
	protected void setup(){
		
		removeMouseListener(this);
		removeKeyListener(this);
		removeWindowListener(this);
		
		rootCrowdLoaded = false;
		
		Thread loadFrame = new Thread(){
			@Override
			public void run() {
				// Load the first frame
				Crowd initialRootCrowd  = new Crowd(new MainMenu());
				rootCrowd = initialRootCrowd;
				
				rootCrowdLoaded = true;
				
				// Add listeners again
				// The whole frame has a listener for click events, further down you can see their implementation in the action list loops
				addMouseListener(instance);
				addWindowListener(instance);
				setFocusable(true);
				addKeyListener(instance);
				
				if(!ActivePaneTimer.getInstance().isPlaying())
					ActivePaneTimer.getInstance().start();
			}
		};
		
		// makes the loaded occur later so the loading screen can be drawn
		SwingUtilities.invokeLater(loadFrame);
	}
	
	public void changeRootCrowd(final Crowd crowd){
		this.rootCrowdLoaded = false;
		this.repaint();
		SwingUtilities.invokeLater(
		new Thread() {
			@Override
			public void run() {
				try {
					loadedMutex.acquire();
					rootCrowd = crowd;
					rootCrowdLoaded = true;
					loadedMutex.release();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		});
	}
	
	public void showLoading() {
		this.rootCrowdLoaded = false;
		this.repaint();
	}
	
	public void cancelLoading() {
		this.rootCrowdLoaded = true;
		this.repaint();		
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		
		requestFocus();
		
		Point mousePosition = new Point(MouseInfo.getPointerInfo().getLocation());
		SwingUtilities.convertPointFromScreen(mousePosition, this);
		
		// Mouse 1
		if(e.getButton() == MouseEvent.BUTTON1){
			L_MOUSE_DOWN = true;
			for(int i = 0; i < rootCrowd.getMouseActionList().size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(rootCrowd.getMouseActionList().get(i).isActive() && rootCrowd.getMouseActionList().get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(rootCrowd.getMouseActionList().get(i).mD(mousePosition, e))
						return;
				}
			}
		}
		// Mouse 2, BUTTON2 is middle mouse 
		else if(e.getButton() == MouseEvent.BUTTON3){
			for(int i = 0; i < rootCrowd.getMouseActionList().size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(rootCrowd.getMouseActionList().get(i).isActive() && rootCrowd.getMouseActionList().get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(rootCrowd.getMouseActionList().get(i).rMD(mousePosition, e))
						return;
				}
			}
		}
		// middle mouse
		else if(e.getButton() == MouseEvent.BUTTON2){
			for(int i = 0; i < rootCrowd.getMouseActionList().size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(rootCrowd.getMouseActionList().get(i).isActive() && rootCrowd.getMouseActionList().get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(rootCrowd.getMouseActionList().get(i).mMC(mousePosition, e))
						return;
				}
			}
		}
    }
	
    @Override
	public void mouseReleased(MouseEvent e){
    	
    	Point mousePosition = new Point(MouseInfo.getPointerInfo().getLocation());
		SwingUtilities.convertPointFromScreen(mousePosition, this);
    	
		// Mouse 1
		if(e.getButton() == MouseEvent.BUTTON1){
			L_MOUSE_DOWN = false;
			for(int i = 0; i < rootCrowd.getMouseActionList().size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(rootCrowd.getMouseActionList().get(i).isActive() && rootCrowd.getMouseActionList().get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(rootCrowd.getMouseActionList().get(i).mU(mousePosition, e))
						return;
				}
			}
		}
		// Mouse 2, BUTTON2 is middle mouse 
		else if(e.getButton() == MouseEvent.BUTTON3){
			for(int i = 0; i < rootCrowd.getMouseActionList().size(); i++){
				// if the mouse click is in the hitbox then peform the action
				if(rootCrowd.getMouseActionList().get(i).isActive() && rootCrowd.getMouseActionList().get(i).isInBounds(mousePosition.x,mousePosition.y)){
					if(rootCrowd.getMouseActionList().get(i).rMU(mousePosition, e))
						return;
				}
			}
		}
    }
    
    @Override
	public void mouseEntered(MouseEvent e){}

    @Override
	public void mouseExited(MouseEvent e){}

    @Override
	public void mouseClicked(MouseEvent e){}
    
    public void mouseEntered(){
    	try {
			loadedMutex.acquire();
			if(rootCrowdLoaded){
		    	Point mousePosition = new Point(MouseInfo.getPointerInfo().getLocation());
				SwingUtilities.convertPointFromScreen(mousePosition, this);
				
				for(int i = 0; i < rootCrowd.getMouseActionList().size(); i++){
					// if the mouse click is in the hitbox then peform the action
					if(rootCrowd.getMouseActionList().get(i).isActive() && rootCrowd.getMouseActionList().get(i).isInBounds(mousePosition.x,mousePosition.y)){
						rootCrowd.getMouseActionList().get(i).mI(mousePosition);
					}
					else if(rootCrowd.getMouseActionList().get(i).isActive() && !rootCrowd.getMouseActionList().get(i).isInBounds(mousePosition.x,mousePosition.y)){
						rootCrowd.getMouseActionList().get(i).mO(mousePosition);
					}
				}
			}
			loadedMutex.release();
    	}
    	catch(InterruptedException ie){
    		System.err.println("interrupedMouseEnter");
			Thread.currentThread().interrupt();
    	}
    }
    
    @Override
	public void keyReleased(KeyEvent e){
    	for(int i = 0; i < rootCrowd.getKeyboardActionList().size(); i++){
    		if(rootCrowd.getKeyboardActionList().get(i).isFocused()){
    			if(rootCrowd.getKeyboardActionList().get(i).kU(e))
					return;
    		}
    	}
    }

    @Override
	public void keyPressed(KeyEvent e){
    	for(int i = 0; i < rootCrowd.getKeyboardActionList().size(); i++){
    		if(rootCrowd.getKeyboardActionList().get(i).isFocused()){
    			if(rootCrowd.getKeyboardActionList().get(i).kD(e))
					return;
    		}
    	}
    }

	@Override
	public void keyTyped(KeyEvent e){
		for(int i = 0; i < rootCrowd.getKeyboardActionList().size(); i++){
			if(rootCrowd.getKeyboardActionList().get(i).isFocused()){
				if(rootCrowd.getKeyboardActionList().get(i).kP(e))
					return;
			}
    	}
	}
    
   @Override
   public void paint(Graphics g){

	   dBuffer = (Graphics2D) bufferS.getDrawGraphics();

	   // Draw background
	   if(rootCrowdLoaded){
		   dBuffer.setColor(backgroundColour);
		   dBuffer.fillRect(0,0,resolution[0],resolution[1]);
	   }
	   
	   dBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	   dBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	   
	   // If lists are loaded then draw to buffer
	   if(rootCrowdLoaded){
		   draw(rootCrowd.getDisplayList());
	   }
	   else{
		   drawLoading();
	   }
	   // Display strategy
	   bufferS.show();
	   
	   g.dispose();
	   dBuffer.dispose();
   }
	
	private void drawLoading() {
//		dBuffer.setFont(FontList.AUD24);
		dBuffer.setColor(Color.white);
		dBuffer.drawString("Loading", 5, resolution[1] - 10);
	}

	public void reloadDisplay(){
		this.repaint();
	}
	
	public void draw(ArrayList<Displayable> displayList){
		for(int i = 0; i < displayList.size(); i++){
			
			// Kicks tweens to their next step in animation
			displayList.get(i).kickTween();
			
			// Asks each item to call ActivePane draw methods to draw itself if visible
			if(displayList.get(i).isVisible()){
				displayList.get(i).drawMethod(dBuffer);
			}
		}
	}
	
	public void drawCrowd(Crowd crowd){
		draw(crowd.getDisplayList());
	}
	
	public boolean isWindowed(){
		return windowed < 2;
	}
	
	public Crowd getRootCrowd(){
		return rootCrowd;
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		closeWindow();
	}

	public void closeWindow() {
		try {
			loadedMutex.acquire();/*
			this.loaded = false;
			this.currentPane.removeOld();
			this.currentPane = null;
			DevMenu.getInstance().setVisible(true);*/
			System.exit(0);
			loadedMutex.release();
		} 
		catch (InterruptedException e1) {
			System.err.println("interrupedWIndowClose");
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}
	

}