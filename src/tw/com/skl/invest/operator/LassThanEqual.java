package tw.com.skl.invest.operator;

public class LassThanEqual extends Expression {
	
	public static final String SYMBOL = "<=";
	
	private Expression left;
	private Expression right;
	
	public LassThanEqual(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		print("cel: " + this.left.interpret() + SYMBOL + this.right.interpret());
		return "LassThanEqualResult";
	}
}