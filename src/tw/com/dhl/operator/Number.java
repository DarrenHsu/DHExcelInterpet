package tw.com.dhl.operator;

public class Number extends Expression {
	
	private String operand;
	
	public Number(String operand) {
		this.operand = operand;
	}
	
	@Override
	public String interpret() {
		this.operand = this.operand.replaceAll("\"", "");
		
		return operand.equals("") ? "0" : operand;
	}
}