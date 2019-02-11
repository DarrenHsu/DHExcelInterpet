package tw.com.dh.excel;

import tw.com.dh.utility.Log;

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
		String result = null;
		
		if (statements.length == 3) {
			this.localTest = this.calPostfix(this.convertToPostfix(statements[0]));
			if (this.localTest.equals(S_TRUE)) {
				result = this.calPostfix(this.convertToPostfix(statements[1]));
			}else {
				result = this.calPostfix(this.convertToPostfix(statements[2]));
			}
		}
		
		Log.d("r " + result);
		return result;
	}
}
