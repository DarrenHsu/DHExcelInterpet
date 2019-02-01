package tw.com.dh;

import java.util.HashMap;

import tw.com.dh.excel.Calculator;
import tw.com.dh.excel.ExcelData;
import tw.com.dh.utility.Log;

public class TestClass {
	Calculator calculator;
	ExcelData excelData;
	HashMap<String, String> map;
	 
	public static void main(String[] args) {
		TestClass test = new TestClass();
		
//		test.calculator.parseStatement("0.000000000012 * 0.0000055");
//		
//		if (true) {
//			return;
//		}
		
		int[] cols = new int[]{1, 0, 2, 3, 4, 5, 6, 7, 8, 9, 19};
		int row = test.excelData.currentRow;
		
		for (int c = 0 ; c < cols.length ; c++) {
			String statement = test.excelData.statements[cols[c]];
			statement = test.replaceNumber(statement);
			test.excelData.currentCol = cols[c];
			for (int i = row ; i < test.excelData.rowCount ; i++) {
				test.excelData.currentRow = i;
				Log.d("index " + test.excelData.currentCol + "," + test.excelData.currentRow);
				String result = test.calculator.parseStatement(statement);
				test.excelData.table[test.excelData.currentCol][test.excelData.currentRow] = result;
			}
		}
		
		Log.d("\n<================ table ====================>");
		for(int r = 0 ; r < test.excelData.rowCount ; r++) {
			String s = "";
			for(int c = 0 ; c < test.excelData.colCount ; c++) {
				s += String.format("%10s", test.excelData.table[c][r]) + (c == test.excelData.colCount - 1 ? "" : ",");
			}
			Log.d(s);
		}
		Log.d("<================ table ====================>");
	}
	
	public TestClass() {
		map = new HashMap<>();
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
				/* A */ "IF(B3<>\"\",INT((B3-1)/12)+1,\"\")",
				/* B */ "IF(ROW()=3,1,IF(OR(B2=\"\",B2>12*(年金給付年齡-投保年齡)),\"\",B2+1))",
				/* C */ "IF(A3<>\"\",投保年齡+A3-1,\"\")",
				/* D */ "IF(B3<>\"\",IF(B3=1,保險費,IF(B3<=13,IF(AND(MOD(B3,(12/繳法別))=1,B3<=繳交年限*12),定期定額續期保險費,0),+IF(AND(MOD(B3,(12/繳法別))=1,B3<=繳交年限*12),定期定額續期保險費,0))),\"\")",
				/* E */	"IF(B3<>\"\",IF(OR(F2+D3>60000000,AND(B3<>1,Q2=0)),0,D3),\"\")",
				/* F */ "IF(B3<>\"\",SUM($E$3:E3),\"\")",
				/* G */ "IF(B3<>\"\",G2+E3*IF(E3<費用表!$D$2,費用表!$E$2,IF(E3<費用表!$D$3,費用表!$E$3,費用表!$E$4)),\"\")",
				/* H */ "IF(B3<>\"\",ROUND(E3-(G3-G2),0),\"\")",
				/* I */ "IF(B3<>\"\",SUM($H$3:H3),\"\")",
				/* J */ "IF(B3<>\"\",IF(AND(B3<>1,Q2=0),0,IF(B3=1,H3,IF(OR(Q2<0,J2=0),0,Q2+H3+T3))),\"\")",
				/* K */ "IF(B3<>\"\",IF(AND(B3<>1,Q2=0),0,IF(I3-SUM($O$2:O2)<費用表!$B$3,費用表!$B$2,0)),\"\")",
				/* L */ "IF(B3<>\"\",L2+K3,\"\")",
				/* M */ "IF(A3<=5,IF(B3<>\"\",IF(AND(B3<>1,Q2=0),0,J3*費用表!$B$4),\"\"),0)",
				/* N */ "IF(B3<>\"\",N2+M3,\"\")",
				/* O */ "IF(B3<>\"\",IF(AND(B3<>1,Q2=0),0,IF(AND(A3>=提領年度,A3<=結束提領年度,B3=12*A3,(J3-$K3)*(1+R$1)^(1/12)-部分提領>0),部分提領,0)),\"\")",
				/* P */ "IF(B3<>\"\",IF(A3<=費用表!$H$1,VLOOKUP(A3,費用表!$G$2:$H$20,2,FALSE)*O3,0),\"\")",
				/* Q */ "IF(B3<>\"\",IF((J3-$K3-M3)*(1+R$1)^(1/12)<0,0,(J3-$K3-M3)*(1+R$1)^(1/12)-O3),\"\")",
				/* R */ "IF(B3<>\"\",IF(A3<=費用表!$H$1,VLOOKUP(A3,費用表!$G$2:$H$20,2,FALSE)*ROUND(Q3,0),0),\"\")",
				/* S */ "IF(B3<>\"\",ROUND(Q3,0)-R3,\"\")",
				/* T */ "IF(AND(A3>=加值給付開始年度,C3<年金給付年齡,MOD(B3,12)=1),AVERAGE(J-8:J3)*VLOOKUP(IF(A3<11,A3,11),費用表!$J$3:$K$13,2,0),0)",
				/* U */ "IF(B3<>\"\",IF((J3-$K3-M3)*(1+R$1)^(1/12)<0,0,(J3-$K3-M3)*(1+R$1)^(1/12)-O3),\"\")"
		};
		
		int cols = statements.length;
		int rows = 1 * 12 + 1 + 2;
		rows = 51;
		
		String name1 = "月化試算表(+1)";
		String[][] table1 = new String[cols][rows];
		
		String name2 = "費用表";
		String[][] table2 = new String[11][13];
		table2[6][1] = "1";
		table2[6][2] = "2";
		table2[6][3] = "3";
		table2[6][5] = "4";
		table2[6][2] = "5";
		
		table2[7][1] = "7";
		table2[7][2] = "6";
		table2[7][3] = "5";
		table2[7][5] = "3";
		table2[7][2] = "2";
		
		table2[9][2] = "1";
		table2[9][3] = "2";
		table2[9][4] = "3";
		table2[9][5] = "4";
		table2[9][6] = "5";
		table2[9][7] = "6";
		table2[9][8] = "7";
		table2[9][9] = "8";
		table2[9][10] = "9";
		table2[9][11] = "10";
		table2[9][12] = "11";
		
		table2[10][2] = "0";
		table2[10][3] = "0";
		table2[10][4] = "0";
		table2[10][5] = "0.6";
		table2[10][6] = "0.6";
		table2[10][7] = "0.6";
		table2[10][8] = "0.6";
		table2[10][9] = "0.6";
		table2[10][10] = "0.6";
		table2[10][11] = "0.6";
		table2[10][12] = "0.6";	
		
		HashMap<String, String[][]> sheet = new HashMap<>();
		sheet.put(name1, table1);
		sheet.put(name2, table2);
		
		this.excelData = new ExcelData(sheet, name1, statements, 0, 2);
		this.calculator = new Calculator(this.excelData);
	}
	
	public String replaceNumber(String statement) {
		for(String key : this.map.keySet()) 
			statement = statement.replace(key, this.map.get(key));
		return statement;
	}
}
