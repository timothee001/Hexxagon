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
		
		for(int i=0;i<1000;i++){
			Node selected = mcts.selection();
			//selected.printState();
			Node expand = mcts.expend(selected);
			String resultSim = mcts.simulate(expand);
			mcts.backPropagate(expand, resultSim);
			
			System.out.println(mcts.root);
		}
		
		for(int i=0;i<mcts.root.getChildsCount();i++){
			System.out.println(mcts.root.getChildAt(i));
		}
	
		
//		Node nodeSelected = mcts.selection();
//		Node startNode = mcts.expend(nodeSelected);
//		String winner = mcts.simulate(mcts.mapSimulation, startNode);
//		mcts.backPropagate(startNode, winner);
//		System.out.println(mcts.root);
		
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
