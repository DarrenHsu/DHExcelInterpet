package tw.com.dh.excel;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.com.dh.utility.Log;

public class Calculator {
	
	private ExcelData excelData;
	private StatementData statementData;
	
	private String allFormulaString = "(" + 
			FormulaIF.NAME + "|" +
			FormulaAND.NAME + "|" +
			FormulaMOD.NAME + "|" +
			FormulaSUM.NAME + "|" +
			FormulaOR.NAME + "|" +
			FormulaROW.NAME + "|" +
			FormulaROUND.NAME + "|" +
			FormulaROUNDUP.NAME + "|" +
			FormulaAVERAGE.NAME + "|" +
			FormulaVLOOKUP.NAME + "|" +
			FormulaINT.NAME + "|" +
			FormulaMAX.NAME + "|" +
			FormulaMIN.NAME +
			")\\(";
	
	public Calculator() {
		this.excelData = new ExcelData();
	}
	
	public Calculator(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	public BigDecimal parseStatement(String statement) {
		Log.d(statement);

		BigDecimal result = BigDecimal.ZERO;
		this.statementData = new StatementData(statement.replace(" ", ""));
		do {
			Log.d("------------- interpreter start -------------");
			result = this.interpretFormula();
		} while(this.isHasFormula(this.statementData));
		
		Log.d("Final Result: " + result + "\n");
		
		return result;
	}
	
	private BigDecimal interpretFormula() {
		String formula = this.getFormula(this.statementData);
		while(formula != null && !formula.isEmpty()) {
			this.interpret(this.statementData, this.convertToRegex(formula));
			formula = this.getFormula(this.statementData);
		}
		
		if (this.statementData.getLastOperator() == null)  
			return this.process(this.statementData);
		
		String lastFullStepment = this.statementData.getLastFullStatement();
		BigDecimal result = this.process(this.statementData);
		String orignalStatement = this.statementData.getOrignalStatement().replace(lastFullStepment, result.setScale(Formula.S_PRECISION, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
		this.statementData = new StatementData(orignalStatement);
		return result;
	}
	
	private boolean isHasFormula(StatementData sr) {
		String regex = this.allFormulaString;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sr.getStatement());
		return m.find();
	}
	
	private String getFormula(StatementData sr) {
		String regex = this.allFormulaString;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sr.getStatement());
		if (m.find()) {
			String f = m.group();
			return f.substring(0, f.length() - 1);
		}else {
			return null;
		}
	}
	
	private String convertToRegex(String formula) {
		switch(formula) {
		case FormulaIF.NAME:
			return FormulaIF.FORMULA_REGEX;
		case FormulaAND.NAME:
			return FormulaAND.FORMULA_REGEX;
		case FormulaMOD.NAME:
			return FormulaMOD.FORMULA_REGEX;
		case FormulaSUM.NAME:
			return FormulaSUM.FORMULA_REGEX;
		case FormulaOR.NAME:
			return FormulaOR.FORMULA_REGEX;
		case FormulaROW.NAME:
			return FormulaROW.FORMULA_REGEX;
		case FormulaROUND.NAME:
			return FormulaROUND.FORMULA_REGEX;
		case FormulaROUNDUP.NAME:
			return FormulaROUNDUP.FORMULA_REGEX;
		case FormulaAVERAGE.NAME:
			return FormulaAVERAGE.FORMULA_REGEX;
		case FormulaVLOOKUP.NAME:
			return FormulaVLOOKUP.FORMULA_REGEX;
		case FormulaINT.NAME:
			return FormulaINT.FORMULA_REGEX;
		case FormulaMAX.NAME:
			return FormulaMAX.FORMULA_REGEX;
		case FormulaMIN.NAME:
			return FormulaMIN.FORMULA_REGEX;
		default:
			return formula;
		}
	}
	
	private BigDecimal process(StatementData sr) {
		String op = sr.getLastOperator();
		if (op == null) 
			return new FormulaNone(this.excelData).interpret(sr.getStatement());
		
		switch(op) {
		case FormulaIF.NAME:
			return new FormulaIF(this.excelData).interpret(sr.getStatement());
		case FormulaAND.NAME:
			return new FormulaAND(this.excelData).interpret(sr.getStatement());
		case FormulaMOD.NAME:
			return new FormulaMOD(this.excelData).interpret(sr.getStatement());
		case FormulaSUM.NAME:
			return new FormulaSUM(this.excelData).interpret(sr.getStatement());
		case FormulaOR.NAME:
			return new FormulaOR(this.excelData).interpret(sr.getStatement());
		case FormulaROW.NAME:
			return new FormulaROW(this.excelData).interpret(sr.getStatement());
		case FormulaROUND.NAME:
			return new FormulaROUND(this.excelData).interpret(sr.getStatement());
		case FormulaROUNDUP.NAME:
			return new FormulaROUNDUP(this.excelData).interpret(sr.getStatement());
		case FormulaAVERAGE.NAME:
			return new FormulaAVERAGE(this.excelData).interpret(sr.getStatement());
		case FormulaVLOOKUP.NAME:
			return new FormulaVLOOKUP(this.excelData).interpret(sr.getStatement());
		case FormulaINT.NAME:
			return new FormulaINT(this.excelData).interpret(sr.getStatement());
		case FormulaMAX.NAME:
			return new FormulaMAX(this.excelData).interpret(sr.getStatement());
		case FormulaMIN.NAME:
			return new FormulaMIN(this.excelData).interpret(sr.getStatement());
		default:
			return BigDecimal.ZERO;
		}
	}
	
	private void interpret(StatementData sr, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sr.getStatement());
		if (m.find()) {
			String operator = m.group().substring(0, m.group().length() - 1);
			String statement = sr.getStatement().substring(m.end());
			
			char[] cs = statement.toCharArray();
			int openParenthesis = 0, closeParenthesis = 0;
			for(int i  = 0 ; i  < cs.length ; i++) {
				switch (cs[i]) {
				case '(':
					openParenthesis += 1;
					break;
				case ')':
					closeParenthesis += 1;
					break;
				}
				
				if (closeParenthesis > openParenthesis) {
					statement = statement.substring(0, i);
					break;
				}
			}
			
			sr.setOperatorAndStatement(operator, statement);
			sr.setStatement(statement);
		}
	}
}
