import Includes.*;
import java.util.*;
import java.lang.Math;

public class MerkleTree{
	//check TreeNode.java for more details
	public TreeNode rootnode;
	public int numdocs;
	
	public String InsertDocument(String document){
		//Implement your code here
		CRF sha = new CRF(64);
		
		TreeNode temp = new TreeNode();
		temp.val = document;
		temp.isLeaf = true;
		temp.numberLeaves = 1;
		temp.maxleafval = temp.val;
		temp.minleafval = temp.val;
		temp.balanceFactor = 0;
		temp.height = 0;
		
		if (rootnode == null) {
			rootnode = temp;
		} else if (rootnode.left == null && rootnode.right == null) {
			TreeNode a = rootnode;
			TreeNode p = new TreeNode();

			rootnode = p;
			if (a.val.compareTo(temp.val)>0) {
				p.right = a;
				p.left = temp;
			} else {
				p.left = a;
				p.right = temp;
			}
			p.left.parent = p;
			p.right.parent = p;
			p.Update();
		} else {
			TreeNode curr = rootnode;
			while (curr.isLeaf == false) {
				if (document.compareTo(curr.minleafval)<0)
					curr = curr.left;
				else if (document.compareTo(curr.maxleafval)>0)
					curr = curr.right;
				else if (document.compareTo(curr.right.minleafval)<0)
					curr = curr.left;
				else 
					curr = curr.right;
			}
			
			TreeNode p = new TreeNode();
			p.parent = curr.parent;
			if (curr.parent.left == curr)
				curr.parent.left = p;
			else
				curr.parent.right = p;
			if (curr.val.compareTo(temp.val)>0){
				p.right = curr;
				p.left = temp;
			} else {
				p.left = curr;
				p.right = temp;
			}
			p.left.parent = p;
			p.right.parent = p;
			p.isLeaf = false;
			p.numberLeaves = 2;
			p.maxleafval = p.right.val;
			p.minleafval = p.left.val;
			
			curr = p;
			while (curr != null) {
				curr.height = 1 + Math.max(curr.left.height, curr.right.height);
				curr.balanceFactor = curr.left.height - curr.right.height;
				curr.val = sha.Fn(curr.left.val+"#"+curr.right.val);
				if (curr.balanceFactor == 2) {
					if (curr.left.balanceFactor == 1)
						curr = curr.SingleRightRotation();
					else
						curr = curr.DoubleLeftRightRotation();
				} else if (curr.balanceFactor == -2) {
					if (curr.right.balanceFactor == -1) 
						curr = curr.SingleLeftRotation();
					else
						curr = curr.DoubleRightLeftRotation();
				}
				if (curr.parent == null)
					rootnode = curr;
				curr = curr.parent;
			}
		}
		return rootnode.val;
	}
	
	public String DeleteDocument(String document){
		//Implement your code here
		return "";
	}
}
