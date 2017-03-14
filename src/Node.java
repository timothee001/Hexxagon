import java.util.ArrayList;

public class Node {

	boolean isRoot=false;
	int wi= 0;// stands for the number of wins after the i-th move;
	int ni = 0; //stands for the number of simulations after the i-th move;
	Node mothernode=null;
	ArrayList<Node> childs = new ArrayList<Node>();
	Action a;
	String turn;
	
	public Node(String turn){
		this.turn=turn;
		this.isRoot=true;
	}
	
	public Node(Node mothernode, String turn, Action a){
		this.turn=turn;
		this.mothernode=mothernode;
		this.a=a;
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
		this.childs.add(child);
		child.setMotherNode(this);
	}
	
	
	public int getChildsCount(){
		return this.childs.size();
	}
	
	public ArrayList<Node> getChilds(){
		return this.childs;
	}
	
	
	
	public Node getNodeAt(int i){
		if(i <this.childs.size()){
			return this.childs.get(i);
		}
		return null;
	}
	
	public String toString(){
		return "W : " + this.getW() + " N : "+this.getN()+ " Childs # : "+this.getChildsCount() + " Action : "+this.a;
	}
	
	
	
}
