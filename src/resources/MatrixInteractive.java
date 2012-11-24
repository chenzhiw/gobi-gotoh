package resources;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import joptsimple.internal.Strings;

public class MatrixInteractive {
	private double[][] matrix = new double[20][20];
	private int[] rowIndex;
	private int[] colIndex;
	private String matrixName;

	public MatrixInteractive(String matName) throws IOException {
		this.matrixName = matName;
		ArrayList<String> matFile = new ArrayList<String>();
		FileInputStream fstream = new FileInputStream(matName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			matFile.add(strLine);
		}
		in.close();

		int currLine = 0;

		for (String line : matFile) {
			if (line.startsWith("ROWINDEX")) {
				rowIndex = readIndex(line.split("\\s+")[1]);
			} else if (line.startsWith("COLINDEX")) {
				colIndex = readIndex(line.split("\\s+")[1]);
			} else if (line.startsWith("MATRIX")) {
				String[] matrixLine = line.split("\\s+");
				for (int i = 1; i < matrixLine.length; i++) {
					matrix[currLine][i - 1] = Double.parseDouble(matrixLine[i]);
				}
				currLine++;
			}
		}
	}

	public static int[] readIndex(String indexLine) {
		int[] findIndex = new int[20];
		for (int i = 0; i < indexLine.length(); i++) {
			findIndex[i] = Aa.getIntRepresentation(String
					.valueOf(indexLine.charAt(i)));
		}

		int[] resultIndex = new int[20];
		for (int i = 0; i < indexLine.length(); i++) {
			resultIndex[findIndex[i]] = i;
		}
		return resultIndex;
	}

	public double[][] getMatrix() {
		return matrix;
	}

	public int[] getRowIndex() {
		return rowIndex;
	}

	public int[] getColIndex() {
		return colIndex;
	}

	public String getMatrixName() {
		return matrixName;
	}

}
