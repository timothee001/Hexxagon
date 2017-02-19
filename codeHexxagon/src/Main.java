import java.util.Timer;
import java.util.TimerTask;


public class Main {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Panel pane =new Panel();
		while(pane.getSumScore()<58){
			pane.iterateOneClick();
			System.out.println(pane.getSumScore());
			Thread.sleep(100);
		}
		
	}
}
