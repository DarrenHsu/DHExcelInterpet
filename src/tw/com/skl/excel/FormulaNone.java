package tw.com.skl.excel;

import tw.com.skl.utility.Log;

public class FormulaNone extends Formula {
	
	public FormulaNone(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
			
		String result = this.calPostfix(this.convertToPostfix(statement));

		Log.d("r " + result);
		return result;
	}
}
