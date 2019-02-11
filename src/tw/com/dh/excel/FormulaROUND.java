package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaROUND extends Formula {
	
	public static final String FORMULA_REGEX = "(ROUND|round)\\(";
	public static final String NAME = "ROUND";
	
	private String number;
	private String digits;
	
	public FormulaROUND(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		this.number = statements[0];
		this.digits = statements[1];
		
		int d = this.calPostfix(this.convertToPostfix(this.digits)).intValue();
		BigDecimal result = this.calPostfix(this.convertToPostfix(this.number)).setScale(d, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
		
		Log.d("r " + result.toPlainString());
		return result.stripTrailingZeros().toPlainString();
	}
}
