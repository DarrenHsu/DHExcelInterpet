package tw.com.skl.excel;

import tw.com.skl.utility.Log;

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
		
		this.calPostfix(this.convertToPostfix(this.number));
		this.calPostfix(this.convertToPostfix(this.digits));
		
		return NAME + "_Result";
	}
}
