import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
public class Panel extends JFrame{
	int rayonCercle=40;
	Icon redIcon = new ImageIcon(new ImageIcon("image/button-red.png").getImage().getScaledInstance(2*rayonCercle, 2*rayonCercle, Image.SCALE_DEFAULT));;
	Icon blueIcon= new ImageIcon(new ImageIcon("image/button-blue.png").getImage().getScaledInstance(2*rayonCercle, 2*rayonCercle, Image.SCALE_DEFAULT));;
	Icon purpleIcon= new ImageIcon(new ImageIcon("image/button-purple.png").getImage().getScaledInstance(2*rayonCercle, 2*rayonCercle, Image.SCALE_DEFAULT));;
	Icon yellowIcon= new ImageIcon(new ImageIcon("image/button-yellow.png").getImage().getScaledInstance(2*rayonCercle, 2*rayonCercle, Image.SCALE_DEFAULT));;
	Icon blackIcon= new ImageIcon(new ImageIcon("image/button-black.png").getImage().getScaledInstance(2*rayonCercle, 2*rayonCercle, Image.SCALE_DEFAULT));;
	Icon greenIcon= new ImageIcon(new ImageIcon("image/button-green.png").getImage().getScaledInstance(2*rayonCercle, 2*rayonCercle, Image.SCALE_DEFAULT));;
	Icon greyIcon= new ImageIcon(new ImageIcon("image/button-grey.png").getImage().getScaledInstance(2*rayonCercle, 2*rayonCercle, Image.SCALE_DEFAULT));;
	JLabel pointJoueur=new JLabel("Point Joueur");
	JLabel pointIA=new JLabel("Point IA");
	
	JButton facile   = new JButton("Facile");
	JButton moyen    = new JButton("Moyen");
	JButton dur = new JButton("Dur");
	
	ButtonGroup bgroup = new ButtonGroup();
	
	
	
	JLabel red=new JLabel(redIcon);
	JLabel blue=new JLabel(blueIcon);
	JLabel yellow=new JLabel(yellowIcon);
	JLabel purple=new JLabel(purpleIcon);
	JLabel green=new JLabel(redIcon);
	JLabel black=new JLabel(blueIcon);
	String redLabel=red.getIcon().toString();
	String blueLabel=blue.getIcon().toString();
	String yellowLabel=yellow.getIcon().toString();
	String purpleLabel=purple.getIcon().toString();
	String greenLabel=green.getIcon().toString();
	String blackLabel=black.getIcon().toString();
	String tourJoueur="green";
	String tourJoueurAdv="yellow";
	
	
	int xsCourant,ysCourant;
	HashMap<String,String> Map=new HashMap<String, String>();
	HashMap<String,JLabel> MapLabel=new HashMap<String, JLabel>();
	/*Constructeur*/
	public Panel(){
		this.setLayout(null);
		
		
		facile.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AlphaBeta.seuil=1;
			}
		});
		
		
		
		moyen.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AlphaBeta.seuil=2;
			}
		});
		
		
		dur.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AlphaBeta.seuil=3;
			}
		});
		
		
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
				String xS=i>=0?"+"+i:""+i;
				String yS=j>=0?"+"+j:""+j;
				this.Map.put(""+xS+yS, "purple");
				this.MapLabel.put(""+xS+yS, new JLabel(purpleIcon));
				final int xs=i, ys=j;
				this.MapLabel.get(""+xS+yS).addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						boolean aJoue=false;
						int nbClick=0;
						if(getColor(xs,ys)==tourJoueur){
							refreshLabel();
							showPossibilities(xs,ys);
							nbClick=1;
							xsCourant=xs;
							ysCourant=ys;
						}
						if(getLabel(xs,ys).getIcon().toString().equals(purpleLabel)){
							nbClick=0;
							refreshLabel();
						}
						if(getLabel(xs,ys).getIcon().toString().equals(redLabel)){
							nbClick=2;
							change(xs,ys,tourJoueur);
							aJoue=true;
						}
						if(getLabel(xs,ys).getIcon().toString().equals(blueLabel)){
							nbClick=2;
							change(xs,ys,tourJoueur);
							change(xsCourant,ysCourant,"purple");
							aJoue=true;
						}
						if(nbClick==2){
							if(getColor(xs-1,ys)==tourJoueurAdv)
								change(xs-1,ys,tourJoueur);
							if(getColor(xs,ys-1)==tourJoueurAdv)
								change(xs,ys-1,tourJoueur);
							if(getColor(xs+1,ys)==tourJoueurAdv)
								change(xs+1,ys,tourJoueur);
							if(getColor(xs,ys+1)==tourJoueurAdv)
								change(xs,ys+1,tourJoueur);
							if(getColor(xs-1,ys+1)==tourJoueurAdv)
								change(xs-1,ys+1,tourJoueur);
							if(getColor(xs+1,ys-1)==tourJoueurAdv)
								change(xs+1,ys-1,tourJoueur);
							if(AlphaBeta.actionList(Panel.this.Map, "yellow").size()==0){
								ArrayList<String> left=Panel.this.getColorPoints("purple");
								for(int i=0;i<left.size();i++){
									Panel.this.change(Integer.parseInt(left.get(i).substring(0,2)), Integer.parseInt(left.get(i).substring(2,4)), "green");
								}
							}
							Panel.this.pointIA.setText("IA :"+Panel.this.getColorPoints(tourJoueurAdv).size());
							Panel.this.pointJoueur.setText("Joueur :"+Panel.this.getColorPoints(tourJoueur).size());
						}
						if(tourJoueurAdv=="yellow"&&aJoue==true&&Map.containsValue("purple")==true){
							IA ia=new IA(Panel.this);
							ArrayList<String> iaResult=ia.IAAlphaBeta();
							String text=iaResult.get(0);
							final int xtext=Integer.parseInt(text.substring(0, 2));
							final int ytext=Integer.parseInt(text.substring(2, 4));
							String xS=xtext>=0?"+"+xtext:""+xtext;
							String yS=ytext>=0?"+"+ytext:""+ytext;
							if(iaResult.get(1)=="0"){
								MapLabel.get(""+xS+yS).setIcon(greyIcon);
								String randomClone=iaResult.get(2);
								final int rCx=Integer.parseInt(randomClone.substring(0, 2));
								final int rCy=Integer.parseInt(randomClone.substring(2, 4));
								TimerTask task = new TimerTask()
								{
									@Override
									public void run() 
									{
										change(rCx,rCy,"yellow");
										if(getColor(rCx-1,rCy)==tourJoueur)
											change(rCx-1,rCy,tourJoueurAdv);
										if(getColor(rCx,rCy-1)==tourJoueur)
											change(rCx,rCy-1,tourJoueurAdv);
										if(getColor(rCx+1,rCy)==tourJoueur)
											change(rCx+1,rCy,tourJoueurAdv);
										if(getColor(rCx,rCy+1)==tourJoueur)
											change(rCx,rCy+1,tourJoueurAdv);
										if(getColor(rCx-1,rCy+1)==tourJoueur)
											change(rCx-1,rCy+1,tourJoueurAdv);
										if(getColor(rCx+1,rCy-1)==tourJoueur)
											change(rCx+1,rCy-1,tourJoueurAdv);
										if(AlphaBeta.actionList(Panel.this.Map, "green").size()==0){
											ArrayList<String> left=Panel.this.getColorPoints("purple");
											for(int i=0;i<left.size();i++){
												Panel.this.change(Integer.parseInt(left.get(i).substring(0,2)), Integer.parseInt(left.get(i).substring(2,4)), "yellow");
											}
										}
										Panel.this.pointIA.setText("IA :"+Panel.this.getColorPoints(tourJoueurAdv).size());
										Panel.this.pointJoueur.setText("Joueur :"+Panel.this.getColorPoints(tourJoueur).size());
									}	
								};
								Timer timer = new Timer();
								timer.schedule(task, 1000);
							}
							else if(iaResult.get(1)=="1"){
								MapLabel.get(""+xS+yS).setIcon(greyIcon);
								String randomJump=iaResult.get(2);
								final int rJx=Integer.parseInt(randomJump.substring(0, 2));
								final int rJy=Integer.parseInt(randomJump.substring(2, 4));
								TimerTask task = new TimerTask()
								{
									@Override
									public void run() 
									{
										change(rJx,rJy,"yellow");
										change(xtext,ytext,"purple");
										if(getColor(rJx-1,rJy)==tourJoueur)
											change(rJx-1,rJy,tourJoueurAdv);
										if(getColor(rJx,rJy-1)==tourJoueur)
											change(rJx,rJy-1,tourJoueurAdv);
										if(getColor(rJx+1,rJy)==tourJoueur)
											change(rJx+1,rJy,tourJoueurAdv);
										if(getColor(rJx,rJy+1)==tourJoueur)
											change(rJx,rJy+1,tourJoueurAdv);
										if(getColor(rJx-1,rJy+1)==tourJoueur)
											change(rJx-1,rJy+1,tourJoueurAdv);
										if(getColor(rJx+1,rJy-1)==tourJoueur)
											change(rJx+1,rJy-1,tourJoueurAdv);
										if(AlphaBeta.actionList(Panel.this.Map, "green").size()==0){
											ArrayList<String> left=Panel.this.getColorPoints("purple");
											for(int i=0;i<left.size();i++){
												Panel.this.change(Integer.parseInt(left.get(i).substring(0,2)), Integer.parseInt(left.get(i).substring(2,4)), "yellow");
											}
										}
										Panel.this.pointIA.setText("IA :"+Panel.this.getColorPoints(tourJoueurAdv).size());
										Panel.this.pointJoueur.setText("Joueur :"+Panel.this.getColorPoints(tourJoueur).size());
									}	
								};
								Timer timer = new Timer();
								timer.schedule(task, 1000);
							}
							nbClick=2;
							aJoue=false;
						}
						if(aJoue){
							if(tourJoueur.equals("yellow")){
								tourJoueur="green";
								tourJoueurAdv="yellow";
							}else{
								tourJoueur="yellow";
								tourJoueurAdv="green";
							}
						}
					}
				});
			}
		}
		MapLabel.get("+0+4").setBounds(400-40, 0, 80, 80);
		this.add(MapLabel.get("+0+4"));
		MapLabel.get("+0+3").setBounds(400-40, 80, 80, 80);
		this.add(MapLabel.get("+0+3"));
		MapLabel.get("+0+2").setBounds(400-40, 160, 80, 80);
		this.add(MapLabel.get("+0+2"));
		MapLabel.get("+0+1").setBounds(400-40, 240, 80, 80);
		this.add(MapLabel.get("+0+1"));
		MapLabel.get("+0+0").setBounds(400-40, 320, 80, 80);
		this.add(MapLabel.get("+0+0"));
		MapLabel.get("+0-1").setBounds(400-40, 400, 80, 80);
		this.add(MapLabel.get("+0-1"));
		MapLabel.get("+0-2").setBounds(400-40, 480, 80, 80);
		this.add(MapLabel.get("+0-2"));
		MapLabel.get("+0-3").setBounds(400-40, 560, 80, 80);
		this.add(MapLabel.get("+0-3"));
		MapLabel.get("+0-4").setBounds(400-40, 640, 80, 80);
		this.add(MapLabel.get("+0-4"));
		MapLabel.get("+1+3").setBounds(400+30, 0+40, 80, 80);
		this.add(MapLabel.get("+1+3"));
		MapLabel.get("+1+2").setBounds(400+30, 0+120, 80, 80);
		this.add(MapLabel.get("+1+2"));
		MapLabel.get("+1+1").setBounds(400+30, 0+200, 80, 80);
		this.add(MapLabel.get("+1+1"));
		MapLabel.get("+1+0").setBounds(400+30, 0+280, 80, 80);
		this.add(MapLabel.get("+1+0"));
		MapLabel.get("+1-1").setBounds(400+30, 0+360, 80, 80);
		this.add(MapLabel.get("+1-1"));
		MapLabel.get("+1-2").setBounds(400+30, 0+440, 80, 80);
		this.add(MapLabel.get("+1-2"));
		MapLabel.get("+1-3").setBounds(400+30, 0+520, 80, 80);
		this.add(MapLabel.get("+1-3"));
		MapLabel.get("+1-4").setBounds(400+30, 0+600, 80, 80);
		this.add(MapLabel.get("+1-4"));
		MapLabel.get("-1+4").setBounds(400-110, 0+40, 80, 80);
		this.add(MapLabel.get("-1+4"));
		MapLabel.get("-1+3").setBounds(400-110, 0+120, 80, 80);
		this.add(MapLabel.get("-1+3"));
		MapLabel.get("-1+2").setBounds(400-110, 0+200, 80, 80);
		this.add(MapLabel.get("-1+2"));
		MapLabel.get("-1+1").setBounds(400-110, 0+280, 80, 80);
		this.add(MapLabel.get("-1+1"));
		MapLabel.get("-1+0").setBounds(400-110, 0+360, 80, 80);
		this.add(MapLabel.get("-1+0"));
		MapLabel.get("-1-1").setBounds(400-110, 0+440, 80, 80);
		this.add(MapLabel.get("-1-1"));
		MapLabel.get("-1-2").setBounds(400-110, 0+520, 80, 80);
		this.add(MapLabel.get("-1-2"));
		MapLabel.get("-1-3").setBounds(400-110, 0+600, 80, 80);
		this.add(MapLabel.get("-1-3"));
		MapLabel.get("-2+4").setBounds(400-180, 0+80, 80, 80);
		this.add(MapLabel.get("-2+4"));
		MapLabel.get("-2+3").setBounds(400-180, 0+160, 80, 80);
		this.add(MapLabel.get("-2+3"));
		MapLabel.get("-2+2").setBounds(400-180, 0+240, 80, 80);
		this.add(MapLabel.get("-2+2"));
		MapLabel.get("-2+1").setBounds(400-180, 0+320, 80, 80);
		this.add(MapLabel.get("-2+1"));
		MapLabel.get("-2+0").setBounds(400-180, 0+400, 80, 80);
		this.add(MapLabel.get("-2+0"));
		MapLabel.get("-2-1").setBounds(400-180, 0+480, 80, 80);
		this.add(MapLabel.get("-2-1"));
		MapLabel.get("-2-2").setBounds(400-180, 0+560, 80, 80);
		this.add(MapLabel.get("-2-2"));
		MapLabel.get("+2+2").setBounds(400+100, 0+80, 80, 80);
		this.add(MapLabel.get("+2+2"));
		MapLabel.get("+2+1").setBounds(400+100, 0+160, 80, 80);
		this.add(MapLabel.get("+2+1"));
		MapLabel.get("+2+0").setBounds(400+100, 0+240, 80, 80);
		this.add(MapLabel.get("+2+0"));
		MapLabel.get("+2-1").setBounds(400+100, 0+320, 80, 80);
		this.add(MapLabel.get("+2-1"));
		MapLabel.get("+2-2").setBounds(400+100, 0+400, 80, 80);
		this.add(MapLabel.get("+2-2"));
		MapLabel.get("+2-3").setBounds(400+100, 0+480, 80, 80);
		this.add(MapLabel.get("+2-3"));
		MapLabel.get("+2-4").setBounds(400+100, 0+560, 80, 80);
		this.add(MapLabel.get("+2-4"));
		MapLabel.get("+3+1").setBounds(400+170, 0+120, 80, 80);
		this.add(MapLabel.get("+3+1"));
		MapLabel.get("+3+0").setBounds(400+170, 0+200, 80, 80);
		this.add(MapLabel.get("+3+0"));
		MapLabel.get("+3-1").setBounds(400+170, 0+280, 80, 80);
		this.add(MapLabel.get("+3-1"));
		MapLabel.get("+3-2").setBounds(400+170, 0+360, 80, 80);
		this.add(MapLabel.get("+3-2"));
		MapLabel.get("+3-3").setBounds(400+170, 0+440, 80, 80);
		this.add(MapLabel.get("+3-3"));
		MapLabel.get("+3-4").setBounds(400+170, 0+520, 80, 80);
		this.add(MapLabel.get("+3-4"));
		MapLabel.get("-3+4").setBounds(400-250, 0+120, 80, 80);
		this.add(MapLabel.get("-3+4"));
		MapLabel.get("-3+3").setBounds(400-250, 0+200, 80, 80);
		this.add(MapLabel.get("-3+3"));
		MapLabel.get("-3+2").setBounds(400-250, 0+280, 80, 80);
		this.add(MapLabel.get("-3+2"));
		MapLabel.get("-3+1").setBounds(400-250, 0+360, 80, 80);
		this.add(MapLabel.get("-3+1"));
		MapLabel.get("-3+0").setBounds(400-250, 0+440, 80, 80);
		this.add(MapLabel.get("-3+0"));
		MapLabel.get("-3-1").setBounds(400-250, 0+520, 80, 80);
		this.add(MapLabel.get("-3-1"));
		MapLabel.get("-4+4").setBounds(400-320, 0+160, 80, 80);
		this.add(MapLabel.get("-4+4"));
		MapLabel.get("-4+3").setBounds(400-320, 0+240, 80, 80);
		this.add(MapLabel.get("-4+3"));
		MapLabel.get("-4+2").setBounds(400-320, 0+320, 80, 80);
		this.add(MapLabel.get("-4+2"));
		MapLabel.get("-4+1").setBounds(400-320, 0+400, 80, 80);
		this.add(MapLabel.get("-4+1"));
		MapLabel.get("-4+0").setBounds(400-320, 0+480, 80, 80);
		this.add(MapLabel.get("-4+0"));
		MapLabel.get("+4+0").setBounds(400+240, 0+160, 80, 80);
		this.add(MapLabel.get("+4+0"));
		MapLabel.get("+4-1").setBounds(400+240, 0+240, 80, 80);
		this.add(MapLabel.get("+4-1"));
		MapLabel.get("+4-2").setBounds(400+240, 0+320, 80, 80);
		this.add(MapLabel.get("+4-2"));
		MapLabel.get("+4-3").setBounds(400+240, 0+400, 80, 80);
		this.add(MapLabel.get("+4-3"));
		MapLabel.get("+4-4").setBounds(400+240, 0+480, 80, 80);
		this.add(MapLabel.get("+4-4"));
		pointJoueur.setBounds(400+240, 0+600, 80, 80);
		this.add(pointJoueur);
		pointIA.setBounds(350+240, 0+650, 80, 80);
		this.add(pointIA);
		
		
		facile.setBounds(800,200 , 80, 80);
		this.add(facile);
		
		moyen.setBounds(900,200 , 80, 80);
		this.add(moyen);
		
		dur.setBounds(1000,200 , 80, 80);
		this.add(dur);
		
		
		change(-1,0,"black");
		change(0,1,"black");
		change(1,-1,"black");
		change(0,-4,"green");
		change(4,0,"green");
		change(-4,4,"green");
		change(0,4,"yellow");
		change(-4,0,"yellow");
		change(4,-4,"yellow");
		this.setDefaultCloseOperation( javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setSize(1200, 800);
		this.setVisible(true);
	}
	/**Permet d'avoir la liste des coordonnées (en String) qui contiennent la couleur en paramètre.
	 * @param une couleur
	 * @return List des emplacement de la couleur  */
	public ArrayList<String> getColorPoints(String color){
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
				if(getColor(i,j)==color){
					String xS=i>=0?"+"+i:""+i;
					String yS=j>=0?"+"+j:""+j;
					YellowKeys.add(""+xS+yS);
				}
			}
		}
		return YellowKeys;
	}
	/**Permet de mettre une couleur à une coordonnée x,y.*/
	public void change(int x,int y, String color){
		String xS=x>=0?"+"+x:""+x;
		String yS=y>=0?"+"+y:""+y;
		this.Map.put(""+xS+yS, color);
		changeLabel(x,y,color);
		//System.out.print(""+xS+yS + " :  "+ color);
		this.refreshLabel();
	}
	/**Renvoie la couleur
	 * @param coordonnée x
	 * @param coordonnée y
	 * @return couleur présent dans la HashMap "map" a la coordonnée*/
	public String getColor(int x,int y){
		String xS=x>=0?"+"+x:""+x;
		String yS=y>=0?"+"+y:""+y;
		return this.Map.get(""+xS+yS);
	}
	/**Permet d'afficher toute les possibilité de mouvement à partir d'un point. */
	public void showPossibilities(int xs,int ys){
		if(tourJoueur=="green"){
			if(getColor(xs,ys)=="green"){
				for(int i=xs-1;i<=xs+1;i++){
					for(int j=ys-1;j<=ys+1;j++){
						if(!(i==xs+1&&j==ys+1)&&!(i==xs-1&&j==ys-1)){
							if(getColor(i,j)=="purple")
								changeLabel(i,j,"red");
						}
					}
				}
				for(int i=xs-2;i<=xs+2;i++){
					for(int j=ys-2;j<=ys+2;j++){
						if((i==xs&&j==ys+2)||(i==xs+1&&j==ys+1)||(i==xs+2&&j==ys)||(i==xs+2&&j==ys-1)||(i==xs+2&&j==ys-2)||(i==xs+1&&j==ys-2)||(i==xs&&j==ys-2)||(i==xs-1&&j==ys-1)||(i==xs-2&&j==ys)||(i==xs-2&&j==ys+1)||(i==xs-2&&j==ys+2)||(i==xs-1&&j==ys+2)){
							if(getColor(i,j)=="purple")
								changeLabel(i,j,"blue");
						}
					}
				}
			}
		}
	}
	/**Permet de prendre les couleurs présentes dans la hashmap map puis de les afficher par le biais
	 * de la hashmap mapLabel.
	 */
	public void refreshLabel(){
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
				changeLabel(i,j,getColor(i,j));
			}
		}
	}
	public JLabel getLabel(int x,int y){
		String xS=x>=0?"+"+x:""+x;
		String yS=y>=0?"+"+y:""+y;
		return this.MapLabel.get(""+xS+yS);
	}
	public static int randomNumber(int min, int max){
		return (min + (int)(Math.random() * ((max - min)+1)));
	}
	/**Permet de changer la couleur du hashmap MapLabel
	 * @param coordonnée x
	 * @param coordonnée y
	 * @param couleur associé
	 * */
	public void changeLabel(int x, int y,String color){
		String xS=x>=0?"+"+x:""+x;
		String yS=y>=0?"+"+y:""+y;
		switch(color){
		case "red":
			MapLabel.get(""+xS+yS).setIcon(redIcon);	
			break;
		case "blue":
			MapLabel.get(""+xS+yS).setIcon(blueIcon);	
			break;
		case "black":
			MapLabel.get(""+xS+yS).setIcon(blackIcon);	
			break;
		case "yellow":
			MapLabel.get(""+xS+yS).setIcon(yellowIcon);	
			break;
		case "green":
			MapLabel.get(""+xS+yS).setIcon(greenIcon);	
			break;
		case "purple":
			MapLabel.get(""+xS+yS).setIcon(purpleIcon);	
			break;
		}
	}
}