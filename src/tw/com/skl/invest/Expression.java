package tw.com.skl.invest;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Expression {

	public static final String S_POWER 				= "^";
	public static final String S_MULTIPLICATION 	= "*";
	public static final String S_DIVISION			= "/";
	public static final String S_DIVISION_INTEGER 	= "\\";
	public static final String S_REMAINDER 			= "%";
	public static final String S_ADDTION 			= "+";
	public static final String S_SUBTRATION 		= "-";
	public static final String S_MORE_THAN 			= ">";
	public static final String S_LASS_THAN 			= "<";
	public static final String S_MORE_THAN_EUQAL 	= ">=";
	public static final String S_LASS_THAN_EQUAL 	= "<=";
	public static final String S_NOT_EUQAL 			= "<>";

	public static String FORMULA_REGEX = "";
	public static String NAME = "";

	public abstract String interpret(String statement);

	protected static void print(String msg) {
		System.out.println(msg);
	}
	
	protected String[] splitComman(String str) {
		String[] splitStr = str.split(",");
		return splitStr;
	}

	protected int getExpressionOperatorPriority(String c) {
		switch (c) {
		case S_POWER:
			return 1;
		case S_MULTIPLICATION: case S_DIVISION:
			return 2;
		case S_DIVISION_INTEGER:
			return 3;
		case S_REMAINDER:
			return 4;
		case S_ADDTION: case S_SUBTRATION:
			return 5;
		case S_MORE_THAN: case S_LASS_THAN: case S_MORE_THAN_EUQAL: case S_LASS_THAN_EQUAL: case S_NOT_EUQAL:
			return 6;
		default:
			return 99;
		}
	}
	
	protected String getExpressionOperatorRegex() {
		return "(\\" + S_POWER + "|" +
				"\\" + S_MULTIPLICATION + "|" +
				"\\" + S_DIVISION + "|" +
				"\\" + S_DIVISION_INTEGER + "|" +
				"\\" + S_REMAINDER + "|" +
				"\\" + S_ADDTION + "|" +
				"\\" + S_SUBTRATION + "|" +
				"(" + S_NOT_EUQAL + "|" +
				S_MORE_THAN_EUQAL + "|" + S_LASS_THAN_EQUAL +  "|" +
				S_MORE_THAN + "|" + S_LASS_THAN + ")" + ")";
	}
	
	protected String[] splitOperand(String statement) {
		Pattern p = Pattern.compile(this.getExpressionOperatorRegex());
		Matcher m = p.matcher(statement);
		ArrayList<String> operands = new ArrayList<>();
		while (m.find()) {
			String operator = m.group();
			print(operator);
		}
		return operands.toArray(new String[operands.size()]);
	}
}
