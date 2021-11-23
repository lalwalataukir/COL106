import Includes.*;
import java.util.*;

public class MerkleTree{
	
	// Check the TreeNode.java file for more details
	public TreeNode rootnode;

	/*==========================
    |- To be done by students -|
    ==========================*/
    /* Lab test to-do */
	
	public int f(TreeNode node) {
		if (node == null)
			return 0;
		node.subtree_sum = f(node.left) + f(node.middle) + f(node.right) + node.val;
		return node.subtree_sum;
	}


	public int SubtreeSum(){
		/* Implement your code here */
		/* Remember to:
			- Turn on your camera
			- turn on your microphone
			- share your screen
			- begin recording the meeting
			- (just before submitting) rename your file as per the convention specified */
		return f(rootnode);	
	}
}
