package gotoh;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import resources.Aa;
import resources.Matrix;
import resources.MatrixInteractive;

public class GotohProfile {
	private String matrixName, pairfile, seqlibfile;
	private double gopen, gextend;
	public int[] rowIndex, colIndex;
	private String mode, printmatrices;
	private boolean check, printali;
	public double[][] matrix;

	// defaults!!
	public GotohProfile() {
		matrixName = "dayhoff";
		gopen = -12;
		gextend = -1;
		mode = "freeshift";
		printali = false;
		printmatrices = "";
		check = false;
		rowIndex = MatrixInteractive.readIndex("ARNDCQEGHILKMFPSTWYV");
		colIndex = MatrixInteractive.readIndex("ARNDCQEGHILKMFPSTWYV");
		matrix = Matrix.dayhoff();
	}

	public String getMatrixName() {
		return matrixName;
	}

	public void setMatrix(String matrixName) {
		this.matrixName = matrixName;
		try {
			MatrixInteractive maInt = new MatrixInteractive(matrixName);
			this.matrix = maInt.getMatrix();
			this.rowIndex = maInt.getRowIndex();
			this.colIndex = maInt.getColIndex();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPairs() {
		return pairfile;
	}

	public void setPairs(String pairs) {
		this.pairfile = pairs;
	}

	public String getSeqlib() {
		return seqlibfile;
	}

	public void setSeqlib(String seqlib) {
		this.seqlibfile = seqlib;
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

	public boolean getPrintali() {
		return this.printali;
	}

	public double getMatrixScore(int x, int y) {
		if (matrix[x][y] == 0)
			return matrix[y][x];
		return matrix[x][y];
	}

	public double[][] getMatrix() {
		return matrix;
	}
}
