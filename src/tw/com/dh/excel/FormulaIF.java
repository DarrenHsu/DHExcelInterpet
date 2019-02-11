package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaIF extends Formula {
	
	public static final String FORMULA_REGEX = "(IF|if)\\(";
	public static final String NAME = "IF";
	
	public FormulaIF(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		BigDecimal result = null;
		
		if (statements.length == 3) {
			if (this.calPostfix(this.convertToPostfix(statements[0])).compareTo(BigDecimal.ZERO) == 0) {
				result = this.calPostfix(this.convertToPostfix(statements[1]));
			}else {
				result = this.calPostfix(this.convertToPostfix(statements[2]));
			}
		}
		
		Log.d("r " + result);
		return result.stripTrailingZeros().toPlainString();
	}
}
