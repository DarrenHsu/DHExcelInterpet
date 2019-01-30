package tw.com.skl.excel;

import tw.com.skl.utility.Log;

public class FormulaIF extends Formula {
	
	public static final String FORMULA_REGEX = "(IF|if)\\(";
	public static final String NAME = "IF";
	
	private String localTest;
	private String trueValue;
	private String falseValue;
	
	public FormulaIF(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		
		if (statements.length == 3) {
			this.localTest = this.calPostfix(this.convertToPostfix(statements[0]));
			this.trueValue = this.calPostfix(this.convertToPostfix(statements[1]));
			this.falseValue = this.calPostfix(this.convertToPostfix(statements[2]));
		}
		
		String result = this.localTest.equals(S_TRUE) ? this.trueValue : this.falseValue;
		Log.d("r " + result);
		return result;
	}
}
