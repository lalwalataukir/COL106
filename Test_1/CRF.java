import java.util.*;

import HelperClasses.Pair;
import HelperClasses.sha256;

public class CRF extends sha256 {

    // Stores the output size of the function Fn()
    public int outputsize;

    CRF(int size) {
        outputsize = size;
        assert outputsize <= 64;
    }

    // Outputs the mapped outputSize characters long string s' for an input string s
    public String Fn(String s) {
        String shasum = encrypt(s);
        return shasum.substring(0,outputsize);
    }

    /* Lab test Fn_2() */
    public String Fn_2(String s) {
        String shasum = s.substring(0, 64-outputsize) + Fn(s.substring(64-outputsize, s.length()));
        return shasum;
    }

    /*==========================
    |- To be done by students -|
    ==========================*/
    

    /* Lab test to-do */
    /* Implement your code here */
    public Pair<String, String> FindColl_Fn_2(String prefix) {
		Vector <String> y = new Vector<String>();
		String a = "00000";
		y.add(a);
		while (true) {
			String b = y.lastElement();
			String sha = Fn(b);
			int pos = y.indexOf(sha);
			if (pos>0) {
				return new Pair<>(prefix+b, prefix+y.elementAt(pos-1));
			}
			y.add(sha);
		}
    }
}
