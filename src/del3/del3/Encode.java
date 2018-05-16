package del3;

import java.util.List;

public class Encode {
public static void main(String[] args) {
	bitReader read = new bitReader();
	List<String> b = read.bitReader(args[0]);
	smartList freqCount = new smartList();
	List<Integer> freq = freqCount.ByteCounter(b); // Encode 1) L�st her.
	HuffmanNode root = Huffman.HuffmanGenerator(freq); //Encode 2) L�st her.
	String[] codeTable = new String[256];
	String s = "";
	codeTable = Huffman.buildCode(codeTable, root, s); //Encode 3) l�st her.
	Huffman.compress(codeTable, freq, args[0], args[1]);
}
}
