package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaOR extends Formula {
	
	public static final String FORMULA_REGEX = "(OR|or)\\(";
	public static final String NAME = "OR";
	
	private String[] logicalS;
	
	public FormulaOR(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public BigDecimal interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		this.logicalS = this.splitComman(statement);
		for(int i  = 0 ; i < this.logicalS.length ; i++) {
			BigDecimal result = this.calPostfix(this.convertToPostfix(this.logicalS[i]));
			if (result.compareTo(BigDecimal.ONE) == 0) {
				Log.d("r " + S_TRUE);
				return BigDecimal.ONE;
			}
		}
		
		Log.d("r " + S_FALSE);
		return BigDecimal.ZERO;
	}
}
