import java.util.*;

public class Calc {
	public static void main(String[] args) {
		String input;
		String postfix;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Enter an infix expression to be converted: ");
			input = sc.nextLine();
			
			
		}while(verifyString(input) == false);
		
		postfix=convertToPostfix(input);
		System.out.println(postfix);
		
		System.out.println(evaluate(postfix));
		
		
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
			} else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*'
					|| input.charAt(i) == '/' || input.charAt(i) == '%') {
				Operator o = new Operator(input.charAt(i));
				if (!st.isEmpty()) {
					Operator stack = new Operator((char) st.peek());
					while (!st.isEmpty() && o.precedence() <= stack.precedence()) {
						stack = new Operator((char) st.peek());
						postString = postString + st.pop();
					}
					st.push(input.charAt(i));
				} else {
					Operator stack = new Operator(input.charAt(i));
					while (!st.isEmpty() && !st.peek().equals('(') && o.precedence() < stack.precedence()) {
						postString = postString + st.pop();
					}
					st.push(input.charAt(i));
				}
			}
		}
		while (!st.isEmpty()) {
			postString = postString + st.pop();
		}
		return postString;
	}

	public static boolean verifyString(String input) {
		boolean flag = true;
		
	
		if (!Character.isDigit(input.charAt(0)) && !Character.isDigit(input.charAt(input.length() - 1)) 
				&& input.charAt(0)!='(' && input.charAt(input.length()-1)!= ')') {
			System.out.println("invalid input format.");
			flag = false;
		} else {

			for (int i = 1; i < input.length(); i++) {
				switch (input.charAt(i)) {
				case '1':
					break;
				case '2':
					break;
				case '3':
					break;
				case '4':
					break;
				case '5':
					break;
				case '6':
					break;
				case '7':
					break;
				case '8':
					break;
				case '9':
					break;
				case '0':
					break;
				case 'x':
					break;
				case '+':
					break;
				case '-':
					break;
				case '*':
					break;
				case '/':
					break;
				case '%':
					break;
				case '(':
					break;
				case ')':
					break;
				default:
					System.out.println("Invalid characters.");
					flag = false;
					i = input.length();
					break;
				}
			}
		}
		return flag;

	}

	public static void convertSpaced(String input) {
		ArrayList<Character> ar = new ArrayList<Character>();
		if(input.indexOf(' ') >=0){
			for(int i = 0; i < input.length(); i++){
				ar.set(i, input.charAt(i));
			}
		}
	}

	public static int evaluate(String input){
		Stack <Integer> s = new Stack<Integer>();
		Scanner sc = new Scanner(input);
		while(sc.hasNext()){
			if(sc.hasNextInt()){
				s.push(sc.nextInt());
			}else {
				int num2 = s.pop();
				int num1 = s.pop();
				String op = sc.next();
				
				if(op.equals("+")){
					s.push(num1 + num2);
				}else if (op.equals("-")){
					s.push(num1-num2);
				}else if (op.equals("*")){
					s.push(num1*num2);
					
				}else if (op.equals("/")){
					s.push(num1/num2);
				}else{
					s.push(num1%num2);
				}
			}

		}
		return s.pop();
	}
}
