package gotoh;

public class GotohAnswer {
	private String seq1ID, seq2Id, seq1, seq2;
	private double score;
	
	
	public GotohAnswer() {
		
	}
	
	public GotohAnswer(String seq1id, String seq2Id, String seq1, String seq2,
			double score) {
		seq1ID = seq1id;
		this.seq2Id = seq2Id;
		this.seq1 = seq1;
		this.seq2 = seq2;
		this.score = score;
	}


	public String getSeq1ID() {
		return seq1ID;
	}


	public void setSeq1ID(String seq1id) {
		seq1ID = seq1id;
	}


	public String getSeq2Id() {
		return seq2Id;
	}


	public void setSeq2Id(String seq2Id) {
		this.seq2Id = seq2Id;
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
	
	
}
