import java.util.*;

public class Operator {

	char op;

	public Operator(char op) {
		this.op = op;
	}

	public char getOp() {
		return op;
	}

	public void setOp(char op) {
		this.op = op;
	}

	public int precedence() {
		switch (op) {
		case '+':
			return 1;
		case '-':
			return 1;
		case '*':
			return 2;
		case '/':
			return 2;
		case '%':
			return 2;
		default:
			return 0;
		}

	}

}
