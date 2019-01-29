package tw.com.skl.excel;

import tw.com.skl.utility.Log;

public class FormulaSUM extends Expression {
	
	public static final String FORMULA_REGEX = "(SUM|sum)\\(";
	public static final String NAME = "SUM";
	
	private String[] numbers;
	
	public FormulaSUM(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		this.numbers = this.splitComman(statement);
		
		for(int i  = 0 ; i < this.numbers.length ; i++) {
			this.calPostfix(this.convertToPostfix(this.numbers[i]));
		}
		
		return NAME + "_Result";
	}
}