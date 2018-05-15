package del3;

import java.util.ArrayList;
import java.util.List;

public class smartList {
	private List<String> auxList = new ArrayList<>();
	private List<Integer> resultList = new ArrayList<>();// listen det retuneres med objektet ByteNumber som har
	// hj�lpe liste som holder styr p� om en byte ses to gange, og de
	// forskellige bytes index placering.

	public List<Integer> ByteCounter(List<String> a) {

		for (int i = 0; i < 256; i++) { // fylder resultat listen ud med alle byte sekvenser og s�tter dem til nul.
										// tilf�jer ogs� alle byte sekvenser til auxlisten
			String seq = Integer.toBinaryString(i);
			seq = seq.format("%8s", seq).replace(" ", "0");
			resultList.add(i, 0);
			auxList.add(seq);
			
		}
		for (int i = 0; i < a.size(); i++) { // genneml�ber a.listen og finder pladsen via aux listen for de relevante
												// byte og incremere deres antal i resultat listen
			int index = auxList.indexOf(a.get(i)); // f�r index pladsen for auxlisten hvor byte sekvensen potentielt er.
			if (index != -1) { // hvis der er noget som ikke er en byte i a.listen (i.e. -1) s� kommer det ikke
								// ind p� resultat listen da det s� ikke findes i aux listen
				resultList.set(index, resultList.get(index) + 1);

			}
		}
		return resultList;
	}

	public List<String> getAuxList() {
		return auxList;
	}

	

}
