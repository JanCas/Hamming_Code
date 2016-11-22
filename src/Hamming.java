import java.io.*;
import java.util.*;

import com.csvreader.*;

public class Hamming {

	// does absolutely nothing
	public Hamming() {
	}

	// Creates a message of X Length with 1 and 0;
	// returns ArrayList<Integer>
	public ArrayList<Integer> GenerateMsg(int Length) {
		Random rand = new Random();
		// Generates new ArrayList
		ArrayList<Integer> BitMsg = new ArrayList<Integer>();

		// Creates Code with length MsgLength
		for (int i = 0; i < Length; i++) {
			int randomN = rand.nextInt(2);
			BitMsg.add(randomN);
		}
		return BitMsg;
	}

	// Checks if a number is a power of 2
	// returns boolean
	public boolean CheckPow2(int N) {

		// Calculations for checking
		double X = Math.pow(2, (int) (Math.log10(N) / Math.log10(2)));

		// assigns x to a boolean
		boolean TF = (N == X);
		return TF;
	}

	// Inserts the parity Bits to the Randomly generated Code before
	// return ArrayList<Integer>
	public ArrayList<Integer> InsertParityBits(ArrayList<Integer> B) {
		int len = B.size();
		// Checks a position in the code is power of two and if == true it adds
		// a parity bit of Value )
		for (int i = 0; i < len; i++) {
			if (CheckPow2(i + 1) == true) {
				B.add(i, 0);
			}
		}
		return B;
	}

	// Calculates how many bit a p certain parity bit covers
	// return ArrayList<Integer>
	public ArrayList<Integer> ParityBitCoverage(int length, int parityBitPosition) {
		ArrayList<Integer> Coverage = new ArrayList<Integer>();

		// goes through the whole code
		for (int i = 0; i <= length; i++) {
			// checks if the parity and i are bitwise AND bigger then 1 if yes
			// add i to the Coverage List
			if ((parityBitPosition & i) > 0) {
				Coverage.add(i);
			}
		}
		return Coverage;
	}

	// Calculates the Value of a certain Parity Bit
	// returns int
	public int CalcParityBit(int SumBits, int party) {

		// sets initial Value to 0
		int Value = 0;

		// Checks if the party is even or odd 0 = even; 1 = odd
		if (party == 0) {

			// Checks if SumBits is even or odd and adapts Value to it
			if ((SumBits % 2) == 0) {

				// even even Value = 1
				Value = 1;
			} else {

				// even odd Value = 0;
				Value = 0;
			}
			// party = odd
		} else if (party == 1) {

			if ((SumBits % 2) == 0) {

				// odd even Value = 0
				Value = 0;
			} else {
				// odd odd Value = 1
				Value = 1;
			}
		}
		return Value;
	}

	// Encodes a MSG with either even or odd
	// returns ArrayList<Integer>
	public ArrayList<Integer> Encode(ArrayList<Integer> bitMsg, int party) {

		ArrayList<Integer> EncodedMSG = InsertParityBits(bitMsg);
		System.out.println("With Inserted Parity Bits " + EncodedMSG);
		int length = EncodedMSG.size();
		// goes through the whole bit code
		for (int i = 0; i < length - 1; i++) {

			// checks if a bit is power of 2
			if (CheckPow2(i + 1)) {

				ArrayList<Integer> BitCover = ParityBitCoverage(EncodedMSG.size(), i + 1);

				int sum_BitCover = 0;
				// adds together all Bit Cover
				for (int y = 0; y < BitCover.size(); y++) {
					sum_BitCover += EncodedMSG.get(BitCover.get(y) - 1);
				}
				// makes the value of the parity bit appropriate
				EncodedMSG.set(i, CalcParityBit(sum_BitCover, party));
			}
		}
		return EncodedMSG;
	}

	// Creates a "Noise in the channel and flips a random bit
	// returns ArrayList<Integer>
	public ArrayList<Integer> Noise(ArrayList<Integer> MSG) {
		Random rand = new Random();
		int random = rand.nextInt(8);

		// if random == 7 do nothing
		if (random == 7) {
		} else {
			// otherwise flip a random bit
			MSG.set(random, (MSG.get(random) + 1) % 2);
		}
		return MSG;
	}

	// sends the bit MSG to a CSV file for odd MSGs
	// no return
	public void send(ArrayList<Integer> BitMSG, int Number, int party) {

		String Snumber = Integer.toString(Number);
		String pty = PartytoString(party);
		try {
			// specifies bit file
			String OutputFile = "./HammingCode" + pty + ".csv";
			// generates writer
			CsvWriter Writer = new CsvWriter(new FileWriter(OutputFile, true), ',');

			// If file file already exists
			boolean alreadyexists = new File(OutputFile).exists();

			// checks if file already exists
			if (!alreadyexists) {

				// generates header
				Writer.write("Number");
				Writer.write("BitCode");
				Writer.endRecord();
			}
			// otherwise assumes that file already has headers

			// ArrayList to String
			String BitCode = BitMSG.toString();

			// writes MSG #1
			Writer.write(Snumber);
			Writer.write(BitCode);
			Writer.endRecord();

			Writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	// gets the max Number in the Number collum
	// returns int
	public int MaxNumberInCsv(int party) {

		int max = 0;
		String pty = PartytoString(party);
		try {
			CsvReader id = new CsvReader("./HammingCode" + pty + ".csv");

			// while it still has something to read it reads and overrides
			// the max
			while (id.readRecord()) {
				int Number = Integer.parseInt(id.get(0));

				// checks if id is bigger then max
				if (Number > max) {
					max = Number;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			max = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			max = 0;
		}
		return max;
	}

	// changes a 1 to "odd" and a 0 to "even"
	// returns String
	public String PartytoString(int party) {
		String P = null;
		if (party == 0) {
			P = "even";
		} else if (party == 1) {
			P = "odd";
		}
		return P;
	}

	// sends a amount of messages
	// no return
	public void sendXMSGs(int Amount, int party, int Length) {
		int x = 0;
		System.out.println("Party is " + PartytoString(party));
		// generates amount messages and encodes and sends them
		for (int i = 0; i < Amount; i++) {

			// Generates random message with 1s and 0s
			ArrayList<Integer> BitMSG = GenerateMsg(Length);
			System.out.println(BitMSG);

			// Encodes the message
			BitMSG = Encode(BitMSG, party);
			System.out.println("Encoded " + BitMSG);

			// applies a random noise
			BitMSG = Noise(BitMSG);
			System.out.println("With 'Noise' " + BitMSG);

			// finds the max count
			x = MaxNumberInCsv(party);
			// sends it to a CSV file
			send(BitMSG, (x + 1), party);
		}
	}

	// Puts a String into an ArrayList
	// returns ArrayList<Integer>
	public ArrayList<Integer> StringtoArray(String MSG) {

		// creates a new ArrayList<Integer>
		ArrayList<Integer> Message = new ArrayList<Integer>();

		// goes through the whole String
		for (int i = 0; i < MSG.length(); i++) {

			// checks if character at index i is equal to 1 or 0
			if ((MSG.charAt(i) == '1') || (MSG.charAt(i) == '0')) {
				// if yes add it to Message
				Message.add(Character.getNumericValue(MSG.charAt(i)));
			}
		}
		return Message;
	}

	// checks a parity bit in the received MSG
	// returns boolean
	public boolean CheckParityBit(ArrayList<Integer> Msg, int index, int sending_Party) {
		boolean parity_bit_value = false;

		ArrayList<Integer> Coverage = ParityBitCoverage(Msg.size(), (index + 1));
		int sum_bit_cover = 0;

		// Adds all the Covered Bits up
		for (int i = 0; i < Coverage.size(); i++) {
			sum_bit_cover += Msg.get(Coverage.get(i) - 1);
		}
		// checks if the parity bit fits toghether with the CalcParityBit
		if (Msg.get(index) == CalcParityBit((sum_bit_cover + Msg.get(index)), sending_Party)) {
			parity_bit_value = true;
		} else {
			parity_bit_value = false;
		}

		return parity_bit_value;
	}

	// Checks and Corrects the Message
	// returns ArrayList<Integer>
	public ArrayList<Integer> CheckandCorrect(ArrayList<Integer> Msg, int sending_party) {

		int sum_error_bit = 0;
		// goes through the whole message
		for (int i = 0; i < Msg.size(); i++) {
			// checks if a bit is a parity bit
			if (CheckPow2(i + 1)) {
				// checks if the bit is wrong
				if (!CheckParityBit(Msg, i, sending_party)) {
					// add all to wrong bits together
					sum_error_bit += Msg.get(i);
				}
			}
		}
		// flips the wrong bit
		Msg.set(sum_error_bit, (Msg.get(sum_error_bit) + 1) % 2);
		return Msg;
	}

	// Removes all parity bits to revert it to the original Message
	// returns ArrayList<Integer>
	public ArrayList<Integer> RemoveParityBit(ArrayList<Integer> Msg) {

		ArrayList<Integer> originalMsg = new ArrayList<Integer>();
		// goes through the whole
		for (int i = 0; i < Msg.size(); i++) {
			// checks if i == parity bit
			if (!CheckPow2(i + 1)) {
				// adds it to a new ArrayList
				originalMsg.add(Msg.get(i));
			}
		}
		return originalMsg;
	}

	// decodes the message
	// returns ArrayList<Integer>
	public ArrayList<Integer> Decode(ArrayList<Integer> Msg, int sending_party) {
		Msg = CheckandCorrect(Msg, sending_party);
		Msg = RemoveParityBit(Msg);
		return Msg;
	}

	public void ReceiveMsg(int party) {
		String pty = PartytoString(party);
		ArrayList<Integer> M = new ArrayList<Integer>();
		try {
			CsvReader MSG = new CsvReader("./HammingCode" + pty + ".csv");

			while (MSG.readRecord()) {
				String Message = MSG.get(1);
				M = StringtoArray(Message);
				M = Decode(M, party);
				System.out.println("the decoded Message equals " + M);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}