import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class Node {

	boolean isRoot=false;
	int wi= 0;// stands for the number of wins after the i-th move;
	int ni = 0; //stands for the number of simulations after the i-th move;
	Node mothernode=null;
	ArrayList<Node> childs = new ArrayList<Node>();
	Action a;
	String turn=null;
	HashMap<String,String> state; 
	String uniqueID = UUID.randomUUID().toString().split("-")[0];
	
	public Node(String colorTurnRoot){
		this.turn=colorTurnRoot;
		this.isRoot=true;
	}
	
	public void setState(HashMap<String,String> state){
		this.state=state;
	}
	
	public Node(Action a){
		this.turn=turn;
		this.a=a;
	}
	
	public void printState(){
		
		HashMap<String,String> prin = new HashMap<String,String> ();
		prin.put("purple", "");
		prin.put("black", "");
		prin.put("yellow", "");
		prin.put("green", "");
		
		for (Entry<String,String> entry : state.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  //System.out.println(key + " : " + value);
			  
			  prin.put(value,prin.get(value) + ", " + key);
			  
		}
		
		for (Entry<String,String> entry : prin.entrySet()) {
			String key = entry.getKey();
			  String value = entry.getValue();
			  System.out.println(key + " : " + value);
			
		}
	}
	
	
	
	public boolean isLeaf(){

		
		return this.childs.size()==0;
	}
	
	public void setMotherNode(Node node){
		this.mothernode=node;
	}
	
	public Node getMotherNode(){
		return this.mothernode;
	}
	
	public void incrementW(){
		this.wi++;
	}
	
	public void incrementN(){
		this.ni++;
	}
	

	public int getW(){
		return this.wi;
	}
	
	public int getN(){
		return this.ni;
	}
	
	public String getTurn(){
		return this.turn;//green or yellow
	}
	
	public void addChild(Node child){
		if(this.turn=="yellow"){
			child.turn="green";
		}else{
			child.turn="yellow";
		}
		this.childs.add(child);
		child.setMotherNode(this);
		
		//System.out.println("this state : "  + this.uniqueID);this.printState();
		child.setState(MCTS.simulationRemplissageCopy(this.state, child.a.getPosDepart(), child.a.getPosArrive(), child.a.getTypeDeplacement()));
		//System.out.println("child state : " + child.uniqueID);child.printState();
	}
	
	
	public int getChildsCount(){
		return this.childs.size();
	}
	
	public ArrayList<Node> getChilds(){
		return this.childs;
	}
	
	
	
	public Node getChildAt(int i){
		if(i <this.childs.size()){
			return this.childs.get(i);
		}
		return null;
	}
	
	public String toString(){
		if(!this.isRoot){
			return "id : " +this.uniqueID + " MotherId : "+ this.mothernode.uniqueID + " turn : "+this.turn +" W : " + this.getW() + " N : "+this.getN()+ " Childs # : "+this.getChildsCount() + " Action : "+this.a 
					+" greenPoints : "+ MCTS.getColorPoints(this.state, "green").size()+" yellowPoints : "+ MCTS.getColorPoints(this.state, "yellow").size()+
					" UCT1 : "+ MCTS.UCT1Utility(this);
		}else{
			return "id : " +this.uniqueID + " MotherId : isRoot "+ " turn : "+this.turn +   " W : " + this.getW() + " N : "+this.getN()+ " Childs # : "+this.getChildsCount() + " Action : "+this.a+
					" UCT1 : "+ MCTS.UCT1Utility(this);

		}
		
			}
	
	
	
}
