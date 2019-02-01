package tw.com.dh.excel;

import java.math.BigDecimal;
import java.math.MathContext;

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
		BigDecimal result = new BigDecimal(this.calPostfix(this.convertToPostfix(statement)));
		return "" + result.intValue();
	}
}
