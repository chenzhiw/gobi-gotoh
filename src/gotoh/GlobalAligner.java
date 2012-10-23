package gotoh;

import static resc.Aa.REVERSE;

import java.util.LinkedList;

import loader.Converter;
import loader.JLoader;

public class GlobalAligner implements Aligner {
	private GotohProfile profile;
	private String seq1ID, seq2ID;
	private int[] seq1, seq2;
	public double[][] score;
	private double[][] ins, del;
	private LinkedList<int[]> tracebackList;

	public GlobalAligner(GotohProfile profile, int[] seq1, int[] seq2,
			String seq1ID, String seq2ID) {
		this.profile = profile;
		this.seq1 = seq1;
		this.seq2 = seq2;
		this.seq1ID = seq1ID;
		this.seq2ID = seq2ID;
		this.score = new double[seq1.length + 1][seq2.length + 1];
		this.ins = new double[seq1.length + 1][seq2.length + 1];
		this.del = new double[seq1.length + 1][seq2.length + 1];
		tracebackList = new LinkedList<int[]>();
	}

	public void initialize() {
		score[0][0] = 0;
		for (int i = 1; i < seq1.length + 1; i++) {
			score[i][0] = profile.getGextend() * (i - 1) + profile.getGopen();
			del[i][0] = Double.NEGATIVE_INFINITY;
		}
		for (int i = 1; i < seq2.length + 1; i++) {
			score[0][i] = profile.getGextend() * (i - 1) + profile.getGopen();
			ins[0][i] = Double.NEGATIVE_INFINITY;
		}
		ins[0][0] = del[0][0] = Double.NEGATIVE_INFINITY;

	}

	/**
	 * this function aligns the two sequences globally
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
						+ profile.getMatrixScore(seq1[x - 1], seq2[y - 1]);
				score[x][y] = Math.max(temp, Math.max(ins[x][y], del[x][y]));
			}
		}
	}

	public void trace(int x, int y) {
		if (x == 0) {
			if (y == 0) {
				int[] res = { x , y };
				tracebackList.push(res);
			} else {
				int[] res = { x, y };
				tracebackList.push(res);
				trace(x, y - 1);
			}
		} else if (y == 0) {
			if (x == 0) {

			} else {
				int[] res = { x, y };
				tracebackList.push(res);
				trace(x - 1, y);
			}
		} else {
			int[] res = { x, y };
			tracebackList.push(res);
			if (score[x][y] == ins[x][y]) {
				int k = 1;
				while (score[x - k][y] + profile.getGextend() * k
						+ profile.getGopen() != score[x][y]) {
					res[0] = x - k;
					tracebackList.push(res);
					k++;
				}
				trace(x - k, y);
			} else if (score[x][y] == del[x][y]) {
				int k = 1;
				while (score[x][y - k] + profile.getGextend() * k
						+ profile.getGopen() != score[x][y]) {
					res[1] = y - k;
					tracebackList.push(res);
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
		result[0] = "";
		result[1] = "";
		int[] temp = new int[2];
		int[] prev = new int[2];
		prev = tracebackList.pop();
		// global mode
		while(prev[0] == 0 || prev[1] == 0) {
			if(prev[0]==0)
				result[0] +="-";
			if(prev[1]==0)
				result[1] +="-";
			temp = prev;
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
		if(sresult[0].startsWith("-") && sresult[1].startsWith("-")) {
			sresult[0] = sresult[0].substring(1);
			sresult[1] = sresult[1].substring(1);
		}

		GotohAnswer result = new GotohAnswer(seq1ID, seq2ID, sresult[0],
				sresult[1], score[seq1.length][seq2.length]);

		return result;
	}

}
