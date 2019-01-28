package tw.com.skl.invest.operator;

public class Division extends Expression {
	private Expression left;
	private Expression right;
	
	public Division(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		print("cel: " + this.left.interpret() + " / " + this.right.interpret());
		return "DivisionResult";
	}
}