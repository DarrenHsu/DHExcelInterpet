package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaROUND extends Formula {
	
	public static final String FORMULA_REGEX = "(ROUND|round)\\(";
	public static final String NAME = "ROUND";
	
	public FormulaROUND(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public BigDecimal interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		int d = this.calPostfix(this.convertToPostfix(statements[1])).intValue();
		BigDecimal result = this.calPostfix(this.convertToPostfix(statements[0])).setScale(d, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
		
		Log.d("r " + result.toPlainString());
		return result;
	}
}
