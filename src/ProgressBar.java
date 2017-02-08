import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;


public class ProgressBar extends JPanel{
	
	
	static final int MIN = 0;
	static final int MAX = 100;
	JProgressBar pbar = new JProgressBar();
	JFrame frame = new JFrame();
	
	public ProgressBar(){
		
		pbar.setMinimum(MIN);
		pbar.setMaximum(MAX);
		pbar.setStringPainted(true);
		add(pbar);
		frame.add(pbar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 300);
		frame.setVisible(true);

	}
	public void display(int d){
		
		System.out.println("this is" + d);
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	System.out.println("Going in");
		      pbar.setValue(d);
		    }
		  });
	      
		
	}
}
