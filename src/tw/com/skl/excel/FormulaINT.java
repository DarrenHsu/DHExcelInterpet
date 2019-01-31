package tw.com.skl.excel;

import java.math.BigDecimal;
import java.math.MathContext;

import tw.com.skl.utility.Log;

public class FormulaINT extends Formula {
	
	public static final String FORMULA_REGEX = "(INT|int)\\(";
	public static final String NAME = "INT";
	
	public FormulaINT(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		BigDecimal result = new BigDecimal(statement);
		return "" + result.intValue();
	}
}
