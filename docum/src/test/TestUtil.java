package test;

import java.util.Date;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUtil {

	private static Random random;
	private static String initialString =
		"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final Long ONE_YEAR_MILLIS = 31536000000L;
	private static final int epoch = 1970;

	static {
		random = new Random();
	}

	public static Long getRandomLong() {
		return Math.abs(random.nextLong());
	}

	public static Integer getRandomInteger() {
		return Math.abs(random.nextInt());
	}	

	public static Integer getRandomInteger(int maximum) {
		return Math.abs(random.nextInt(maximum));
	}

	public static Date getRandomDate(int maxYear) {
		Long date = random.nextInt(maxYear-epoch) * ONE_YEAR_MILLIS;
		return new Date(date);
	}

	public static String getRandomString(int length) {
		String result = "";
		int initialSize = initialString.length();
		char[] initialChars = initialString.toCharArray() ;

		for (int i = 0; i < length; i++) {
			result += initialChars[getRandomInteger(initialSize - 1)];
		}

		return result;
	}
	
	public static ApplicationContext getSpringApplicationContext() {
		ApplicationContext result = null;
        String[] paths = {"docum-context.xml"};
        result = new ClassPathXmlApplicationContext(paths);
		
		return result;
	}
}
