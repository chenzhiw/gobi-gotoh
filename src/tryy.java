import gotoh.FreeshiftAligner;
import gotoh.GotohAnswer;
import gotoh.GotohProfile;
import loader.Converter;

public class tryy {
	public static void main(String[] args) {
		GotohProfile prof = new GotohProfile();
		prof.setPrintali(true);
		Converter c = new Converter();

		// 1jcdB00:
		// SSNAKADQASSDAQTANAKADQASNDANAARSDAQAAKDDAARANQRADNA------------------------------------------
		// 1jy3Q00:
		// --------------------------------------------------GWPFCSDEDWNTKCPSGCRMKGLIDEVDQDFTSRINKLRDSLF

		String[] sequ1 = new String[2];
		String[] sequ2 = new String[2];

		sequ1 = "1jcdB00: SSNAKADQASSDAQTANAKADQASNDANAARSDAQAAKDDAARANQRADNA"
				.split(": ");
		sequ2 = "1jy3Q00: GWPFCSDEDWNTKCPSGCRMKGLIDEVDQDFTSRINKLRDSLF"
				.split(": ");
		int[] seq1 = c.convertSeq(sequ1[1]);
		int[] seq2 = c.convertSeq(sequ2[1]);
		FreeshiftAligner al = new FreeshiftAligner(prof, seq1, seq2, sequ1[0],
				sequ2[0]);
		GotohAnswer ga = new GotohAnswer();
		ga = al.alignPair();
		ga.printAlignment();

		// NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		// DecimalFormat df = (DecimalFormat)nf;
		// df.setMinimumFractionDigits(4);
		// // DecimalFormat df = new DecimalFormat("#.####");
		// System.out.println(df.format(5.92384752084));
	}

}
