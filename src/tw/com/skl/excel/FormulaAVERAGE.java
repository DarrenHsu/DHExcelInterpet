package tw.com.skl.excel;

import java.math.BigDecimal;

import tw.com.skl.utility.Log;

public class FormulaAVERAGE extends Formula {
	
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
		
		BigDecimal result = new BigDecimal("0");
		for(int i = 0 ; i < this.numbers.length ; i++) {
			result = result.add(new BigDecimal(this.calPostfix(this.convertToPostfix(this.numbers[i]))));
		}
		
		result = result.divide(new BigDecimal(this.numbers.length));
		
		Log.d("r " + result);
		return result.toString();
	}
}
