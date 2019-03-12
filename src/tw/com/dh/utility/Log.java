package tw.com.dh.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static void d(String msg) {
//		System.out.println(msg);
	}
	
	public static void e(String msg) {
		System.out.println(sdf.format(new Date()) + " - " + msg);
	}
	
	public static void i(String msg) {
		System.out.println(msg);
	}
}
