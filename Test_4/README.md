Consider a modified `TreeNode` class where the data attribute `val` is an integer (instead of a string as in the Module). Additionally, two integer attributes named `var1` and `var2` are provided which you may use freely for any purpose (these variables will not be tested).

Implement a method of class `MerkleTree` named `LowestCommonAncestor(TreeNode node1, TreeNode node2)` that returns the lowest common ancestor of `node1` and `node2`. The lowest common ancestor of two nodes p and q is defined as the lowest node in the tree that has both p and q as its descendants (A node can be a descendant of itself) Assume that all nodes in the tree have distinct `val` and that the tree will always have atleast two nodes.

Hint: 
 - Start with node1, go all the way up to root, and store the points in an array A1.
 - Start with node2, go all the way up to root, and store the points in an array A2.
 - Note that A1 and A2 may not have the same length. Start with the last item in A1 and A2 (this will be the root of the tree), move backwards, find the first point where they diverge.

You are not allowed to change any existing attribute or method but may create extra ones you need within MerkleTree.java.

Submission instructions: You have to only submit MerkleTree.java inside a zip named 'EntryNumber_test4.zip' (example: 2010CS10123_test4.zip) during your lab test.
You can create a zip by:
- Windows: Right-click on the MerkleTree.java file and select Send To -> Compressed (zipped) folder.
- Linux/macOS: Run 'zip -r 2010CS10123_test4.zip MerkleTree.java' in the terminal inside the folder that contains your MerkleTree.java file. Please make sure you change the entry number in the given command to your own entry number.
