package tw.com.dh.excel;

import tw.com.dhl.operator.Addtion;
import tw.com.dhl.operator.Number;

import tw.com.dh.utility.Log;

public class FormulaSUM extends Formula {
	
	public static final String FORMULA_REGEX = "(SUM|sum)\\(";
	public static final String NAME = "SUM";
	
	private String[] numbers;
	
	public FormulaSUM(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String result = "0";
		
		if (statement.indexOf(":") >= 0) {
			this.numbers = this.splitComman(statement, ":");
			
			if (this.numbers.length == 2) {
				int[] index1 = this.excelData.getColumnIndex(this.numbers[0]);
				int[] index2 = this.excelData.getColumnIndex(this.numbers[1]);

				for(int i = index1[0] ; i <= index2[0] ; i++) {
					for(int j = index1[1] ; j <= index2[1] ; j++) {
						Log.d("SUM " + i + "," + j + " = " + this.excelData.table[i][j]);
						result = new Addtion(new Number(result), new Number(this.excelData.table[i][j].isEmpty() ? "0" : this.excelData.table[i][j])).interpret();
					}
				}
			}
		}else if(statement.indexOf(",") >= 0) {
			this.numbers = this.splitComman(statement, ",");
			for(int i = 0 ; i < this.numbers.length ; i++) {
				result = new Addtion(new Number(result), new Number(this.calPostfix(this.convertToPostfix(this.numbers[i])))).interpret();
				Log.d("SUM " + i + " = " + result);
			}
		}
		
		Log.d("r " + result);
		return result;
	}
}