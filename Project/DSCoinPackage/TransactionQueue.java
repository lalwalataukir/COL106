package DSCoinPackage;

public class TransactionQueue {

  public Transaction firstTransaction;
  public Transaction lastTransaction;
  public int numTransactions;

  public void AddTransactions (Transaction transaction) {
  	  if (lastTransaction == null) {
  	  	  firstTransaction = transaction;
	  } else {
	  	  lastTransaction.next = transaction;
	  	  transaction.previous = lastTransaction;
	  }
	  lastTransaction = transaction;
	  numTransactions++;
  }
  
  public Transaction RemoveTransaction () throws EmptyQueueException {
    if (lastTransaction == null) 
    	throw new EmptyQueueException();
	
	Transaction temp = firstTransaction;
	numTransactions--;

	if (firstTransaction == lastTransaction) {
		firstTransaction = null;
		lastTransaction = null;
	} else {
		firstTransaction = firstTransaction.next;
		firstTransaction.previous = null;
	}
    return temp;
  }

  public int size() {
    return numTransactions;
  }
}
