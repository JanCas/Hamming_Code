# Hamming Code

**Functionality:**

This Program takes in the length of the message to be sent, the party of the parity and how many messages are sent. It then encodes the messages, and sends them by writing into a Csv file. Every message has its own ID. During the sending the message can get up to one corrupted bit. After sending, the receive method then processes the Csv file. If there is a corrupt bit, it finds and corrects it.

**How it encodes and sends:**

1. from left to right, it puts in placeholders for where the parity bits will be (every position in the Array List that is a Power of 2)
2. calculates the coverage that each parity bit has (bits that are covered by a particular parity bit)
3. calculates the value of the parity bits (if the sum of the covered bits is even and the party is even then the parity bit is 1,          otherwise if the sum is odd and the party even then parity bit is 0; works the other way around if the party is odd) and decides if they are either 1 or 0
4. now the message can be corrupted (random bit is being flipped, slight chance of no bit being flipped)
5. writes the message into a csv file 


**How it receives and decodes:**

1. Reads the message out of the csv file
2. decodes by checking for a faulty parity bit and correcting it (it checks every single parity bit if the value it has right now is the same value as it is supposed to have)
3. removes its parity bits

**Methods:**

1. _Hamming_:takes in 3 parameters: Length, Number of messages to be sent and the party: sends,encodes,receives the messages. It is the Constructor
2. _CalcParityBit_: 2 parameters: Sum of the covered Bits, party. Calculates the Value of a parity Bit.
3. _CheckandCorrect_: 2 parameters: Message out of csv file, party. Checks and corrects the message by  using _CheckParityBit_
4. _CheckParityBit_: 3 parameters: Message out of csv file, index of the parity bits, party. checks if the parity bit is corrupted or faulty
5. _CheckPow2_: 1 parameter: a number. checks if the number is a power of 2.
6. _Decode_: 2 parameters: Message out of csv file, party. Decodes the message by using _CheckandCorrect_.
7. _Encode_: 2 parameters: Original Message, party . Encodes the message.
8. _GenerateMsg_: 1 parameter: length of the message. Creates a message of any given length
9. _InsertParityBits_: 1 parameter: Original message. puts in placeholders where the parity bits will be
10. _MaxNumberInCsv_: 1 parameter: party. Checks for the Max ID in the csv file
11. _Noise_: 1 parameter: message. flips a random bit
12. _ParityBitCoverage_: 2 parameter: length of the Message, index of parity bit. Finds the covered bits of a certain parity bit
13. _PartytoString_: 1 parameter: party. makes an integer into a String 1 = odd, 0 = even
14. _ReceiveMsg_: 1 parameter: party. receives all the messages of a certain party
15. _ReoveParityBit_: 1 parameter: decoded message. removes all the parity bits
16. _send_: 3 parameters: message, ID, party. Writes the message to a party with a certain ID into a csv party
17. _sendXMSGs_: 3 parameters: Number of messages to send, party, Length of message. Combines all the methods so that it generates encodes and sends a certain amount of messages.
18. _StringtoArray_: 1 parameter: String. Converts a String to an Array.


Source Wikipedia Haming Code: https://en.wikipedia.org/wiki/Hamming_code
