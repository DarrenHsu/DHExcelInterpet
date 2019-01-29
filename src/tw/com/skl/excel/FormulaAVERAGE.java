package tw.com.skl.excel;

public class FormulaAVERAGE extends Expression {
	
	public static final String FORMULA_REGEX = "(AVERAGE|average)\\(";
	public static final String NAME = "AVERAGE";
	
	private String[] numbers;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		
		this.numbers = this.splitComman(statement);
		
		for(int i  = 0 ; i < this.numbers.length ; i++) {
			String number = this.numbers[i];
			this.calPostfix(this.convertToPostfix(number));
		}
		
		return NAME + "_Result";
	}
}