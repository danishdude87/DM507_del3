package del3;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class bitReader {

	public List<String> bitReader(String a) {
		List<String> list = new ArrayList<>();
		try {
			FileInputStream fin = new FileInputStream(a);
			BitInputStream bin = new BitInputStream(fin);
			int x;
			int y = 0;
			String byt = "";

			do {
				x = bin.readBit();
				if (y < 8) { // Vil kun tilføje byte, så -1 og "løse" bit der må være bliver ikke medtaget.
					byt = byt.concat(Integer.toString(x));
					y++;
				}
				if (y == 8) { // Tilføj hel byte og nulstil opsamlings systemet.
					list.add(byt);
					byt = "";
					y = 0;
				}

			} while (x != -1);// minus en er det der sker når intet er tilbage i filen)

			fin.close();
			bin.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
}
