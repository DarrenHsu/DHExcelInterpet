package tw.com.skl.excel;

import tw.com.skl.utility.Log;

public class FormulaAVERAGE extends Expression {
	
	public static final String FORMULA_REGEX = "(AVERAGE|average)\\(";
	public static final String NAME = "AVERAGE";
	
	private String[] numbers;
	
	public FormulaAVERAGE(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		this.numbers = this.splitComman(statement);
		
		for(int i  = 0 ; i < this.numbers.length ; i++) {
			String number = this.numbers[i];
			this.calPostfix(this.convertToPostfix(number));
		}
		
		return NAME + "_Result";
	}
}
