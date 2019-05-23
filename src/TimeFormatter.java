import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class TimeFormatter {
	public static String getFormattedDate(long timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date(timeStamp));
	}
	
	public static String getFormattedLogDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return format.format(new Date(Calendar.getInstance().getTimeInMillis()));
	}
	
	public static void main(String[] args) {
		while(true) {
			log("Please input a time stamp:");
			Scanner scanner = new Scanner(System.in);
			String line = scanner.nextLine();
			if (line.toLowerCase().equals("q")) {
				break;
			}
			long time = Long.parseLong(line);
			log("date:" + getFormattedDate(time));
		}
	}
	
	public static void log(String msg) {
		System.out.println(msg);
	}
}
