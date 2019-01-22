package tw.com.skl.invest;

public interface Expression {
	static String formulaRegex = "";
	static String name = "";
	
	String interpret(String statement);
}
