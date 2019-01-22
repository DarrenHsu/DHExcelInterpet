package tw.com.skl.invest;

public class FormulaMOD extends Expression {
	
	public static final String formulaRegex = "(MOD|mod)\\(";
	public static final String name = "MOD";
	
	private String divisor;
	private String dividend;
	
	@Override
	public String interpret(String statement) {
		print("p " + name + " : " + statement);
		String[] statements = this.splitComman(statement);
		if (statements.length == 2) {
			this.divisor = statements[0];
			this.dividend = statements[1];
		}
		
		print("p " + this.divisor + " % " + this.dividend);
		return name + "_Result";
	}
}
