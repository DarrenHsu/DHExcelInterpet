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
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		this.logicals = this.splitComman(statement);
		
		String result = S_TRUE;
		BigDecimal r = BigDecimal.ZERO;
		
		for(int i  = 0 ; i < this.logicals.length ; i++) {
			if (i == 0) {
				r = this.calPostfix(this.convertToPostfix(this.logicals[i]));
				continue;
			}
			BigDecimal tmp = this.calPostfix(this.convertToPostfix(this.logicals[i]));
			if (r.compareTo(tmp) != 0) {
				result = S_FALSE;
				break;
			}else {
				r = tmp;
			}
		}

		Log.d("r " + result);
		return result;
	}
}
