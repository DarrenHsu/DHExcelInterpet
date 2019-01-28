package tw.com.skl.excel;

public class FormulaROW extends Expression {
	
	public static final String FORMULA_REGEX = "(ROW|row)\\(";
	public static final String NAME = "ROW";
	
	private String operand;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		
		this.operand = statement;
		
		return NAME + "_Result";
	}
}
