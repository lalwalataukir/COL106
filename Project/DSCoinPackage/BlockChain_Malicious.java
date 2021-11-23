package DSCoinPackage;

import HelperClasses.CRF;
import HelperClasses.MerkleTree;
import HelperClasses.Pair;
import java.util.ArrayList;
public class BlockChain_Malicious {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock[] lastBlocksList;

  public static boolean checkTransactionBlock (TransactionBlock tB) {
    if (!tB.dgst.substring(0,4).equals("0000"))
    	return false;
    CRF sha = new CRF(64);
    if (tB.previous == null) {
		if (!tB.dgst.equals(sha.Fn(start_string+"#"+tB.trsummary+"#"+tB.nonce)))
			return false;
	} else if (!tB.dgst.equals(sha.Fn(tB.previous.dgst+"#"+tB.trsummary+"#"+tB.nonce)))
		return false;
    MerkleTree temp = new MerkleTree();
    if (!tB.trsummary.equals(temp.Build(tB.trarray)))
    	return false;
    for (Transaction t : tB.trarray)
    	if (!tB.checkTransaction(t))
    		return false;
	return true;
  }

  public TransactionBlock FindLongestValidChain () {
	ArrayList<Pair<TransactionBlock, Integer>> tails = new ArrayList<Pair<TransactionBlock, Integer>>();
    for (TransactionBlock t : lastBlocksList) {
    	Pair<TransactionBlock, Integer> tail = new Pair<TransactionBlock, Integer>(null, 0);
		
		TransactionBlock curr = t;
    	while (curr != null) {
    		if (!checkTransactionBlock(curr)) {
    			tail.first = null;
    			tail.second = 0;
			} else {
				if (tail.first == null)
					tail.first = curr;
				tail.second++;
			}
    		curr = curr.previous;
		}
		
		if (tail.first != null)
			tails.add(tail);
	}
	if (tails.size() == 0)
		return null;
	Pair<TransactionBlock, Integer> longest = tails.get(0);
	for (Pair<TransactionBlock, Integer> t : tails)
		if (t.second > longest.second)
			longest = t;
	return longest.first;
  }

  public void InsertBlock_Malicious (TransactionBlock newBlock) {
	TransactionBlock lastBlock = FindLongestValidChain();
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
	for (int k=0; k<lastBlocksList.length; k++) {
		if (lastBlocksList[k] == lastBlock) {
			lastBlocksList[k] = newBlock;
			break;
		}
	}
  }
}
