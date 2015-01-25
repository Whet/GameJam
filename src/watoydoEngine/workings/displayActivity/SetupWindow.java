package watoydoEngine.workings.displayActivity;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class SetupWindow extends JFrame{
	
	private static SetupWindow instance;
	
	public static SetupWindow getInstance(){
		if(instance == null){
			instance = new SetupWindow();
		}
		return instance;
	}
	
	private JButton windowed, start, exit;
	private JComboBox<String> resolution;
	private int windowedMode;
	private int[] resolutionMode;
	private final int[] DIMENSIONS = {590,248};

	private SetupWindow(){
		
		windowedMode = 0;
		resolutionMode = new int[2];
		resolutionMode[0] = 1280;
		resolutionMode[1] = 720;
		
		this.setUndecorated(true);
		
		// sets up window properties
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(DIMENSIONS[0], DIMENSIONS[1]);
		this.setResizable(false);
		
		// Centralise setup window on current display device
		this.setLocation((int)(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().x - DIMENSIONS[0] * 0.5)
						,(int)(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().y - DIMENSIONS[1] * 0.5));
		
		// Set window logo to watoydo logo
		this.setTitle("GameJam Setup");
		
//		Image watLogo = null;
//		Image setupImage = null;
//		ImageIcon atrophyIcon = null;
//		
//		try{
//			watLogo = ImageIO.read(ReadWriter.getResourceAsInputStream("images/watoydo/watoydoLogo.jpg"));
//			setupImage = ImageIO.read(ReadWriter.getResourceAsInputStream("images/atrophy/setupImage.png"));
//		}
//		catch(IOException ioexcept){
//			System.err.println("watoydo window logo not found mods folder likely out of place, terminating");
//			System.exit(-1);
//		}
//		this.setIconImage(watLogo);
//		
//		JLabel setupImageLabel = new JLabel(new ImageIcon(setupImage));
		
		// Init buttons
		windowed = new JButton("Windowed");
		resolution = new JComboBox<>();
		start = new JButton("Start");
		exit = new JButton("Exit");
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		// Init default settings on activePane
		ActivePane.getInstance().setMode(resolutionMode,windowedMode);
		
		// Generates window content
		Container content = this.getContentPane();
		
		BorderLayout grid = new BorderLayout();
		
		content.setLayout(grid);
		
		JPanel buttonsContainer = new JPanel();
		
		content.add(buttonsContainer, BorderLayout.SOUTH);
		
//		content.add(new JLabel(atrophyIcon));
		
		content.setBackground(Color.white);
		
		GridLayout buttonsLayout = new GridLayout(1,5);
		
		buttonsContainer.setLayout(buttonsLayout);
		
		buttonsContainer.add(windowed);

		buttonsContainer.add(resolution);
		
		buttonsContainer.add(start);
		buttonsContainer.add(exit);
		
//		this.add(setupImageLabel, BorderLayout.CENTER);
		
		resolution.addItem("1280 720");
		resolution.addItem("1280 800");
		resolution.addItem("1366 768");
		resolution.addItem("1600 900");
		resolution.addItem("1920 1080");
		resolution.setSelectedIndex(0);
		
		// Listeners
		windowed.addActionListener(new ChangeWindowed());
		resolution.addActionListener(new ChangeResolution());
		start.addActionListener(new StartGame());
		
		buttonsContainer.setVisible(true);
		this.setVisible(true);
	}
	
	
	private class ChangeWindowed implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			
			windowedMode++;
			
			if(windowedMode > 2)
				windowedMode = 0;
			
			if(windowedMode == 0){
				windowed.setText("Windowed");
			}
			else if(windowedMode == 1) {
				windowed.setText("Borderless Window");
			}
			else{
				windowed.setText("Fullscreen");
			}
			ActivePane.getInstance().setMode(resolutionMode,windowedMode);
		}
	}
	
	private class ChangeResolution implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch((String)resolution.getSelectedItem()){
				case "1280 720":
					resolutionMode = new int[]{1280,720};
				break;
				case "1280 800":
					resolutionMode = new int[]{1280,800};
				break;
				case "1366 768":
					resolutionMode = new int[]{1366,768};
				break;
				case "1600 900":
					resolutionMode = new int[]{1600,900};
				break;
				case "1920 1080":
					resolutionMode = new int[]{1920,1080};
				break;
			}
			ActivePane.getInstance().setMode(resolutionMode,windowedMode);
		}
		
	}
	
	// Happens when start is pressed, applies the graphical settings where needed
	private class StartGame implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			ActivePane.getInstance().setDisplay();
			ActivePane.getInstance().setup();
			setVisible(false);
		}
	}
	
}