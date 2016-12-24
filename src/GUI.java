import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class GUI extends JPanel{
	
	
	static final int MIN = 0;
	static final int MAX = 100;
	JProgressBar pbar = new JProgressBar();
	JFrame frame = new JFrame("ProgressBar Send");
	
	
	public GUI(int N){
		
		pbar.setMinimum(MIN);
		pbar.setMaximum(MAX);
		add(pbar);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(pbar);
		frame.pack();
		

	}
	public void display(int d){

		
		frame.setVisible(true);
		pbar.setStringPainted(true);
		
		System.out.println("this is" + d);
		assert d <= MAX;
		
		try{
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				pbar.setValue(d);
				pbar.repaint();
				frame.repaint();
			}
			});
		java.lang.Thread.sleep(100);
	      } catch (InterruptedException e) {
	        ;
		}
		
	}
}
