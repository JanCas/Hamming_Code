import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class ProgressBar extends JPanel{
	
	
	static final int MIN = 0;
	static final int MAX = 100;
	JProgressBar pbar = new JProgressBar();
	JFrame frame = new JFrame();
	
	public ProgressBar(){
		
		pbar.setMinimum(MIN);
		pbar.setMaximum(MAX);
		add(pbar);
		frame.setSize(800, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(pbar);
		frame.setLocation(400, 400);
		frame.setVisible(true);
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
