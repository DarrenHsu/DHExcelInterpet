package tw.com.skl.invest.operator;

public class Remainder extends Expression {
	public static final String SYMBOL = "%";
	
	private Expression left;
	private Expression right;
	
	public Remainder(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		print("cel: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		return "RemainderResult";
	}
}