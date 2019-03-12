package tw.com.dh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TreeMap;

import tw.com.dh.excel.Calculator;
import tw.com.dh.excel.ExcelData;
import tw.com.dh.utility.Log;

public class TestClass2 {
	
	Calculator calculator;
	ExcelData excelData;
	TreeMap<String, String> map;
	 
	public static void main(String[] args) {
		TestClass2 test = new TestClass2();

		BigDecimal testResult = test.calculator.parseStatement("1+2");
		Log.e(testResult.toString());
		if (true) return;
		
		Log.e("start");
		
		int[] cols = new int[]{1, 0, 2, 3};
		int row = test.excelData.currentRow;
		
		for (int i = row ; i < test.excelData.rowCount ; i++) {
			for (int c = 0 ; c < cols.length ; c++) {
				String statement = test.excelData.statements[cols[c]];
				test.excelData.currentCol = cols[c];
				test.excelData.currentRow = i;
				Log.d("index " + test.excelData.currentCol + "," + test.excelData.currentRow);
				test.excelData.table[test.excelData.currentCol][test.excelData.currentRow] = test.calculator.parseStatement(statement);
			}
		}
		
		Log.i("\n<================ table ====================>");
		String h = "";
		for(int c = 0 ; c < test.excelData.colCount ; c++) {
			h += String.format("%6s", "" + c) + (c == test.excelData.colCount - 1 ? "" : ",");
		}
		Log.i(h);
		for(int r = 0 ; r < test.excelData.rowCount ; r++) {
			String s = "";
			for(int c = 0 ; c < test.excelData.colCount ; c++) {
				BigDecimal result = test.excelData.table[c][r];
				s += String.format("%6s", result.setScale(0, BigDecimal.ROUND_HALF_UP).toString()) + (c == test.excelData.colCount - 1 ? "" : ",");
			}
			Log.i(s);
		}
		Log.i("<================ table ====================>");
		
		Log.e("end");
	}
	
	public TestClass2() {
		map = new TreeMap<>();
		map.put("投保年齡", "30");
		map.put("基本資料輸入介面!$C$39", "0");
		map.put("基本資料輸入介面!$E$39", "0");
		map.put("基本保費繳交年限", "1");
		map.put("基本保費", "800");
		map.put("繳法別", "1");
		map.put("不定期增額保費", "9200");
		map.put("增額定期繳交年限", "0");
		map.put("定期增額保費", "0");
		map.put("弱體加費", "0");
		map.put("弱體加費", "0");
		map.put("保費費用率", "費用表!$D$2:$G$7");
		map.put("保額變更年度", "1");
		map.put("變更後保額", "5");
		map.put("保險金額", "5");
		map.put("性別", "1");
		map.put("費用表!$B$3", "80000");
		map.put("提領年度", "0");
		map.put("結束提領年度", "0");
		map.put("部分提領", "0");
		
		String[] statements = new String[]{
				/* A 0  */ "IF(B3<>\"\",INT((B3-1)/12)+1,\"\")",
				/* B 1  */ "IF(ROW()=3,1,IF(OR(B2=\"\",B2>12*(100-投保年齡)),\"\",B2+1))",
				/* C 2  */ "IF(A3<>\"\",投保年齡+A3-1,\"\")",
				/* D 3  */ "IF(B3<=基本資料輸入介面!$C$39*12+基本資料輸入介面!$E$39,1,IF(C3<41,1.3,IF(C3<71,1.15,1.01)))",
				/* E 4  */ "IF(G3=0,E2,IF(B3<=基本資料輸入介面!$C$39*12+基本資料輸入介面!$E$39,Q3/Q3,(T3*10000+Q3)/Q3))",
				/* F 5  */ "IF(AND(B3<>1,Z2=0),0,IF(B3=1,基本保費,IF(Z2<0,0,IF(AND(A3<=基本保費繳交年限,MOD(B3,12/繳法別)=1),基本保費,0))))",
				/* G 6  */ "IF(OR(IF(B3<基本資料輸入介面!$C$39*12+基本資料輸入介面!$E$39,L2+基本保費>30000000,L2+基本保費+S3*10000>60000000),AND(B3<>1,Z2=0)),0,F3)",
				/* H 7  */ "IF(AND(B3<>1,Z2=0),0,IF(AND(A3=1,B3=1),不定期增額保費,IF(A3<=增額定期繳交年限,IF(MOD(B3,12/繳法別)=1,定期增額保費,0),0)))",
				/* I 8  */ "IF(OR(IF(B3<基本資料輸入介面!$C$39*12+基本資料輸入介面!$E$39,L2+G3+定期增額保費>30000000,L2+G3+定期增額保費+S3*10000>60000000),AND(B3<>1,Z2=0)),0,IF(B3=1,H3,IF(AND(B3>=基本資料輸入介面!$C$39*12+基本資料輸入介面!$E$39,弱體加費=0),H3,IF(B3<基本資料輸入介面!$C$39*12+基本資料輸入介面!$E$39,H3,IF(AND(G3<>0,Z2+G3*(1-VLOOKUP(A3,保費費用率,2))+H3*(1-VLOOKUP(A3,保費費用率,3))<=S3*10000/(D3-1)),H3,0)))))",
				/* J 9  */ "SUM($G$3:G3)",
				/* K 10 */ "SUM($I$3:$I3)",
				/* L 11 */ "SUM($G$3:G3,$I$3:I3)",
				/* M 12 */ "IF(A3=1,M2+G3*VLOOKUP(A3,保費費用率,2),基本保費*繳法別*VLOOKUP(INT(J3/(基本保費*繳法別)),保費費用率,4)+(J3-基本保費*繳法別*INT(J3/(基本保費*繳法別)))*VLOOKUP(1+INT(J3/(基本保費*繳法別)),保費費用率,2))",
				/* N 13 */ "N2+I3*VLOOKUP(A3,保費費用率,3)",
				/* O 14 */ "ROUND(G3-(M3-M2)+I3-(N3-N2),0)",
				/* P 15 */ "SUM($O$3:O3)",
				/* Q 16 */ "IF(AND(B3<>1,Z2=0),0,IF(B3=1,ROUND(O3,2),IF(OR(Z2<0,Q2=0),0,ROUND(Z2+O3,2))))",
				/* R 17 */ "IF(D3=100%,0,IF(AND(B3<>1,Z2=0),0,IF(OR(B3=基本資料輸入介面!$C$39*12+基本資料輸入介面!$E$39+1,B3=1),S3,IF(OR(O3<>0,B3=(保額變更年度-1)*12+1),AG3,AF2))))",
				/* S 18 */ "IF(AND(B3<>1,Z2=0),0,IF(A3>=保額變更年度,變更後保額,保險金額))",
				/* T 19 */ "IF(AND(B3<>1,Z2=0),0,IF(B3<=(基本資料輸入介面!$C$39*12+基本資料輸入介面!$E$39),0,IF(弱體加費=0,MAX((MAX(R3,S3)*10000-Q3)/10000,0),MAX((S3*10000-Q3)/10000,0))))",
				/* U 20 */ "ROUND(T3*VLOOKUP(C3,費用表!$A$12:$E$122,IF(性別=1,3,5))*(1+弱體加費),2)",
				/* V 21 */ "V2+U3",
				/* W 22 */ "IF(AND(B3<>1,Z2=0),0,IF((L3-AD3)>=費用表!$B$3,0,費用表!$B$2))",
				/* X 23 */ "X2+W3",
				/* Y 24 */ "IF(AND(B3<>1,Z2=0),0,IF(AND(A3>=提領年度,A3<=結束提領年度,B3=12*A3,(Q3-U3-$W3)*(1+AA$1)^(1/12)-部分提領>0),部分提領,0))",
				/* Z 25 */ "IF((Q3-U3-$W3-AB3)*(1+AA$1)^(1/12)<0,0,(Q3-U3-$W3-AB3)*(1+AA$1)^(1/12)-Y3)",
				/* AA26 */ "IF(Z3>0,Q3+T3*10000,0)",
				/* AB27 */ "IF(AND(B3<>1,Z2=0),0,VLOOKUP(A3,費用表!$J$2:$K$100,2)*Q3)",
				/* AC28 */ "AB3+AC2",
				/* AD29 */ "Y3+AD2",
				/* AE30 */ "IF(AND(B3<>1,Z2=0),0,Y3*VLOOKUP(A3,費用表!M$2:N$100,2))",
				/* AF31 */ "ROUNDUP(IF(AND(T3>0,Y3>0),(R3*10000-Y3)/10000,IF(AND(T3=0,Y3>0),MIN(Z3,S3*10000)/10000,R3)),1)",
				/* AG32 */ "ROUNDUP((O3)*D3/10000,1)"
		};
		
		ArrayList<String> ss = new ArrayList<>();
		for (String s : statements) {
			ss.add(this.replaceNumber(s));
		}
		
		BigDecimal[][] rate = new BigDecimal[14][123];
		rate[3][1] = new BigDecimal(1);
		rate[3][2] = new BigDecimal(2);
		rate[3][3] = new BigDecimal(3);
		rate[3][4] = new BigDecimal(4);
		rate[3][5] = new BigDecimal(5);
		rate[3][6] = new BigDecimal(6);
		
		rate[0][11] = new BigDecimal(0); rate[1][11] = new BigDecimal(0.000522); rate[2][11] = new BigDecimal(0.4783); rate[3][11] = new BigDecimal(0.000389); rate[4][11] = new BigDecimal(0.3567); 
		rate[0][12] = new BigDecimal(1); rate[1][12] = new BigDecimal(0.000384); rate[2][12] = new BigDecimal(0.3517); rate[3][12] = new BigDecimal(0.000304); rate[4][12] = new BigDecimal(0.2783); 
		rate[0][13] = new BigDecimal(2); rate[1][13] = new BigDecimal(0.000277); rate[2][13] = new BigDecimal(0.2542); rate[3][13] = new BigDecimal(0.000218); rate[4][13] = new BigDecimal(0.2); 
		rate[0][14] = new BigDecimal(3); rate[1][14] = new BigDecimal(0.000215); rate[2][14] = new BigDecimal(0.1975); rate[3][14] = new BigDecimal(0.000183); rate[4][14] = new BigDecimal(0.1675); 
		rate[0][15] = new BigDecimal(4); rate[1][15] = new BigDecimal(0.000181); rate[2][15] = new BigDecimal(0.1658); rate[3][15] = new BigDecimal(0.000158); rate[4][15] = new BigDecimal(0.145); 
		rate[0][16] = new BigDecimal(5); rate[1][16] = new BigDecimal(0.000166); rate[2][16] = new BigDecimal(0.1525); rate[3][16] = new BigDecimal(0.000138); rate[4][16] = new BigDecimal(0.1267); 
		rate[0][17] = new BigDecimal(6); rate[1][17] = new BigDecimal(0.000149); rate[2][17] = new BigDecimal(0.1367); rate[3][17] = new BigDecimal(0.000121); rate[4][17] = new BigDecimal(0.1108); 
		rate[0][18] = new BigDecimal(7); rate[1][18] = new BigDecimal(0.000139); rate[2][18] = new BigDecimal(0.1275); rate[3][18] = new BigDecimal(0.00011); rate[4][18] = new BigDecimal(0.1008); 
		rate[0][19] = new BigDecimal(8); rate[1][19] = new BigDecimal(0.000134); rate[2][19] = new BigDecimal(0.1225); rate[3][19] = new BigDecimal(0.000103); rate[4][19] = new BigDecimal(0.0942); 
		rate[0][20] = new BigDecimal(9); rate[1][20] = new BigDecimal(0.000133); rate[2][20] = new BigDecimal(0.1217); rate[3][20] = new BigDecimal(0.000101); rate[4][20] = new BigDecimal(0.0925); 
		rate[0][21] = new BigDecimal(10); rate[1][21] = new BigDecimal(0.000129); rate[2][21] = new BigDecimal(0.1183); rate[3][21] = new BigDecimal(0.000103); rate[4][21] = new BigDecimal(0.0942); 
		rate[0][22] = new BigDecimal(11); rate[1][22] = new BigDecimal(0.000131); rate[2][22] = new BigDecimal(0.12); rate[3][22] = new BigDecimal(0.00011); rate[4][22] = new BigDecimal(0.1008); 
		rate[0][23] = new BigDecimal(12); rate[1][23] = new BigDecimal(0.000153); rate[2][23] = new BigDecimal(0.14); rate[3][23] = new BigDecimal(0.000123); rate[4][23] = new BigDecimal(0.1125); 
		rate[0][24] = new BigDecimal(13); rate[1][24] = new BigDecimal(0.000196); rate[2][24] = new BigDecimal(0.18); rate[3][24] = new BigDecimal(0.000141); rate[4][24] = new BigDecimal(0.1292); 
		rate[0][25] = new BigDecimal(14); rate[1][25] = new BigDecimal(0.000255); rate[2][25] = new BigDecimal(0.2342); rate[3][25] = new BigDecimal(0.000159); rate[4][25] = new BigDecimal(0.1458); 
		rate[0][26] = new BigDecimal(15); rate[1][26] = new BigDecimal(0.000344); rate[2][26] = new BigDecimal(0.315); rate[3][26] = new BigDecimal(0.000181); rate[4][26] = new BigDecimal(0.1658); 
		rate[0][27] = new BigDecimal(16); rate[1][27] = new BigDecimal(0.000455); rate[2][27] = new BigDecimal(0.4175); rate[3][27] = new BigDecimal(0.000206); rate[4][27] = new BigDecimal(0.1892); 
		rate[0][28] = new BigDecimal(17); rate[1][28] = new BigDecimal(0.00054); rate[2][28] = new BigDecimal(0.495); rate[3][28] = new BigDecimal(0.000232); rate[4][28] = new BigDecimal(0.2125); 
		rate[0][29] = new BigDecimal(18); rate[1][29] = new BigDecimal(0.000584); rate[2][29] = new BigDecimal(0.535); rate[3][29] = new BigDecimal(0.000243); rate[4][29] = new BigDecimal(0.2225); 
		rate[0][30] = new BigDecimal(19); rate[1][30] = new BigDecimal(0.000607); rate[2][30] = new BigDecimal(0.5567); rate[3][30] = new BigDecimal(0.000249); rate[4][30] = new BigDecimal(0.2283); 
		rate[0][31] = new BigDecimal(20); rate[1][31] = new BigDecimal(0.000624); rate[2][31] = new BigDecimal(0.5717); rate[3][31] = new BigDecimal(0.000253); rate[4][31] = new BigDecimal(0.2317); 
		rate[0][32] = new BigDecimal(21); rate[1][32] = new BigDecimal(0.000641); rate[2][32] = new BigDecimal(0.5875); rate[3][32] = new BigDecimal(0.000259); rate[4][32] = new BigDecimal(0.2375); 
		rate[0][33] = new BigDecimal(22); rate[1][33] = new BigDecimal(0.000668); rate[2][33] = new BigDecimal(0.6125); rate[3][33] = new BigDecimal(0.000273); rate[4][33] = new BigDecimal(0.25); 
		rate[0][34] = new BigDecimal(23); rate[1][34] = new BigDecimal(0.00071); rate[2][34] = new BigDecimal(0.6508); rate[3][34] = new BigDecimal(0.000295); rate[4][34] = new BigDecimal(0.2708); 
		rate[0][35] = new BigDecimal(24); rate[1][35] = new BigDecimal(0.000762); rate[2][35] = new BigDecimal(0.6983); rate[3][35] = new BigDecimal(0.000323); rate[4][35] = new BigDecimal(0.2958); 
		rate[0][36] = new BigDecimal(25); rate[1][36] = new BigDecimal(0.000821); rate[2][36] = new BigDecimal(0.7525); rate[3][36] = new BigDecimal(0.000356); rate[4][36] = new BigDecimal(0.3267); 
		rate[0][37] = new BigDecimal(26); rate[1][37] = new BigDecimal(0.000885); rate[2][37] = new BigDecimal(0.8117); rate[3][37] = new BigDecimal(0.000367); rate[4][37] = new BigDecimal(0.3367); 
		rate[0][38] = new BigDecimal(27); rate[1][38] = new BigDecimal(0.000926); rate[2][38] = new BigDecimal(0.8492); rate[3][38] = new BigDecimal(0.000373); rate[4][38] = new BigDecimal(0.3417); 
		rate[0][39] = new BigDecimal(28); rate[1][39] = new BigDecimal(0.000965); rate[2][39] = new BigDecimal(0.885); rate[3][39] = new BigDecimal(0.00038); rate[4][39] = new BigDecimal(0.3483); 
		rate[0][40] = new BigDecimal(29); rate[1][40] = new BigDecimal(0.001008); rate[2][40] = new BigDecimal(0.9242); rate[3][40] = new BigDecimal(0.00039); rate[4][40] = new BigDecimal(0.3575); 
		rate[0][41] = new BigDecimal(30); rate[1][41] = new BigDecimal(0.001061); rate[2][41] = new BigDecimal(0.9725); rate[3][41] = new BigDecimal(0.000401); rate[4][41] = new BigDecimal(0.3675); 
		rate[0][42] = new BigDecimal(31); rate[1][42] = new BigDecimal(0.001127); rate[2][42] = new BigDecimal(1.0333); rate[3][42] = new BigDecimal(0.000415); rate[4][42] = new BigDecimal(0.3808); 
		rate[0][43] = new BigDecimal(32); rate[1][43] = new BigDecimal(0.001209); rate[2][43] = new BigDecimal(1.1083); rate[3][43] = new BigDecimal(0.00044); rate[4][43] = new BigDecimal(0.4033); 
		rate[0][44] = new BigDecimal(33); rate[1][44] = new BigDecimal(0.001305); rate[2][44] = new BigDecimal(1.1967); rate[3][44] = new BigDecimal(0.000481); rate[4][44] = new BigDecimal(0.4408); 
		rate[0][45] = new BigDecimal(34); rate[1][45] = new BigDecimal(0.001413); rate[2][45] = new BigDecimal(1.295); rate[3][45] = new BigDecimal(0.000523); rate[4][45] = new BigDecimal(0.4792); 
		rate[0][46] = new BigDecimal(35); rate[1][46] = new BigDecimal(0.001532); rate[2][46] = new BigDecimal(1.4042); rate[3][46] = new BigDecimal(0.000559); rate[4][46] = new BigDecimal(0.5125); 
		rate[0][47] = new BigDecimal(36); rate[1][47] = new BigDecimal(0.001661); rate[2][47] = new BigDecimal(1.5225); rate[3][47] = new BigDecimal(0.000594); rate[4][47] = new BigDecimal(0.5442); 
		rate[0][48] = new BigDecimal(37); rate[1][48] = new BigDecimal(0.001804); rate[2][48] = new BigDecimal(1.6533); rate[3][48] = new BigDecimal(0.000635); rate[4][48] = new BigDecimal(0.5825); 
		rate[0][49] = new BigDecimal(38); rate[1][49] = new BigDecimal(0.001949); rate[2][49] = new BigDecimal(1.7867); rate[3][49] = new BigDecimal(0.000692); rate[4][49] = new BigDecimal(0.6342); 
		rate[0][50] = new BigDecimal(39); rate[1][50] = new BigDecimal(0.002089); rate[2][50] = new BigDecimal(1.915); rate[3][50] = new BigDecimal(0.000756); rate[4][50] = new BigDecimal(0.6933); 
		rate[0][51] = new BigDecimal(40); rate[1][51] = new BigDecimal(0.002254); rate[2][51] = new BigDecimal(2.0658); rate[3][51] = new BigDecimal(0.000822); rate[4][51] = new BigDecimal(0.7533); 
		rate[0][52] = new BigDecimal(41); rate[1][52] = new BigDecimal(0.002429); rate[2][52] = new BigDecimal(2.2267); rate[3][52] = new BigDecimal(0.000888); rate[4][52] = new BigDecimal(0.8142); 
		rate[0][53] = new BigDecimal(42); rate[1][53] = new BigDecimal(0.002636); rate[2][53] = new BigDecimal(2.4167); rate[3][53] = new BigDecimal(0.000951); rate[4][53] = new BigDecimal(0.8717); 
		rate[0][54] = new BigDecimal(43); rate[1][54] = new BigDecimal(0.002875); rate[2][54] = new BigDecimal(2.6358); rate[3][54] = new BigDecimal(0.001026); rate[4][54] = new BigDecimal(0.9408); 
		rate[0][55] = new BigDecimal(44); rate[1][55] = new BigDecimal(0.003139); rate[2][55] = new BigDecimal(2.8775); rate[3][55] = new BigDecimal(0.001118); rate[4][55] = new BigDecimal(1.025); 
		rate[0][56] = new BigDecimal(45); rate[1][56] = new BigDecimal(0.003418); rate[2][56] = new BigDecimal(3.1333); rate[3][56] = new BigDecimal(0.001231); rate[4][56] = new BigDecimal(1.1283); 
		rate[0][57] = new BigDecimal(46); rate[1][57] = new BigDecimal(0.003714); rate[2][57] = new BigDecimal(3.4042); rate[3][57] = new BigDecimal(0.001357); rate[4][57] = new BigDecimal(1.2442); 
		rate[0][58] = new BigDecimal(47); rate[1][58] = new BigDecimal(0.004033); rate[2][58] = new BigDecimal(3.6967); rate[3][58] = new BigDecimal(0.00149); rate[4][58] = new BigDecimal(1.3658); 
		rate[0][59] = new BigDecimal(48); rate[1][59] = new BigDecimal(0.004381); rate[2][59] = new BigDecimal(4.0158); rate[3][59] = new BigDecimal(0.001636); rate[4][59] = new BigDecimal(1.5); 
		rate[0][60] = new BigDecimal(49); rate[1][60] = new BigDecimal(0.004766); rate[2][60] = new BigDecimal(4.3692); rate[3][60] = new BigDecimal(0.001804); rate[4][60] = new BigDecimal(1.6533); 
		rate[0][61] = new BigDecimal(50); rate[1][61] = new BigDecimal(0.005136); rate[2][61] = new BigDecimal(4.7083); rate[3][61] = new BigDecimal(0.001992); rate[4][61] = new BigDecimal(1.8258); 
		rate[0][62] = new BigDecimal(51); rate[1][62] = new BigDecimal(0.005524); rate[2][62] = new BigDecimal(5.0633); rate[3][62] = new BigDecimal(0.002207); rate[4][62] = new BigDecimal(2.0233); 
		rate[0][63] = new BigDecimal(52); rate[1][63] = new BigDecimal(0.005939); rate[2][63] = new BigDecimal(5.4442); rate[3][63] = new BigDecimal(0.002415); rate[4][63] = new BigDecimal(2.2142); 
		rate[0][64] = new BigDecimal(53); rate[1][64] = new BigDecimal(0.006351); rate[2][64] = new BigDecimal(5.8217); rate[3][64] = new BigDecimal(0.00262); rate[4][64] = new BigDecimal(2.4017); 
		rate[0][65] = new BigDecimal(54); rate[1][65] = new BigDecimal(0.006754); rate[2][65] = new BigDecimal(6.1908); rate[3][65] = new BigDecimal(0.002813); rate[4][65] = new BigDecimal(2.5783); 
		rate[0][66] = new BigDecimal(55); rate[1][66] = new BigDecimal(0.007189); rate[2][66] = new BigDecimal(6.59); rate[3][66] = new BigDecimal(0.003022); rate[4][66] = new BigDecimal(2.77); 
		rate[0][67] = new BigDecimal(56); rate[1][67] = new BigDecimal(0.007689); rate[2][67] = new BigDecimal(7.0483); rate[3][67] = new BigDecimal(0.003275); rate[4][67] = new BigDecimal(3.0025); 
		rate[0][68] = new BigDecimal(57); rate[1][68] = new BigDecimal(0.00832); rate[2][68] = new BigDecimal(7.6267); rate[3][68] = new BigDecimal(0.003599); rate[4][68] = new BigDecimal(3.2992); 
		rate[0][69] = new BigDecimal(58); rate[1][69] = new BigDecimal(0.009084); rate[2][69] = new BigDecimal(8.3267); rate[3][69] = new BigDecimal(0.004002); rate[4][69] = new BigDecimal(3.6683); 
		rate[0][70] = new BigDecimal(59); rate[1][70] = new BigDecimal(0.01004); rate[2][70] = new BigDecimal(9.2033); rate[3][70] = new BigDecimal(0.004469); rate[4][70] = new BigDecimal(4.0967); 
		rate[0][71] = new BigDecimal(60); rate[1][71] = new BigDecimal(0.010943); rate[2][71] = new BigDecimal(10.0308); rate[3][71] = new BigDecimal(0.004984); rate[4][71] = new BigDecimal(4.5683); 
		rate[0][72] = new BigDecimal(61); rate[1][72] = new BigDecimal(0.01168); rate[2][72] = new BigDecimal(10.7067); rate[3][72] = new BigDecimal(0.005481); rate[4][72] = new BigDecimal(5.0242); 
		rate[0][73] = new BigDecimal(62); rate[1][73] = new BigDecimal(0.012592); rate[2][73] = new BigDecimal(11.5425); rate[3][73] = new BigDecimal(0.005983); rate[4][73] = new BigDecimal(5.4842); 
		rate[0][74] = new BigDecimal(63); rate[1][74] = new BigDecimal(0.013699); rate[2][74] = new BigDecimal(12.5575); rate[3][74] = new BigDecimal(0.006557); rate[4][74] = new BigDecimal(6.0108); 
		rate[0][75] = new BigDecimal(64); rate[1][75] = new BigDecimal(0.014981); rate[2][75] = new BigDecimal(13.7325); rate[3][75] = new BigDecimal(0.007219); rate[4][75] = new BigDecimal(6.6175); 
		rate[0][76] = new BigDecimal(65); rate[1][76] = new BigDecimal(0.016404); rate[2][76] = new BigDecimal(15.0367); rate[3][76] = new BigDecimal(0.007993); rate[4][76] = new BigDecimal(7.3267); 
		rate[0][77] = new BigDecimal(66); rate[1][77] = new BigDecimal(0.017892); rate[2][77] = new BigDecimal(16.4008); rate[3][77] = new BigDecimal(0.008896); rate[4][77] = new BigDecimal(8.155); 
		rate[0][78] = new BigDecimal(67); rate[1][78] = new BigDecimal(0.019497); rate[2][78] = new BigDecimal(17.8725); rate[3][78] = new BigDecimal(0.009948); rate[4][78] = new BigDecimal(9.1192); 
		rate[0][79] = new BigDecimal(68); rate[1][79] = new BigDecimal(0.021322); rate[2][79] = new BigDecimal(19.545); rate[3][79] = new BigDecimal(0.011162); rate[4][79] = new BigDecimal(10.2317); 
		rate[0][80] = new BigDecimal(69); rate[1][80] = new BigDecimal(0.023359); rate[2][80] = new BigDecimal(21.4125); rate[3][80] = new BigDecimal(0.01254); rate[4][80] = new BigDecimal(11.495); 
		rate[0][81] = new BigDecimal(70); rate[1][81] = new BigDecimal(0.025556); rate[2][81] = new BigDecimal(23.4267); rate[3][81] = new BigDecimal(0.014081); rate[4][81] = new BigDecimal(12.9075); 
		rate[0][82] = new BigDecimal(71); rate[1][82] = new BigDecimal(0.027961); rate[2][82] = new BigDecimal(25.6308); rate[3][82] = new BigDecimal(0.01577); rate[4][82] = new BigDecimal(14.4558); 
		rate[0][83] = new BigDecimal(72); rate[1][83] = new BigDecimal(0.030517); rate[2][83] = new BigDecimal(27.9742); rate[3][83] = new BigDecimal(0.017537); rate[4][83] = new BigDecimal(16.0758); 
		rate[0][84] = new BigDecimal(73); rate[1][84] = new BigDecimal(0.03329); rate[2][84] = new BigDecimal(30.5158); rate[3][84] = new BigDecimal(0.019528); rate[4][84] = new BigDecimal(17.9008); 
		rate[0][85] = new BigDecimal(74); rate[1][85] = new BigDecimal(0.036264); rate[2][85] = new BigDecimal(33.2417); rate[3][85] = new BigDecimal(0.021753); rate[4][85] = new BigDecimal(19.94); 
		rate[0][86] = new BigDecimal(75); rate[1][86] = new BigDecimal(0.039482); rate[2][86] = new BigDecimal(36.1917); rate[3][86] = new BigDecimal(0.024265); rate[4][86] = new BigDecimal(22.2433); 
		rate[0][87] = new BigDecimal(76); rate[1][87] = new BigDecimal(0.042913); rate[2][87] = new BigDecimal(39.3367); rate[3][87] = new BigDecimal(0.027089); rate[4][87] = new BigDecimal(24.8317); 
		rate[0][88] = new BigDecimal(77); rate[1][88] = new BigDecimal(0.046627); rate[2][88] = new BigDecimal(42.7417); rate[3][88] = new BigDecimal(0.030202); rate[4][88] = new BigDecimal(27.685); 
		rate[0][89] = new BigDecimal(78); rate[1][89] = new BigDecimal(0.050663); rate[2][89] = new BigDecimal(46.4408); rate[3][89] = new BigDecimal(0.03367); rate[4][89] = new BigDecimal(30.8642); 
		rate[0][90] = new BigDecimal(79); rate[1][90] = new BigDecimal(0.05509); rate[2][90] = new BigDecimal(50.4992); rate[3][90] = new BigDecimal(0.03747); rate[4][90] = new BigDecimal(34.3475); 
		rate[0][91] = new BigDecimal(80); rate[1][91] = new BigDecimal(0.059942); rate[2][91] = new BigDecimal(54.9467); rate[3][91] = new BigDecimal(0.041628); rate[4][91] = new BigDecimal(38.1592); 
		rate[0][92] = new BigDecimal(81); rate[1][92] = new BigDecimal(0.065252); rate[2][92] = new BigDecimal(59.8142); rate[3][92] = new BigDecimal(0.04621); rate[4][92] = new BigDecimal(42.3592); 
		rate[0][93] = new BigDecimal(82); rate[1][93] = new BigDecimal(0.070972); rate[2][93] = new BigDecimal(65.0575); rate[3][93] = new BigDecimal(0.051234); rate[4][93] = new BigDecimal(46.9642); 
		rate[0][94] = new BigDecimal(83); rate[1][94] = new BigDecimal(0.077204); rate[2][94] = new BigDecimal(70.77); rate[3][94] = new BigDecimal(0.056797); rate[4][94] = new BigDecimal(52.0642); 
		rate[0][95] = new BigDecimal(84); rate[1][95] = new BigDecimal(0.083852); rate[2][95] = new BigDecimal(76.8642); rate[3][95] = new BigDecimal(0.062902); rate[4][95] = new BigDecimal(57.66); 
		rate[0][96] = new BigDecimal(85); rate[1][96] = new BigDecimal(0.091053); rate[2][96] = new BigDecimal(83.465); rate[3][96] = new BigDecimal(0.069618); rate[4][96] = new BigDecimal(63.8167); 
		rate[0][97] = new BigDecimal(86); rate[1][97] = new BigDecimal(0.098875); rate[2][97] = new BigDecimal(90.6358); rate[3][97] = new BigDecimal(0.077205); rate[4][97] = new BigDecimal(70.7717); 
		rate[0][98] = new BigDecimal(87); rate[1][98] = new BigDecimal(0.107353); rate[2][98] = new BigDecimal(98.4067); rate[3][98] = new BigDecimal(0.085467); rate[4][98] = new BigDecimal(78.345); 
		rate[0][99] = new BigDecimal(88); rate[1][99] = new BigDecimal(0.116732); rate[2][99] = new BigDecimal(107.0042); rate[3][99] = new BigDecimal(0.09478); rate[4][99] = new BigDecimal(86.8817); 
		rate[0][100] = new BigDecimal(89); rate[1][100] = new BigDecimal(0.127197); rate[2][100] = new BigDecimal(116.5975); rate[3][100] = new BigDecimal(0.105023); rate[4][100] = new BigDecimal(96.2708); 
		rate[0][101] = new BigDecimal(90); rate[1][101] = new BigDecimal(0.139237); rate[2][101] = new BigDecimal(127.6342); rate[3][101] = new BigDecimal(0.116733); rate[4][101] = new BigDecimal(107.005); 
		rate[0][102] = new BigDecimal(91); rate[1][102] = new BigDecimal(0.153157); rate[2][102] = new BigDecimal(140.3942); rate[3][102] = new BigDecimal(0.130814); rate[4][102] = new BigDecimal(119.9125); 
		rate[0][103] = new BigDecimal(92); rate[1][103] = new BigDecimal(0.16696); rate[2][103] = new BigDecimal(153.0467); rate[3][103] = new BigDecimal(0.148153); rate[4][103] = new BigDecimal(135.8067); 
		rate[0][104] = new BigDecimal(93); rate[1][104] = new BigDecimal(0.182008); rate[2][104] = new BigDecimal(166.8408); rate[3][104] = new BigDecimal(0.165051); rate[4][104] = new BigDecimal(151.2967); 
		rate[0][105] = new BigDecimal(94); rate[1][105] = new BigDecimal(0.198411); rate[2][105] = new BigDecimal(181.8767); rate[3][105] = new BigDecimal(0.183875); rate[4][105] = new BigDecimal(168.5525); 
		rate[0][106] = new BigDecimal(95); rate[1][106] = new BigDecimal(0.216292); rate[2][106] = new BigDecimal(198.2675); rate[3][106] = new BigDecimal(0.204847); rate[4][106] = new BigDecimal(187.7767); 
		rate[0][107] = new BigDecimal(96); rate[1][107] = new BigDecimal(0.235786); rate[2][107] = new BigDecimal(216.1375); rate[3][107] = new BigDecimal(0.228211); rate[4][107] = new BigDecimal(209.1933); 
		rate[0][108] = new BigDecimal(97); rate[1][108] = new BigDecimal(0.257035); rate[2][108] = new BigDecimal(235.6158); rate[3][108] = new BigDecimal(0.254239); rate[4][108] = new BigDecimal(233.0525); 
		rate[0][109] = new BigDecimal(98); rate[1][109] = new BigDecimal(0.280201); rate[2][109] = new BigDecimal(256.8508); rate[3][109] = new BigDecimal(0.283236); rate[4][109] = new BigDecimal(259.6333); 
		rate[0][110] = new BigDecimal(99); rate[1][110] = new BigDecimal(0.305453); rate[2][110] = new BigDecimal(279.9983); rate[3][110] = new BigDecimal(0.31554); rate[4][110] = new BigDecimal(289.245); 
		rate[0][111] = new BigDecimal(100); rate[1][111] = new BigDecimal(0.332982); rate[2][111] = new BigDecimal(305.2333); rate[3][111] = new BigDecimal(0.351529); rate[4][111] = new BigDecimal(322.235); 
		rate[0][112] = new BigDecimal(101); rate[1][112] = new BigDecimal(0.362992); rate[2][112] = new BigDecimal(332.7425); rate[3][112] = new BigDecimal(0.391622); rate[4][112] = new BigDecimal(358.9867); 
		rate[0][113] = new BigDecimal(102); rate[1][113] = new BigDecimal(0.395706); rate[2][113] = new BigDecimal(362.7308); rate[3][113] = new BigDecimal(0.436288); rate[4][113] = new BigDecimal(399.9308); 
		rate[0][114] = new BigDecimal(103); rate[1][114] = new BigDecimal(0.431369); rate[2][114] = new BigDecimal(395.4217); rate[3][114] = new BigDecimal(0.486048); rate[4][114] = new BigDecimal(445.5442); 
		rate[0][115] = new BigDecimal(104); rate[1][115] = new BigDecimal(0.470245); rate[2][115] = new BigDecimal(431.0583); rate[3][115] = new BigDecimal(0.541484); rate[4][115] = new BigDecimal(496.36); 
		rate[0][116] = new BigDecimal(105); rate[1][116] = new BigDecimal(0.512626); rate[2][116] = new BigDecimal(469.9075); rate[3][116] = new BigDecimal(0.603242); rate[4][116] = new BigDecimal(552.9717); 
		rate[0][117] = new BigDecimal(106); rate[1][117] = new BigDecimal(0.558826); rate[2][117] = new BigDecimal(512.2575); rate[3][117] = new BigDecimal(0.672044); rate[4][117] = new BigDecimal(616.04); 
		rate[0][118] = new BigDecimal(107); rate[1][118] = new BigDecimal(0.609189); rate[2][118] = new BigDecimal(558.4233); rate[3][118] = new BigDecimal(0.748693); rate[4][118] = new BigDecimal(686.3017); 
		rate[0][119] = new BigDecimal(108); rate[1][119] = new BigDecimal(0.664092); rate[2][119] = new BigDecimal(608.7508); rate[3][119] = new BigDecimal(0.834085); rate[4][119] = new BigDecimal(764.5783); 
		rate[0][120] = new BigDecimal(109); rate[1][120] = new BigDecimal(0.723942); rate[2][120] = new BigDecimal(663.6133); rate[3][120] = new BigDecimal(0.929215); rate[4][120] = new BigDecimal(851.7808); 
		rate[0][121] = new BigDecimal(110); rate[1][121] = new BigDecimal(1); rate[2][121] = new BigDecimal(916.6667); rate[3][121] = new BigDecimal(1); rate[4][121] = new BigDecimal(916.6667); 		
		
		rate[9][1] = new BigDecimal(1); rate[10][1] = new BigDecimal(0.0015); rate[12][1] = new BigDecimal(1); rate[13][1] = new BigDecimal(0.05); 
		rate[9][2] = new BigDecimal(2); rate[10][2] = new BigDecimal(0.0014); rate[12][2] = new BigDecimal(2); rate[13][2] = new BigDecimal(0.04); 
		rate[9][3] = new BigDecimal(3); rate[10][3] = new BigDecimal(0.0013); rate[12][3] = new BigDecimal(3); rate[13][3] = new BigDecimal(0.02); 
		rate[9][4] = new BigDecimal(4); rate[10][4] = new BigDecimal(0); rate[12][4] = new BigDecimal(4); rate[13][4] = new BigDecimal(0); 
		rate[9][5] = new BigDecimal(5); rate[10][5] = new BigDecimal(0); rate[12][5] = new BigDecimal(5); rate[13][5] = new BigDecimal(0); 
		rate[9][6] = new BigDecimal(6); rate[10][6] = new BigDecimal(0); rate[12][6] = new BigDecimal(6); rate[13][6] = new BigDecimal(0); 
		rate[9][7] = new BigDecimal(7); rate[10][7] = new BigDecimal(0); rate[12][7] = new BigDecimal(7); rate[13][7] = new BigDecimal(0); 
		rate[9][8] = new BigDecimal(8); rate[10][8] = new BigDecimal(0); rate[12][8] = new BigDecimal(8); rate[13][8] = new BigDecimal(0); 
		rate[9][9] = new BigDecimal(9); rate[10][9] = new BigDecimal(0); rate[12][9] = new BigDecimal(9); rate[13][9] = new BigDecimal(0); 
		rate[9][10] = new BigDecimal(10); rate[10][10] = new BigDecimal(0); rate[12][10] = new BigDecimal(10); rate[13][10] = new BigDecimal(0); 
		rate[9][11] = new BigDecimal(11); rate[10][11] = new BigDecimal(0); rate[12][11] = new BigDecimal(11); rate[13][11] = new BigDecimal(0); 
		rate[9][12] = new BigDecimal(12); rate[10][12] = new BigDecimal(0); rate[12][12] = new BigDecimal(12); rate[13][12] = new BigDecimal(0); 
		rate[9][13] = new BigDecimal(13); rate[10][13] = new BigDecimal(0); rate[12][13] = new BigDecimal(13); rate[13][13] = new BigDecimal(0); 
		rate[9][14] = new BigDecimal(14); rate[10][14] = new BigDecimal(0); rate[12][14] = new BigDecimal(14); rate[13][14] = new BigDecimal(0); 
		rate[9][15] = new BigDecimal(15); rate[10][15] = new BigDecimal(0); rate[12][15] = new BigDecimal(15); rate[13][15] = new BigDecimal(0); 
		rate[9][16] = new BigDecimal(16); rate[10][16] = new BigDecimal(0); rate[12][16] = new BigDecimal(16); rate[13][16] = new BigDecimal(0); 
		rate[9][17] = new BigDecimal(17); rate[10][17] = new BigDecimal(0); rate[12][17] = new BigDecimal(17); rate[13][17] = new BigDecimal(0); 
		rate[9][18] = new BigDecimal(18); rate[10][18] = new BigDecimal(0); rate[12][18] = new BigDecimal(18); rate[13][18] = new BigDecimal(0); 
		rate[9][19] = new BigDecimal(19); rate[10][19] = new BigDecimal(0); rate[12][19] = new BigDecimal(19); rate[13][19] = new BigDecimal(0); 
		rate[9][20] = new BigDecimal(20); rate[10][20] = new BigDecimal(0); rate[12][20] = new BigDecimal(20); rate[13][20] = new BigDecimal(0); 
		rate[9][21] = new BigDecimal(21); rate[10][21] = new BigDecimal(0); rate[12][21] = new BigDecimal(21); rate[13][21] = new BigDecimal(0); 
		rate[9][22] = new BigDecimal(22); rate[10][22] = new BigDecimal(0); rate[12][22] = new BigDecimal(22); rate[13][22] = new BigDecimal(0); 
		rate[9][23] = new BigDecimal(23); rate[10][23] = new BigDecimal(0); rate[12][23] = new BigDecimal(23); rate[13][23] = new BigDecimal(0); 
		rate[9][24] = new BigDecimal(24); rate[10][24] = new BigDecimal(0); rate[12][24] = new BigDecimal(24); rate[13][24] = new BigDecimal(0); 
		rate[9][25] = new BigDecimal(25); rate[10][25] = new BigDecimal(0); rate[12][25] = new BigDecimal(25); rate[13][25] = new BigDecimal(0); 
		rate[9][26] = new BigDecimal(26); rate[10][26] = new BigDecimal(0); rate[12][26] = new BigDecimal(26); rate[13][26] = new BigDecimal(0); 
		rate[9][27] = new BigDecimal(27); rate[10][27] = new BigDecimal(0); rate[12][27] = new BigDecimal(27); rate[13][27] = new BigDecimal(0); 
		rate[9][28] = new BigDecimal(28); rate[10][28] = new BigDecimal(0); rate[12][28] = new BigDecimal(28); rate[13][28] = new BigDecimal(0); 
		rate[9][29] = new BigDecimal(29); rate[10][29] = new BigDecimal(0); rate[12][29] = new BigDecimal(29); rate[13][29] = new BigDecimal(0); 
		rate[9][30] = new BigDecimal(30); rate[10][30] = new BigDecimal(0); rate[12][30] = new BigDecimal(30); rate[13][30] = new BigDecimal(0); 
		rate[9][31] = new BigDecimal(31); rate[10][31] = new BigDecimal(0); rate[12][31] = new BigDecimal(31); rate[13][31] = new BigDecimal(0); 
		rate[9][32] = new BigDecimal(32); rate[10][32] = new BigDecimal(0); rate[12][32] = new BigDecimal(32); rate[13][32] = new BigDecimal(0); 
		rate[9][33] = new BigDecimal(33); rate[10][33] = new BigDecimal(0); rate[12][33] = new BigDecimal(33); rate[13][33] = new BigDecimal(0); 
		rate[9][34] = new BigDecimal(34); rate[10][34] = new BigDecimal(0); rate[12][34] = new BigDecimal(34); rate[13][34] = new BigDecimal(0); 
		rate[9][35] = new BigDecimal(35); rate[10][35] = new BigDecimal(0); rate[12][35] = new BigDecimal(35); rate[13][35] = new BigDecimal(0); 
		rate[9][36] = new BigDecimal(36); rate[10][36] = new BigDecimal(0); rate[12][36] = new BigDecimal(36); rate[13][36] = new BigDecimal(0); 
		rate[9][37] = new BigDecimal(37); rate[10][37] = new BigDecimal(0); rate[12][37] = new BigDecimal(37); rate[13][37] = new BigDecimal(0); 
		rate[9][38] = new BigDecimal(38); rate[10][38] = new BigDecimal(0); rate[12][38] = new BigDecimal(38); rate[13][38] = new BigDecimal(0); 
		rate[9][39] = new BigDecimal(39); rate[10][39] = new BigDecimal(0); rate[12][39] = new BigDecimal(39); rate[13][39] = new BigDecimal(0); 
		rate[9][40] = new BigDecimal(40); rate[10][40] = new BigDecimal(0); rate[12][40] = new BigDecimal(40); rate[13][40] = new BigDecimal(0); 
		rate[9][41] = new BigDecimal(41); rate[10][41] = new BigDecimal(0); rate[12][41] = new BigDecimal(41); rate[13][41] = new BigDecimal(0); 
		rate[9][42] = new BigDecimal(42); rate[10][42] = new BigDecimal(0); rate[12][42] = new BigDecimal(42); rate[13][42] = new BigDecimal(0); 
		rate[9][43] = new BigDecimal(43); rate[10][43] = new BigDecimal(0); rate[12][43] = new BigDecimal(43); rate[13][43] = new BigDecimal(0); 
		rate[9][44] = new BigDecimal(44); rate[10][44] = new BigDecimal(0); rate[12][44] = new BigDecimal(44); rate[13][44] = new BigDecimal(0); 
		rate[9][45] = new BigDecimal(45); rate[10][45] = new BigDecimal(0); rate[12][45] = new BigDecimal(45); rate[13][45] = new BigDecimal(0); 
		rate[9][46] = new BigDecimal(46); rate[10][46] = new BigDecimal(0); rate[12][46] = new BigDecimal(46); rate[13][46] = new BigDecimal(0); 
		rate[9][47] = new BigDecimal(47); rate[10][47] = new BigDecimal(0); rate[12][47] = new BigDecimal(47); rate[13][47] = new BigDecimal(0); 
		rate[9][48] = new BigDecimal(48); rate[10][48] = new BigDecimal(0); rate[12][48] = new BigDecimal(48); rate[13][48] = new BigDecimal(0); 
		rate[9][49] = new BigDecimal(49); rate[10][49] = new BigDecimal(0); rate[12][49] = new BigDecimal(49); rate[13][49] = new BigDecimal(0); 
		rate[9][50] = new BigDecimal(50); rate[10][50] = new BigDecimal(0); rate[12][50] = new BigDecimal(50); rate[13][50] = new BigDecimal(0); 
		rate[9][51] = new BigDecimal(51); rate[10][51] = new BigDecimal(0); rate[12][51] = new BigDecimal(51); rate[13][51] = new BigDecimal(0); 
		rate[9][52] = new BigDecimal(52); rate[10][52] = new BigDecimal(0); rate[12][52] = new BigDecimal(52); rate[13][52] = new BigDecimal(0); 
		rate[9][53] = new BigDecimal(53); rate[10][53] = new BigDecimal(0); rate[12][53] = new BigDecimal(53); rate[13][53] = new BigDecimal(0); 
		rate[9][54] = new BigDecimal(54); rate[10][54] = new BigDecimal(0); rate[12][54] = new BigDecimal(54); rate[13][54] = new BigDecimal(0); 
		rate[9][55] = new BigDecimal(55); rate[10][55] = new BigDecimal(0); rate[12][55] = new BigDecimal(55); rate[13][55] = new BigDecimal(0); 
		rate[9][56] = new BigDecimal(56); rate[10][56] = new BigDecimal(0); rate[12][56] = new BigDecimal(56); rate[13][56] = new BigDecimal(0); 
		rate[9][57] = new BigDecimal(57); rate[10][57] = new BigDecimal(0); rate[12][57] = new BigDecimal(57); rate[13][57] = new BigDecimal(0); 
		rate[9][58] = new BigDecimal(58); rate[10][58] = new BigDecimal(0); rate[12][58] = new BigDecimal(58); rate[13][58] = new BigDecimal(0); 
		rate[9][59] = new BigDecimal(59); rate[10][59] = new BigDecimal(0); rate[12][59] = new BigDecimal(59); rate[13][59] = new BigDecimal(0); 
		rate[9][60] = new BigDecimal(60); rate[10][60] = new BigDecimal(0); rate[12][60] = new BigDecimal(60); rate[13][60] = new BigDecimal(0); 
		rate[9][61] = new BigDecimal(61); rate[10][61] = new BigDecimal(0); rate[12][61] = new BigDecimal(61); rate[13][61] = new BigDecimal(0); 
		rate[9][62] = new BigDecimal(62); rate[10][62] = new BigDecimal(0); rate[12][62] = new BigDecimal(62); rate[13][62] = new BigDecimal(0); 
		rate[9][63] = new BigDecimal(63); rate[10][63] = new BigDecimal(0); rate[12][63] = new BigDecimal(63); rate[13][63] = new BigDecimal(0); 
		rate[9][64] = new BigDecimal(64); rate[10][64] = new BigDecimal(0); rate[12][64] = new BigDecimal(64); rate[13][64] = new BigDecimal(0); 
		rate[9][65] = new BigDecimal(65); rate[10][65] = new BigDecimal(0); rate[12][65] = new BigDecimal(65); rate[13][65] = new BigDecimal(0); 
		rate[9][66] = new BigDecimal(66); rate[10][66] = new BigDecimal(0); rate[12][66] = new BigDecimal(66); rate[13][66] = new BigDecimal(0); 
		rate[9][67] = new BigDecimal(67); rate[10][67] = new BigDecimal(0); rate[12][67] = new BigDecimal(67); rate[13][67] = new BigDecimal(0); 
		rate[9][68] = new BigDecimal(68); rate[10][68] = new BigDecimal(0); rate[12][68] = new BigDecimal(68); rate[13][68] = new BigDecimal(0); 
		rate[9][69] = new BigDecimal(69); rate[10][69] = new BigDecimal(0); rate[12][69] = new BigDecimal(69); rate[13][69] = new BigDecimal(0); 
		rate[9][70] = new BigDecimal(70); rate[10][70] = new BigDecimal(0); rate[12][70] = new BigDecimal(70); rate[13][70] = new BigDecimal(0); 
		rate[9][71] = new BigDecimal(71); rate[10][71] = new BigDecimal(0); rate[12][71] = new BigDecimal(71); rate[13][71] = new BigDecimal(0); 
		rate[9][72] = new BigDecimal(72); rate[10][72] = new BigDecimal(0); rate[12][72] = new BigDecimal(72); rate[13][72] = new BigDecimal(0); 
		rate[9][73] = new BigDecimal(73); rate[10][73] = new BigDecimal(0); rate[12][73] = new BigDecimal(73); rate[13][73] = new BigDecimal(0); 
		rate[9][74] = new BigDecimal(74); rate[10][74] = new BigDecimal(0); rate[12][74] = new BigDecimal(74); rate[13][74] = new BigDecimal(0); 
		rate[9][75] = new BigDecimal(75); rate[10][75] = new BigDecimal(0); rate[12][75] = new BigDecimal(75); rate[13][75] = new BigDecimal(0); 
		rate[9][76] = new BigDecimal(76); rate[10][76] = new BigDecimal(0); rate[12][76] = new BigDecimal(76); rate[13][76] = new BigDecimal(0); 
		rate[9][77] = new BigDecimal(77); rate[10][77] = new BigDecimal(0); rate[12][77] = new BigDecimal(77); rate[13][77] = new BigDecimal(0); 
		rate[9][78] = new BigDecimal(78); rate[10][78] = new BigDecimal(0); rate[12][78] = new BigDecimal(78); rate[13][78] = new BigDecimal(0); 
		rate[9][79] = new BigDecimal(79); rate[10][79] = new BigDecimal(0); rate[12][79] = new BigDecimal(79); rate[13][79] = new BigDecimal(0); 
		rate[9][80] = new BigDecimal(80); rate[10][80] = new BigDecimal(0); rate[12][80] = new BigDecimal(80); rate[13][80] = new BigDecimal(0); 
		rate[9][81] = new BigDecimal(81); rate[10][81] = new BigDecimal(0); rate[12][81] = new BigDecimal(81); rate[13][81] = new BigDecimal(0); 
		rate[9][82] = new BigDecimal(82); rate[10][82] = new BigDecimal(0); rate[12][82] = new BigDecimal(82); rate[13][82] = new BigDecimal(0); 
		rate[9][83] = new BigDecimal(83); rate[10][83] = new BigDecimal(0); rate[12][83] = new BigDecimal(83); rate[13][83] = new BigDecimal(0); 
		rate[9][84] = new BigDecimal(84); rate[10][84] = new BigDecimal(0); rate[12][84] = new BigDecimal(84); rate[13][84] = new BigDecimal(0); 
		rate[9][85] = new BigDecimal(85); rate[10][85] = new BigDecimal(0); rate[12][85] = new BigDecimal(85); rate[13][85] = new BigDecimal(0); 
		rate[9][86] = new BigDecimal(86); rate[10][86] = new BigDecimal(0); rate[12][86] = new BigDecimal(86); rate[13][86] = new BigDecimal(0); 
		rate[9][87] = new BigDecimal(87); rate[10][87] = new BigDecimal(0); rate[12][87] = new BigDecimal(87); rate[13][87] = new BigDecimal(0); 
		rate[9][88] = new BigDecimal(88); rate[10][88] = new BigDecimal(0); rate[12][88] = new BigDecimal(88); rate[13][88] = new BigDecimal(0); 
		rate[9][89] = new BigDecimal(89); rate[10][89] = new BigDecimal(0); rate[12][89] = new BigDecimal(89); rate[13][89] = new BigDecimal(0); 
		rate[9][90] = new BigDecimal(90); rate[10][90] = new BigDecimal(0); rate[12][90] = new BigDecimal(90); rate[13][90] = new BigDecimal(0); 
		rate[9][91] = new BigDecimal(91); rate[10][91] = new BigDecimal(0); rate[12][91] = new BigDecimal(91); rate[13][91] = new BigDecimal(0); 
		rate[9][92] = new BigDecimal(92); rate[10][92] = new BigDecimal(0); rate[12][92] = new BigDecimal(92); rate[13][92] = new BigDecimal(0); 
		rate[9][93] = new BigDecimal(93); rate[10][93] = new BigDecimal(0); rate[12][93] = new BigDecimal(93); rate[13][93] = new BigDecimal(0); 
		rate[9][94] = new BigDecimal(94); rate[10][94] = new BigDecimal(0); rate[12][94] = new BigDecimal(94); rate[13][94] = new BigDecimal(0); 
		rate[9][95] = new BigDecimal(95); rate[10][95] = new BigDecimal(0); rate[12][95] = new BigDecimal(95); rate[13][95] = new BigDecimal(0); 
		rate[9][96] = new BigDecimal(96); rate[10][96] = new BigDecimal(0); rate[12][96] = new BigDecimal(96); rate[13][96] = new BigDecimal(0); 
		rate[9][97] = new BigDecimal(97); rate[10][97] = new BigDecimal(0); rate[12][97] = new BigDecimal(97); rate[13][97] = new BigDecimal(0); 
		rate[9][98] = new BigDecimal(98); rate[10][98] = new BigDecimal(0); rate[12][98] = new BigDecimal(98); rate[13][98] = new BigDecimal(0); 
		rate[9][99] = new BigDecimal(99); rate[10][99] = new BigDecimal(0); rate[12][99] = new BigDecimal(99); rate[13][99] = new BigDecimal(0); 
		
		this.excelData = new ExcelData("月化試算表", statements.length, 70 * 12 + 1  + 2, ss.toArray(new String[ss.size()]), 0, 2);
		this.excelData.setSheet("費用表", rate);
		this.excelData.setValue("月化試算表", 26, 0, new BigDecimal(0.06));
		this.excelData.setValue("月化試算表", 27, 2, new BigDecimal(3));
		this.excelData.setValue("月化試算表", 28, 1, new BigDecimal(5));
		
		this.calculator = new Calculator(this.excelData);
	}

	public String replaceNumber(String statement) {
		for(String key : this.map.keySet()) 
			statement = statement.replace(key, this.map.get(key));
		
		return statement;
	}
}
