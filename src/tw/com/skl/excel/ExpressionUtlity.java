package tw.com.skl.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionUtlity {
	public void parseStatement(String statement) {
		ExpressionUtlity ex = new ExpressionUtlity();
		StatementResult sr = new StatementResult(statement);

		print(statement);
		print("------------- interpreter start -------------");
		
		ex.interpretFormula(sr);
	}
	
	private static void print(String msg) {
		System.out.println(msg);
	}
	
	private String getAllFormulaString() {
		return "(" + 
				FormulaIF.NAME + "|" +
				FormulaAND.NAME + "|" +
				FormulaMOD.NAME + "|" +
				FormulaSUM.NAME + "|" +
				FormulaOR.NAME + "|" +
				FormulaROW.NAME + "|" +
				FormulaROUND.NAME + "|" +
				FormulaAVERAGE.NAME + "|" +
				FormulaVLOOKUP.NAME + "|" +
				FormulaINT.NAME + 
				")\\(";
	}
	
	private boolean isHasFormula(StatementResult sr) {
		String regex = this.getAllFormulaString();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sr.getStatement());
		return m.find();
	}
	
	private String getFormula(StatementResult sr) {
		String regex = this.getAllFormulaString();
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
		case FormulaAVERAGE.NAME:
			return FormulaAVERAGE.FORMULA_REGEX;
		case FormulaVLOOKUP.NAME:
			return FormulaVLOOKUP.FORMULA_REGEX;
		case FormulaINT.NAME:
			return FormulaINT.FORMULA_REGEX;
		default:
			return formula;
		}
	}
	
	private String process(StatementResult sr) {
		switch(sr.getLastOperator()) {
		case FormulaIF.NAME:
			return new FormulaIF().interpret(sr.getStatement());
		case FormulaAND.NAME:
			return new FormulaAND().interpret(sr.getStatement());
		case FormulaMOD.NAME:
			return new FormulaMOD().interpret(sr.getStatement());
		case FormulaSUM.NAME:
			return new FormulaSUM().interpret(sr.getStatement());
		case FormulaOR.NAME:
			return new FormulaOR().interpret(sr.getStatement());
		case FormulaROW.NAME:
			return new FormulaROW().interpret(sr.getStatement());
		case FormulaROUND.NAME:
			return new FormulaROUND().interpret(sr.getStatement());
		case FormulaAVERAGE.NAME:
			return new FormulaAVERAGE().interpret(sr.getStatement());
		case FormulaVLOOKUP.NAME:
			return new FormulaVLOOKUP().interpret(sr.getStatement());
		case FormulaINT.NAME:
			return new FormulaINT().interpret(sr.getStatement());
		default:
			return null;
		}
	}
	
	private void interpretFormula(StatementResult sr) {
		String formula = this.getFormula(sr);
		if (formula != null && !formula.isEmpty()) {
			String regex = this.convertToRegex(formula);
			this.interpret(sr, regex);
			this.interpretFormula(sr);
		} else {
			String lastOperator = sr.getLastOperator();
			if (lastOperator == null) {
				print("Result");
				return;
			}
			
			String lastFullStepment = sr.getLastFullStatement();
			String result = this.process(sr);
			String orignalStatement = sr.getOrignalStatement().replace(lastFullStepment, result);
			print("\n" + orignalStatement);
			
			sr = new StatementResult(orignalStatement);
			if (this.isHasFormula(sr)) {
				print("------------- interpreter start --------------");
				this.interpretFormula(sr);
			}
			return;
		}
	}
	
	private StatementResult interpret(StatementResult sr, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sr.getStatement());
		if (m.find()) {
			String operator = m.group().substring(0, m.group().length() - 1);
			String statement = sr.getStatement().substring(m.end());
			
			char[] cs = statement.toCharArray();
			int openParenthesis = 0, closeParenthesis = 0;
			for(int i  = 0 ; i  < cs.length ; i++) {
				if (cs[i] == '(') 
					openParenthesis += 1;
				else if(cs[i] == ')') 
					closeParenthesis += 1;
				
				if (closeParenthesis > openParenthesis) {
					statement = statement.substring(0, i);
					break;
				}
			}
			
			sr.setOperatorAndStatement(operator, statement);
			sr.setStatement(statement);
		}
		return sr;
	}
}
