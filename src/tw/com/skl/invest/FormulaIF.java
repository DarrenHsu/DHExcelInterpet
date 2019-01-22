package tw.com.skl.invest;

public class FormulaIF implements Expression {
	
	public static final String formulaRegex = "(IF|if)\\(";
	public static final String name = "IF";
	
	@Override
	public String interpret(String statement) {
		System.out.println("p " + name + " : " + statement);
		return name + "_Result";
	}
}
