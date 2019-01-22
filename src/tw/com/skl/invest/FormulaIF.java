package tw.com.skl.invest;

public class FormulaIF extends Expression {
	
	public static final String formulaRegex = "(IF|if)\\(";
	public static final String name = "IF";
	
	private String operater;
	private String trueResult;
	private String falseResult;
	
	@Override
	public String interpret(String statement) {
		print("p " + name + " : " + statement);
		String[] statements = this.splitComman(statement);
		if (statements.length == 3) {
			this.operater = statements[0];
			this.trueResult = statements[1];
			this.falseResult = statements[2];
		}
		
		print("p if " + this.operater + "," + this.trueResult + "," + this.falseResult);
		return name + "_Result";
	}
}
