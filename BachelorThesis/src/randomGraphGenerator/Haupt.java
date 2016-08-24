package randomGraphGenerator;

public class Haupt {
	public static void main(String[] args){
		RandomGraphGenerator r = new RandomGraphGenerator(500, 0.6);
		r.createGraph();
		System.out.println(r.testCohesion());
//		r.print();
		r.writeInFile();
	}
}
