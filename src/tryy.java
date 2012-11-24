import gotoh.GotohAnswer;
import gotoh.GotohProfile;
import gotoh.LocalAligner;
import loader.Converter;

public class tryy {
	public static void main(String[] args) {
		GotohProfile prof = new GotohProfile();
		prof.setMatrix("matr/dayhoff.mat");
		// prof.setGopen(-9);
		// prof.setGextend(-4);
		prof.setPrintmatrices("txt");
		prof.setPrintali(true);
		Converter c = new Converter();

		String[] sequ1 = new String[2];
		String[] sequ2 = new String[2];

//		1j2xA00: GPLDVQV---------------------TEDAVRR--YLTRKPMTTKDLLKKFQTKKTGLSSEQTVNVLAQILKRLNPERKMINDKMHFSLK-------------
//		1s29A00: -------GSHPLSSENKQKLQKQVEFYFSDVNVQRDIFLKGKAENAEGFV-SLETLLTFKRVNSVTTDVKEVVEAIRPSEKLV---------LSEDGLVRRRDPL
		sequ1 = "1j2xA00: GPLDVQVTEDAVRRYLTRKPMTTKDLLKKFQTKKTGLSSEQTVNVLAQILKRLNPERKMINDKMHFSLK"
				.split(": ");
		sequ2 = "1s29A00: GSHPLSSENKQKLQKQVEFYFSDVNVQRDIFLKGKAENAEGFVSLETLLTFKRVNSVTTDVKEVVEAIRPSEKLVLSEDGLVRRRDPL"
				.split(": ");
		int[] seq1 = c.convertSeq(sequ1[1]);
		int[] seq2 = c.convertSeq(sequ2[1]);
		LocalAligner al = new LocalAligner(prof, seq1, seq2, sequ1[0], sequ2[0]);
		GotohAnswer ga = new GotohAnswer();
		ga = al.alignPair();
		ga.printAlignment();
		System.out.println(al.getCheckScore());

		// NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		// DecimalFormat df = (DecimalFormat)nf;
		// df.setMinimumFractionDigits(4);
		// // DecimalFormat df = new DecimalFormat("#.####");
		// System.out.println(df.format(5.92384752084));
	}

}
