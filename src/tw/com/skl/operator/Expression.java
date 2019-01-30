package tw.com.skl.operator;

public abstract class Expression {
	
	public static final String SYMBOL 	= "";
	
	public abstract String interpret();
	
	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}
}