package DSCoinPackage;

import java.util.*;
import HelperClasses.Pair;

class coinComp implements Comparator<Pair<String,TransactionBlock>> {
	public int compare(Pair<String,TransactionBlock> a, Pair<String,TransactionBlock> b) {
		return a.first.compareTo(b.first);
	}
}

public class Members
 {

  public String UID;
  public List<Pair<String, TransactionBlock>> mycoins;
  public Transaction[] in_process_trans;

  public void initiateCoinsend(String destUID, DSCoin_Honest DSobj) {
	Pair<String, TransactionBlock> coin = mycoins.remove(0);
  	Transaction tobj = new Transaction();
  	tobj.coinID = coin.first;
  	tobj.coinsrc_block = coin.second;
  	tobj.Source = this;
  	for (Members member : DSobj.memberlist)
		if (member.UID.equals(destUID)) 
			tobj.Destination = member;
	for (int i=0; i<in_process_trans.length; i++) {
		if (in_process_trans[i] == null) {
			in_process_trans[i] = tobj;
			break;
		}
	}
	DSobj.pendingTransactions.AddTransactions(tobj);
  }
  
  public Pair<List<Pair<String, String>>, List<Pair<String, String>>> finalizeCoinsend (Transaction tobj, DSCoin_Honest DSObj) throws MissingTransactionException {

	Pair<List<Pair<String, String>>, List<Pair<String, String>>> ret = new Pair<List<Pair<String, String>>, List<Pair<String, String>>>(new ArrayList<Pair<String, String>>(), new ArrayList<Pair<String,String>>());

    TransactionBlock tB = DSObj.bChain.lastBlock;
    while (true) {
    	if (tB == null)
    		throw new MissingTransactionException();
    	
    	for (int i=0; i<tB.trarray.length; i++) {
    		if (
    			tB.trarray[i].coinID.equals(tobj.coinID) &&
    			tB.trarray[i].Source.UID.equals(tobj.Source.UID) &&
    			tB.trarray[i].Destination.UID.equals(tobj.Destination.UID)
    		) {
				ret.first = tB.Tree.Query(i);
				TransactionBlock curr = DSObj.bChain.lastBlock;
				while (curr != tB.previous) {
					ret.second.add(new Pair<>(curr.dgst, curr.previous.dgst+"#"+curr.trsummary+"#"+curr.nonce));
					curr = curr.previous;
				}
				ret.second.add(new Pair<String, String>(tB.previous.dgst, null));
				Collections.reverse(ret.second);
				for (int j=0; j<in_process_trans.length; j++) {
					if (in_process_trans[j]!=null) {
						if (
							in_process_trans[j].coinID.equals(tobj.coinID) &&
							in_process_trans[j].Source.UID.equals(tobj.Source.UID) &&
							in_process_trans[j].Destination.UID.equals(tobj.Destination.UID)
						) {
							in_process_trans[j] = null;
							break;
						}
					}
				}

				for (Members m : DSObj.memberlist) {
					if (m.UID.equals(tobj.Destination.UID)) {
						m.mycoins.add(new Pair<>(tobj.coinID, tB));
						m.mycoins.sort(new coinComp());
					}
				}

				return ret;
			}
		}

    	tB = tB.previous;
	}
  }

  public void MineCoin(DSCoin_Honest DSObj) {
	Transaction[] valid_transactions = new Transaction[DSObj.bChain.tr_count];
	int valid_count = 0;
	while (valid_count<DSObj.bChain.tr_count-1) {
		try {
			Transaction t = DSObj.pendingTransactions.RemoveTransaction();
			TransactionBlock tB = t.coinsrc_block;
			Boolean ok = true;		
			if (t.coinsrc_block != null) {
				Boolean found = false;
				for (Transaction temp : t.coinsrc_block.trarray)
					found |= t.Source.UID.equals(temp.Destination.UID);
				ok &= found;
			}
			TransactionBlock curr = DSObj.bChain.lastBlock;
			while (curr != tB) {
				for (Transaction temp : curr.trarray) 
					ok &= !(temp.coinID.equals(t.coinID));
				curr = curr.previous;
			}
			for (Transaction old : valid_transactions)
				ok &= (old != t);
			if (ok)
				valid_transactions[valid_count++] = t;
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	Transaction minerRewardTransaction = new Transaction(); 
	
	Integer oldID = new Integer(DSObj.latestCoinID);
	DSObj.latestCoinID = (++oldID).toString();
	
	minerRewardTransaction.coinID = DSObj.latestCoinID;	
	minerRewardTransaction.Source = null;
	minerRewardTransaction.Destination = this;
	minerRewardTransaction.coinsrc_block = null;
	
	valid_transactions[valid_count++] = minerRewardTransaction;
	TransactionBlock tB = new TransactionBlock(valid_transactions);
	mycoins.add(new Pair<>(minerRewardTransaction.coinID, tB));
	DSObj.bChain.InsertBlock_Honest(tB);
  }

  public void MineCoin(DSCoin_Malicious DSObj) {
	Transaction[] valid_transactions = new Transaction[DSObj.bChain.tr_count];
	int valid_count = 0;
	while (valid_count<DSObj.bChain.tr_count-1) {
		try {
			Transaction t = DSObj.pendingTransactions.RemoveTransaction();
			TransactionBlock tB = t.coinsrc_block;
			Boolean ok = true;
			
			if (t.coinsrc_block != null) {
				Boolean found = false;
				for (Transaction temp : t.coinsrc_block.trarray)
					found |= t.Source.UID.equals(temp.Destination.UID);
				ok &= found;
			}
			TransactionBlock curr = DSObj.bChain.FindLongestValidChain();
			while (curr != tB) {
				for (Transaction temp : curr.trarray)
					ok &= !temp.coinID.equals(t.coinID);
				curr = curr.previous;
			}
		
			for (Transaction old : valid_transactions)
				ok &= (old != t);
			if (ok)
				valid_transactions[valid_count++] = t;
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	Transaction minerRewardTransaction = new Transaction(); 
	
	Integer oldID = new Integer(DSObj.latestCoinID);
	DSObj.latestCoinID = (++oldID).toString();
	
	minerRewardTransaction.coinID = DSObj.latestCoinID;	
	minerRewardTransaction.Source = null;
	minerRewardTransaction.Destination = this;
	minerRewardTransaction.coinsrc_block = null;
	
	valid_transactions[valid_count++] = minerRewardTransaction;
	TransactionBlock tB = new TransactionBlock(valid_transactions);
	mycoins.add(new Pair<>(minerRewardTransaction.coinID, tB));
	DSObj.bChain.InsertBlock_Malicious(tB);
  }  
}
