package resources;

public class Aa {
	public static final int A = 0, R = 1, N = 2, D = 3, C = 4, Q = 5, E = 6,
			G = 7, H = 8, I = 9, L = 10, K = 11, M = 12, F = 13, P = 14,
			S = 15, T = 16, W = 17, Y = 18, V = 19;

	public static final char[] REVERSE = { (char) 65, (char) 82, (char) 78,
			(char) 68, (char) 67, (char) 81, (char) 69, (char) 71, (char) 72,
			(char) 73, (char) 76, (char) 75, (char) 77, (char) 70, (char) 80,
			(char) 83, (char) 84, (char) 87, (char) 89, (char) 86 };
	
	/**
	 * kinda bad function to make string representation of aminoacid to int
	 * 
	 * @param s
	 *            string representation of aminoacid
	 * @return -1 if input is somehow wrong, the int code of the aminoacid in
	 *         the normal scenario
	 */
	public static int getIntRepresentation(String s) {
		if (s.equals("A")) {
			return A;
		} else if (s.equals("C")) {
			return C;
		} else if (s.equals("D")) {
			return D;
		} else if (s.equals("E")) {
			return E;
		} else if (s.equals("F")) {
			return F;
		} else if (s.equals("G")) {
			return G;
		} else if (s.equals("H")) {
			return H;
		} else if (s.equals("I")) {
			return I;
		} else if (s.equals("K")) {
			return K;
		} else if (s.equals("L")) {
			return L;
		} else if (s.equals("M")) {
			return M;
		} else if (s.equals("N")) {
			return N;
		} else if (s.equals("P")) {
			return P;
		} else if (s.equals("Q")) {
			return Q;
		} else if (s.equals("R")) {
			return R;
		} else if (s.equals("S")) {
			return S;
		} else if (s.equals("T")) {
			return T;
		} else if (s.equals("V")) {
			return V;
		} else if (s.equals("W")) {
			return W;
		} else if (s.equals("Y")) {
			return Y;
		} else if (s.equals("ALA")) {
			return A;
		} else if (s.equals("CYS")) {
			return C;
		} else if (s.equals("ASP")) {
			return D;
		} else if (s.equals("GLU")) {
			return E;
		} else if (s.equals("PHE")) {
			return F;
		} else if (s.equals("GLY")) {
			return G;
		} else if (s.equals("HIS")) {
			return H;
		} else if (s.equals("ILE")) {
			return I;
		} else if (s.equals("LYS")) {
			return K;
		} else if (s.equals("LEU")) {
			return L;
		} else if (s.equals("MET")) {
			return M;
		} else if (s.equals("ASN")) {
			return N;
		} else if (s.equals("PRO")) {
			return P;
		} else if (s.equals("GLN")) {
			return Q;
		} else if (s.equals("ARG")) {
			return R;
		} else if (s.equals("SER")) {
			return S;
		} else if (s.equals("THR")) {
			return T;
		} else if (s.equals("VAL")) {
			return V;
		} else if (s.equals("TRP")) {
			return W;
		} else if (s.equals("TYR")) {
			return Y;
		} else if (Character.isUpperCase(s.codePointAt(0))) {
			return C;
		} else
			return -1;
	}
	
	public static int[] defaultOrder = { 0, 4, 3, 6, 13, 7, 8, 9, 11, 10, 12,
		2, 14, 5, 1, 15, 16, 19, 17, 18 };
}
