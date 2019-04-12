package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaROUNDUP extends Formula {
	public static final String FORMULA_REGEX = "(ROUNDUP|roundup)\\(";
	public static final String NAME = "ROUNDUP";
	
	public FormulaROUNDUP(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public BigDecimal interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		int d = this.calPostfix(this.convertToPostfix(statements[1])).intValue();
		BigDecimal result = this.calPostfix(this.convertToPostfix(statements[0])).setScale(d, BigDecimal.ROUND_UP).stripTrailingZeros();
		
		Log.d("r " + result.toPlainString());
		return result;
	}

}
