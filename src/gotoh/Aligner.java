package gotoh;

import java.util.ArrayList;
import java.util.List;

public class Aligner {
	private GotohProfile profile;
	private int[] seq1, seq2;
	public double[][] score;
	private double[][] ins, del;
	private List<int[]> tracebackList;

	public Aligner(GotohProfile profile, int[] seq1, int[] seq2) {
		this.profile = profile;
		this.seq1 = seq1;
		this.seq2 = seq2;
		this.score = new double[seq1.length + 1][seq2.length + 1];
		this.ins = new double[seq1.length + 1][seq2.length + 1];
		this.del = new double[seq1.length + 1][seq2.length + 1];
		tracebackList = new ArrayList<int[]>();
	}

	/**
	 * depending on the mode the initialization of the score matrix is
	 * performed. For a local or free shift alignment the first row and column
	 * are all set to 0; for a global alignment one needs to apply the gap
	 * function.
	 */
	public void initialize() {
		score[0][0] = 0;
		if (profile.getMode().equals("global")) {
			for (int i = 1; i < seq1.length + 1; i++) {
				score[i][0] = profile.getGextend() * (i - 1)
						+ profile.getGopen();
				del[i][0] = Double.NEGATIVE_INFINITY;
			}
			for (int i = 1; i < seq2.length + 1; i++) {
				score[0][i] = profile.getGextend() * (i - 1)
						+ profile.getGopen();
				ins[0][i] = Double.NEGATIVE_INFINITY;
			}
			ins[0][0] = del[0][0] = Double.NEGATIVE_INFINITY;
		} else {
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
	}

	/**
	 * this function aligns the two sequences globally
	 */
	public void globalAlign() {
		for (int x = 1; x <= seq1.length; x++) {
			for (int y = 1; y <= seq2.length; y++) {
				double w1 = profile.getGextend() + profile.getGopen();
				ins[x][y] = Math.max(score[x - 1][y] + w1, ins[x - 1][y]
						+ profile.getGextend());
				del[x][y] = Math.max(score[x][y - 1] + w1, del[x][y - 1]
						+ profile.getGextend());
				double temp = score[x - 1][y - 1]
						+ profile.getMatrixScore(seq1[x - 1], seq2[y - 1]);
				score[x][y] = Math.max(temp, Math.max(ins[x][y], del[x][y]));
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public double[] nonGlobalAlign() {
		double[] max = { 0, 0, 0 };
		for (int x = 1; x <= seq1.length; x++) {
			for (int y = 1; y <= seq2.length; y++) {
				double w1 = profile.getGextend() + profile.getGopen();
				ins[x][y] = Math.max(score[x - 1][y] + w1, ins[x - 1][y]
						+ profile.getGextend());
				del[x][y] = Math.max(score[x][y - 1] + w1, del[x][y - 1]
						+ profile.getGextend());
				double temp = score[x - 1][y - 1]
						+ profile.getMatrixScore(seq1[x - 1], seq2[y - 1]);
				score[x][y] = Math.max(Math.max(temp, 0),
						Math.max(ins[x][y], del[x][y]));
				// save the max; we need that for traceback (?)
				if (score[x][y] >= max[2]) {
					max[0] = x;
					max[1] = y;
					max[2] = score[x][y];
				}
			}
		}
		return max;
	}

	public void trace(int x, int y) {
		if (x == 0 && y == 0) {

		} else {
			int[] res = { x, y };
			tracebackList.add(res);
			if (score[x][y] == ins[x][y]) {
				trace(x - 1, y);
			} else if (score[x][y] == del[x][y]) {
				trace(x, y - 1);
			} else {
				trace(x - 1, y - 1);
			}
		}
		System.out.println(x + " " + y);
	}

	@Deprecated
	public void printAlignment() {
		for (int y = 0; y <= seq2.length; y++) {
			for (int x = 0; x <= seq1.length; x++) {
				System.out.print(score[x][y] + "\t");
			}
			System.out.println();
		}
	}
}
