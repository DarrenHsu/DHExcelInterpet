package tw.com.skl.excel;

public class FormulaOR extends Expression {
	
	public static final String FORMULA_REGEX = "(OR|or)\\(";
	public static final String NAME = "OR";
	
	private String[] operands;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		
		this.operands = this.splitComman(statement);
		
		for(int i  = 0 ; i < this.operands.length ; i++) {
			String o = this.operands[i];
			this.calPostfix(this.convertToPostfix(o));
		}
		
		return NAME + "_Result";
	}
}
