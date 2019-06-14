import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Ssq {
	private int generatePositive(Random random, int bound, int count) {
		int result = random.nextInt(bound);
		if (result > 0) {
			return result;
		} else if (result == 0 && count-- > 0) {
			return generatePositive(random, bound, count);
		} else {
			return -9;
		}
	}

	/**
	 * red:1-33
	 * blue:1-16
	 * @param bytes
	 */
	private void generate2(byte[] bytes) {
		if (bytes == null || bytes.length != 7) {
			return;
		}
		Random random = new Random();
		int randomNum;
		for (int i = 0; i < bytes.length; i++) {
			if (i == bytes.length -1) {
				randomNum = generatePositive(random, 17, 5);
			} else {
				randomNum = generatePositive(random, 34, 5);
			}
			bytes[i] = (byte)randomNum;
		}
	}

	/**
	 * 0-99
	 * @param bytes
	 */
	private void generate(byte[] bytes) {
		if (bytes == null || bytes.length != 7) {
			return;
		}
		Random random = new Random();
		random.nextBytes(bytes);
//		Log.d("raw numbers:" + Arrays.toString(bytes));
		byte b;
		for (byte i = 0; i < bytes.length; i++) {
			b = bytes[i];
			if (b < 0) {
				int handred = -b / 100 + 1;
				b = (byte) (handred * 100 + b);
			}
			if (b > 99) {
				b = (byte)(b % 100);
			}
			bytes[i] = b;
		}
//		Log.d("processed numbers:" + Arrays.toString(bytes));
	}

	public static void main(String[] args) {
		Ssq ssq = new Ssq();
		byte[] bytes = new byte[7];

		Scanner scanner = new Scanner(System.in);
		String line;
		int count;
		boolean realSsq;
		while(true) {
			Log.d("Tell me how many times you'd like to generate:");
			line = scanner.nextLine();
			if (line.toLowerCase().contentEquals("q")) {
				break;
			}
			try {
				count = Integer.parseInt(line);
				realSsq = count > 0;
				if (count < 0) {
					count = Math.abs(count);
				}
				for (int i = 1; i <= count; i++) {
					if (realSsq) {
						ssq.generate2(bytes);
					} else {
						ssq.generate(bytes);
					}
					byte[] red = Arrays.copyOf(bytes, 6);
					Log.d("the %d time, red:%s; blue:%d. ", i, Arrays.toString(red), bytes[6]);
				}
			} catch (NumberFormatException e) {
				Log.d("error input, exit");
				break;
			}
		}
	}

}
