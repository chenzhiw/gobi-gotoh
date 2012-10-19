package loader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author papadopoulos
 * 
 */
public class JLoader {
	private String pairFile;
	private String seqLibFile;

	/**
	 * this function parses a .pairs file and stores the first two columns of
	 * each row, the IDs of the sequences to be aligned
	 * 
	 * @param pairFile
	 *            a file containing at least two columns per row, containing the
	 *            IDs of sequences that are to be aligned with one another
	 * @return a String[][] with each array being a pair of IDs that need to be
	 *         aligned
	 */
	public String[][] loadPairFile(String pairFile) {
		String[][] pairList = {};
		try {
			FileInputStream fstream = new FileInputStream(pairFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int listLength = countLines(pairFile);
			pairList = new String[listLength][2];
			int i = 0;
			while ((strLine = br.readLine()) != null) {
				pairList[i][0] = strLine.split(" ")[0];
				pairList[i][1] = strLine.split(" ")[1];
				i++;
			}
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return pairList;
	}

	/**
	 * *really* fast search for \n characters - maybe expand for whole
	 * expressions?
	 * 
	 * @param filename
	 *            the name of the file to count lines into
	 * @return the number of lines of the text file
	 * @throws IOException
	 */
	public int countLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n')
						++count;
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
}
