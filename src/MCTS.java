import java.util.ArrayList;
import java.util.HashMap;

public class MCTS {

	Panel pane;
	HashMap<String,String> mapSimulation=new HashMap<String,String>();
	AlphaBeta2 AB;
	double c=Math.sqrt(2);//the exploration parameter—theoretically equal to √2; in practice usually chosen empirically;
	int t=0;// stands for the total number of simulations for the node considered. It is equal to the sum of all the ni.
	Node root;
	
	public MCTS(Panel pane){
		this.pane=pane;
		this.pane.setVisible(false);
		this.mapSimulation.putAll(pane.Map);
		this.AB= new AlphaBeta2();
		this.root = new Node("yellow");
		this.root.setState(mapSimulation);
	}
	
	
	public Node selection(){
		
		Node currentNode = this.root;
		boolean hasStats = true;
		
		while(hasStats){
			
		ArrayList<Node> childs = currentNode.getChilds();
		if(childs.size()==0){
			hasStats=!hasStats;
		}else{
			boolean childHasStats = false;
			for(int i=0;i<childs.size();i++){
				
				double bestSoFar =0;
				if(childs.get(i).getN()==0){
					return childs.get(i);
				}else if(childs.get(i).getN()>0){
					childHasStats=true;
					double utility=UCT1Utility(childs.get(i));
					if(utility>bestSoFar){
						bestSoFar=utility;
						currentNode = childs.get(i);
					}
					
					
				}
			}
			
			hasStats=childHasStats;
		}
		
		}
		System.out.println("Selection of this node");
		System.out.println(currentNode);
		return currentNode;
	}
	
	public Node expend(Node node){
		System.out.print("Expension of the node : ");
		System.out.println(node);
		String turn;
		if(node.getTurn()=="yellow"){
			turn="green";
		}else{
			turn="yellow";
		}
			ArrayList<Action> possibleActions = getAllPossibilities(node.state,turn);
			for(int i =0;i<possibleActions.size();i++){
				Node n = new Node(possibleActions.get(i));
				node.addChild(n);
				//System.out.println(n);
				//n.printState();
			}
		
		
		if(possibleActions.size()>0){
			int randomNum =  (int)(Math.random() * possibleActions.size()); 
			
			return node.getChildAt(randomNum);
		}else{
			return null;
		}
	}
	
	
	public String simulate(Node startNode){
		System.out.println("Start simulation from this node " + startNode);
		HashMap<String,String> originalMap = startNode.state;
		t++;
		//first move chosen
		this.AB.seuil=1;
		Action a = startNode.a;
		String pion = a.getPosDepart();
		String dest = a.getPosArrive();
		String mode = a.getTypeDeplacement();
		HashMap<String,String> newSimulation = simulationRemplissageCopy(originalMap,pion,dest,mode);
		
		String currentTurn = startNode.getTurn();
		
		int greenPoints=0;
		int yellowPoints=0;
		int count=0;
		while(true){
			count++;
			 greenPoints=getColorPoints(newSimulation, "green").size();
			//System.out.println("green points : " + greenPoints);
			
			 yellowPoints = getColorPoints(newSimulation, "yellow").size();
			//System.out.println("yellow points : " + yellowPoints);
			
		String opponent;
		if(currentTurn=="green"){
			currentTurn="yellow";
			opponent="green";
		}else{
			currentTurn="green";
			opponent="yellow";
		}
		
		Action aS = this.AB.alphaBetaDecision(newSimulation,currentTurn);
		String pionS = aS.getPosDepart();
		String destS = aS.getPosArrive();
		String modeS = aS.getTypeDeplacement();
		simulationRemplissageNoCopy(newSimulation,pionS,destS,modeS);
		//System.out.println(aS);
		
		if(getAllPossibilities(newSimulation,opponent).size()==0||count>100){
			break;
		}
		}
		
		
		String winner = greenPoints>yellowPoints?"green":"yellow";
		System.out.println("winner of the simulation : " + winner);
		return winner;
	}
	
	
	public void backPropagate(Node endNode, String winner){
		System.out.println("backpropagate from " +endNode);
		Node currentNode=endNode;
		
		while(true){
			
			currentNode.incrementN();
			if(currentNode.getTurn()==winner){
				currentNode.incrementW();
			}
			System.out.println(currentNode);
			
			if(currentNode.isRoot){
				break;
			}
			
			currentNode=currentNode.getMotherNode();
			
		}
		
		System.out.println("end backpropagate ");
	}
	
	public double UCT1Utility(Node node){
		double utility = ((double)node.getW()/(double)node.getN()) + this.c * (Math.sqrt(Math.log(this.t)/(double)node.getN()));
		//.out.println(utility);
		//System.out.println("W : "+(double)node.getW()+ " N : "+(double)node.getN()+" Logt : "+Math.log(this.t)  +" UCT1 : " + utility);
		return utility;
	}
	
	public ArrayList<String> PlayABOpponent(){
		this.AB.seuil=3;
		ArrayList<String> choice= new ArrayList<String>();
		Action a = this.AB.alphaBetaDecision(mapSimulation,"yellow");
		String pion = a.getPosDepart();
		String dest = a.getPosArrive();
		String mode = a.getTypeDeplacement();
		choice.add(pion);	
		choice.add(mode);
		choice.add(dest);		
		simulationRemplissageNoCopy(mapSimulation,pion,dest,mode);
		System.out.println(choice);
		return choice;
	}
	
	public ArrayList<String> PlayABMCTS(){
		this.AB.seuil=2;
		ArrayList<String> choice= new ArrayList<String>();
		Action a = this.AB.alphaBetaDecision(mapSimulation,"green");
		String pion = a.getPosDepart();
		String dest = a.getPosArrive();
		String mode = a.getTypeDeplacement();
		choice.add(pion);	
		choice.add(mode);
		choice.add(dest);	
		simulationRemplissageNoCopy(mapSimulation,pion,dest,mode);
		System.out.println(choice);
		return choice;
	}
	
	public static String getColor(HashMap<String,String> Simulation,int x,int y){
		String xS=x>=0?"+"+x:""+x;
		String yS=y>=0?"+"+y:""+y;
		return Simulation.get(""+xS+yS);
	}

	public static void change(HashMap<String,String> Simulation,int x,int y, String color){
		String xS=x>=0?"+"+x:""+x;
		String yS=y>=0?"+"+y:""+y;
		Simulation.put(""+xS+yS, color);
	}

	public static ArrayList<String> clonePossibilitiesList(HashMap<String,String> Simulation,String s){
		int xs=Integer.parseInt(s.substring(0,2));
		int ys=Integer.parseInt(s.substring(2,4));
		ArrayList<String> cloneList=new ArrayList<String>();
		if(getColor(Simulation,xs,ys)=="yellow"||getColor(Simulation,xs,ys)=="green"){
			for(int i=xs-1;i<=xs+1;i++){
				for(int j=ys-1;j<=ys+1;j++){
					if(!(i==xs+1&&j==ys+1)&&!(i==xs-1&&j==ys-1)){
						String xS=i>=0?"+"+i:""+i;
						String yS=j>=0?"+"+j:""+j;
						if(getColor(Simulation,i,j)=="purple"){
							cloneList.add(""+xS+yS);
						}
					}
				}
			}
		}
		return cloneList;
	}

	public static HashMap<String,ArrayList<String>> describeEnvironnement(HashMap<String,String> Simulation,String xy){
		ArrayList<String> purpleList=new ArrayList<String>();
		ArrayList<String> yellowList=new ArrayList<String>();
		ArrayList<String> blackList=new ArrayList<String>();
		ArrayList<String> greenList=new ArrayList<String>();
		HashMap<String,ArrayList<String>> map=new 	HashMap<String,ArrayList<String>>();
		int xs=Integer.parseInt(xy.substring(0, 2));
		int ys=Integer.parseInt(xy.substring(2, 4));
		for(int i=xs-1;i<=xs+1;i++){
			for(int j=ys-1;j<=ys+1;j++){
				if(!(i==xs+1&&j==ys+1)&&!(i==xs-1&&j==ys-1)&&!(i==xs&&j==ys)){
					if(getColor(Simulation,i,j)!=null){
						String xS=i>=0?"+"+i:""+i;
						String yS=j>=0?"+"+j:""+j;
						switch(getColor(Simulation,i,j)){
						case "purple":
							purpleList.add(""+xS+yS);
							break;
						case "black":
							blackList.add(""+xS+yS);
							break;
						case "yellow":
							yellowList.add(""+xS+yS);
							break;
						case "green":
							greenList.add(""+xS+yS);
							break;
						}
					}
				}
			}
		}
		map.put("purple", purpleList);
		map.put("black", blackList);
		map.put("yellow", yellowList);
		map.put("green", greenList);
		/*
	System.out.println("purple : " +map.get("purple").toString());
	System.out.println("black : " +map.get("black").toString());
	System.out.println("green : " +map.get("green").toString());
	System.out.println("yellow : " +map.get("yellow").toString());
		 */
		return map;
	}

	public static ArrayList<String> jumpPossibilitiesList(HashMap<String,String> Simulation,String s){
		int xs=Integer.parseInt(s.substring(0,2));
		int ys=Integer.parseInt(s.substring(2,4));
		ArrayList<String> jumpList=new ArrayList<String>();
		if(getColor(Simulation,xs,ys)=="yellow"||getColor(Simulation,xs,ys)=="green"){
			for(int i=xs-2;i<=xs+2;i++){
				for(int j=ys-2;j<=ys+2;j++){
					if((i==xs&&j==ys+2)||(i==xs+1&&j==ys+1)||(i==xs+2&&j==ys)||(i==xs+2&&j==ys-1)||(i==xs+2&&j==ys-2)||(i==xs+1&&j==ys-2)||(i==xs&&j==ys-2)||(i==xs-1&&j==ys-1)||(i==xs-2&&j==ys)||(i==xs-2&&j==ys+1)||(i==xs-2&&j==ys+2)||(i==xs-1&&j==ys+2)){
						String xS=i>=0?"+"+i:""+i;
						String yS=j>=0?"+"+j:""+j;
						if(getColor(Simulation,i,j)=="purple"){
							jumpList.add(""+xS+yS);
						}
					}
				}
			}
		}
		return jumpList;
	}


	public static HashMap<String,ArrayList<String>> describeEnvironnementDegre2(HashMap<String,String> Simulation,String s){
		int xs=Integer.parseInt(s.substring(0,2));
		int ys=Integer.parseInt(s.substring(2,4));

		ArrayList<String> purpleList=new ArrayList<String>();
		ArrayList<String> yellowList=new ArrayList<String>();
		ArrayList<String> blackList=new ArrayList<String>();
		ArrayList<String> greenList=new ArrayList<String>();
		HashMap<String,ArrayList<String>> map=new 	HashMap<String,ArrayList<String>>();



		for(int i=xs-2;i<=xs+2;i++){
			for(int j=ys-2;j<=ys+2;j++){
				if((i==xs&&j==ys+2)||(i==xs+1&&j==ys+1)||(i==xs+2&&j==ys)||(i==xs+2&&j==ys-1)||(i==xs+2&&j==ys-2)||(i==xs+1&&j==ys-2)||(i==xs&&j==ys-2)||(i==xs-1&&j==ys-1)||(i==xs-2&&j==ys)||(i==xs-2&&j==ys+1)||(i==xs-2&&j==ys+2)||(i==xs-1&&j==ys+2)){
					String xS=i>=0?"+"+i:""+i;
					String yS=j>=0?"+"+j:""+j;
					if(getColor(Simulation,i,j)!=null){
						switch(getColor(Simulation,i,j)){
						case "purple":
							purpleList.add(""+xS+yS);
							break;
						case "black":
							blackList.add(""+xS+yS);
							break;
						case "yellow":
							yellowList.add(""+xS+yS);
							break;
						case "green":
							greenList.add(""+xS+yS);
							break;
						}
					}
				}
			}

		}

		System.out.println("Describe environnement degré 2");
		System.out.println("purple : " +purpleList.toString());
		System.out.println("black : " +blackList.toString());
		System.out.println("green : " +greenList.toString());
		System.out.println("yellow : " +yellowList.toString());

		return map;
	}

	public static HashMap<String,String> simulationRemplissageNoCopy(HashMap<String,String> Simulation,String pion, String Dest, String mode){

		String tourJoueurAdv, tourJoueur;

		if(Simulation.get(pion)=="yellow"){

			tourJoueur="yellow";
			tourJoueurAdv="green";

		}else{

			tourJoueur="green";
			tourJoueurAdv="yellow";

		}

		int xs=Integer.parseInt(Dest.substring(0,2));
		int ys=Integer.parseInt(Dest.substring(2,4));

		if(mode=="1"){
			change(Simulation,Integer.parseInt(pion.substring(0,2)),Integer.parseInt(pion.substring(2,4)),"purple");
		}

		change(Simulation,xs,ys,tourJoueur);


		if(getColor(Simulation,xs-1,ys)==tourJoueurAdv)
			change(Simulation,xs-1,ys,tourJoueur);
		if(getColor(Simulation,xs,ys-1)==tourJoueurAdv)
			change(Simulation,xs,ys-1,tourJoueur);
		if(getColor(Simulation,xs+1,ys)==tourJoueurAdv)
			change(Simulation,xs+1,ys,tourJoueur);
		if(getColor(Simulation,xs,ys+1)==tourJoueurAdv)
			change(Simulation,xs,ys+1,tourJoueur);
		if(getColor(Simulation,xs-1,ys+1)==tourJoueurAdv)
			change(Simulation,xs-1,ys+1,tourJoueur);
		if(getColor(Simulation,xs+1,ys-1)==tourJoueurAdv)
			change(Simulation,xs+1,ys-1,tourJoueur);
		return Simulation;

	}

	public static HashMap<String,String> simulationRemplissageCopy(HashMap<String,String> Simulation,String pion, String Dest, String mode){

		String tourJoueurAdv, tourJoueur;


		HashMap<String,String> sim= new HashMap<String,String>();


		sim.putAll(Simulation);

		if(Simulation.get(pion)=="yellow"){

			tourJoueur="yellow";
			tourJoueurAdv="green";

		}else{

			tourJoueur="green";
			tourJoueurAdv="yellow";

		}

		int xs=Integer.parseInt(Dest.substring(0,2));
		int ys=Integer.parseInt(Dest.substring(2,4));

		if(mode=="1"){

			change(sim,Integer.parseInt(pion.substring(0,2)),Integer.parseInt(pion.substring(2,4)),"purple");

		}



		change(sim,xs,ys,tourJoueur);


		if(getColor(sim,xs-1,ys)==tourJoueurAdv)
			change(sim,xs-1,ys,tourJoueur);
		if(getColor(sim,xs,ys-1)==tourJoueurAdv)
			change(sim,xs,ys-1,tourJoueur);
		if(getColor(sim,xs+1,ys)==tourJoueurAdv)
			change(sim,xs+1,ys,tourJoueur);
		if(getColor(sim,xs,ys+1)==tourJoueurAdv)
			change(sim,xs,ys+1,tourJoueur);
		if(getColor(sim,xs-1,ys+1)==tourJoueurAdv)
			change(sim,xs-1,ys+1,tourJoueur);
		if(getColor(sim,xs+1,ys-1)==tourJoueurAdv)
			change(sim,xs+1,ys-1,tourJoueur);

		return sim;

	}
	
	
	public static ArrayList<Action> getAllPossibilities(HashMap<String,String> Simulation, String color){
		ArrayList<String> allColorPoints = getColorPoints(Simulation,color);
		ArrayList<Action> allPossibilities = new ArrayList<Action> ();
		
		for(int i=0;i<allColorPoints.size();i++){
			ArrayList<String> cloneList = clonePossibilitiesList(Simulation,allColorPoints.get(i));
			ArrayList<String> jumpList = jumpPossibilitiesList(Simulation,allColorPoints.get(i));
			
	
			
			for(int k=0;k<jumpList.size();k++){
				allPossibilities.add(new Action(allColorPoints.get(i),jumpList.get(k),"1"));
			}
	
			
			
			for(int j=0;j<cloneList.size();j++){
				allPossibilities.add(new Action(allColorPoints.get(i),cloneList.get(j),"0"));
			}
			
		
			
		}
		
		return allPossibilities;
	}

	public static ArrayList<String> getColorPoints(HashMap<String,String> Simulation,String color){
		int caseTab=0;
		ArrayList<String> YellowKeys= new ArrayList<String>();
		for(int i=-4;i<=4;i++){
			int min=0,max=0;
			switch(i){
			case -4:
				min=0;max=4;
				break;
			case -3:
				min=-1;max=4;
				break;
			case -2:
				min=-2;max=4;
				break;
			case -1:
				min=-3;max=4;
				break;
			case 0:
				min=-4;max=4;
				break;
			case 1:
				min=-4;max=3;
				break;
			case 2:
				min=-4;max=2;
				break;
			case 3:
				min=-4;max=1;
				break;
			case 4:
				min=-4;max=0;
				break;
			}
			for(int j=min;j<=max;j++){
				if(getColor(Simulation,i,j)==color){
					String xS=i>=0?"+"+i:""+i;
					String yS=j>=0?"+"+j:""+j;
					YellowKeys.add(""+xS+yS);
				}
			}
		}
		return YellowKeys;
	}
	
	
}
