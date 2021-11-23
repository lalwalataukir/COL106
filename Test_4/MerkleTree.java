import Includes.*;
import java.util.*;

public class MerkleTree{

	// Check the TreeNode.java file for more details
	public TreeNode rootnode;

    /*==========================
    |- To be done by students -|
    ==========================*/
    /* Lab test to-do */

	public TreeNode LowestCommonAncestor(TreeNode node1, TreeNode node2){
		/* Implement your code here */
		/* Remember to:
			- Turn on your camera
			- turn on your microphone
			- share your screen
			- begin recording the meeting
			- (just before submitting) rename your file as per the convention specified */
		Vector<TreeNode> v1 = new Vector<TreeNode>();
		Vector<TreeNode> v2 = new Vector<TreeNode>();
		TreeNode a = new TreeNode(null, 0);
		TreeNode b = new TreeNode(null, 1);
		v1.add(a);
		v2.add(b);
		
		while (node1!=null) {
			v1.add(node1);
			node1 = node1.parent;
		}
		while (node2!=null) {
			v2.add(node2);
			node2 = node2.parent;
		}
		
		for (int i=1; i<Math.min(v1.size(), v2.size()); i++) { 
			if (v1.get(v1.size()-i-1) != v2.get(v2.size()-i-1)) {
				return v1.get(v1.size()-i);
			}
		}

		return null;
		/*
		Slower N^2 method
		for (int i=0; i<v1.size(); i++)
			for (int j=0; j<v2.size(); j++)
				if (v1.get(i) == v2.get(j))
					return v1.get(i);
		*/
	}
}
