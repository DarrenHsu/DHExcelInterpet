package tw.com.skl.excel;

public class FormulaMOD extends Expression {
	
	public static final String FORMULA_REGEX = "(INT|int)\\(";
	public static final String NAME = "INT";
	
	private String number;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		
		this.number = statement;
		
		this.calPostfix(this.convertToPostfix(this.number));
		
		return NAME + "_Result";
	}
}
