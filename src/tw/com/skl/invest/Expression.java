package tw.com.skl.invest;

public abstract class Expression {
	static String formulaRegex = "";
	static String name = "";
	
	public abstract String interpret(String statement);
	
	public static void print(String msg) {
		System.out.println(msg);
	}
	
	public String[] splitComman(String str) {
		String[] splitStr = str.split(",");
		return splitStr;
	}
}
