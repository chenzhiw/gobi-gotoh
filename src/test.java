
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import resc.Matrix;

public class test {
	public static void main(String []args) throws PyException {
	    PythonInterpreter interp = new PythonInterpreter();
	    interp.exec("f = open('matr/sanity.pairs')");
	    interp.exec("for i, l in enumerate(f):" + "\n\t" + "pass");
	    interp.exec("print(i+1)");
		
	  }
}
