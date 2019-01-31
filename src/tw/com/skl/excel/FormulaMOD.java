package tw.com.skl.excel;

import tw.com.skl.operator.Number;
import tw.com.skl.operator.Remainder;
import tw.com.skl.utility.Log;

public class FormulaMOD extends Formula {
	
	public static final String FORMULA_REGEX = "(MOD|mod)\\(";
	public static final String NAME = "MOD";
	
	private String number;
	private String divisor;
	
	public FormulaMOD(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		String result = null;
		if (statements.length == 2) {
			this.number = this.calPostfix(this.convertToPostfix(statements[0]));
			this.divisor = this.calPostfix(this.convertToPostfix(statements[1]));
			result = new Remainder(new Number(this.number), new Number(this.divisor)).interpret();
		}
		Log.d("r " + result);
		return result;
	}
}
