package tw.com.skl.excel;

import tw.com.skl.utility.Log;

public class FormulaIF extends Expression {
	
	public static final String FORMULA_REGEX = "(IF|if)\\(";
	public static final String NAME = "IF";
	
	private String logical;
	private String trueResult;
	private String falseResult;
	
	public FormulaIF(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		if (statements.length == 3) {
			this.logical = statements[0];
			this.trueResult = statements[1];
			this.falseResult = statements[2];
			
			this.calPostfix(this.convertToPostfix(this.logical));
			this.calPostfix(this.convertToPostfix(this.trueResult));
			this.calPostfix(this.convertToPostfix(this.falseResult));
		}
		
		return NAME + "_Result";
	}
}
