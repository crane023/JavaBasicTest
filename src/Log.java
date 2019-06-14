
public class Log {
    static void d(String msg) {
        System.out.println(TimeFormatter.getFormattedLogDate() + " " + Thread.currentThread().getName() + " D/ " + msg);
    }

    static void d(String format, Object... args) {
    	String msg = String.format(format, args);
        System.out.println(TimeFormatter.getFormattedLogDate() + " " + Thread.currentThread().getName() + " D/ " + msg);
    }
}
