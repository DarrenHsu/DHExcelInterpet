package tw.com.dh;

import java.math.BigDecimal;

public class TestMe {
	
	public static void main(String[] args) {
		BigDecimal a1 = new BigDecimal("278904.21");
		BigDecimal a2 = new BigDecimal("299845.8640210041426004346724761001384");
		System.out.println(a1.multiply(a2).toString());
		System.out.println(a2.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
	}

	public static boolean isNumeric(String str) {
	  return str.matches("-?\\d+(\\.\\d+)?");
	}
}
