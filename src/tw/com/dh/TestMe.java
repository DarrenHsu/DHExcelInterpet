package tw.com.dh;

import java.math.BigDecimal;

public class TestMe {
	
	public static void main(String[] args) {
		BigDecimal a1 = new BigDecimal("2");
		BigDecimal a2 = new BigDecimal("1");
		System.out.print(a1.compareTo(a2));
	}

	public static boolean isNumeric(String str) {
	  return str.matches("-?\\d+(\\.\\d+)?");
	}
}
