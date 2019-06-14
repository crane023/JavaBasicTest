import java.util.Vector;

public class VectorGetAndRemoveTest {
	Object mLock = new Object();
	Vector<Task> mTasks;

	public VectorGetAndRemoveTest() {
		mTasks = new Vector();
	}

	public static class Task{
		private String name;
		public Task(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private void add(Task task) {
		if (task == null) {
			return;
		}
		synchronized(mLock) {
			mTasks.add(task);
		}
	}

	private void popFirst() {
		synchronized(mLock) {
			if (mTasks != null && mTasks.size() > 0) {
				mTasks.remove(0);
				Log.d("popFirst X; size:" + mTasks.size());
			}
		}
	}

	private Task getLast() {
		synchronized(mLock) {
			if (mTasks != null && mTasks.size() > 0) {
				return mTasks.lastElement();
			}
			return null;
		}
	}

	private void start() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				add(new Task("AAAAAA"));
			}

		}, "Sub1").start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				popFirst();
			}

		}, "Sub2").start();

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Task task = getLast();
		Log.d("first get:" + task.name);

		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.d("the first:" + task + "; second:" + getLast());
	}

	/**
	 * It's the thread which owns the monitor, not the method.
	 * So, add() can complete its execution.
	 */
	private void start2() {
		synchronized(mLock) {
			Log.d("start2");
			add(new Task("bBBBBB"));
			Log.d("after added:" + getLast());
		}
	}

	public static void main(String[] args) {
		new VectorGetAndRemoveTest().start();
	}
}
