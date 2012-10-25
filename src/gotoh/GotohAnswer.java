package gotoh;

import static rescources.Aa.*;

public class GotohAnswer {
	private String seq1ID, seq2ID, seq1, seq2;
	private double score;
	private GotohProfile prof;

	public GotohAnswer() {
	
	}

	public GotohAnswer(String seq1id, String seq2Id, String seq1, String seq2,
			double score, GotohProfile prof) {
		seq1ID = seq1id;
		this.seq2ID = seq2Id;
		this.seq1 = seq1;
		this.seq2 = seq2;
		this.score = score;
		this.prof = prof;
	}

	public String getSeq1ID() {
		return seq1ID;
	}

	public void setSeq1ID(String seq1id) {
		seq1ID = seq1id;
	}

	public String getSeq2Id() {
		return seq2ID;
	}

	public void setSeq2Id(String seq2ID) {
		this.seq2ID = seq2ID;
	}

	public String getSeq1() {
		return seq1;
	}

	public void setSeq1(String seq1) {
		this.seq1 = seq1;
	}

	public String getSeq2() {
		return seq2;
	}

	public void setSeq2(String seq2) {
		this.seq2 = seq2;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void printAlignment() {
		if (prof.getPrintali()) {
			System.out.println(">" + seq1ID + " " + seq2ID + " " + score);
			System.out.println(seq1ID + ": " + seq1);
			System.out.println(seq2ID + ": " + seq2);
		} else {
			System.out.println(seq1ID + " " + seq2ID + " " + score);
		}
	}

	public void printMatriceTxt() {
		for (int i = 0; i < 21; i++)
			System.out.print("\t" + REVERSE[(char) i]);
		for (int j = 0; j < 21; j++) {
			for (int i = -1; i <= j; i++) {
				if (i == -1) {
					System.out.print(REVERSE[(char) i] + "\t");
					continue;
				}
				System.out.print(prof.getMatrix()[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public void printAll() {
		if (prof.getPrintmatrices().equals("")) {
			printAlignment();
		} else if (prof.getPrintmatrices().equals("txt")) {
			printMatriceTxt();
		}
	}
}