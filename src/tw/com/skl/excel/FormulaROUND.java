package tw.com.skl.excel;

public class FormulaROUND extends Expression {
	
	public static final String FORMULA_REGEX = "(ROUND|round)\\(";
	public static final String NAME = "ROUND";
	
	private String number;
	private String digits;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		this.number = statements[0];
		this.digits = statements[1];
		
		this.calPostfix(this.convertToPostfix(this.number));
		this.calPostfix(this.convertToPostfix(this.digits));
		
		return NAME + "_Result";
	}
}