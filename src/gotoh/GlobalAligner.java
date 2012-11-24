package gotoh;

import static resources.Aa.REVERSE;

import java.util.LinkedList;

import resources.Aa;

public class GlobalAligner extends Aligner {

	public GlobalAligner(GotohProfile profile, int[] seq1, int[] seq2,
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
			score[i][0] = profile.getGextend() * i + profile.getGopen();
			del[i][0] = Double.NEGATIVE_INFINITY;
		}
		for (int i = 1; i < seq2.length + 1; i++) {
			score[0][i] = profile.getGextend() * i + profile.getGopen();
			ins[0][i] = Double.NEGATIVE_INFINITY;
		}
		ins[0][0] = del[0][0] = Double.NEGATIVE_INFINITY;
	}

	/**
	 * super function aligns the two sequences globally
	 */
	public void align() {
		for (int x = 1; x <= seq1.length; x++) {
			for (int y = 1; y <= seq2.length; y++) {
				double w1 = profile.getGopen() + profile.getGextend();
				ins[x][y] = Math.max(score[x - 1][y] + w1, ins[x - 1][y]
						+ profile.getGextend());
				del[x][y] = Math.max(score[x][y - 1] + w1, del[x][y - 1]
						+ profile.getGextend());
				double temp = score[x - 1][y - 1]
						+ profile.getMatrixScore(profile.colIndex[seq1[x - 1]],
								profile.rowIndex[seq2[y - 1]]);
				score[x][y] = Math.max(temp, Math.max(ins[x][y], del[x][y]));
			}
		}
	}

	public void trace(int x, int y) {
		while (x > 0 && y > 0) {
			if (score[x][y] == ins[x][y]) {
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
			} else if (score[x][y] == del[x][y]) {
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
		result[0] = "";
		result[1] = "";
		int[] temp = new int[2];
		int[] prev = new int[2];
		prev = tracebackList.pop();
		// global mode
		while (prev[0] == 0 && prev[1] == 0)
			prev = tracebackList.pop();

		if (prev[0] == 0) {
			for (int i = 0; i < prev[1]; i++) {
				result[0] += "-";
				result[1] += Character
						.toString(REVERSE[(char) seq2[i]]);
			}
			prev = tracebackList.pop();
		}
		if (prev[1] == 0) {
			for (int i = 0; i < prev[0]; i++) {
				result[0] += Character
						.toString(REVERSE[(char) seq1[i]]);
				result[1] += "-";
			}
			prev = tracebackList.pop();
		}
		result[0] += Character.toString(REVERSE[(char) seq1[prev[0] - 1]]);
		result[1] += Character.toString(REVERSE[(char) seq2[prev[1] - 1]]);
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
		if (temp[0] < seq1.length && temp[1] < seq2.length) {
			result[0] += Character.toString(REVERSE[(char) seq1[temp[0]]]);
			result[1] += Character.toString(REVERSE[(char) seq2[temp[1]]]);
		}
		if (temp[0] < seq1.length && temp[1] >= seq2.length) {
			result[0] += Character.toString(REVERSE[(char) seq1[temp[0]]]);
			result[1] += "-";
		}
		if (temp[0] >= seq1.length && temp[1] < seq2.length) {
			result[0] += "-";
			result[1] += Character.toString(REVERSE[(char) seq2[temp[1]]]);
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

	public GotohAnswer alignPair() {
		initialize();
		align();
		trace(seq1.length, seq2.length);
		String[] sresult = new String[2];
		sresult = interpretTraceback();
		calculateCheckScore(sresult);
		if (sresult[0].startsWith("-") && sresult[1].startsWith("-")) {
			sresult[0] = sresult[0].substring(1);
			sresult[1] = sresult[1].substring(1);
		}

		GotohAnswer result = new GotohAnswer(seq1ID, seq2ID, sresult[0],
				sresult[1], score[seq1.length][seq2.length], profile);

		// System.out.println(seq1ID + " " + seq2ID + " " + result.getScore());
		return result;
	}

	private void calculateCheckScore(String[] sresult) {
		char[] charSeq1 = sresult[0].toCharArray();
		char[] charSeq2 = sresult[1].toCharArray();
		checkScore = 0;

		for (int i = 0; i < charSeq1.length; i++) {
			if (charSeq1[i] == 45) {
				checkScore += profile.getGopen();
				while (i < charSeq1.length && charSeq1[i] == 45) {
					checkScore += profile.getGextend();
					i++;
				}
			} else if (charSeq2[i] == 45) {
				checkScore += profile.getGopen();
				while (i < charSeq1.length && charSeq1[i] == 45) {
					checkScore += profile.getGextend();
					i++;
				}
			} else {
				checkScore += profile.getMatrixScore(profile.colIndex[Aa
						.getIntRepresentation(String.valueOf(charSeq1[i]))],
						profile.rowIndex[Aa.getIntRepresentation(String
								.valueOf(charSeq2[i]))]);
			}
		}

	}

	private static final double epsilon = 0.0001d;

	private static boolean isInEpsilon(double a, double b) {
		return (a > (b - epsilon)) && (a < (b + epsilon));
	}

	public double getCheckScore() {
		return super.checkScore;
	}
}
