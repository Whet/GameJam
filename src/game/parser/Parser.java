package game.parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;


public class Parser {
	private ArrayList<Solution> solutions;
	
	public static void main(String[] args) {
		Parser p = new Parser(new File("dummySolution.txt"));
		ObjectGodPair[] pairs = new ObjectGodPair[4];
		pairs[0] = new ObjectGodPair(1, 1);
		pairs[1] = new ObjectGodPair(2, 2);
		pairs[2] = new ObjectGodPair(3, 3);
		pairs[3] = new ObjectGodPair(4, 4);
		System.out.println(p.getStoryLine(pairs));
	}
	
	public Parser(File file) {
		ArrayList<String> lines = read(file);
		solutions = parse(lines);
		
		for(Solution s : solutions) {
//			System.out.println(s.getStory());
			ArrayList<ObjectGodPair> idHolders = s.getPairs();
			for(ObjectGodPair ih : idHolders) {
//				System.out.println(ih.objectID + " " + ih.godID.name());
			}
		}
	}
	
	public String getStoryLine(ObjectGodPair[] pairs) {

		TreeSet<ObjectGodPair> tree = new TreeSet<ObjectGodPair>(new Comparator<ObjectGodPair>() {
			@Override
			public int compare(ObjectGodPair o1, ObjectGodPair o2) {
				if(o1.equals(o2))
					return 0;
				return 1;
			};
		}) {
			@Override
			public boolean add(ObjectGodPair e) {
				for(ObjectGodPair pair:this) {
					if(e.equals(pair))
						return false;
				}
				return super.add(e);
			}
		};
		
		for(Solution sol : solutions) {
			for(ObjectGodPair ogp : sol.getPairs()){
				tree.add(ogp);
			}
		}
		
		System.out.println("The tree contains:");
		for(ObjectGodPair ogp : tree) {
			System.out.println(ogp);
		}
		System.out.println();
		
		for(ObjectGodPair ogp : pairs) {
			System.out.println("Tree contains " + ogp.toString() + " is " + tree.contains(ogp));
			tree.add(ogp);
		}
		
		System.out.println("The tree contains:");
		for(ObjectGodPair ogp : tree) {
			System.out.println(ogp);
		}
		System.out.println();
		
		System.out.println(tree.size());
		
//		TreeSet<ObjectGodPair> pairSet = new TreeSet<ObjectGodPair>(pairComp);
//		for(Solution sol : solutions) {
//			System.out.println("Add given pairs");
//			for(ObjectGodPair pair : pairs) {
//				pairSet.add(pair);
////				System.out.println(pair);
//			}
//			
//			System.out.println();
//			for(ObjectGodPair ogp : pairSet) {
//				System.out.println(ogp);
//			}
//			System.out.println();
//			
//			System.out.println("Add solution pairs");
//			for(ObjectGodPair solutionPair : sol.getPairs()) {
//				System.out.println("Adding " + solutionPair);
//				boolean added = pairSet.add(solutionPair);
//				if(added) System.out.println("ADDED");
//				System.out.println();
////				System.out.println(solutionPair);
//			}
//			System.out.println("---");
//			for(ObjectGodPair ogp : pairSet) {
//				System.out.println(ogp);
//			}
//			System.out.println("---");
//			System.out.println(pairSet.size());
//			System.out.println();
//			if(pairSet.size() == 4) {
//				return sol.getStory();
//			}
//		}
		
		return "";
	}
	
	private ArrayList<Solution> parse(ArrayList<String> lines) {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		for(String line : lines) {
			String[] splitLines = line.split(";");
			String scenario = splitLines[0];
			String story = splitLines[2];
			
			String[] idStrings = splitLines[1].split(",");
			ArrayList<ObjectGodPair> pairs = new ArrayList<ObjectGodPair>();
			for(int i=0; i<idStrings.length; i += 2) {
				Integer objectIdString = Integer.valueOf(idStrings[i]);
				Integer godIdString = Integer.valueOf(idStrings[i+1]);
				ObjectGodPair ih = new ObjectGodPair(objectIdString, godIdString);
				pairs.add(ih);
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
