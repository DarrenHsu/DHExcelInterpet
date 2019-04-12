package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaMAX extends Formula {
	
	public static final String FORMULA_REGEX = "(MAX|max)\\(";
	public static final String NAME = "MAX";
	
	public FormulaMAX(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public BigDecimal interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		BigDecimal valA = this.calPostfix(this.convertToPostfix(statements[0]));
		BigDecimal valB = this.calPostfix(this.convertToPostfix(statements[1]));
		return valA.compareTo(valB) >= 0 ? valA : valB;
	}
}
