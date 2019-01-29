package tw.com.skl.excel;

public class FormulaROW extends Expression {
	
	public static final String FORMULA_REGEX = "(ROW|row)\\(";
	public static final String NAME = "ROW";
	
	private String number;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		
		this.number = statement;
		
		return NAME + "_Result";
	}
}
