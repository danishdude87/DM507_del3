package del3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Huffman {
	/*
	 * Takes a path for a compresed file. File must be compresed by the Encode
	 * methoed. Returns a decompresed file. If a file already exist of the given
	 * name user action is required.
	 */
	public void Decode(String path, String exit) {

		File exitFile = new File(exit);
		bitReader read = new bitReader(); // Fejlen er i bitReader da den ikke får en hel byte til sidst.
		List<String> b = read.bitReader(path); // Kan se at den manglende byte er i den komprimerede fil.
		List<Integer> freq = new ArrayList<>();// bit der mangler er 100011	
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
					//System.out.println(s + "  " + index);
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

	public void Encode(String path, String exit) {// SKAL ÆNDRES TIL ARGE[0] og ARGE[1]

		bitReader read = new bitReader();
		List<String> b = read.bitReader(path);
		smartList freqCount = new smartList();
		List<Integer> freq = freqCount.ByteCounter(b); // Encode 1) Løst her.
		HuffmanNode root = Huffman.HuffmanGenerator(freq);
		String[] codeTable = new String[256];
		String s = "";
		codeTable = Huffman.buildCode(codeTable, root, s);
		Huffman.compress(codeTable, freq, path, exit);
	}

	private static HuffmanNode HuffmanGenerator(List<Integer> x) {
		List<Element> nodeList = new ArrayList<>();
		PQHeap PqHeap = new PQHeap(256);
		for (int i = 0; i < x.size(); i++) {
			HuffmanNode Hnode = new HuffmanNode(i, null, null);
			if (x.get(i) != 0) {
			}
			Element node = new Element(x.get(i), Hnode);
			PqHeap.insert(node);
			nodeList.add(node); // Adds to a list a node with its byte rep and frequency

		}
		do {
			Element nodeX = PqHeap.extractMin(); // leftnode
			Element nodeY = PqHeap.extractMin();// rightnode
			int sumFreq = nodeX.getKey() + nodeY.getKey();
			HuffmanNode NewNode = new HuffmanNode(sumFreq, nodeX.getObject(), nodeY.getObject());
			Element NewElement = new Element(sumFreq, NewNode);
			PqHeap.insert(NewElement);
		} while (PqHeap.PQHeapSize() > 1);// Den forsætter til den kun har en rootnode. (i.e. et træ)

		HuffmanNode root = (HuffmanNode) PqHeap.extractMin().getObject(); // The nodeList is now just one index but
		return root;
	}

	private static String[] buildCode(String[] codeTable, Object node, String s) {
		HuffmanNode root = (HuffmanNode) node;
		if (root.returnLeft() != null || root.returnRight() != null) {
			buildCode(codeTable, root.returnLeft(), s + '0');
			buildCode(codeTable, root.returnRight(), s + '1');
		} else {
			codeTable[root.getKey()] = s;
		}
		return codeTable;
	}

	private static void compress(String[] codeTable, List<Integer> freq, String path, String exit) {
		File exitFile = new File(exit);
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
				String byteValue;
				char[] bitValue;

				for (int i = 0; i < freq.size(); i++) {
					BOS.writeInt(freq.get(i));
				}

				bitReader read = new bitReader();
				List<String> b = read.bitReader(path);
				for (int i = 0; i < b.size(); i++) {
					int index = auxList.indexOf(b.get(i));
					if (index != -1) {
						bitValue = codeTable[index].toCharArray();
						for (int j = 0; j < bitValue.length; j++) {
							if (bitValue[j] == 48) {
								BOS.writeBit(0);
							} else {
								BOS.writeBit(1);
							}
						}
					}
				}
				BOS.close();
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
