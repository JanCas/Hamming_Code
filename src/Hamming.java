/**
 * @author Jan Cas
 * @author Amer Islam
 * @author Yair
 * @author Daniel
 * @author Max
 * @author Arjun
 * this program computes a hamming code procedure. 
 * it will be executed in the exc.java file
 * https://github.com/JanCas/Hamming_Code
 */

import java.io.*;
import java.util.*;

import com.csvreader.*;

public class Hamming {

	/**
	 * @author Jan Cas
	 * Runs the needed methods
	 * @param Number
	 * @param length
	 * @param party
	 */
	public Hamming(int Number, int length, int party){
		assert Number > 0;
		assert length > 0;
		sendXMSGs(Number, party, length);
		ReceiveMsg(party);
	}
	
	/**
	 * @author Jan Cas
	 * does absolutely nothing at all
	 */
	public Hamming(){}

	/**
	 * @author Arjun
	 * Creates a message with length of @param Length
	 * @return ArrayList<Integer>
	 */
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

	/**
	 * @author Jan, Max
	 * checks if @param N is a power of 2
	 * @return bollean
	 */
	public boolean CheckPow2(int N) {

		// Calculations for checking
		double X = Math.pow(2, (int) (Math.log10(N) / Math.log10(2)));

		// assigns x to a boolean (T/F)
		boolean TF = (N == X);
		return TF;
	}

	/**
	 * @author Daniel, Yair
	 * Inserts 0 at the locations of the parity bits  in the ArrayList<Integer> @param B
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> InsertParityBits(ArrayList<Integer> B) {
		
		// Checks a position in the code is power of two and if == true it adds
		// a parity bit of Value )
		for (int i = 0; i < B.size(); i++) {
			if (CheckPow2(i + 1) == true) {
				B.add(i, 0);
			}
		}
		return B;
	}

	/**
	 * @author Jan, Max
	 * Calculate how many bits are getting covered by the bit at (@param parityBitPosition)
	 * @param length
	 * @return ArrayList<Integer>
	 */
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

	/**
	 * @author Amer, Arjun
	 * calculates the value of a certain parity bit by using @param party and @param SumBits
	 * @param SumBits
	 * @param party
	 * @return int
	 * 
	 * it checks if the party and the number are the same or diffrent
	 * if diffrent value = 0; other value = 1
	 */
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

	/**
	 * @author Everyone
	 * Encodes a bitmsg 
	 * @param bitMsg
	 * @param party
	 * @return ArrayList<Integer>
	 * 
	 * loop1 -> goes throught the whole message, enters loop2 -> checks if the index it is at rn is power of 2
	 * checks which parity bits that bit covers loop -> adds the value of all those parity bits. back to loop1 -> 
	 * flips the parity bit to the right value.
	 */
	public ArrayList<Integer> Encode(ArrayList<Integer> bitMsg, int party) {

		ArrayList<Integer> EncodedMSG = InsertParityBits(bitMsg);
		System.out.println("With Inserted Parity Bits " + EncodedMSG);
		
		// goes through the whole bit code
		for (int i = 0; i < EncodedMSG.size() - 1; i++) {

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

	/**
	 * @author Yair
	 * flips one random bit or no bit at all
	 * @param MSG
	 * @return MSG
	 */
	public ArrayList<Integer> Noise(ArrayList<Integer> MSG) {
		Random rand = new Random();
		int random = rand.nextInt(MSG.size() + 1);

		// if random == MSG.size() do nothing
		if (random == (MSG.size())) {
			System.out.println("No corrupted bit");
		} else {
			// otherwise flip a random bit
			MSG.set(random, (MSG.get(random) + 1) % 2);
			System.out.println("Corrupted bit: " + random);
		}
		return MSG;
	}

	/**
	 * @author Daniel, Arjun, Amer, Yair
	 * sends a certain number of messages to a csv file according to its party
	 * @param BitMSG
	 * @param Number
	 * @param party
	 * generates a csv file -> writes it into the location where the project is as well.
	 * it is a library downloaded from the internet and used their exaple code.
	 */
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
		} finally {
		}
	}

	/**
	 * @author jan
	 * returns the id of the last message of a certain party sent
	 * @param party
	 * @return MaxNuber
	 * it makes the initial max zero and then overwrites it every time it finds a bigger number.
	 * so every time it is called 
	 * it uses a try catch -> so in case the file does not exist yet the catch statement is to make maxNumber 0.
	 */
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
			max = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			max = 0;
		}
		return max;
	}

	/**
	 * @author Jan
	 * changes a String into a party
	 * @param party
	 * @return String
	 * no big deal just turns a int into a string
	 */
	public String PartytoString(int party) {
		String P = null;
		if (party == 0) {
			P = "even";
		} else if (party == 1) {
			P = "odd";
		}
		return P;
	}

	/**
	 * @author Everyone
	 * sends number of messages into a specified csv file
	 * @param Amount
	 * @param party
	 * @param Length
	 * first it says which party is sending messages
	 * this method goes through a for loop and stops when it sends the amount of messages wanted by the user.
	 * it makes a random message with the specified length
	 * Encodes that message
	 * affects noise on it
	 * gets the max number in the csv
	 * and then sends it
	 */
	public void sendXMSGs(int Number, int party, int Length) {
		ProgressBar progressBar = new ProgressBar();
		
		int x = 0;
		System.out.println(PartytoString(party) + " is sending message(s)");
		System.out.println();
		// generates amount messages and encodes and sends them
		for (int i = 0; i < Number; i++) {
			
			progressBar.display((int) ((i + 1)*100.0/Number));
			// Generates random message with 1s and 0s
			ArrayList<Integer> BitMSG = GenerateMsg(Length);
			System.out.println("Original Message: " + BitMSG);

			// Encodes the message
			BitMSG = Encode(BitMSG, party);
			System.out.println("Encoded " + BitMSG);

			// applies a random noise
			BitMSG = Noise(BitMSG);
			System.out.print("Message ID: " + x);
			System.out.println(" " + BitMSG + " sent.");
			System.out.println();
			// finds the max count
			x = MaxNumberInCsv(party);
			// sends it to a CSV file
			send(BitMSG, (x + 1), party);
		}
	}

	/**
	 * @author Yair, Max
	 * puts a string into an arrayList
	 * @param MSG
	 * @return
	 * turns a string into an array which will be used so that when we read the csv file
	 * it reads a value as a string but we need it as an arrayList
	 */
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

	/**
	 * @author Jan, Max
	 * checks if a parity bit is faulty
	 * @param Msg
	 * @param index
	 * @param sending_Party
	 * @return
	 * checks if a parity bit is its right value
	 * it adds all the values of all the coverage togehter
	 * and then checks if the value of the current parity bit is equal to the value of the parity bit that it should be.
	 */
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

	/**
	 * @author Arjun, Amer, Jan
	 * checks and corrects the message by checking every parity bit on faultyness
	 * @param Msg
	 * @param sending_party
	 * @return
	 * the value of every wrong parity bit index reveals the error bit.
	 * loop! -> goes through the whole message loop2 -> checks if bit at index i is parity bit.
	 * loop3 -> checks if the selected parity bit is faluty or not. 
	 * adds all the faulty bits together
	 * the result is the faulty bit
	 */
	public ArrayList<Integer> CheckandCorrect(ArrayList<Integer> Msg, int sending_party) {

		int sum_error_bit = 0;
		// goes through the whole message
		for (int i = 0; i < Msg.size(); i++) {
			// checks if a bit is a parity bit
			if (CheckPow2(i + 1)) {
				// checks if the bit is wrong
				if (!CheckParityBit(Msg, i, sending_party)) {
					// add all to wrong bits together
					sum_error_bit += i + 1;
				}
			}
		}
		// flips the wrong bit
		if (sum_error_bit > 0) {
			System.out.println("Corrupted bit: " + sum_error_bit);
			Msg.set((sum_error_bit - 1), (Msg.get(sum_error_bit - 1) + 1) % 2);
			System.out.println("Corrected Message: " + Msg);
		}
		return Msg;
	}

	/**
	 * @author jan
	 * removes all the parity bits
	 * @param Msg
	 * @return
	 * just removes all the parity bits, reveiling the original message
	 */
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

	/**
	 * @author Everyone
	 * decodes the message
	 * @param Msg
	 * @param sending_party
	 * @return
	 * Checks and corrects the message & then removes the parity bits
	 */
	public ArrayList<Integer> Decode(ArrayList<Integer> Msg, int sending_party) {
		Msg = CheckandCorrect(Msg, sending_party);
		Msg = RemoveParityBit(Msg);
		return Msg;
	}

	/**
	 * @author Everyone
	 * receives, decodes and prints the message
	 * @param party
	 * this method first prints that it is receiving messages
	 * then it goes in to a try catch
	 * first checks if the file existst
	 * then goes into a while loop which read the csv until there is nothing more to read
	 * 1st reads the message 
	 * 2nd reads its ID
	 * makes an Array out of the message read
	 * then Decodes it -> checks for any wrong parity bits and corects them
	 * after that stripping the message of its parity bits
	 * in case the file does not exists it prints the Strack Trace
	 */
	public void ReceiveMsg(int party) {
		String pty = PartytoString(party);
		ArrayList<Integer> M = new ArrayList<Integer>();
		System.out.println();
		System.out.println();
		System.out.println(pty + " is receiving message(s)");
		System.out.println();
		
		//try catch to read the codes out of the csv file and decode it
		try {
			CsvReader MSG = new CsvReader("./HammingCode" + pty + ".csv");

			while (MSG.readRecord()) {
				String Message = MSG.get(1);
				String ID = MSG.get(0);
				M = StringtoArray(Message);
				System.out.print("Message with ID: " + ID);
				System.out.println("  " + M + " received");
				M = Decode(M, party);
				System.out.print("Message with ID: " + ID);
				System.out.println("  " + M + " decoded");
				System.out.println();
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}