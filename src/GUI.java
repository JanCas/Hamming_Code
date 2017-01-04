import java.awt.event.*;
import javax.swing.*;




@SuppressWarnings("serial")
public class GUI extends JPanel{
	

	public GUI(){
		JFrame frame = new JFrame();

		
		final int FIELD_WIDTH = 10;
		
		JLabel NumberLabel = new JLabel("Messages send");
		final JTextField NumberField = new JTextField(FIELD_WIDTH);
		NumberField.setText("" + 100);
		
		JLabel LengthLabel = new JLabel("Length of Message");
		final JTextField LengthField = new JTextField(FIELD_WIDTH);
		LengthField.setText("" + 4);
		
		JLabel ParityLabel = new JLabel("Parity Value");
		final JTextField ParityField = new JTextField(FIELD_WIDTH);
		ParityField.setText("1 or 0");
		
		JButton SB = new JButton("Start");
		
		JPanel panel = new JPanel();
		panel.add(NumberLabel);
		panel.add(NumberField);
		panel.add(LengthLabel);
		panel.add(LengthField);
		panel.add(ParityLabel);
		panel.add(ParityField);
		panel.add(SB);
		frame.add(panel);
		
		class Listener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				
				int number = Integer.parseInt(NumberField.getText());
				int length = Integer.parseInt(LengthField.getText());
				int parity = Integer.parseInt(ParityField.getText());
				
				@SuppressWarnings("unused")
				Hamming H = new Hamming(number, length, parity);
				
			}
		}
		
		ActionListener listen = new Listener();
		SB.addActionListener(listen);
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 150;
}
