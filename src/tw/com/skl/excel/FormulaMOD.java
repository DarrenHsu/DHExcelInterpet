package tw.com.skl.excel;

import java.math.BigDecimal;

import tw.com.skl.operator.Number;
import tw.com.skl.operator.Remainder;
import tw.com.skl.utility.Log;

public class FormulaMOD extends Formula {
	
	public static final String FORMULA_REGEX = "(MOD|mod)\\(";
	public static final String NAME = "MOD";
	
	public FormulaMOD(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		BigDecimal result = null;
		if (statements.length == 2) {
			BigDecimal n = new BigDecimal(this.calPostfix(this.convertToPostfix(statements[0])));
			BigDecimal d = new BigDecimal(this.calPostfix(this.convertToPostfix(statements[1])));
			result = n.remainder(d);
		}
		Log.d("r " + result);
		return result.toString();
	}
}
