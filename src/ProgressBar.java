import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

<<<<<<< HEAD

=======
@SuppressWarnings("serial")
>>>>>>> origin/master
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
<<<<<<< HEAD
		frame.add(pbar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 300);
		frame.setVisible(true);

=======
		frame.setSize(800, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(pbar);
		frame.setLocation(400, 400);
		frame.setVisible(true);
>>>>>>> origin/master
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
