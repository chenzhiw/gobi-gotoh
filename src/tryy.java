import gotoh.FreeshiftAligner;
import gotoh.GotohAnswer;
import gotoh.GotohProfile;
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

		// 1b8iA00:
		// FYPW---------------MARQTYTRYQTLELEKEFHTNHYLTRRRRIEMAHAL-------SLTERQIKIWFQNRRMKLKKE
		// 1wh5A00:
		// ----GSSGSSGSSAEAGGGIRKRHRTKFTAEQKERMLALAERIGWRIQRQDDEVIQRFCQETGVPRQVLKVWLHNNKHSGPSS
		sequ1 = "1b8iA00: FYPWMARQTYTRYQTLELEKEFHTNHYLTRRRRIEMAHALSLTERQIKIWFQNRRMKLKKE"
				.split(": ");
		sequ2 = "1wh5A00: GSSGSSGSSAEAGGGIRKRHRTKFTAEQKERMLALAERIGWRIQRQDDEVIQRFCQETGVPRQVLKVWLHNNKHSGPSS"
				.split(": ");
		int[] seq1 = c.convertSeq(sequ1[1]);
		int[] seq2 = c.convertSeq(sequ2[1]);
		FreeshiftAligner al = new FreeshiftAligner(prof, seq1, seq2, sequ1[0],
				sequ2[0]);
		GotohAnswer ga = new GotohAnswer();
		ga = al.alignPair();
		// al.printMatrices();
		ga.printAlignment();
		System.out.println(al.getCheckScore());

		double wert = al.calcCheckScore(
				"FYPW---------------MARQTYTRYQTLELEKEFHTNHYLTRRRRIEMAHAL-------SLTERQIKIWFQNRRMKLKKE",
				"----GSSGSSGSSAEAGGGIRKRHRTKFTAEQKERMLALAERIGWRIQRQDDEVIQRFCQETGVPRQVLKVWLHNNKHSGPSS");
		System.err.println(wert);

		if (prof.getPrintmatrices().equals("html")) {
			System.out.println("</body>");
			System.out.println("</html>");
		}

		// NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		// DecimalFormat df = (DecimalFormat)nf;
		// df.setMinimumFractionDigits(4);
		// // DecimalFormat df = new DecimalFormat("#.####");
		// System.out.println("PAFVNKLWSMVNDKSNEKFIHWSTSGESIVVPNRERF-----------".length());
	}

}
