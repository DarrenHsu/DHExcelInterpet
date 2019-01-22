package tw.com.skl.invest;

import java.util.ArrayList;

public class StatementResult {
	private ArrayList<String> operaters;
	private ArrayList<String> statements;
	private String orignalStatement;
	private String statement;
	
	public StatementResult() {}
	
	public StatementResult(String orignalStatement) {
		this.orignalStatement = orignalStatement;
		this.statement = orignalStatement;
		this.operaters = new ArrayList<>();
		this.statements = new ArrayList<>();
	}
	
	public void setOperaterAndStatement(String o, String s) {
		this.statements.add(s);
		this.operaters.add(o);
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
		if (this.operaters.size() == 0 && this.operaters.size() == 0) {
			return this.statement;
		}
		String o = this.operaters.get(this.operaters.size() - 1);
		String s = this.statements.get(this.statements.size() - 1);
		return o + "(" + s + ")";
	}
	
	public String getLastOperater() {
		if (this.operaters.size() == 0) 
			return null;
		
		return this.operaters.get(this.operaters.size() - 1);
	}
	
	public String getLastStatement() {
		if (this.operaters.size() == 0) 
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
			String o = this.operaters.get(i);
			str += "a " + o + " : " + s + "\n";
		}
		str += "-----------------------------------";
		return str;
	}
	
	
}
