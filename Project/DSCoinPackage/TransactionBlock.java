package DSCoinPackage;

import HelperClasses.MerkleTree;
import HelperClasses.CRF;

public class TransactionBlock {

  public Transaction[] trarray;
  public TransactionBlock previous;
  public MerkleTree Tree;
  public String trsummary;
  public String nonce;
  public String dgst;

  TransactionBlock(Transaction[] t) {
	trarray = new Transaction[t.length];
	for (int i=0; i<t.length; i++)
		trarray[i] = t[i];
	previous = null;
	Tree = new MerkleTree();
	trsummary = Tree.Build(trarray);
	dgst = null;
  }

  public boolean checkTransaction (Transaction t) {
    if (t.coinsrc_block == null)
		return true;
	Boolean ok = false;
	for (Transaction t1 : t.coinsrc_block.trarray)
    	if (t1.coinID.equals(t.coinID))
    		if (t1.Destination.UID.equals(t.Source.UID))
    			ok = true;
	TransactionBlock curr = previous;
	while (curr != t.coinsrc_block) {
		for (Transaction t1 : curr.trarray)
			if (t1.coinID.equals(t.coinID))
    			ok = false;
    	curr = curr.previous;
	}
    return ok;
  }
}
