package DSCoinPackage;

import HelperClasses.CRF;

public class BlockChain_Honest {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock lastBlock;

  public void InsertBlock_Honest (TransactionBlock newBlock) {
	Integer i = new Integer(1_000_000_000);
	CRF sha = new CRF(64);
	do {
		newBlock.nonce = (++i).toString();
		if (lastBlock == null)
			newBlock.dgst = sha.Fn(start_string+"#"+newBlock.trsummary+"#"+newBlock.nonce);
		else
			newBlock.dgst = sha.Fn(lastBlock.dgst+"#"+newBlock.trsummary+"#"+newBlock.nonce);
	} while (!newBlock.dgst.substring(0,4).equals("0000"));
	newBlock.previous = lastBlock;
	lastBlock = newBlock;
  }
}
