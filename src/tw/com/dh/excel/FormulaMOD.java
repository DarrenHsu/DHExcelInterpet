package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;
import tw.com.dhl.operator.Remainder;
import tw.com.dhl.operator.Number;

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
		BigDecimal result = BigDecimal.ZERO;
		if (statements.length == 2) {
			result = new Remainder(
					new Number(this.calPostfix(this.convertToPostfix(statements[0]))),
					new Number(this.calPostfix(this.convertToPostfix(statements[1])))
					).interpret();
		}
		Log.d("r " + result);
		return result.toString();
	}
}
