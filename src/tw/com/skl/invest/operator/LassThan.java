package tw.com.skl.invest.operator;

public class LassThan extends Expression {
	
	public static final String SYMBOL = "<";
	
	private Expression left;
	private Expression right;
	
	public LassThan(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		print("cel: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		return "LassThanResult";
	}
}