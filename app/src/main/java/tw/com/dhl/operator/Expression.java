package tw.com.dhl.operator;

import java.math.BigDecimal;

public abstract class Expression {
	
	public static final String SYMBOL 	= "";
	
	public abstract BigDecimal interpret();
	
	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}
}