package tw.com.skl.excel;

import tw.com.skl.utility.Log;

public class FormulaMOD extends Formula {
	
	public static final String FORMULA_REGEX = "(INT|int)\\(";
	public static final String NAME = "INT";
	
	private String number;
	
	public FormulaMOD(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		this.number = statement;
		
		this.calPostfix(this.convertToPostfix(this.number));
		
		return NAME + "_Result";
	}
}
