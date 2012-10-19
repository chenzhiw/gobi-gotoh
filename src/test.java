import loader.JLoader;
import loader.PyLoader;

import org.python.core.PyException;

public class test {
	public static void main(String[] args) throws PyException {
		// PythonInterpreter interp = new PythonInterpreter();
		// String d = "'filename'";
		// String s = "open(" + d + ")";
		// interp.exec(s);
//		PyLoader loader = new PyLoader();
//		loader.loadPairFile("matr/sanity.pairs");

		JLoader jloader = new JLoader();
		jloader.loadPairFile("matr/sanity.pairs");
	}
}
