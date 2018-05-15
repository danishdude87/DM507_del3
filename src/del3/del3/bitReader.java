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
				if (y < 8) { // Vil kun tilf�je byte, s� -1 og "l�se" bit der m� v�re bliver ikke medtaget.
					byt = byt.concat(Integer.toString(x));
					y++;
				}
				if (y == 8) { // Tilf�j hel byte og nulstil opsamlings systemet.
					list.add(byt);
					byt = "";
					y = 0;
				}

			} while (x != -1);// minus en er det der sker n�r intet er tilbage i filen)

			fin.close();
			bin.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
}
