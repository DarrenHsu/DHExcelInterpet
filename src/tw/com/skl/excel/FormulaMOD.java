package tw.com.skl.excel;

public class FormulaMOD extends Expression {
	
	public static final String FORMULA_REGEX = "(MOD|mod)\\(";
	public static final String NAME = "MOD";
	
	private String divisor;
	private String dividend;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		if (statements.length == 2) {
			this.divisor = statements[0];
			this.dividend = statements[1];
			
			this.splitStatement(this.divisor);
			this.splitStatement(this.dividend);
		}
		
		return NAME + "_Result";
	}
}
