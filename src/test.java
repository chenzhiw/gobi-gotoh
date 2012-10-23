import gotoh.Aligner;
import gotoh.GotohProfile;
import loader.Converter;

public class test {
	public static void main(String[] args) {
		// System.out.println(Double.NEGATIVE_INFINITY - 1);
		GotohProfile prof = new GotohProfile();
		prof.setMode("global");
		prof.setMatrix("blakeCohen");
		Converter c = new Converter();
		int[] seq1 = c
				.convertSeq("00:WTHGQA");
		int[] seq2 = c
				.convertSeq("00:WTHA");
		Aligner al = new Aligner(prof, seq1, seq2);
		al.initialize();
		al.globalAlign();
		al.printAlignment();
		al.trace(6, 4);
	}

}