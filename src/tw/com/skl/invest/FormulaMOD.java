package tw.com.skl.invest;

public class FormulaMOD implements Expression {
	
	public static final String formulaRegex = "(MOD|mod)\\(";
	public static final String name = "MOD";
	
	@Override
	public String interpret(String statement) {
		System.out.println("p " + name + " : " + statement);
		return name + "_Result";
	}
}
