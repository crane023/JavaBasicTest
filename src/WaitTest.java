
public class WaitTest {
	static Object sLock = new Object();
	private Processor mProcessor;

	static class Processor extends Thread {

		@Override
		public void run() {
			super.run();
			synchronized (Processor.this) {
				try {
					log("run:");
					sleep(2000);
					log("before wait");

					/*
					 *  The thread releases ownership of this monitor and waits until
					 *  another thread notifies threads waiting on this object's monitor wake up
					 *  either through a call to the notify method or the notifyAll method.
					 *  The thread then waits until it can re-obtain ownership of the monitor
					 *  and resumes execution.
					 */
					wait();
					log("after wait");
					sleep(2000);
					log("after 2nd sleep");
				} catch (InterruptedException e) {
					log("on interrupted: " + e.getLocalizedMessage());
				}
			}
		}

		public void doA() {
			synchronized(Processor.this) {
				log("doA:");
			}
		}

		public void doB() {
			synchronized(Processor.this) {
				log("doB:");
				notifyAll();
			}
		}
	}

	public WaitTest( ) {
		mProcessor = new Processor();
	}

	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mProcessor.start();
			}

		}, "111").start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					log("interrupted in 222");
				}
				mProcessor.doA();
			}

		}, "222").start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					log("interrupted in 333");
				}
				mProcessor.doB();
			}

		}, "333").start();
	}

	/*
	 * 	13/run: // thead13 obtains ownership of Processor.this's monitor, and enter sleep with ownership of monitor
		13/before wait // thread13 owns Processor.this's monitor, and thread15 wait
		15/doA: // wait() release thread13's ownership, and thread15 obtains.
		16/doB: // thread16 obtains ownership since thead15 and thread13 both released.
		13/after wait // thread16 notifyAll and thread13 re-own monitor.
		13/after 2nd sleep
	 */
	public static void main(String[] args) {
		new WaitTest().start();
	}

	static void log(String msg) {
		System.out.println(Thread.currentThread().getId() + "/" + msg);
	}
}
