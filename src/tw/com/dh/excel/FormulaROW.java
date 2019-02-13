package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaROW extends Formula {
	
	public static final String FORMULA_REGEX = "(ROW|row)\\(";
	public static final String NAME = "ROW";
	
	public FormulaROW(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public BigDecimal interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		int result = this.excelData.currentRow + 1;
		
		Log.d("r " + result);
		return new BigDecimal(result);
	}
}
