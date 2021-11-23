package DSCoinPackage;
import java.util.ArrayList;
import HelperClasses.Pair;
public class Moderator
 {

  public void initializeDSCoin(DSCoin_Honest DSObj, int coinCount) {
	DSObj.latestCoinID =  "99999";
	Transaction[] txn = new Transaction[coinCount];
	Members Mod = new Members();
	Mod.UID = "Moderator";
	
	for (int i=0; i<coinCount; i++) {
		Integer id = new Integer(DSObj.latestCoinID);
		DSObj.latestCoinID = (++id).toString();
		txn[i] = new Transaction();
		txn[i].coinID = DSObj.latestCoinID;
		txn[i].Source = Mod;
		txn[i].Destination = DSObj.memberlist[i%DSObj.memberlist.length]; 
		txn[i].coinsrc_block = null;
	}
	
	for (int i=0; i<coinCount/DSObj.bChain.tr_count; i++) {
		Transaction[] t = new Transaction[DSObj.bChain.tr_count];
		for (int j=0; j<t.length; j++)
			t[j] = txn[j+i*t.length];
		TransactionBlock txnB = new TransactionBlock(t);
		DSObj.bChain.InsertBlock_Honest(txnB);
		for (Transaction temp : txnB.trarray)
			temp.Destination.mycoins.add(new Pair<>(temp.coinID, txnB));
	}
  }
    
  public void initializeDSCoin(DSCoin_Malicious DSObj, int coinCount) {
	DSObj.latestCoinID = "99999";
	Transaction[] txn = new Transaction[coinCount];
	Members Mod = new Members();
	Mod.UID = "Moderator";
	
	for (int i=0; i<coinCount; i++) {
		Integer id = new Integer(DSObj.latestCoinID);
		DSObj.latestCoinID = (++id).toString();
		txn[i] = new Transaction();
		txn[i].coinID = DSObj.latestCoinID;
		txn[i].Source = Mod;
		txn[i].Destination = DSObj.memberlist[i%DSObj.memberlist.length]; 
		txn[i].coinsrc_block = null;
	}
	
	for (int i=0; i<coinCount/DSObj.bChain.tr_count; i++) {
		Transaction[] t = new Transaction[DSObj.bChain.tr_count];
		for (int j=0; j<t.length; j++)
			t[j] = txn[j+i*t.length];
		TransactionBlock txnB = new TransactionBlock(t);
		DSObj.bChain.InsertBlock_Malicious(txnB);
		for (Transaction temp : txnB.trarray)
			temp.Destination.mycoins.add(new Pair<>(temp.coinID, txnB));
	}
  }
}
