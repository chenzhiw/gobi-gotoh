package gotoh;

import static resources.Aa.REVERSE;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;

import resources.Aa;

public class FreeshiftAligner extends Aligner {

	public FreeshiftAligner(GotohProfile profile, int[] seq1, int[] seq2,
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
			del[i][0] = Double.NEGATIVE_INFINITY;
		}
		for (int i = 1; i < seq2.length + 1; i++) {
			score[0][i] = 0;
			ins[0][i] = Double.NEGATIVE_INFINITY;
		}
		ins[0][0] = del[0][0] = Double.NEGATIVE_INFINITY;

	}

	/**
	 * super function aligns the two sequences
	 */
	public void align() {
		for (int x = 1; x <= seq1.length; x++) {
			for (int y = 1; y <= seq2.length; y++) {
				double w1 = profile.getGextend() + profile.getGopen();
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

	private static final double epsilon = 0.0001d;

	private static boolean isInEpsilon(double a, double b) {
		return (a > (b - epsilon)) && (a < (b + epsilon));
	}

	public void trace(int x, int y) {
		if (y <= 0 || x <= 0) {

		} else {
			int[] res = { x, y };
			checkScore += profile.getMatrixScore(seq1[x], seq2[y]);
			tracebackList.push(res);
			if (score[x][y] == ins[x][y]) {
				int k = 1;
				while (score[x - k][y] + profile.getGextend() * k
						+ profile.getGopen() != score[x][y]) {
					int[] resn = { x - k, y };
					checkScore += profile.getMatrixScore(seq1[x - k], seq2[y])
							+ profile.getGextend() * k + profile.getGopen();
					tracebackList.push(resn);
					if (x - k == 0)
						break;
					k++;
				}
				trace(x - k, y);
			} else if (score[x][y] == del[x][y]) {
				int k = 1;
				while (score[x][y - k] + profile.getGextend() * k
						+ profile.getGopen() != score[x][y]) {
					int[] resn = { x, y - k };
					checkScore += profile.getMatrixScore(seq1[x], seq2[y - k])
							+ profile.getGextend() * k + profile.getGopen();
					tracebackList.push(resn);
					if (y - k == 0)
						break;
					k++;
				}
				trace(x, y - k);
			} else {
				trace(x - 1, y - 1);
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
		if (prev[0] > prev[1]) {
			for (int i = 1; i < prev[0]; i++) {
				result[0] += Character.toString(REVERSE[(char) seq1[i - 1]]);
				result[1] += "-";
			}
		} else {
			for (int i = 1; i < prev[1]; i++) {
				result[1] += Character.toString(REVERSE[(char) seq2[i - 1]]);
				result[0] += "-";
			}
		}

		// dont miss the first pair!
		if (prev[0] != 0 && prev[1] != 0) {
			result[0] += Character.toString(REVERSE[(char) seq1[prev[0] - 1]]);
			result[1] += Character.toString(REVERSE[(char) seq2[prev[1] - 1]]);
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

		if (prev[0] < prev[1]) {
			for (int i = prev[0]; i <= seq1.length; i++) {
				result[0] += Character.toString(REVERSE[(char) seq1[i - 1]]);
				result[1] += "-";
			}
		} else {
			for (int i = prev[1]; i <= seq2.length; i++) {
				result[1] += Character.toString(REVERSE[(char) seq2[i - 1]]);
				result[0] += "-";
			}
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
		double colScore, rowScore; // for debugging purposes only
		for (int i = 0; i < seq1.length + 1; i++) { // check last row for max
			rowScore = score[i][seq2.length];
			if (score[i][seq2.length] > max[2] || isInEpsilon(rowScore, max[2])) {
				max[0] = i;
				max[1] = seq2.length;
				max[2] = score[i][seq2.length];
			}
		}

		for (int i = 0; i < seq2.length + 1; i++) { // check last column for max
			colScore = score[seq1.length][i];
			if (score[seq1.length][i] > max[2] || isInEpsilon(colScore, max[2])) {
				max[0] = seq1.length;
				max[1] = i;
				max[2] = score[seq1.length][i];
			}
		}
		if (max[0] == 0)
			max[0]++;
		if (max[1] == 0)
			max[1]++;
		trace((int) max[0] - 1, (int) max[1] - 1);
		String[] sresult = new String[2];
		sresult = interpretTraceback();
		calculateCheckScore(sresult);

		GotohAnswer result = new GotohAnswer(seq1ID, seq2ID, sresult[0],
				sresult[1], max[2], profile);
		// System.out.println(seq1ID + " " + seq2ID + " " + max[2]);
		return result;
	}

	private void calculateCheckScore(String[] sresult) {
		char[] charSeq1 = sresult[0].toCharArray();
		char[] charSeq2 = sresult[1].toCharArray();
		checkScore = 0;

		int start = 0, end = 0;

		for (int i = 0; i < charSeq1.length; i++) {
			while (charSeq1[i] == 45 || charSeq2[i] == 45) {
				i++;
			}
			start = i;
			break;
		}

		for (int i = charSeq1.length - 1; i > 0; i--) {
			while (charSeq1[i] == 45 || charSeq2[i] == 45) {
				i--;
			}
			end = i + 1;
			break;
		}

		for (int i = start; i < end; i++) {
			if (charSeq1[i] == 45) {
				checkScore += profile.getGopen();
				while (i < end && charSeq1[i] == 45) {
					checkScore += profile.getGextend();
					i++;
				}
			} else if (charSeq2[i] == 45) {
				checkScore += profile.getGopen();
				while (i < end && charSeq2[i] == 45) {
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

	public double getCheckScore() {
		return super.checkScore;
	}
}
