import java.awt.*;

import javax.swing.*;


public class Fenetre{

	public static void createFenetre(JPanel pane){
		
		JFrame frame = new JFrame("HEXXAGON");
		frame.setContentPane(pane);
		frame.setDefaultCloseOperation( javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setVisible(true);
		
		

				
	}
	
}
