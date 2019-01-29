package tw.com.skl.excel;

public class FormulaSUM extends Expression {
	
	public static final String FORMULA_REGEX = "(SUM|sum)\\(";
	public static final String NAME = "SUM";
	
	private String[] numbers;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		this.numbers = this.splitComman(statement);
		
		for(int i  = 0 ; i < this.numbers.length ; i++) {
			this.calPostfix(this.convertToPostfix(this.numbers[i]));
		}
		
		return NAME + "_Result";
	}
}