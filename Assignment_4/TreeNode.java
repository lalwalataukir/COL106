public class TreeNode{
	public TreeNode parent;
	public TreeNode left;
	public TreeNode right;
	public String val;
	public boolean isLeaf;
	public int numberLeaves;
	public String maxleafval;
	public String minleafval;
	public int balanceFactor;

	public int height;

	public void Update() {
		Includes.CRF sha = new Includes.CRF(64);
		val = sha.Fn(left.val+"#"+right.val);
		
		height = 1 + Math.max(left.height, right.height);
		balanceFactor = left.height - right.height;
		
		numberLeaves = left.numberLeaves + right.numberLeaves;
		maxleafval = right.maxleafval;
		minleafval = left.minleafval;
		
		if (parent != null)
			parent.Update();
	}


	public TreeNode SingleLeftRotation(){
		//Implement your code here
		TreeNode x = this;
		TreeNode y = this.right.right;
		TreeNode z = this.right;
		TreeNode T2 = z.left;
		
		if (x.parent != null) {
			if (x.parent.left == x)
				x.parent.left = z;
			else
				x.parent.right = z;
		}
		z.parent = x.parent;

		z.left = x;
		x.parent = z;
		
		x.right = T2;
		T2.parent = x;
		
		x.Update();
		return z;
	}
	
	public TreeNode SingleRightRotation(){
		//Implement your code here
		TreeNode x = this;
		TreeNode y = this.left.left;
		TreeNode z = this.left;
		TreeNode T3 = z.right;
		
		if (x.parent != null) {
			if (x.parent.left == x)
				x.parent.left = z;
			else
				x.parent.right = z;
		}
		z.parent = x.parent;
		
		z.right = x;
		x.parent = z;
		
		x.left = T3;
		T3.parent = x;
		
		x.Update();
		return z;
	}
	
	public TreeNode DoubleLeftRightRotation(){
		//Implement your code here
		this.left.SingleLeftRotation();
		return this.SingleRightRotation();
	}
	
	public TreeNode DoubleRightLeftRotation(){
		//Implement your code here
		this.right.SingleRightRotation();
		return this.SingleLeftRotation();
	}
}

