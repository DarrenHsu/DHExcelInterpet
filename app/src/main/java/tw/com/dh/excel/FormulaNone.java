package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaNone extends Formula {
	
	public FormulaNone(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public BigDecimal interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
			
		BigDecimal result = this.calPostfix(this.convertToPostfix(statement));

		Log.d("r " + result);
		return result;
	}
}
