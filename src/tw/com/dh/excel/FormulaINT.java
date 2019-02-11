package tw.com.dh.excel;

import tw.com.dh.utility.Log;

public class FormulaINT extends Formula {
	
	public static final String FORMULA_REGEX = "(INT|int)\\(";
	public static final String NAME = "INT";
	
	public FormulaINT(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		return "" + this.calPostfix(this.convertToPostfix(statement)).intValue();
	}
}
