import java.util.*;

public class Calc {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter an infix expression to be converted: ");
		String input = sc.nextLine();

		System.out.println(convertToPostfix(input));

	}

	public static String convertToPostfix(String input) {

		String postString = "";
		Stack<Character> st = new Stack<>();
		for (int i = 0; i < input.length(); i++) {
			if (Character.isDigit(input.charAt(i)) || input.charAt(i) == 'x') {
				postString = postString + input.charAt(i);
			} else if (input.charAt(i) == '(' || input.charAt(i) == ')') {
				switch (input.charAt(i)) {
				case '(':
					st.push('(');
					break;
				case ')':
					while (!st.peek().equals('(') && !st.isEmpty()) {
						postString = postString + st.pop();
					}
					if (st.peek().equals('(')) {
						st.pop();
					}
					break;
				}

				// ERROR ZONE
			} else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*'
					|| input.charAt(i) == '/' || input.charAt(i) == '%') {
				Operator o = new Operator(input.charAt(i));

				if (!st.isEmpty()) {
					Operator stack = new Operator((char) st.peek());

					while (!st.isEmpty() && o.precedence() <= stack.precedence()) {

						postString = postString + st.pop();
						stack = new Operator((char) st.peek());
					}

					st.push(input.charAt(i));

				} else {
					while (!st.isEmpty() && !st.peek().equals('(') && o.precedence() < 2) {
						postString = postString + st.pop();
					}
					st.push(input.charAt(i));
				}

			}
			
		}
		return postString;
	}
}
