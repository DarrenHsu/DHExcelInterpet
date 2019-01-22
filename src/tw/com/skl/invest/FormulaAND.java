package tw.com.skl.invest;

public class FormulaAND implements Expression {
	
	public static final String formulaRegex = "(AND|and)\\(";
	public static final String name = "AND";
	
	@Override
	public String interpret(String statement) {
		System.out.println("p " + name + " : " + statement);
		return name + "_Result";
	}
}
