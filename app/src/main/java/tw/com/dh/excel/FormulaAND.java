package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaAND extends Formula {
	
	public static final String FORMULA_REGEX = "(AND|and)\\(";
	public static final String NAME = "AND";
	
	private String[] logicals;
	
	public FormulaAND(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public BigDecimal interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		this.logicals = this.splitComman(statement);
		
		for(int i  = 0 ; i < this.logicals.length ; i++) {
			BigDecimal result = this.calPostfix(this.convertToPostfix(this.logicals[i]));
			if (result.equals(S_FALSE_VALUE)) {
				Log.d("r " + S_FALSE);
				return S_FALSE_VALUE;
			}
		}

		Log.d("r " + S_TRUE);
		return S_TRUE_VALUE;
	}
}
