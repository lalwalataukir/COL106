import Includes.*;
import java.util.*;
import java.io.*;

public class DriverCode{
	public static void main(String[] args)
    {
				Boolean[] tc = new Boolean[8];
				Arrays.fill(tc, Boolean.TRUE);
				TreeNode ret_node;

        MerkleTree tree1 = new MerkleTree(); //Left-skewed tree
				tree1.rootnode = new TreeNode(null,22);
				tree1.rootnode.left = new TreeNode(tree1.rootnode,11);
				tree1.rootnode.left.left = new TreeNode(tree1.rootnode.left,5);
				tree1.rootnode.left.left.left = new TreeNode(tree1.rootnode.left.left,14);
				tree1.rootnode.left.left.left.left = new TreeNode(tree1.rootnode.left.left.left,23);

				ret_node = tree1.LowestCommonAncestor(tree1.rootnode.left,tree1.rootnode.left.left.left);
				if(ret_node.val!=11){tc[0] = false;} // LCA(11,14) = 11
				System.out.println(tc[0]?"Test case 1 of 8: passed":"Test case 1 of 8: failed");

				MerkleTree tree2 = new MerkleTree(); //Right-skewed tree
				tree2.rootnode = new TreeNode(null,53);
				tree2.rootnode.right = new TreeNode(tree2.rootnode,65);
				tree2.rootnode.right.right = new TreeNode(tree2.rootnode.right,34);
				tree2.rootnode.right.right.right = new TreeNode(tree2.rootnode.right.right,9);
				tree2.rootnode.right.right.right.right = new TreeNode(tree2.rootnode.right.right.right,28);
				tree2.rootnode.right.right.right.right.right = new TreeNode(tree2.rootnode.right.right.right.right,41);

				ret_node = tree2.LowestCommonAncestor(tree2.rootnode.right.right,tree2.rootnode.right.right.right.right);
				if(ret_node.val!=34){tc[1] = false;} //LCA(34,28) = 34
				System.out.println(tc[1]?"Test case 2 of 8: passed":"Test case 2 of 8: failed");


				MerkleTree tree3 = new MerkleTree(); //Complete tree
				tree3.rootnode = new TreeNode(null,10);
				tree3.rootnode.left = new TreeNode(tree3.rootnode,32);
				tree3.rootnode.right = new TreeNode(tree3.rootnode,51);
				tree3.rootnode.left.left = new TreeNode(tree3.rootnode.left,84);
				tree3.rootnode.right.right = new TreeNode(tree3.rootnode.right,27);
				tree3.rootnode.left.right = new TreeNode(tree3.rootnode.left,46);
				tree3.rootnode.right.left = new TreeNode(tree3.rootnode.right,13);

				ret_node = tree3.LowestCommonAncestor(tree3.rootnode.left.left,tree3.rootnode.left.right);
				if(ret_node.val!=32){tc[2] = false;} //LCA(84,46) = 32
				System.out.println(tc[2]?"Test case 3 of 8: passed":"Test case 3 of 8: failed");

				ret_node = tree3.LowestCommonAncestor(tree3.rootnode.right.left,tree3.rootnode.left.left);
				if(ret_node.val!=10){tc[3] = false;} // LCA(13,84) = 10
				System.out.println(tc[3]?"Test case 4 of 8: passed":"Test case 4 of 8: failed");

				ret_node = tree3.LowestCommonAncestor(tree3.rootnode.right.right,tree3.rootnode.right);
				if(ret_node.val!=51){tc[4] = false;} // LCA(27,51) = 51
				System.out.println(tc[4]?"Test case 5 of 8: passed":"Test case 5 of 8: failed");

				MerkleTree tree4 = new MerkleTree(); //Arbitary tree
				tree4.rootnode = new TreeNode(null,52);
				tree4.rootnode.left = new TreeNode(tree4.rootnode,25);
				tree4.rootnode.right = new TreeNode(tree4.rootnode,7);
				tree4.rootnode.left.left = new TreeNode(tree4.rootnode.left,81);
				tree4.rootnode.left.right = new TreeNode(tree4.rootnode.left,36);
				tree4.rootnode.right.left = new TreeNode(tree4.rootnode.right,13);
				tree4.rootnode.right.right = new TreeNode(tree4.rootnode.right,8);
				tree4.rootnode.left.left.left = new TreeNode(tree4.rootnode.left.left,11);
				tree4.rootnode.left.left.right = new TreeNode(tree4.rootnode.left.left,4);
				tree4.rootnode.left.right.left = new TreeNode(tree4.rootnode.left.right,9);
				tree4.rootnode.right.right.left = new TreeNode(tree4.rootnode.right.right,17);

				ret_node = tree4.LowestCommonAncestor(tree4.rootnode.left.left,tree4.rootnode.left.right.left);
				if(ret_node.val!=25){tc[5] = false;} //LCA(81,9) = 25
				System.out.println(tc[5]?"Test case 6 of 8: passed":"Test case 6 of 8: failed");

				ret_node = tree4.LowestCommonAncestor(tree4.rootnode.left.left.left,tree4.rootnode.right.right.left);
				if(ret_node.val!=52){tc[6] = false;} //LCA(11,17) = 52
				System.out.println(tc[6]?"Test case 7 of 8: passed":"Test case 7 of 8: failed");

				ret_node = tree4.LowestCommonAncestor(tree4.rootnode.right.left,tree4.rootnode.right.right);
				if(ret_node.val!=7){tc[7] = false;} //LCA(13,8) = 7
				System.out.println(tc[7]?"Test case 8 of 8: passed":"Test case 8 of 8: failed");

    }

}
