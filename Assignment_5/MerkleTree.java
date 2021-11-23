import Includes.*;
import java.util.*;

public class MerkleTree{
	// Check the TreeNode.java file for more details
	public TreeNode rootnode;
	public int numstudents;

	public String Build(List<Pair<String,Integer>> documents){
		// Implement Code here
		numstudents = documents.size();
		CRF sha = new CRF(64);
		ArrayList<ArrayList<TreeNode>> N = new ArrayList<ArrayList<TreeNode>>();
		
		int d = 0;
		while ((1<<d)<numstudents) d++;
		
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
				if (i==d) {
					node.isLeaf = true;
					node.numberLeaves = 1;
					node.val = documents.get(j-1).First+"_"+documents.get(j-1).Second.toString();
					node.maxleafval = documents.get(j-1).Second;
				}
				else {
					node.isLeaf = false;
					node.numberLeaves = node.left.numberLeaves + node.right.numberLeaves;
					node.val = sha.Fn(node.left.val + "#" + node.right.val);
					node.maxleafval = Math.max(node.left.maxleafval, node.right.maxleafval);
				}
			}
		}
		
		return rootnode.val;
	}

	/*
		Pair is a generic data structure defined in the Pair.java file
		It has two attributes - First and Second
	*/

	public String UpdateDocument(int student_id, int newScore){
		// Implement Code here
		
		int d=-1;
		for (TreeNode t = rootnode; t!=null; d++) 
			t = t.left;
		
		TreeNode curr = rootnode;
		while (d>0) {
			if ((student_id & (1<<(d-1))) >0) {
				curr = curr.right;
			} else {
				curr = curr.left;
			}
			d--;
		}
		
		Integer newVal = new Integer(newScore);
		curr.val = curr.val.split("_")[0]+"_"+newVal.toString();
		curr.maxleafval = newScore;
		
		CRF sha = new CRF(64);
		curr = curr.parent;
		while (curr != null) {
			curr.val = sha.Fn(curr.left.val + "#" + curr.right.val);
			curr.maxleafval = Math.max(curr.left.maxleafval, curr.right.maxleafval);
			curr = curr.parent;
		}
		return rootnode.val;
	}
}
