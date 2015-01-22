package watoydoEngine.workings;

import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import watoydoEngine.io.ReadWriter;
import watoydoEngine.workings.displayActivity.SetupWindow;


public class Main{
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		@SuppressWarnings("unused")
		Main main = new Main();
	}
	
	public Main() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		
		ReadWriter.HOME_LOCATION = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		if(ReadWriter.HOME_LOCATION.endsWith("Atrophy.jar"))
			ReadWriter.HOME_LOCATION = ReadWriter.HOME_LOCATION.substring(0, ReadWriter.HOME_LOCATION.length() - "Atrophy.jar".length());
		
		UIManager.put("nimbusBase", new Color(20,20,20));
		UIManager.put("nimbusBlueGrey", new Color(50, 50, 50));
		UIManager.put("control", new Color(90, 90, 90));
		
		for (LookAndFeelInfo laf:UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(laf.getName())) {
				UIManager.setLookAndFeel(laf.getClassName());
				break;
			}
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				SetupWindow.getInstance();
			}
		});
	}

}