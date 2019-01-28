package tw.com.skl.invest.operator;

public class Subtration extends Expression {
	private Expression left;
	private Expression right;
	
	public Subtration(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		print(this.left.interpret() + " - " + this.right.interpret());
		return "SubtrationResult";
	}
}