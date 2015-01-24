package game.parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Parser {

	public static void main(String[] args) {
		Parser p = new Parser(new File("dummySolution.txt"));
	}
	
	public Parser(File file) {
		ArrayList<String> lines = read(file);
		ArrayList<Solution> solutions = parse(lines);
		
		for(Solution s : solutions) {
			System.out.println(s.getStory());
			IdHolder[] idHolders = s.getPairs();
			for(IdHolder ih : idHolders) {
				System.out.println(ih.objectID + " " + ih.godID);
			}
		}
	}
	
	private ArrayList<Solution> parse(ArrayList<String> lines) {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		for(String line : lines) {
			String[] splitLines = line.split(";");
			String scenario = splitLines[0];
			String story = splitLines[2];
			
			String[] idStrings = splitLines[1].split(",");
			IdHolder[] pairs = new IdHolder[(idStrings.length / 2)];
			for(int i=0; i<idStrings.length; i += 2) {
				Integer objectIdString = Integer.valueOf(idStrings[i]);
				Integer godIdString = Integer.valueOf(idStrings[i+1]);
				IdHolder ih = new IdHolder(objectIdString, godIdString);
				pairs[i/2] = ih;
			}
			solutions.add(new Solution(scenario, story, pairs));
		}
		return solutions;
	}

	public ArrayList<String> read(File file) {
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}
}
