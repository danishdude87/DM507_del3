package del3;

public class TestProjectPartIII {
	/*
	 * Test program exercising DictBinTree.java. Move to the directory containing
	 * the project java files, compile and run.
	 */

	public static void main(String[] args) {
		String a = "C:\\Users\\Flindt\\Desktop\\testfil.txt";
		String b ="C:\\Users\\Flindt\\Desktop\\compresedFile.txt";
		Huffman test = new Huffman();
		test.Encode(a,b);
	}
}