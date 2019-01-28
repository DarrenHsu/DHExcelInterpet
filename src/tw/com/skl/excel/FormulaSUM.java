package tw.com.skl.excel;

public class FormulaSUM extends Expression {
	
	public static final String FORMULA_REGEX = "(SUM|sum)\\(";
	public static final String NAME = "SUM";
	
	private String[] operands;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		this.operands = this.splitComman(statement);
		
		for(int i  = 0 ; i < this.operands.length ; i++) {
			String o = this.operands[i];
			String[] ops = this.splitStatement(o);
		}
		
		return NAME + "_Result";
	}
}