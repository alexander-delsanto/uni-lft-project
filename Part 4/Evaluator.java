import java.io.*;

public class Evaluator {
	private Lexer lex;
	private BufferedReader pbr;
	private Token look;

	public Evaluator(Lexer l, BufferedReader br) {
		lex = l;
		pbr = br;
		move();
	}

	private void move() {
		look = lex.lexical_scan(pbr);
		System.out.println("token = " + look);
	}

	private void error(String s) {
		throw new Error("near line " + Lexer.line + ": " + s);
	}

	private void match(int t) {
		if (look.tag == t) {
			if (look.tag != Tag.EOF) move();
		} else error("syntax error");
	}

	public void start() {
		int expr_val;
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				expr_val = expr();
				match(Tag.EOF);
				System.out.println("Result: " + expr_val);
				break;
			default:
				error("found " + look + " in start with guide {(, NUM}");
		}
	}

	private int expr() {
		int exprp_i, expr_val = 0;
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				exprp_i = term();
				expr_val = exprp(exprp_i);
				break;
			default:
				error("found " + look + " in expr with guide {(, NUM}");
		}
		return expr_val;
	}

	private int exprp(int exprp_i) {
		int exprp_val = 0;
		switch (look.tag) {
			case '+':
				match('+');
				exprp_i += term();
				exprp_val = exprp(exprp_i);
				break;
			case '-':
				match('-');
				exprp_i -= term();
				exprp_val = exprp(exprp_i);
				break;
			case ')':
			case Tag.EOF:
				exprp_val = exprp_i;
				break;
			default:
				error("found " + look + " in exprp with guide {+, -, ), EOF}");
		}
		return exprp_val;
	}

	private int term() {
		int termp_i, term_val = 0;
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				termp_i = fact();
				term_val = termp(termp_i);
				break;
			default:
				error("found " + look + " in term with guide {(, NUM}");
		}
		return term_val;
	}

	private int termp(int termp_i) {
		int termp_val = 0;
		switch (look.tag) {
			case '*':
				match('*');
				termp_i *= fact();
				termp_val = termp(termp_i);
				break;
			case '/':
				match('/');
				termp_i /= fact();
				termp_val = termp(termp_i);
				break;
			case '+':
			case '-':
			case ')':
			case Tag.EOF:
				termp_val = termp_i;
				break;
			default:
				error("found " + look + " in termp with guide {*, /, +, -, ), EOF}");
		}
		return termp_val;
	}

	private int fact() {
		int fact_val = 0;
		switch (look.tag) {
			case '(':
				match('(');
				fact_val = expr();
				match(')');
				break;
			case Tag.NUM:
				fact_val = ((NumberTok)look).getNumber();
				match(Tag.NUM);
				break;
			default:
				error("found " + look + " in fact with guide {(, NUM}");
		}
		return fact_val;
	}

	public static void main(String[] args) {
		Lexer lex = new Lexer();
		String path = "input.txt"; // il percorso del file da leggere
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			Evaluator Evaluator = new Evaluator(lex, br);
			Evaluator.start();
			br.close();
		} catch (IOException e) {e.printStackTrace();}
	}
}
