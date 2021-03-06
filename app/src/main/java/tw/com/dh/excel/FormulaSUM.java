package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;
import tw.com.dhl.operator.Addtion;
import tw.com.dhl.operator.Number;

public class FormulaSUM extends Formula {
	
	public static final String FORMULA_REGEX = "(SUM|sum)\\(";
	public static final String NAME = "SUM";
	
	private String[] numbers;
	
	public FormulaSUM(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public BigDecimal interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		BigDecimal result = BigDecimal.ZERO;
		
		if(statement.indexOf(",") >= 0) {
			this.numbers = this.splitComman(statement, ",");
			for(int i = 0 ; i < this.numbers.length ; i++) {
				if (this.numbers[i].indexOf(":") >= 0) {
					result = new Addtion(new Number(result), new Number(this.process(this.numbers[i]))).interpret();
				}else {
					result = new Addtion(new Number(result), new Number(this.calPostfix(this.convertToPostfix(this.numbers[i])))).interpret();
				}
			}
		}else {
			result = this.process(statement);			
		}
		
		Log.d("r " + result);
		return result;
	}
	
	private BigDecimal process(String statement) {
		BigDecimal result = BigDecimal.ZERO;
		
		if (statement.indexOf(":") >= 0) {
			String[] numbers = this.splitComman(statement, ":");
			
			if (numbers.length == 2) {
				int[] index1 = this.excelData.getColumnIndex(numbers[0]);
				int[] index2 = this.excelData.getColumnIndex(numbers[1]);

				for(int i = index1[0] ; i <= index2[0] ; i++) {
					for(int j = index1[1] ; j <= index2[1] ; j++) {
						result = new Addtion(new Number(result), new Number(this.excelData.table[i][j])).interpret();
					}
				}
			}
		}
		return result;
	}
}