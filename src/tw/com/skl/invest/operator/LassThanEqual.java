package tw.com.skl.invest.operator;

public class LassThanEqual extends Expression {
	private Expression left;
	private Expression right;
	
	public LassThanEqual(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		print("cel: " + this.left.interpret() + " <= " + this.right.interpret());
		return "LassThanEqualResult";
	}
}