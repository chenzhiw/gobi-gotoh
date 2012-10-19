import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import loader.JLoader;

public class test {
	public static void main(String[] args) throws IOException {
		String query = "1ltqA01";
		String file = "mat/domains.seqlib";
		JLoader jl = new JLoader("", file);
		int lines = jl.countLines(file);
		System.out.println(lines);

		/*
		 * manual search
		 */

//		 FileInputStream fstream = new FileInputStream(file);
//		 DataInputStream in = new DataInputStream(fstream);
//		 BufferedReader br = new BufferedReader(new InputStreamReader(in));
//		 String result = "";
//		 String strLine;
//		 while ((strLine = br.readLine()) != null) {
//		 if (strLine.startsWith(query))
//		 result = strLine;
//		 }
//		 in.close();
//		 System.out.println(result);

		/*
		 * binary search. Seems to be an average of 0.02 secs better than manual
		 * search for queries near the middle of the file. Since this procedure
		 * will be used twice every alignment for a total of more than 1500
		 * times, I'm guessing the sacrifice in memory is worth the 1 minute
		 * I'll be winning.
		 * 
		 * it remains a very interesting question what would happen with a
		 * really big seqLib file; will a computer have enough memory for all
		 * things to be stored in RAM? Remember I am also saving the pair list
		 * on memory.
		 * 
		 * I think the computer will easily handle this assignment, but should
		 * probably look for a larger seqlib and pairfile.
		 */
		String[] seqList = new String[lines];
		FileInputStream fstream = new FileInputStream(file);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String result = "";
		String strLine;
		int i = 0;
		while ((strLine = br.readLine()) != null) {
			seqList[i] = strLine;
			i++;
		}
		in.close();
		int min = 0;
		int max = lines;
		while (min < max) {
			int mid = (min + max) / 2;

			// code must guarantee the interval is reduced at each iteration
			assert (mid < max);
			// note: 0 <= imin < imax implies imid will always be less than imax

			// reduce the search
			if (seqList[mid].substring(0, 6).compareTo(query) < 0)
				min = mid + 1;
			else
				max = mid;
		}
		// At exit of while:
		// if A[] is empty, then imax < imin
		// otherwise imax == imin

		// deferred test for equality
		if ((max == min) && (seqList[min - 1].startsWith(query)))
			System.out.println("Query found at position " + min + ":" + seqList[min - 1]);
		else
			System.out.println("string not found");
	}
}
