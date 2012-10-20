package gotoh;

public class Aligner {
	private GotohProfile profile;
	private int[] seq1, seq2;
	public double[][] score;
	private double[] p, q;

	public Aligner(GotohProfile profile, int[] seq1, int[] seq2) {
		this.profile = profile;
		this.seq1 = seq1;
		this.seq2 = seq2;
		this.score = new double[seq1.length + 1][seq2.length + 1];
		this.p = new double[seq2.length + 1];
		this.q = new double[seq1.length + 1];
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
			for (int i = 1; i < seq1.length + 1; i++)
				score[i][0] = profile.getGextend() * (i - 1)
						+ profile.getGopen();
			for (int i = 1; i < seq2.length + 1; i++)
				score[0][i] = profile.getGextend() * (i - 1) + profile.getGopen();
		} else {
			for (int i = 1; i < seq1.length + 1; i++)
				score[i][0] = 0;
			for (int i = 1; i < seq2.length + 1; i++)
				score[0][i] = 0;
			p[0] = 0;

		}
	}

	public void align() {

	}
}
