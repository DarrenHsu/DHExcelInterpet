package tw.com.skl.excel;

import tw.com.skl.utility.Log;

public class FormulaROW extends Formula {
	
	public static final String FORMULA_REGEX = "(ROW|row)\\(";
	public static final String NAME = "ROW";
	
	public FormulaROW(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		int result = this.excelData.currentRow + 1;
		Log.d("r " + result);
		return "" + result;
	}
}
