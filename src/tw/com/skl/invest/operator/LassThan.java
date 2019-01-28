package tw.com.skl.invest.operator;

public class LassThan extends Expression {
	private Expression left;
	private Expression right;
	
	public LassThan(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		print("cel: " + this.left.interpret() + " < " + this.right.interpret());
		return "LassThanResult";
	}
}