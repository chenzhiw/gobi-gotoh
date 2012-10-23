import gotoh.FreeshiftAligner;
import gotoh.GotohAnswer;
import gotoh.GotohProfile;
import loader.Converter;

public class test {
	public static void main(String[] args) {
		GotohProfile prof = new GotohProfile();
		Converter c = new Converter();		
	
		String[] sequ1 = new String[2];
		sequ1 = "1j2xA00: GPLDVQVTEDAVRRYLTRKPMTTKDLLKKFQTKKTGLSSEQTVNVLAQILKRLNPERKMINDKMHFSLK"
				.split(": ");
		String[] sequ2 = new String[2];
		sequ2 = "1hw2B01: SPAGFAEEYIIESIWNNRFPPGTILPAERELSELIGVTRTTLREVLQRLARDGWLTIQHGKPTKVNNFWETS"
				.split(": ");
		int[] seq1 = c.convertSeq(sequ1[1]);
		int[] seq2 = c.convertSeq(sequ2[1]);
		FreeshiftAligner al = new FreeshiftAligner(prof, seq1, seq2, sequ1[0], sequ2[0]);
		GotohAnswer ga = new GotohAnswer();
		ga = al.alignPair();
		System.out.println("jamaica".substring(1));
	}

}