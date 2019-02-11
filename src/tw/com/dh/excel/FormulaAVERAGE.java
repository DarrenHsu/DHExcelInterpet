package tw.com.dh.excel;

import java.math.BigDecimal;
import java.math.MathContext;

import tw.com.dh.utility.Log;
import tw.com.dhl.operator.Division;
import tw.com.dhl.operator.Number;;

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
		
		String r = null;
		BigDecimal result = new BigDecimal("0");
		
		if (this.numbers.length == 1 && numbers[0].indexOf(":") >= 0) {
			String s = numbers[0].substring(0, numbers[0].indexOf(":"));
			String e = numbers[0].substring(numbers[0].indexOf(":") + 1);
			int [] sindex = this.excelData.getColumnIndex(s);
			int [] eindex = this.excelData.getColumnIndex(e);
			int count = 0;
			
			for (int i = sindex[0] ; i <= eindex[0]; i++) {
				for (int j = sindex[1] ; j <= eindex[1]; j++) {
					if (i < 0 || j < 0) continue;
					String value = this.excelData.table[i][j];
					result = result.add(new BigDecimal(value.isEmpty() ? "0" : value));
					count += 1;
					Log.d("AVERAGE " + i + "," + j + " = " + value);
				}
			}
			
			r = new Division(new Number(result.toString()), new Number("" + count)).interpret();
		}else {
			for(int i = 0 ; i < this.numbers.length ; i++) {
				if (numbers[i].equals("#REF!")) 
					return "0";
				result = result.add(new BigDecimal(this.calPostfix(this.convertToPostfix(this.numbers[i]))));
			}
			
			r = new Division(new Number(result.toString()), new Number("" + this.numbers.length)).interpret();
		}
		
		Log.d("r " + r);
		return r;
	}
}
