package del3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Decode {

	public static void main(String[] args) {
		File exitFile = new File(args[1]);
		bitReader read = new bitReader();
		List<String> b = read.bitReader(args[0]);
		List<Integer> freq = new ArrayList<>();
		String fByte;
		String[] codeTable = new String[256];

		/*
		 * Reads the first 256x4 byte. Decode this for the frequency of the different
		 * byte.
		 */
		for (int i = 0; i < 1021; i = i + 4) {
			fByte = b.get(i) + b.get(i + 1) + b.get(i + 2) + b.get(i + 3);
			freq.add(Integer.parseInt(fByte, 2));
		}

		HuffmanNode root = Huffman.HuffmanGenerator(freq);
		String s = "";
		codeTable = Huffman.buildCode(codeTable, root, s);
		List<String> codeArray = new ArrayList<String>(Arrays.asList(codeTable));

		// Makes the auxList with the different byte sequences.

		ArrayList<String> auxList = new ArrayList<>();
		for (int i = 0; i < 256; i++) {
			String seq = Integer.toBinaryString(i);
			seq = seq.format("%8s", seq).replace(" ", "0");
			auxList.add(seq);
		}
		while (true) {
			try {
				if (exitFile.exists()) {
					System.out.println("File existes. Press y  to overwrite existing file, or press other to cancel:");
					Scanner input = new Scanner(System.in);
					String action;
					action = input.next();
					if (!action.equals("y")) {
						break;
					}
				}

				exitFile.createNewFile();
				FileOutputStream OPS = new FileOutputStream(exitFile, false);
				BitOutputStream BOS = new BitOutputStream(OPS);

				char[] bitValue = new char[8];
				List<Character> charList = new ArrayList<>();

				/*
				 * Reads the remaning byte and converts them to a bit list (charList) Read
				 * through each bit and concatenate it to the next bit in the string s. When
				 * found in huffman codeArray its index is used to find it in the auxList. The
				 * byte representing the full sequence is used it is written to the exitfile.
				 * After that string s is zeroed.
				 */
				for (int i = 1024; i < b.size(); i++) {
					bitValue = b.get(i).toCharArray();
					for (int j = 0; j < bitValue.length; j++) {
						charList.add(bitValue[j]);
					}
				}

				s = "";

				int index;
				for (int i = 0; i < charList.size(); i++) {
					s = s + charList.get(i);
					index = codeArray.indexOf(s);
					if (index != -1) {
						bitValue = auxList.get(index).toCharArray();
						for (int j = 0; j < bitValue.length; j++) {
							if (bitValue[j] == 48) {
								BOS.writeBit(0);
							} else {
								BOS.writeBit(1);

							}
						}
						s = "";
					}
				}
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
