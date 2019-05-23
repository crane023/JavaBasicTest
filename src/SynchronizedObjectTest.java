public class SynchronizedObjectTest {

    private Operator mOpr;
    private Thread mSyncOprThread;
    private Thread mOprUserThread;

    public SynchronizedObjectTest() {
        mOpr = new Operator();
    }

    public void beginTest() {
        mSyncOprThread = new Thread(new Runnable() {
            @Override
            public void run() {
                log("running");
                synchronized(mOpr) {
                    try {
                        log("own monitor:" + mOpr.toString());
                        Thread.sleep(3 * 1000);
                        log("after sleep, and enter wait");

                        /*
                         * IllegalMonitorStateException -
                         * if the current thread is not the owner of the object's monitor
                         */
                        mOpr.wait();
                        log("after wait");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, "SyncOprThread");
        mSyncOprThread.start();

        mOprUserThread = new Thread(new Runnable() {
            @Override
            public void run() {
                log("running");
                try {
                    Thread.sleep(1 * 1000);
                    log("after sleep, before operate");
                    mOpr.operate();
                    log("after operate");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "OprUserThread");
        mOprUserThread.start();

    }

    static class Operator{
        public void operate() {
            log("I wanna to remove block from me.");
            synchronized(this) {
            	log("own monitor:" + this.toString());

                /*
                 * throw java.lang.IllegalMonitorStateException
                 * if the current thread is not the owner of this object's monitor.
                 */
            	this.notifyAll();
            }
        }
    }

    /*
     * the outputs:
     * 2019-05-23 11:07:41.581 main/ say, now we will begin.
     * 2019-05-23 11:07:41.583 SyncOprThread/ running
     * 2019-05-23 11:07:41.583 SyncOprThread/ own monitor:SynchronizedObjectTest$Operator@7a3ec50c // will sleep 3s
     * 2019-05-23 11:07:41.583 OprUserThread/ running
     * 2019-05-23 11:07:42.583 OprUserThread/ after sleep, before operate // after sleep 1s
     *
     * // operate() was called but cannot enter sync without ownership of monitor
     * // and waiting for the ownership is released.
     * 2019-05-23 11:07:42.583 OprUserThread/ I wanna to remove block from me.
     *
     * 2019-05-23 11:07:44.584 SyncOprThread/ after sleep, and enter wait // wait() release ownership
     *
     * // operate() obtained ownership, and notifyAll
     * 2019-05-23 11:07:44.584 OprUserThread/ own monitor:SynchronizedObjectTest$Operator@7a3ec50c
     * 2019-05-23 11:07:44.584 OprUserThread/ after operate
     *
     * 2019-05-23 11:07:44.584 SyncOprThread/ after wait // re-obtain ownership after mOpr.notifyAll()
     */
    public static void main(String[] args) {
        log("say, now we will begin.");
        new SynchronizedObjectTest().beginTest();
    }

    static void log(String msg) {
        System.out.println(TimeFormatter.getFormattedLogDate() + " " + Thread.currentThread().getName() + "/ " + msg);
    }

}
