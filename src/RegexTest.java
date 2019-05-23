import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
	public static void match(String raw) {
		String regex = "Regex((?i:abc))_(te)st";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(raw);
		log("matches:" + matcher.matches() + "; count:" + matcher.groupCount());
		while (matcher.find()) {
			log("--- group ---" + matcher.group());
			if (matcher.groupCount() >= 0) {
				for(int i = 0; i <= matcher.groupCount(); i++) {
					log("group_" + i + ":" + matcher.group(i));
				}
			}
		}

	}

	// iiiaaaRegexABC_test1345RegexAbC_testendend
	public static void main(String[] args) {
		while(true) {
			log("Please input a string:");
			Scanner scanner = new Scanner(System.in);
			String line = scanner.nextLine();
			if (line.toLowerCase().equals("q")) {
				break;
			}
			match(line);
		}
	}

    static void log(String msg) {
        System.out.println(TimeFormatter.getFormattedLogDate() + " " + Thread.currentThread().getName() + "/ " + msg);
    }
}
