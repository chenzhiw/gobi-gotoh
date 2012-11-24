package resources;

import static resources.Aa.REVERSE;

public class Matrix {

	public static void printMatriceTxt(double[][] matrix) {
		for (int i = 0; i < 20; i++)
			System.out.print("\t" + REVERSE[(char) i]);
		System.out.println();
		for (int x = 0; x < 20; x++) {
			for (int y = -1; y <= x; y++) {
				if (y == -1) {
					System.out.print(REVERSE[(char) x] + "\t");
					y++;
				}
				System.out.print(matrix[x][y] + "\t");
			}
			System.out.println();
		}
	}

	public static String printMatriceHtml(double[][] matrix) {
		StringBuilder b = new StringBuilder();
		b.append("<!DOCTYPE html PUBLIC \"...\">");
		b.append("<html>");
		b.append("\t<head>");
		b.append("&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "</head>");
		b.append("&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "<body>"
				+ "<code>");
		b.append("<table> <tr> <td> / </td>");
		for (int i = 0; i < 20; i++)
			b.append("<td>" + REVERSE[(char) i] + "</td>");
		b.append("</tr><br>");
		for (int x = 0; x < 20; x++) {
			b.append("<tr>");
			for (int y = -1; y <= x; y++) {
				if (y == -1) {
					b.append("<td>" + REVERSE[(char) x] + "</td>");
					y++;
				}
				b.append("<td>" + matrix[x][y] + "</td>");
			}
		}
		b.append("</code><br>");
		return b.toString();
	}

	public static final double[][] dayhoff() {
		String[] m = {
				"NAME dayhoff",
				"SCORE ",
				"NUMROW 20",
				"NUMCOL 20",
				"ROWINDEX ARNDCQEGHILKMFPSTWYV",
				"COLINDEX ARNDCQEGHILKMFPSTWYV",
				"MATRIX	1.80",
				"MATRIX	-1.60	6.10",
				"MATRIX	0.20	-0.00	2.00",
				"MATRIX	0.30	-1.30	2.10	3.90",
				"MATRIX	-2.00	-3.60	-3.60	-5.10	12.00",
				"MATRIX	-0.40	1.20	0.80	1.60	-5.30	4.10",
				"MATRIX	0.30	-1.10	1.40	3.40	-5.30	2.50	3.90",
				"MATRIX	1.30	-2.60	0.40	0.60	-3.30	-1.20	0.20	4.80",
				"MATRIX	-1.40	1.50	1.60	0.70	-3.40	2.90	0.60	-2.10	6.60",
				"MATRIX	-0.50	-2.00	-1.80	-2.40	-2.30	-2.00	-2.00	-2.60	-2.50	4.60",
				"MATRIX	-1.90	-3.00	-2.90	-4.00	-6.00	-1.80	-3.30	-4.00	-2.10	2.40	6.00",
				"MATRIX	-1.20	3.40	1.00	0.10	-5.40	0.70	-0.10	-1.70	-0.10	-1.90	-2.90	4.70",
				"MATRIX	-1.20	-0.50	-1.80	-2.60	-5.20	-1.00	-2.20	-2.80	-2.20	2.20	3.70	0.40	6.60",
				"MATRIX	-3.50	-4.50	-3.50	-5.60	-4.30	-4.70	-5.40	-4.80	-1.80	1.00	1.80	-5.30	0.20	9.10",
				"MATRIX	1.10	-0.20	-0.50	-1.00	-2.70	0.20	-0.60	-0.50	-0.30	-2.00	-2.60	-1.20	-2.10	-4.60	5.90",
				"MATRIX	1.10	-0.30	0.70	0.30	-0.00	-0.50	-0.00	1.10	-0.80	-1.40	-2.80	-0.20	-1.60	-3.20	0.90	1.60",
				"MATRIX	1.20	-0.90	0.40	-0.10	-2.20	-0.80	-0.40	-0.00	-1.30	0.10	-1.70	-0.00	-0.60	-3.10	0.30	1.30	2.60",
				"MATRIX	-5.60	2.30	-3.90	-6.60	-7.50	-4.60	-6.80	-6.80	-2.50	-5.00	-1.70	-3.30	-4.10	0.50	-5.50	-2.30	-5.00	17.30",
				"MATRIX	-3.50	-4.20	-2.10	-4.30	0.40	-4.00	-4.30	-5.30	-0.10	-1.00	-0.90	-4.50	-2.50	7.00	-5.00	-2.80	-2.80	0.00	10.20",
				"MATRIX	0.20	-2.50	-1.80	-2.20	-1.90	-1.90	-1.80	-1.40	-2.30	3.70	1.80	-2.50	1.80	-1.20	-1.20	-1.00	0.30	-6.10	-2.50	4.30" };
		int currLine = 0;
		double[][] matrix = new double[20][20];
		for (String line : m) {
			if (line.startsWith("MATRIX")) {
				String[] matrixLine = line.split("\\s+");
				for (int i = 1; i < matrixLine.length; i++) {
					matrix[currLine][i - 1] = Double.parseDouble(matrixLine[i]);
				}
				currLine++;
			}
		}
		return matrix;
	}
}
