public class program implements Runnable {
	int n = 1;

	public synchronized void print() {
		System.out.println(Thread.currentThread().getName()+" prints: "+ n++);
	}

	public void run() {
		for (int i=0; i<5; i++) {
			print();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}

	public static void main(String args[]) {
		program p = new program();
		Thread t1 = new Thread(p);
		Thread t2 = new Thread(p);
		t1.start();
		t2.start();
	}
}
