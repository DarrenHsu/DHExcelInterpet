package tw.com.skl.invest.operator;

public class DivisionInteger extends Expression {
	private Expression left;
	private Expression right;
	
	public DivisionInteger(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		print(this.left.interpret() + " \\ " + this.right.interpret());
		return "DivisionIntegerResult";
	}
}