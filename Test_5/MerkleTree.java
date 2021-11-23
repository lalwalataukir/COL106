import Includes.*;
import java.util.*;

public class MerkleTree {

	// Check the TreeNode.java file for more details
	public TreeNode rootnode;

    /*==========================
    |- To be done by students -|
    ==========================*/
    /* Lab test to-do */

	public TreeNode f(TreeNode node, int key) {
		if (node==null)
			return null;
		if (node.val1 == key || node.val2 == key)
			return node;
		if (node.val1 > key) 
			return f(node.leftchild, key);
		if (node.val2 <	key)
			return f(node.rightchild, key);
		return f(node.midchild, key);
	}

	public TreeNode Search(int key) {
		/* Implement your code here */
		/* Remember to:
			- Turn on your camera
			- turn on your microphone
			- share your screen
			- begin recording the meeting
			- (just before submitting) rename your file as per the convention specified */
		
		return f(rootnode, key);
	}
}
