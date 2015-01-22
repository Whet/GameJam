package watoydoEngine.fonts;


public class FontList {
	
	static{
		
//		Font dynamicFont = null;
//	    
//		try {
//			dynamicFont = Font.createFont(Font.TRUETYPE_FONT, ReadWriter.getResourceAsInputStream("fonts/AUDIMRG_.TTF"));
//		} catch (FontFormatException | IOException e) {
//			e.printStackTrace();
//			System.exit(-1);
//		}
//	   
//		AUD14 = dynamicFont.deriveFont(14f);
	}
	
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
