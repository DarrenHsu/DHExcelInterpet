package tw.com.skl.excel;

import tw.com.skl.utility.Log;

public class FormulaROW extends Expression {
	
	public static final String FORMULA_REGEX = "(ROW|row)\\(";
	public static final String NAME = "ROW";
	
	private String number;
	
	public FormulaROW(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		this.number = statement;
		
		return NAME + "_Result";
	}
}
