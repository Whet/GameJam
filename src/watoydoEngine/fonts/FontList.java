package watoydoEngine.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import watoydoEngine.io.ReadWriter;


public class FontList {
	
	static{
		
		Font dynamicFont = null;
	    
		try {
			dynamicFont = Font.createFont(Font.TRUETYPE_FONT, ReadWriter.getResourceAsInputStream("fonts/Chunkfive.ttf"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	   
		STANDARD_FONT = dynamicFont.deriveFont(24f);
	}
	
	public static Font STANDARD_FONT;
	
	public static String digitString(int digits, int number) {
		
		int length = (int)(Math.log10(number)+1);
		
		if(length > digits)
			return Integer.toString(number);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(number);
		
		for(int i = 0; i < digits - length; i++) {
			sb.append(" ");
		}
		
		return sb.toString();
	}
	
}
