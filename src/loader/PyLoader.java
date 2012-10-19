package loader;

import org.python.util.PythonInterpreter;

/**
 * this class handles all operations that have to do with reading files; it
 * should take care of reading the pairFile and retrieve the aforementioned
 * pairs from a sequence library file. This implementation is grossly
 * inefficient, and right now it serves as a proof of concept. I needed much
 * less code to achieve the same result, though.
 * 
 * @author papadopoulos
 * 
 */
public class PyLoader {
	private String pairFile;
	private String seqLibFile;

	/**
	 * this function parses a .pairs file and stores the first two columns of each row, the IDs of the sequences to be aligned
	 * @param pairFile a file containing at least two columns per row, containing the IDs of sequences that are to be aligned with one another
	 */
	public void loadPairFile(String pairFile) {
		PythonInterpreter interp = new PythonInterpreter();
		String pFile = "'" + pairFile + "'";
		String command = "f = open(" + pFile + ", 'r')";
		interp.exec(command);
		interp.exec("for i, l in enumerate(f):" + "\n\t" + "pass");
		int listLength = interp.eval("(i+1)").asInt();
		String[][] pairList = new String[listLength][2];
		interp.exec("f = open('matr/sanity.pairs', 'r')");
		interp.exec("seq1 = []\n" + "seq2 = []\n" + "for line in f:\n"
				+ "	seq1.append(line.split(' ')[0])\n"
				+ "	seq2.append(line.split(' ')[1])\n");
		// interp.exec("print(seq1.pop())");
		for (int j = 0; j < listLength; j++) {
			interp.exec("i = 0");
			pairList[j][0] = interp.eval("seq1.pop()").asString();
			pairList[j][1] = interp.eval("seq2.pop()").asString();
		}
		System.out.println(pairList[1][0]);
	}
}
