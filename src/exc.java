
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
		H.sendXMSGs(10, 1, 4);
		H.ReceiveMsg(1);
	}

}
