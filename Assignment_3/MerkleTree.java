import Includes.*;
import java.util.*;

public class MerkleTree{
	
	// Check the TreeNode.java file for more details
	public TreeNode rootnode;
	public int numdocs;

	public String Build(String[] documents){
		// Implement Code here
		CRF sha = new CRF(64);
		numdocs = documents.length;
		ArrayList<ArrayList<TreeNode>> N = new ArrayList<ArrayList<TreeNode>>();
		
		int d = 0;
		while ((1<<d)<numdocs) d++;
		
		for (int i=0; i<=d; i++) {
			N.add(new ArrayList<TreeNode>());
			for (int j=0; j<=(1<<i); j++)
				N.get(i).add(new TreeNode());
		}
		
		rootnode = N.get(0).get(1);
		for (int i=0; i<d; i++) {
			for (int j=1; j<=(1<<i); j++) {
				TreeNode p = N.get(i).get(j);
				TreeNode l = N.get(i+1).get(2*j-1);
				TreeNode r = N.get(i+1).get(2*j);
				p.left = l;
				p.right = r;
				l.parent = p;
				r.parent = p;
			}
		}
		
		for (int i=d; i>=0; i--) {
			for (int j=1; j<=(1<<i); j++) {
				TreeNode node = N.get(i).get(j);
				if (i==d)
					node.val = documents[j-1];
				else
					node.val = sha.Fn(node.left.val + "#" + node.right.val);
			}
		}
		
		return rootnode.val;
	}

	/* 
		Pair is a generic data strcuture defined in the Pair.java file
		It has two attributes - First and Second
	*/
		
	public List<Pair<String,String>> QueryDocument(int doc_idx){
		// Implement Code here
		ArrayList<Pair<String,String>> scp = new ArrayList<Pair<String,String>>();
		
		int d=-1;
		for (TreeNode t = rootnode; t!=null; d++) t = t.left;
		
		scp.add(new Pair<String,String>(rootnode.val,null));
		
		TreeNode curr = rootnode;
		int j=1;
		for (int i=0; i<d; i++) {
			scp.add(new Pair<String,String>(curr.left.val, curr.right.val));
			if (doc_idx > j*(1<<(d-i-1))) {
				curr = curr.right;
				j=2*j;
			} else {
				curr = curr.left;
				j=2*j-1;
			}
		}
		
		Collections.reverse(scp);
		return scp;
	}

	public static boolean Authenticate(List<Pair<String,String>> path, String summary){
		// Implement Code here
		CRF sha = new CRF(64);
		for (int i=0; i<path.size()-1; i++) {
			Pair<String,String> a = path.get(i);
			Pair<String,String> p = path.get(i+1);
			String hash = sha.Fn(a.First + "#" + a.Second);
			if (!hash.equals(p.First))
				if (!hash.equals(p.Second))
					return false;
		}
		return path.get(path.size()-1).First.equals(summary);
	}

	public String UpdateDocument(int doc_idx, String new_document){
		// Implement Code here
		int d=-1;
		for (TreeNode t = rootnode; t!=null; d++) t = t.left;
		
		TreeNode curr = rootnode;
		int j=1;
		for (int i=0; i<d; i++) {
			if (doc_idx > j*(1<<(d-i-1))) {
				curr = curr.right;
				j=2*j;
			} else {
				curr = curr.left;
				j=2*j-1;
			}
		}
		curr.val = new_document;
		
		CRF sha = new CRF(64);
		curr = curr.parent;
		while (curr != null) {
			curr.val = sha.Fn(curr.left.val + "#" + curr.right.val);
			curr = curr.parent;
		}
		return rootnode.val;
	}
}
