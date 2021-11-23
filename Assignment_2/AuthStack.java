import Includes.*;

public class AuthStack{
	// PLEASE USE YOUR ENTRY NUMBER AS THE START STRING
	private static final String start_string = "2020EE10461";
	private StackNode top;

	/*
		Note that the Exceptions have already been defined for you in the includes file,
		you just have to raise them accordingly

	*/

	/* 
	Notice that this function is static, the reason why this is static is that we don't want this to be tied with
	an object of the class. 	
	*/
	public static boolean CheckStack(AuthStack current, String proof) throws AuthenticationFailedException{
		if (current.top == null) 
			return true;
		if (!proof.equals(current.top.dgst)) 
			throw new AuthenticationFailedException();
		StackNode curr = current.top;
		CRF hash = new CRF(64);
		while (curr != null) {
			String dgst = "";
			if (curr.previous == null) 
				dgst = hash.Fn(current.start_string + "#" + curr.data.value);
			else 
				dgst = hash.Fn(curr.previous.dgst + "#" + curr.data.value);
			if (!dgst.equals(curr.dgst)) 
				throw new AuthenticationFailedException();
			curr = curr.previous;
		}
		return true;
	}

	public String push(Data datainsert, String proof)throws AuthenticationFailedException{
		CheckStack(this, proof);
		CRF hash = new CRF(64);
		StackNode temp = new StackNode();
		temp.data = datainsert;
		if (top == null) 
			temp.dgst = hash.Fn(start_string + "#" + datainsert.value);
		else 
			temp.dgst = hash.Fn(top.dgst + "#" + datainsert.value);
		temp.previous = top;
		top = temp;
		return top.dgst;
	}

	public String pop(String proof)throws AuthenticationFailedException, EmptyStackException{
		CheckStack(this, proof);
		CRF hash = new CRF(64);
		if (top == null) 
			throw new EmptyStackException();
		StackNode temp = top.previous;
		top.previous = null;
		top = temp;
		return top.dgst;
	}

	public StackNode GetTop(String proof)throws AuthenticationFailedException{
		CheckStack(this, proof);
		return top;
	}
}
