package tw.com.skl.excel;

import tw.com.skl.utility.Log;

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
		
		for(int i  = 0 ; i < this.logicals.length ; i++) {
			this.calPostfix(this.convertToPostfix(this.logicals[i]));
		}

		return NAME + "_Result";
	}
}
