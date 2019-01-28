package tw.com.skl.invest;

public class FormulaAND extends Expression {
	
	public static final String FORMULA_REGEX = "(AND|and)\\(";
	public static final String NAME = "AND";
	
	private String[] operands;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		this.operands = this.splitComman(statement);
		
		String msg = "p ";
		for(int i  = 0 ; i < this.operands.length ; i++) {
			String o = this.operands[i];
			String[] ops = this.splitStatement(o);
			msg += o + " & ";
		}
		print(msg.substring(0, msg.length() - 2));
		
		return NAME + "_Result";
	}
}
