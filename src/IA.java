import java.util.ArrayList;
import java.util.HashMap;


public class IA {

	private Panel pane;

	private HashMap<String,String> mapSimulation=new HashMap<String,String>();

	public IA(Panel pane){
		this.pane=pane;
		this.mapSimulation.putAll(pane.Map);
	}


	/* une fonction IA doit renvoyer une arraylist avec 3 trucs : 1: les coordonnee xy du pions qu'il choisit, 2:le mode de déplacement 3: les coordonné xy ou il se déplace
	une coordonnées doit être sous la forme +X+Y ou -X-Y ou -X+Y ou  +X-Y
	Le mode de déplacement : renvoi 0 si on veux se dupliquer 1 si on veux sauter*/

	public ArrayList<String> IAAlphaBeta(){
		ArrayList<String> choice= new ArrayList<String>();
		
		
		Action a = AlphaBeta.alphaBetaDecision(mapSimulation, "yellow");

		/***/
		
		
		
		String pion = a.getPosDepart();
		String dest = a.getPosArrive();
		String mode = a.getTypeDeplacement();
		
		/*
		System.out.print(pion);
		System.out.print(dest);
		System.out.print(mode);
		*/

		choice.add(pion);	
		choice.add(mode);
		choice.add(dest);
		
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
