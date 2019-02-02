package tw.com.dh.excel;

import java.math.BigDecimal;
import java.util.ArrayList;

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
		this.numbers = this.splitComman(statement, ":");
		
		int[] index1 = this.excelData.getColumnIndex(this.numbers[0]);
		int[] index2 = this.excelData.getColumnIndex(this.numbers[1]);

		BigDecimal number = new BigDecimal(0);	
		for(int i = index1[0] ; i <= index2[0] ; i++) {
			for(int j = index1[1] ; j <= index2[1] ; j++) {
				Log.d("SUM " + i + "," + j + " = " + this.excelData.table[i][j]);
				number = number.add(new BigDecimal(this.excelData.table[i][j].isEmpty() ? "0" : this.excelData.table[i][j]));
			}
		}

		Log.d("r " + number.toString());
		return number.toString();
	}
}