package watoydoEngine.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadWriter{
	
	public static String HOME_LOCATION;
	private static final Character ESCAPE_COMMA = '~';
	
	public static String readFromArray(String inputline, int index){
		if(!inputline.equals("")){
			char[] inputChars = new char[inputline.length()];
			
			inputChars = inputline.toCharArray();
			
			StringBuilder sb = new StringBuilder();
			
			int indexCount = -1;
			
			String returnString = "";
			
			boolean ignoreComma = false;
			
			// Array written as [i1,i2,i3]
			// [] are ignored
			// at every , delete the current entry and increase the indexCount
			
			// skip past any commands before brackets e.g addButton[], addButton should be ignored
			int j = 0;
			while(!Character.toString(inputChars[j]).equals("[")){
			j++;
			}
			
			// have to set i=i for formatting issues
			for(int i = j; i < inputChars.length; i++){
				if(Character.toString(inputChars[i]).equals("[") || (Character.toString(inputChars[i]).equals(",") && !ignoreComma) || Character.toString(inputChars[i]).equals("]")){
					if(indexCount == index){
						returnString = sb.toString();
						return returnString;
					}
					else{
						// Empty buffer
						sb.delete(0, sb.length());
						indexCount++;
					}
				}
				else{
					
					if(inputChars[i] == ESCAPE_COMMA){
						ignoreComma = !ignoreComma;
						continue;
					}
					
					sb.append(Character.toString(inputChars[i]));
				}
			}
		}
		
		return "";
		
	}
	
	// get size of a text array by counting commas
	// it is assumed that the entry is an array
	public static int getArraySize(String inputline){
		
		int entries = 1;
		
		char[] inputChars = new char[inputline.length()];
		
		inputChars = inputline.toCharArray();
		
		boolean ignoreComma = false;
		
		for(int i = 0; i < inputChars.length; i++){
			// every comma states there is another entry in the array
			if(inputChars[i] == ',' && !ignoreComma){
				++entries;
			}
			else if(inputChars[i] == ESCAPE_COMMA){
				ignoreComma = !ignoreComma;
			}
		}
		return entries;
	}
	
	// Code from anon on /v/idya gamedev thread
	public static InputStream getResourceAsInputStream(String resource) throws FileNotFoundException {
    	
		InputStream resourceInputStream = ReadWriter.class.getClassLoader().getResourceAsStream("watoydoEngine/mods/"+resource);
		
		if (resourceInputStream == null){
			File file = new File(resource);
			if (file.exists()) {
				resourceInputStream = new FileInputStream(file);
			}
		}
		
		if (resourceInputStream == null){
			throw new FileNotFoundException(resource);
		}
		
		return resourceInputStream;
	}
	
	public static File getRootFile(String resource) throws FileNotFoundException {

		String homeLocation = HOME_LOCATION;
		File file =  new File(homeLocation + "/" + resource);
		
		if(!file.exists())
			throw new FileNotFoundException();
		
		return file;
	}
	
	public static String readFromFile(File file, int line) throws IOException{
		
		BufferedReader in = null;
		String returnString = "";
		try{
           in = new BufferedReader(new FileReader(file));
		   for(int i = 0; i < line; i++){
				in.readLine();
		   }
		   returnString = in.readLine();
		   in.close();
		  
        }
		finally{
			in.close();
		}
		
		return returnString;
	}

	public static String readFromFile(InputStream inputStream, int line) throws IOException{
		BufferedReader in = null;
		String returnString = "";
		try{
           in = new BufferedReader(new InputStreamReader(inputStream));
		   for(int i = 0; i < line; i++){
				in.readLine();
		   }
		   returnString = in.readLine();
		   in.close();
		  
        }
		finally{
			in.close();
		}
		
		return returnString;
	}

}
