package game;

import java.util.Comparator;
import java.util.TreeSet;

public class TreeTest {

	private static class Pair {
		private int number;
		private God text;
		
		public Pair(God text, int number) {
			this.number = number;
			this.text = text;
		}
		
		@Override
		public boolean equals(Object obj) {
			
			if(!(obj instanceof Pair))
				return false;
			
			Pair otherPair = (Pair) obj;
			
			if(this.number == otherPair.number && this.text.equals(otherPair.text))
				return true;
			
			return false;
		}
	}
	
	TreeSet<Pair> tree = new TreeSet<Pair>(new Comparator<Pair>() {
		
		public int compare(Pair o1, Pair o2) {
			if(o1.equals(o2))
				return 0;
			return 1;
		};
	});
	
	public static void main(String[] args) {
		
		TreeTest test = new TreeTest();
	}
	
	TreeTest() {
		tree.add(new Pair(God.butter, 1));
		tree.add(new Pair(God.butter, 1));
		tree.add(new Pair(God.butter, 1));
		tree.add(new Pair(God.debauchery, 1));
		tree.add(new Pair(God.butter, 1));
		tree.add(new Pair(God.butter, 1));
		tree.add(new Pair(God.debauchery, 1));
		tree.add(new Pair(God.butter, 2));
		
		System.out.println(tree.size());
	}
	
}
