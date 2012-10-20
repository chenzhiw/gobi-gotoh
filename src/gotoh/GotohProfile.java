package gotoh;
import resc.Matrix;


public class GotohProfile {
	private String matrix, pairs, seqlib;
	private double gopen, gextend;
	private String mode, printmatrices;
	private boolean check, printali;
	
	// defaults!!
	public GotohProfile() {
		matrix = "dayhoff";
		gopen = -12;
		gextend = -1;
		mode = "freeshift";
		printali = false;
		printmatrices = "txt";
		check = false;
	}

	public String getMatrix() {
		return matrix;
	}

	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}

	public String getPairs() {
		return pairs;
	}

	public void setPairs(String pairs) {
		this.pairs = pairs;
	}

	public String getSeqlib() {
		return seqlib;
	}

	public void setSeqlib(String seqlib) {
		this.seqlib = seqlib;
	}

	public double getGopen() {
		return gopen;
	}

	public void setGopen(double gopen) {
		this.gopen = gopen;
	}

	public double getGextend() {
		return gextend;
	}

	public void setGextend(double gextend) {
		this.gextend = gextend;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getPrintmatrices() {
		return printmatrices;
	}

	public void setPrintmatrices(String printmatrices) {
		this.printmatrices = printmatrices;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean isPrintali() {
		return printali;
	}

	public void setPrintali(boolean printali) {
		this.printali = printali;
	}
	
	
}
