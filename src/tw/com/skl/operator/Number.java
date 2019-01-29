package tw.com.skl.operator;

public class Number extends Expression {
	
	private String operand;
	
	public Number(String operand) {
		this.operand = operand;
	}
	
	@Override
	public String interpret() {
		return operand;
	}
}