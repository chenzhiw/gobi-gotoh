import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class tryy {
	public static void main(String[] args) {
//		GotohProfile prof = new GotohProfile();
////		prof.setPrintali(true);
//		Converter c = new Converter();
//
////		1hstA00: SHPTYSEMI---------AAAIRAEKSRGGSS--RQSIQKYIK---SHYKVGHNADLQIKLSIRRLLAAGVLKQTKGVGASGSFRLA
////		1iufA02: KYPLLEAALFEWQVQQGDDATLSGETIKRAAAILWHKIPEYQDQPVPNFSNGWLEGFRKR------------------------HIL
//		
//		String[] sequ1 = new String[2];
//		String[] sequ2 = new String[2];
//		
//		sequ1 = "1hstA00: SHPTYSEMIAAAIRAEKSRGGSSRQSIQKYIKSHYKVGHNADLQIKLSIRRLLAAGVLKQTKGVGASGSFRLA".split(": ");
//		sequ2 = "1iufA02: KYPLLEAALFEWQVQQGDDATLSGETIKRAAAILWHKIPEYQDQPVPNFSNGWLEGFRKRHIL".split(": ");
//		int[] seq1 = c.convertSeq(sequ1[1]);
//		int[] seq2 = c.convertSeq(sequ2[1]);
//		GlobalAligner al = new GlobalAligner(prof, seq1, seq2, sequ1[0],
//				sequ2[0]);
//		GotohAnswer ga = new GotohAnswer();
//		ga = al.alignPair();
////		ga.printAll();
		
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat df = (DecimalFormat)nf;
		df.setMinimumFractionDigits(4);
//		DecimalFormat df = new DecimalFormat("#.####");
		System.out.println(df.format(5.92384752084));
	}

}
