package tw.com.skl.invest;

public abstract class Expression {

	public static final String S_POWER = "^";
	public static final String S_MULTIPLICATION = "*";
	public static final String S_DIVISION = "/";
	public static final String S_DIVISION_INTEGER = "\\";
	public static final String S_REMAINDER = "%";
	public static final String S_ADDTION = "+";
	public static final String S_SUBTRATION = "-";
	public static final String S_MORE_THAN = ">";
	public static final String S_LASS_THAN = "<";
	public static final String S_MORE_THAN_EUQAL = ">=";
	public static final String S_LASS_THAN_EQUAL = "<=";
	public static final String S_NOT_EUQAL = "<>";

	public static String FORMULA_REGEX = "";
	public static String NAME = "";

	public abstract String interpret(String statement);

	public static void print(String msg) {
		System.out.println(msg);
	}

	public String[] splitComman(String str) {
		String[] splitStr = str.split(",");
		return splitStr;
	}

	public int getPriority(String c) {
		switch (c) {
		case S_POWER:
			return 1;
		case S_MULTIPLICATION:
		case S_DIVISION:
			return 2;
		case S_DIVISION_INTEGER:
			return 3;
		case S_REMAINDER:
			return 4;
		case S_ADDTION:
		case S_SUBTRATION:
			return 5;
		case S_MORE_THAN:
		case S_LASS_THAN:
		case S_MORE_THAN_EUQAL:
		case S_LASS_THAN_EQUAL:
		case S_NOT_EUQAL:
			return 6;
		default:
			return 99;
		}
	}
}
