import java.io.*;

public class Parser {
	private Lexer lex;
	private BufferedReader pbr;
	private Token look;

	public Parser(Lexer l, BufferedReader br) {
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
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				expr();
				match(Tag.EOF);
				break;
			default: 
				error("found " + look + " in start with guide {(, NUM}");
		}
	}

	private void expr() {
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				term();
				exprp();
				break;
			default: 
				error("found " + look + " in expr with guide {(, NUM}");
		}
	}

	private void exprp() {
		switch (look.tag) {
			case '+':
				match('+');
				term();
				exprp();
				break;
			case '-':
				match('-');
				term();
				exprp();
				break;
			case ')':
			case Tag.EOF:
				break;
			default: 
				error("found " + look + " in exprp with guide {+, -, ), EOF}");
		}
	}

	private void term() {
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				fact();
				termp();
				break;
			default: 
				error("found " + look + " in term with guide {(, NUM}");
		}
	}

	private void termp() {
		switch (look.tag) {
			case '*':
				match('*');
				fact();
				termp();
				break;
			case '/':
				match('/');
				fact();
				termp();
				break;
			case '+':
			case '-':
			case ')':
			case Tag.EOF:
				break;
			default: 
				error("found " + look + " in termp with guide {*, /, +, -, ), EOF}");
		}
	}

	private void fact() {
		switch (look.tag) {
			case '(':
				match('(');
				expr();
				match(')');
				break;
			case Tag.NUM:
				match(Tag.NUM);
				break;
			default:
				error("found " + look + " in fact with guide {(, NUM}");
		}
	}
		
	public static void main(String[] args) {
		Lexer lex = new Lexer();
		String path = "input.txt"; // il percorso del file da leggere
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			Parser parser = new Parser(lex, br);
			parser.start();
			System.out.println("Input OK");
			br.close();
		} catch (IOException e) {e.printStackTrace();}
	}
}