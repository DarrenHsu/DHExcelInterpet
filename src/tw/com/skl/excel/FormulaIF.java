package tw.com.skl.excel;

public class FormulaIF extends Expression {
	
	public static final String FORMULA_REGEX = "(IF|if)\\(";
	public static final String NAME = "IF";
	
	private String operand;
	private String trueResult;
	private String falseResult;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		if (statements.length == 3) {
			this.operand = statements[0];
			this.trueResult = statements[1];
			this.falseResult = statements[2];
			
			this.calPostfix(this.convertToPostfix(this.operand));
			this.calPostfix(this.convertToPostfix(this.trueResult));
			this.calPostfix(this.convertToPostfix(this.falseResult));
		}
		
		return NAME + "_Result";
	}
}
