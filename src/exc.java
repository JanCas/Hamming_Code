
/**
 * @author Jan Cas
 *
 */
public class exc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Hamming H = new Hamming();
		H.sendXMSGs(5, 0, 3);
		H.ReceiveMsg(0);
	}

}
