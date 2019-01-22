package tw.com.skl.invest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionUtlity {
	
	public static void print(String msg) {
		System.out.println(msg);
	}
	
	public static void main(String[] args) {
		String str = null;
		str = "IF(B3<>\"\",IF(B3=1,保險費,IF(B3<=13,IF(AND(MOD(B3,(12/繳法別))=1,B3<=繳交年限*12),定期定額續期保險費,B5>5/B2+1),+IF(AND(MOD(B3,(12/繳法別))=1,B3<=繳交年限*12),定期定額續期保險費,0))),\"\")";
//		str = "IF(B3<>\"\",IF(B3=1,保險費,IF(B3<=13,IF(AND(MOD(B3,(12/繳法別))=1,B3<=繳交年限*12),定期定額續期保險費,0),+IF(AND(MOD(B3,(12/繳法別))>1,MOD(B4,(12/繳法別))<3),定期定額續期保險費,0))),\"\")";
//		str = "AND(MOD(B3,(12/繳法別))>1,MOD(B4,(12/繳法別))<3)";
//		str = "B3<>\\\"\\\"";
//		str = "IF(B3<>\"\",IF(AND(B3<>1,Q2=0),0,IF(AND(A3>=提領年度,A3<=結束提領年度,B3=12*A3,(J3-$K3)*(1+R$1)^(1/12)-部分提領>0),部分提領,0)),\"\")";
//		str = "AND(MOD(B3,(12/繳法別))=1,B3<=繳交年限*12)";
		ExpressionUtlity ex = new ExpressionUtlity();
		print(str);
		print("------------- interpreter start -------------");
		StatementResult sr = new StatementResult(str);
		ex.interpretFormula(sr);
	}
	
	public String getAllFormulaString() {
		return "(" + FormulaIF.name + "|" +
				FormulaAND.name + "|" +
				FormulaMOD.name + 
				")\\(";
	}
	
	public boolean isHasFormula(StatementResult sr) {
		String regex = this.getAllFormulaString();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sr.getStatement());
		return m.find();
	}
	
	public String getFormula(StatementResult sr) {
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
	
	public String convertToRegex(String formula) {
		switch(formula) {
		case FormulaIF.name:
			return FormulaIF.formulaRegex;
		case FormulaAND.name:
			return FormulaAND.formulaRegex;
		case FormulaMOD.name:
			return FormulaMOD.formulaRegex;
		default:
			break;
		}
		
		return null;
	}
	
	public String process(StatementResult sr) {
				switch(sr.getLastOperater()) {
		case FormulaIF.name:
			return new FormulaIF().interpret(sr.getStatement());
		case FormulaAND.name:
			return new FormulaAND().interpret(sr.getStatement());
		case FormulaMOD.name:
			return new FormulaMOD().interpret(sr.getStatement());
		default:
			return null;
		}
	}
	
	public void interpretFormula(StatementResult sr) {
		String formula = this.getFormula(sr);
		if (formula != null) {
			String regex = this.convertToRegex(formula);
			this.interpret(sr, regex);
			this.interpretFormula(sr);
		} else {
			String lastOperater = sr.getLastOperater();
			if (lastOperater == null) {
				print("Result");
				return;
			}
			
			String lastFullStepment = sr.getLastFullStatement();
			String result = this.process(sr);
			String orignalStatement = sr.getOrignalStatement().replace(lastFullStepment, result);
			print(orignalStatement);
			
			sr = new StatementResult(orignalStatement);
			if (this.isHasFormula(sr)) {
				print("------------- interpreter start --------------");
				this.interpretFormula(sr);
			}
			return;
		}
	}
	
	public StatementResult interpret(StatementResult sr, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sr.getStatement());
		if (m.find()) {
			String operater = m.group().substring(0, m.group().length() - 1);
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
			
			sr.setOperaterAndStatement(operater, statement);
			sr.setStatement(statement);
		}
		return sr;
	}
}
