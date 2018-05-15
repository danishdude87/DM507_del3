package del3;

public class TestProjectPartIII {
	/*
	 * Test program exercising DictBinTree.java. Move to the directory containing
	 * the project java files, compile and run.
	 */

	public static void main(String[] args) {
		String a = "C:\\Users\\Flindt\\Desktop\\testfil.txt";
		String b ="C:\\Users\\Flindt\\Desktop\\compresedFile.txt";
		String c = "C:\\Users\\Flindt\\Desktop\\overview.jpg";
		String d = "C:\\Users\\Flindt\\Desktop\\compsedFile.jpg";
		String e = "C:\\Users\\Flindt\\Desktop\\decompsedFile.jpg";
		Huffman test = new Huffman();
		test.Encode(c,d);
		test.Decode(d,e);
	}
}