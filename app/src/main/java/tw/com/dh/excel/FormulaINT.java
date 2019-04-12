package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaINT extends Formula {
	
	public static final String FORMULA_REGEX = "(INT|int)\\(";
	public static final String NAME = "INT";
	
	public FormulaINT(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public BigDecimal interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		return this.calPostfix(this.convertToPostfix(statement)).setScale(0, BigDecimal.ROUND_DOWN);
	}
}
