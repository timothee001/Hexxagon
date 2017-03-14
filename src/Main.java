import java.util.Timer;
import java.util.TimerTask;


public class Main {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//Panel pane =new Panel();
//		while(pane.getSumScore()<58){
//			pane.iterateOneClick();
//			System.out.println(pane.getSumScore());
//			Thread.sleep(100);
//		}
		
		
		
		Panel pane =new Panel();
		MCTS mcts = new MCTS(pane);
		
		Node node= new Node("green");
		Action a = mcts.AB.alphaBetaDecision(mcts.mapSimulation, "green");
		node.a=a;
		
		mcts.simulate(mcts.mapSimulation, node);
		
		
//		System.out.println(mcts.getAllPossibilities(mcts.mapSimulation, "green").toString());
//		
//		int totalSumPoints=0;
//		
		
		
//		while(totalSumPoints<58){
//			mcts.PlayABMCTS();
//			
//			int greenPoints=mcts.getColorPoints(mcts.mapSimulation, "green").size();
//			System.out.println("green points : " + greenPoints);
//			mcts.PlayABOpponent();
//			int yellowPoints = mcts.getColorPoints(mcts.mapSimulation, "yellow").size();
//			System.out.println("yellow points : " + yellowPoints);
//
//			totalSumPoints = greenPoints+yellowPoints;
//		}
//		
//		
		
		
		
		
		
	}
}
