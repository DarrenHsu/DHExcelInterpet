package tw.com.skl;

import java.util.HashMap;

import tw.com.skl.excel.ExcelData;
import tw.com.skl.utility.Log;
import tw.com.skl.excel.Calculator;

public class TestClass {
	Calculator calculator;
	ExcelData excelData;
	 
	public static void main(String[] args) {
		TestClass test = new TestClass();
		
		test.calculator.parseStatement("MOD(10, 3)");
		
		if (true) {
			return;
		}
		
		int row = test.excelData.currentRow;
		int col = 5;
		
		String statement = test.excelData.statements[col];
		statement = test.replaceNumber(statement);
		
		test.excelData.currentCol = col;
		
		for (int i = row ; i < test.excelData.rowCount ; i++) {
			test.excelData.currentRow = i;
			Log.d("row " + test.excelData.currentRow);
			String result = test.calculator.parseStatement(statement);
			test.excelData.table[test.excelData.currentCol][test.excelData.currentRow] = result;
		}
		
		System.out.println("\n<================ table ====================>");
		for(int r = 0 ; r < test.excelData.rowCount ; r++) {
			for(int c = 0 ; c < test.excelData.colCount ; c++) {
				System.out.print(test.excelData.table[c][r] + (c == test.excelData.colCount - 1 ? "" : ",\t"));
			}
			System.out.print("\n");
		}
		System.out.println("<================ table ====================>");
	}
	
	public TestClass() {
		HashMap<String, String> map = new HashMap<>();
		map.put("投保年齡", "1");
		map.put("年金給付年齡", "70");
		map.put("保險費", "300000");
		map.put("繳交年限", "5000");
		map.put("繳法別", "12");
		map.put("定期定額續期保險費", "1");
		map.put("費用表!$D$2", "500000");
		map.put("費用表!$E$2", "0");
		map.put("費用表!$D$3", "5000000");
		map.put("費用表!$E$3", "0");
		map.put("費用表!$E$4", "0");
		map.put("費用表!$B$3", "3000000");
		map.put("費用表!$B$2", "0");
		map.put("費用表!$B$4", "0.001");
		map.put("提領年度", "1");
		map.put("結束提領年度", "30");
		map.put("部分提領", "5000");
		map.put("費用表!$H$1", "5");
		map.put("加值給付開始年度", "5");
		
		String[] statements = new String[]{
				"IF(B3<>\"\",INT((B3-1)/12)+1,\"\")",
				"IF(ROW()=3,1,IF(OR(B2=\"\",B2>12*(年金給付年齡-投保年齡)),\"\",B2+1))",
				"IF(A3<>\"\",投保年齡+A3-1,\"\")",
				"IF(B3<>\"\",IF(B3=1,保險費,IF(B3<=13,IF(AND(MOD(B3,(12/繳法別))=1,B3<=繳交年限*12),定期定額續期保險費,0),+IF(AND(MOD(B3,(12/繳法別))=1,B3<=繳交年限*12),定期定額續期保險費,0))),\"\")",
				"IF(B3<>\"\",IF(OR(F2+D3>60000000,AND(B3<>1,Q2=0)),0,D3),\"\")",
				"IF(B3<>\"\",SUM($E$3:E3),\"\")",
				"IF(B3<>\"\",G2+E3*IF(E3<費用表!$D$2,費用表!$E$2,IF(E3<費用表!$D$3,費用表!$E$3,費用表!$E$4)),\"\")",
				"IF(B3<>\"\",ROUND(E3-(G3-G2),0),\"\")",
				"IF(B3<>\"\",SUM($H$3:H3),\"\")",
				"IF(B3<>\"\",IF(AND(B3<>1,Q2=0),0,IF(B3=1,H3,IF(OR(Q2<0,J2=0),0,Q2+H3+T3))),\"\")",
				"IF(B3<>\"\",IF(AND(B3<>1,Q2=0),0,IF(I3-SUM($O$2:O2)<費用表!$B$3,費用表!$B$2,0)),\"\")",
				"IF(B3<>\"\",L2+K3,\"\")",
				"IF(A3<=5,IF(B3<>\"\",IF(AND(B3<>1,Q2=0),0,J3*費用表!$B$4),\"\"),0)",
				"IF(B3<>\"\",N2+M3,\"\")",
				"IF(B3<>\"\",IF(AND(B3<>1,Q2=0),0,IF(AND(A3>=提領年度,A3<=結束提領年度,B3=12*A3,(J3-$K3)*(1+R$1)^(1/12)-部分提領>0),部分提領,0)),\"\")",
				"IF(B3<>\"\",IF(A3<=費用表!$H$1,VLOOKUP(A3,費用表!$G$2:$H$20,2,FALSE)*O3,0),\"\")",
				"IF(B3<>\"\",IF((J3-$K3-M3)*(1+R$1)^(1/12)<0,0,(J3-$K3-M3)*(1+R$1)^(1/12)-O3),\"\")",
				"IF(B3<>\"\",IF(A3<=費用表!$H$1,VLOOKUP(A3,費用表!$G$2:$H$20,2,FALSE)*ROUND(Q3,0),0),\"\")",
				"IF(B3<>\"\",ROUND(Q3,0)-R3,\"\")",
				"IF(AND(A3>=加值給付開始年度,C3<年金給付年齡,MOD(B3,12)=1),AVERAGE(#REF!)*VLOOKUP(IF(A3<11,A3,11),費用表!$J$3:$K$13,2,0),0)",
				"IF(B3<>\"\",IF((J3-$K3-M3)*(1+R$1)^(1/12)<0,0,(J3-$K3-M3)*(1+R$1)^(1/12)-O3),\"\")"
		};
		
		int cols = statements.length;
		int rows = 1 * 12 + 1 + 2;
		
		String[][] table = new String[cols][rows];
		
		this.excelData = new ExcelData(table, statements, map, 0, 2);
		this.calculator = new Calculator(this.excelData);
	}
	
	public String replaceNumber(String statement) {
		for(String key : this.excelData.map.keySet()) {
			statement = statement.replaceAll(key, this.excelData.map.get(key));
		}
		return statement;
	}
}
