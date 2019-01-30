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
		Log.d("r " + this.excelData.currentRow);
		return "" + this.excelData.currentRow;
	}
}
