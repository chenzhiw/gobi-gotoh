import gotoh.Aligner;
import gotoh.FreeshiftAligner;
import gotoh.GlobalAligner;
import gotoh.GotohAnswer;
import gotoh.GotohProfile;
import gotoh.LocalAligner;

import java.io.IOException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import loader.Converter;
import loader.JLoader;
import resources.Matrix;

public class Main {
	public static void main(String[] args) throws IOException {

		GotohProfile prof = new GotohProfile();

		Converter c = new Converter();

		String[] sequ1 = new String[2];
		String[] sequ2 = new String[2];

		OptionParser parser = new OptionParser();

		parser.accepts("pairs", "pairfile with sequence IDs").withRequiredArg()
				.ofType(String.class);
		parser.accepts("seqlib", "sequence file").withRequiredArg()
				.ofType(String.class);
		parser.accepts(
				"m",
				"matrix name (standard dayhoff, alternatives: pam250, blosum50, threader, blakeCohen)")
				.withOptionalArg().ofType(String.class);
		parser.accepts("go", "gap open penalty").withOptionalArg()
				.ofType(Double.class);
		parser.accepts("ge", "gap extend penalty").withOptionalArg()
				.ofType(Double.class);
		parser.accepts("mode",
				"alignment mode (default: freeshift, alternatives: global, local)")
				.withOptionalArg().ofType(String.class);
		parser.accepts("printali",
				"whether to print the alignment along with the scores");
		parser.accepts("printmatrices",
				"prints the matrices before the alignment; format can be txt or html")
				.withOptionalArg().ofType(String.class);
		parser.accepts("check",
				"the algorithm now checks the score with the alignment");
		parser.accepts("h", "show help");

		OptionSet options = parser.parse(args);

		if (options.has("h")) {
			parser.printHelpOn(System.out);
		}

		if (options.has("pairs")) {
			prof.setPairs((String) options.valueOf("pairs"));

		} else {
			System.err.println("a pairs file is required");
			return;
		}
		if (options.has("seqlib")) {
			prof.setSeqlib((String) options.valueOf("seqlib"));
		} else {
			System.err.println("a seqlib file is required");
			return;
		}
		if (options.has("m")) {
			prof.setMatrix((String) options.valueOf("m"));
		}

		if (options.has("go")) {
			prof.setGopen((Double) options.valueOf("go"));
			if (prof.getGopen() > 0)
				prof.setGopen(-prof.getGopen());
		}
		if (options.has("ge")) {
			prof.setGextend((Double) options.valueOf("ge"));
			if(prof.getGextend() > 0)
				prof.setGextend(-prof.getGextend());
		}
		if (options.has("mode")) {
			prof.setMode((String) options.valueOf("mode"));
		}
		if (options.has("printali")) {
			prof.setPrintali(true);
		}
		if (options.has("printmatrices")) {
			prof.setPrintmatrices((String) options.valueOf("printmatrices"));
		} else {
			prof.setPrintmatrices("");
		}
		if (options.has("check")) {
			prof.setCheck(true);
		} else {
			prof.setCheck(false);
		}

		JLoader loader = new JLoader(prof.getPairs(), prof.getSeqlib());
		loader.loadPairFile();
		loader.loadSeqLibFile();

		if (prof.getPrintmatrices().equals("txt")) {
			Matrix.printMatriceTxt(prof.getMatrix());
		} else if (prof.getPrintmatrices().equals("html")) {
			System.out.println(Matrix.printMatriceHtml(prof.getMatrix()));
		}

		for (int i = 0; i < loader.getPairLength(); i++) {
			sequ1 = loader.retrieveSeqBin(loader.pairs[i][0], loader.sequences)
					.split(":");
			sequ2 = loader.retrieveSeqBin(loader.pairs[i][1], loader.sequences)
					.split(":");
			int[] seq1 = c.convertSeq(sequ1[1]);
			int[] seq2 = c.convertSeq(sequ2[1]);
			Aligner al = new Aligner();

			if (prof.getMode().equals("global")) {
				al = new GlobalAligner(prof, seq1, seq2, sequ1[0], sequ2[0]);
			} else if (prof.getMode().equals("local")) {
				al = new LocalAligner(prof, seq1, seq2, sequ1[0], sequ2[0]);

			} else {
				al = new FreeshiftAligner(prof, seq1, seq2, sequ1[0], sequ2[0]);
			}
			GotohAnswer ga = new GotohAnswer();
			ga = al.alignPair();
			al.printMatrices();
			ga.printAlignment();
//			if (prof.isCheck()) {
//				if (ga.getScore() != al.getCheckScore()) {
//					ga.printAlignment();
//				}
//			}
		}
		if (prof.getPrintmatrices().equals("html")) {
			System.out.println("</body>");
			System.out.println("</html>");
		}
//		System.out.println("end");
	}

}