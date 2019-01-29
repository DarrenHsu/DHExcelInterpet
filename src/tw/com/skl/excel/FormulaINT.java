package tw.com.skl.excel;

public class FormulaINT extends Expression {
	
	public static final String FORMULA_REGEX = "(MOD|mod)\\(";
	public static final String NAME = "MOD";
	
	private String divisor;
	private String dividend;
	
	public FormulaINT(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		this.divisor = statements[0];
		this.dividend = statements[1];
		
		this.calPostfix(this.convertToPostfix(this.divisor));
		this.calPostfix(this.convertToPostfix(this.dividend));
		
		return NAME + "_Result";
	}
}
