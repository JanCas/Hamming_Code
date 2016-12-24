import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * @author Everyone
 * Executes the Hamming programm
 * some of the gui was added on by @author janlc
 */

public class exc {
	
	public static void main(String[] args){
		graphics();
	}
	
	public static void graphics(){
		
		String input = JOptionPane.showInputDialog("Enter how many messages you want to send");
		int number = Integer.parseInt(input);
		
		String input2 = JOptionPane.showInputDialog("Enter the length of a message");
		int length = Integer.parseInt(input2);
		
		String input3 = JOptionPane.showInputDialog("Enter its parity(1=odd, 0=even)");
		int parity = Integer.parseInt(input3);
		
		
		Hamming Hamming = new Hamming(number, length, parity);
		}
}
