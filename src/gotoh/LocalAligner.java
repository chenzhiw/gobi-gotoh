package gotoh;

import static resources.Aa.REVERSE;

import java.util.LinkedList;

import loader.Converter;
import resources.Aa;

public class LocalAligner extends Aligner {
	public LocalAligner(GotohProfile profile, int[] seq1, int[] seq2,
			String seq1ID, String seq2ID) {
		super.profile = profile;
		super.seq1 = seq1;
		super.seq2 = seq2;
		super.seq1ID = seq1ID;
		super.seq2ID = seq2ID;
		super.score = new double[seq1.length + 1][seq2.length + 1];
		super.ins = new double[seq1.length + 1][seq2.length + 1];
		super.del = new double[seq1.length + 1][seq2.length + 1];
		tracebackList = new LinkedList<int[]>();
	}

	public void initialize() {
		score[0][0] = 0;
		for (int i = 1; i < seq1.length + 1; i++) {
			score[i][0] = 0;
			del[i][0] = 0;
		}
		for (int i = 1; i < seq2.length + 1; i++) {
			score[0][i] = 0;
			ins[0][i] = 0;
		}
		ins[0][0] = del[0][0] = 0;

	}

	private static final double epsilon = 0.0001d;

	private static boolean isInEpsilon(double a, double b) {
		return (a > (b - epsilon)) && (a < (b + epsilon));
	}

	/**
	 * super function aligns the two sequences globally
	 */
	public void align() {
		for (int x = 1; x <= seq1.length; x++) {
			for (int y = 1; y <= seq2.length; y++) {
				// if(x == 56 && y == 83)
				// System.out.println();
				double w1 = profile.getGextend() + profile.getGopen();
				ins[x][y] = Math.max(score[x - 1][y] + w1, ins[x - 1][y]
						+ profile.getGextend());
				double tScore = score[x - 1][y];
				double tInsPrev = ins[x - 1][y];
				ins[x][y] = Math.max(ins[x][y], 0.0);
				del[x][y] = Math.max(score[x][y - 1] + w1, del[x][y - 1]
						+ profile.getGextend());
				del[x][y] = Math.max(del[x][y], 0.0);
				double temp = score[x - 1][y - 1]
						+ profile.getMatrixScore(profile.colIndex[seq1[x - 1]],
								profile.rowIndex[seq2[y - 1]]);
				score[x][y] = Math.max(temp, Math.max(ins[x][y], del[x][y]));
				score[x][y] = Math.max(score[x][y], 0.0);

				if (score[x][y] >= max[2] || isInEpsilon(score[x][y], max[2])) {
					max[0] = x - 1;
					max[1] = y - 1;
					max[2] = score[x][y];
				}
			}
		}
	}

	public void trace(int x, int y) {
		int[] tres = { x, y };
		tracebackList.push(tres);
		while (score[x][y] != 0) {
			if (isInEpsilon(score[x][y], ins[x][y])) {
				// find the x-k,y position where the gap was opened

				boolean found = false;
				while (!found) {
					if (ins[x - 1][y] + profile.getGextend() == ins[x][y]) {
						int[] res = { x - 1, y };
						tracebackList.push(res);
						x--;
						continue;
					} else {
						int[] res = { x - 1, y };
						x--;
						tracebackList.push(res);
						found = true;
					}
				}
			} else if (isInEpsilon(score[x][y], del[x][y])) {
				// find the x,y-k position where the gap was opened
				boolean found = false;
				while (!found) {
					if (del[x][y - 1] + profile.getGextend() == del[x][y]) {
						int[] res = { x, y - 1 };
						tracebackList.push(res);
						y--;
						continue;
					} else {
						int[] res = { x, y - 1 };
						y--;
						tracebackList.push(res);
						found = true;
					}
				}
			} else {// that means we came from score[x-1][y-1]
				int[] res = { x - 1, y - 1 };
				// checkScore += profile.getMatrixScore(seq1[x - 1], seq2[y -
				// 1]);
				tracebackList.push(res);
				x--;
				y--;
				continue;
			}
		}
	}

	public String[] interpretTraceback() {
		String[] result = new String[2];
		result[0] = result[1] = "";
		int[] temp = new int[2];
		int[] prev = new int[2];
		if (tracebackList.isEmpty()) {
			prev[0] = (int) max[0];
			prev[1] = (int) max[1];
		} else {
			prev = tracebackList.pop();
		}
		// super element has y=0, so I have to align every x before
		// prev[0],prev[1] with gaps, or x=0, so the other way round

		for (int i = 0; i < prev[0]; i++) {
			result[0] += Character.toString(REVERSE[(char) seq1[i]]);
			result[1] += "-";
		}

		for (int i = 0; i < prev[1]; i++) {
			result[1] += Character.toString(REVERSE[(char) seq2[i]]);
			result[0] += "-";
		}

		while (!tracebackList.isEmpty()) {
			temp = tracebackList.pop();
			if (temp[0] == prev[0]) {
				result[0] += "-";
				result[1] += Character
						.toString(REVERSE[(char) seq2[temp[1] - 1]]);
			} else if (temp[1] == prev[1]) {
				result[0] += Character
						.toString(REVERSE[(char) seq1[temp[0] - 1]]);
				result[1] += "-";
			} else {
				result[0] += Character
						.toString(REVERSE[(char) seq1[temp[0] - 1]]);
				result[1] += Character
						.toString(REVERSE[(char) seq2[temp[1] - 1]]);
			}
			prev = temp;
		}

		if (prev[0] != seq1.length && prev[1] != seq2.length) {
			result[0] += Character.toString(REVERSE[(char) seq1[prev[0]]]);
			result[1] += Character.toString(REVERSE[(char) seq2[prev[1]]]);
		}

		// now we are at the end of the freeshift; it remains to recover the
		// portion of the alignment that is parallel with the y axis

		for (int i = prev[0]; i <= seq1.length; i++) {
			result[0] += Character.toString(REVERSE[(char) seq1[i - 1]]);
			result[1] += "-";
		}

		for (int i = prev[1]; i <= seq2.length; i++) {
			result[1] += Character.toString(REVERSE[(char) seq2[i - 1]]);
			result[0] += "-";
		}

		return result;
	}

	@Override
	public void printAlignment() {
		for (int y = 0; y <= seq2.length; y++) {
			for (int x = 0; x <= seq1.length; x++) {
				System.out.print(score[x][y] + "\t");
			}
			System.out.println();
		}
	}

	@Override
	public GotohAnswer alignPair() {
		initialize();
		align();
		trace((int) max[0], (int) max[1]);
		String[] sresult = new String[2];
		sresult = interpretTraceback();
		// 1j2xA00:
		// GP-----------------------------LDVQVTEDAVRRYLTR---KPMTTKDLLKKFQTKKTGLSSEQTVNVLAQILKRLNPERKMINDKMHFHFSLK
		// 1p4xA01:
		// --MKYNNHDKIRDFIIIEAYMFRFKKKVKPEVDMTIKEFILLTYLFHQQENTLPFKKIVSDLCYKQSDLVQHIKVLVKHSYISKV---RSKIDERNTY-----
		checkScore = calcLocalScore(sresult[0], sresult[1]);
		GotohAnswer result = new GotohAnswer(seq1ID, seq2ID, sresult[0],
				sresult[1], max[2], profile);
		// System.out.println(seq1ID + " " + seq2ID + " " + max[2]);
		return result;
	}

	private double calcLocalScore(String a, String b) {
		// look for first and last (mis-)match
		// This idea came from Paul Kerbs.
		// It should be performing better. thanks!
		int length = a.length();
		int first = 0;
		int last = 0;

		double debugScore = 0;
		double debugGaps = 0;

		// find first (mis-)match
		for (int i = 0; i < length - 1; i++) {
			if (a.charAt(i) != '-' && b.charAt(i) != '-') {
				first = i;
				break;
			}
		}
		// find last (mis-)match
		for (int i = length - 1; i > 0; i--) {
			if (a.charAt(i) != '-' && b.charAt(i) != '-') {
				last = i;
				break;
			}
		}
//		System.out.println("first = " + first + ", last = " + last);
		boolean gapopened = false;
		double addedvalue = 0;
		for (int i = first; i <= last; i++) {
			addedvalue = 0;
			char x = a.charAt(i);
			char y = b.charAt(i);
			if (x == '-' || y == '-') { // deletion
				if (gapopened) {
					addedvalue = profile.getGextend();
					debugGaps += addedvalue;
				} else { // insertion
					gapopened = true;
					addedvalue = profile.getGopen() + profile.getGextend();
					debugGaps += addedvalue;
				}
			} else {
				gapopened = false;
				int ix = profile.colIndex[Aa.getIntRepresentation(String
						.valueOf(x))];
				int iy = profile.rowIndex[Aa.getIntRepresentation(String
						.valueOf(y))];
				addedvalue = profile.getMatrixScore(ix, iy);
				// System.out.println(String.valueOf(x) + String.valueOf(y) +
				// " " + addedvalue);
				debugScore += addedvalue;
			}
		}
//		System.err.println(debugScore + debugGaps);
		return debugScore + debugGaps;
	}

	public double getCheckScore() {
		return checkScore;
	}
}
