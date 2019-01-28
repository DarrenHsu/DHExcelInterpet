package tw.com.skl.invest.operator;

public abstract class Expression {
	public abstract String interpret();
	
	protected static void print(String msg) {
		System.out.println(msg);
	}
}