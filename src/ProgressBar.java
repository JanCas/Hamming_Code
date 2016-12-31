import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressBar extends JPanel{
	
	
	static final int MIN = 0;
	static final int MAX = 100;
	JProgressBar pbar = new JProgressBar();
	
	public ProgressBar(){
		
		pbar.setMinimum(MIN);
		pbar.setMaximum(MAX);
		add(pbar);
		

	}
	public void display(int d){
		pbar.setStringPainted(true);
		
		System.out.println("this is" + d);
		
		try{
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				pbar.setValue(d);
				pbar.repaint();
			}
			});
		java.lang.Thread.sleep(100);
	      } catch (InterruptedException e) {
	        ;
		}
		
	}
}
