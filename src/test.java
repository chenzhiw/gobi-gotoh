import gotoh.Aligner;
import gotoh.GotohProfile;
import loader.Converter;

public class test {
	public static void main(String[] args) {
		
		GotohProfile prof = new GotohProfile();
		prof.setMode("global");
		Converter c = new Converter();
		int[] seq1  = c.convertSeq("00:MKRESHKHAEQARRNRLAVALHELASLIPAEWKQQNVSAAPSKATTVEAACRYIRHLQQNGS");
		int[] seq2  = c.convertSeq("00:MKRESHKHAEQARRNRLAVALHELASLIPAEELASLIPAEELASLIPAEAAPSKATTVEAACRYIRHLQQNGS");
		Aligner al = new Aligner(prof, seq1, seq2);
		al.initialize();
		System.out.println(al.score[0]);
	}
		
}
