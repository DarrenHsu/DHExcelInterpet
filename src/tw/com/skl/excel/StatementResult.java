package tw.com.skl.excel;

import java.util.ArrayList;

public class StatementResult {
	private ArrayList<String> operators;
	private ArrayList<String> statements;
	private String orignalStatement;
	private String statement;
	
	public StatementResult() {}
	
	public StatementResult(String orignalStatement) {
		this.orignalStatement = orignalStatement;
		this.statement = orignalStatement;
		this.operators = new ArrayList<>();
		this.statements = new ArrayList<>();
	}
	
	public void setOperatorAndStatement(String o, String s) {
		this.statements.add(s);
		this.operators.add(o);
	}
	
	public String getOrignalStatement() {
		return orignalStatement;
	}

	public void setOrignalStatement(String orignalStatement) {
		this.orignalStatement = orignalStatement;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getLastFullStatement() {
		if (this.operators.size() == 0 && this.operators.size() == 0) {
			return this.statement;
		}
		String o = this.operators.get(this.operators.size() - 1);
		String s = this.statements.get(this.statements.size() - 1);
		return o + "(" + s + ")";
	}
	
	public String getLastOperator() {
		if (this.operators.size() == 0) 
			return null;
		
		return this.operators.get(this.operators.size() - 1);
	}
	
	public String getLastStatement() {
		if (this.operators.size() == 0) 
			return null;
		
		return this.statements.get(this.statements.size() - 1);
	}

	@Override
	public String toString() {
		String str = "-----------------------------------\n" +
				"o " + this.orignalStatement + "\n" +
				"s " + this.statement + "\n";
		
		for (int i = 0 ; i < this.statements.size() ; i++) {
			String s = this.statements.get(i);
			String o = this.operators.get(i);
			str += "a " + o + " : " + s + "\n";
		}
		str += "-----------------------------------";
		return str;
	}
	
	
}
