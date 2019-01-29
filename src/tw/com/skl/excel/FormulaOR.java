package tw.com.skl.excel;

public class FormulaOR extends Expression {
	
	public static final String FORMULA_REGEX = "(OR|or)\\(";
	public static final String NAME = "OR";
	
	private String[] logicalS;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		
		this.logicalS = this.splitComman(statement);
		
		for(int i  = 0 ; i < this.logicalS.length ; i++) {
			this.calPostfix(this.convertToPostfix(this.logicalS[i]));
		}
		
		return NAME + "_Result";
	}
}
