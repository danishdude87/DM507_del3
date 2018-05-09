package del3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Huffman {

	public void Encode(String path, String exit) {// SKAL ÆNDRES TIL ARGE[0] og ARGE[1]
		bitReader read = new bitReader();
		List<String> b = read.bitReader(path);
		smartList test3 = new smartList();
		List<Integer> f = test3.ByteCounter(b); // Encode 1) Løst her.
		List<Element> nodeList = new ArrayList<>();
		int value;
		PQHeap PqHeap = new PQHeap(256);
		for (int i = 0; i < f.size(); i++) {
			HuffmanNode Hnode = new HuffmanNode(i, null, null);
			if (f.get(i) != 0) {
			}
			Element node = new Element(f.get(i), Hnode);
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
		String[] codeTable = new String[256];
		String s = "";
		codeTable = Huffman.buildCode(codeTable, root, s);
		/*
		 * for (int i = 0; i < codeTable.length; i++) { if (f.get(i) != 0) {
		 * System.out.println(i + "  " + test3.getAuxList().get(i) + "   " + f.get(i) +
		 * "   " + codeTable[i]); } } System.out.println(b);
		 * 
		 */

		Huffman.compress(codeTable, path, exit);

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

	private static void compress(String[] codeTable, String path, String exit) {


		File file = new File(exit);

		ArrayList<String> auxList = new ArrayList<>();
		for (int i = 0; i < 256; i++) {
			String seq = Integer.toBinaryString(i);
			seq = seq.format("%8s", seq).replace(" ", "0");
			auxList.add(seq);
		}
		while (true) {
			try {
				if (file.exists()) {
					System.out.println("File existes. Press y  to overwrite existing file, or press other to cancel:");
					Scanner input = new Scanner(System.in);
					String action;
					action = input.next();
					if (!action.equals("y")) {
						input.close();
						break;
					}
					input.close();
				}

				file.createNewFile();
				FileWriter fw = new FileWriter(file, false);
				BufferedWriter bw = new BufferedWriter(fw);
				for (int i = 0; i < codeTable.length; i++) {
					bw.write(codeTable[i]);
					bw.newLine();

				}
				bitReader read = new bitReader();
				List<String> b = read.bitReader(path);
				for (int i = 0; i < b.size(); i++) {
					int index = auxList.indexOf(b.get(i));
					if (index != -1) {
						bw.write(codeTable[index].toString());
					}
				}
				bw.close();
				break;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
