package randomGraphGenerator;

public class Haupt {
	public static void main(String[] args){
		
		for(int i = 0; i < 10; i++){
			RandomGraphGenerator r = new RandomGraphGenerator(250, 0.8);
			r.createGraph();
			if(r.testCohesion()){
				r.writeInFile();
			}
			r.raiseK();
		}
	}
}
