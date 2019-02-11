package tw.com.dh.excel;

import tw.com.dh.utility.Log;
import tw.com.dhl.operator.Addtion;
import tw.com.dhl.operator.Division;
import tw.com.dhl.operator.Number;

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
		
		String result = "0";
		
		if (statement.indexOf(":") >= 0) {
			this.numbers = this.splitComman(statement, ":");
			
			if (this.numbers.length == 2) {
				int [] sindex = this.excelData.getColumnIndex(numbers[0]);
				int [] eindex = this.excelData.getColumnIndex(numbers[1]);
				int count = 0;
				
				for (int i = sindex[0] ; i <= eindex[0]; i++) {
					for (int j = sindex[1] ; j <= eindex[1]; j++) {
						if (i < 0 || j < 0) continue;
						String value = this.excelData.table[i][j];
						result = new Addtion(new Number(result), new Number(value.isEmpty() ? "0" : value)).interpret();
						count += 1;
						Log.d("AVERAGE " + i + "," + j + " = " + value);
					}
				}
				
				result = new Division(new Number(result), new Number("" + count)).interpret();
			}
		}else if (statement.indexOf(",") >= 0) {
			this.numbers = this.splitComman(statement, ",");
			for(int i = 0 ; i < this.numbers.length ; i++) {
				result = new Addtion(new Number(result), new Number(this.calPostfix(this.convertToPostfix(this.numbers[i])))).interpret();
				Log.d("AVERAGE " + i + " = " + result);
			}
			
			result = new Division(new Number(result), new Number("" + this.numbers.length)).interpret();
		}
		
		Log.d("r " + result);
		return result;
	}
}
