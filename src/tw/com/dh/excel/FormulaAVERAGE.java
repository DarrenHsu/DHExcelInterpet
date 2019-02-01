package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

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
		
		BigDecimal result = BigDecimal.ZERO;
		
		if (this.numbers.length == 1 && numbers[0].indexOf(":") >= 0) {
			String s = numbers[0].substring(0, numbers[0].indexOf(":"));
			String e = numbers[0].substring(numbers[0].indexOf(":") + 1);
			int [] sindex = this.excelData.getColumnIndex(s);
			int [] eindex = this.excelData.getColumnIndex(e);
			
			for (int i = sindex[0] ; i <= eindex[0]; i++) {
				for (int j = sindex[1] ; j <= eindex[1]; j++) {
					if (i < 0 || j < 0) continue;
					String value = this.excelData.table[i][j];
					result = result.add(new BigDecimal(value.isEmpty() ? "0" : ""));
				}
			}
			
			result = result.divide(new BigDecimal(this.numbers.length));
		}else {
			for(int i = 0 ; i < this.numbers.length ; i++) {
				if (numbers[i].equals("#REF!")) 
					return "0";
				result = result.add(new BigDecimal(this.calPostfix(this.convertToPostfix(this.numbers[i]))));
			}
			
			result = result.divide(new BigDecimal(this.numbers.length));
		}
		
		Log.d("r " + result);
		return result.toString();
	}
}
