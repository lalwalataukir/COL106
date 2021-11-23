import Includes.*;
import java.util.*;

public class BlockChain{
	public static final String start_string = "LabModule5";
	public Block firstblock;
	public Block lastblock;

	public String InsertBlock(List<Pair<String,Integer>> Documents, int inputyear){
		/*
			Implement Code here
		*/
		CRF hash = new CRF(64);
		Block temp = new Block();
		temp.mtree = new MerkleTree();
		temp.mtree.Build(Documents);
		temp.year = inputyear;
		temp.value = temp.mtree.rootnode.val+"_"+temp.mtree.rootnode.maxleafval;
		if (firstblock == null) {
			firstblock = lastblock = temp;
			temp.dgst = hash.Fn(start_string + "#" + temp.value);
		} else {
			temp.dgst = hash.Fn(lastblock.dgst + "#" + temp.value);
			temp.previous = lastblock;
			lastblock.next = temp;
			lastblock = temp;
		}
		return temp.dgst;
	}

	public Pair<List<Pair<String,String>>, List<Pair<String,String>>> ProofofScore(int student_id, int year){
		// Implement Code here
		Pair<List<Pair<String, String>>, List<Pair<String, String>>> scp = new Pair<List<Pair<String, String>>, List<Pair<String, String>>>();
		scp.First = new ArrayList<Pair<String,String>>();	
		scp.Second = new ArrayList<Pair<String,String>>();	
		
		Block temp = lastblock;
		scp.Second.add(new Pair<String,String>(temp.value, temp.dgst));
		while (temp.year!=year) {
			temp = temp.previous;
			scp.Second.add(new Pair<String,String>(temp.value, temp.dgst));
		}

		int d=-1;
		for (TreeNode t = temp.mtree.rootnode; t!=null; d++) t = t.left;
		
		scp.First.add(new Pair<String, String>(temp.mtree.rootnode.val, null));
		
		TreeNode curr = temp.mtree.rootnode;
		while (d>0) {
			scp.First.add(new Pair<String,String>(curr.left.val, curr.right.val));
			if ((student_id & (1<<(d-1))) >0) {
				curr = curr.right;
			} else {
				curr = curr.left;
			}
			d--;
		}
		
		Collections.reverse(scp.First);
		Collections.reverse(scp.Second);
		return scp;
	}
}
