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

	public void prog() {
		switch(look.tag) {
			case Tag.ASSIGN:
			case Tag.PRINT:
			case Tag.READ:
			case Tag.WHILE:
			case Tag.COND:
			case '{':
				statlist();
				match(Tag.EOF);
				break;
		default:
			error("found " + look + " in prog");
		}
	}

	public void statlist() {
		switch(look.tag) {
			case Tag.ASSIGN:
			case Tag.PRINT:
			case Tag.READ:
			case Tag.WHILE:
			case Tag.COND:
			case '{':
				stat();
				statlistp();
				break;
		default:
			error("found " + look + " in statlist");
		}
	}

	public void statlistp() {
		switch(look.tag) {
			case ';':
				match(';');
				stat();
				statlistp();
				break;
			case Tag.EOF:
			case '}':
				break;
			default:
				error("found " + look + " in statlist");
		}
	}

	public void stat() {
		switch(look.tag) {
			case Tag.ASSIGN:
				match(Tag.ASSIGN);
				expr();
				match(Tag.TO);
				idlist();
				break;
			case Tag.PRINT:
				match(Tag.PRINT);
				match('[');
				exprlist();
				match(']');
				break;
			case Tag.READ:
				match(Tag.READ);
				match('[');
				idlist();
				match(']');
				break;
			case Tag.WHILE:
				match(Tag.WHILE);
				match('(');
				bexpr();
				match(')');
				stat();
				break;
			case Tag.COND:
				match(Tag.COND);
				match('[');
				optlist();
				match(']');
				statp();
				break;
			case '{':
				match('{');
				statlist();
				match('}');
				break;
			default:
				error("found " + look + " in stat");
		}
	}

	public void statp() {
		switch(look.tag) {
			case Tag.END:
				match(Tag.END);
				break;
			case Tag.ELSE:
				match(Tag.ELSE);
				stat();
				match(Tag.END);
				break;
			default:
				error("found " + look + " in statp");
		}
	}

	public void idlist() {
		switch(look.tag) {
			case Tag.ID:
				match(Tag.ID);
				idlistp();
				break;
			default:
				error("found " + look + " in idlist");
		}
	}

	public void idlistp() {
		switch(look.tag) {
			case ',':
				match(',');
				match(Tag.ID);
				idlistp();
				break;
			case Tag.EOF:
			case Tag.END:
			case Tag.OPTION:
			case ']':
			case '}':
			case ';':
				break;
			default:
				error("found " + look + " in idlistp");
		}
	}

	public void optlist() {
		switch(look.tag) {
			case Tag.OPTION:
				optitem();
				optlistp();
				break;
			default:
				error("found " + look + " in optlist");
		}
	}

	public void optlistp() {
		switch(look.tag) {
			case Tag.OPTION:
				optitem();
				optlistp();
				break;
			case ']':
				break;
			default:
				error("found " + look + " in optlistp");
		}
	}

	public void optitem() {
		switch(look.tag) {
			case Tag.OPTION:
				match(Tag.OPTION);
				match('(');
				bexpr();
				match(')');
				match(Tag.DO);
				stat();
				break;
			default:
				error("found " + look + " in optitem");
		}
	}

	public void bexpr() {
		switch(look.tag) {
			case Tag.RELOP:
				match(Tag.RELOP);
				expr();
				expr();
				break;
			default:
				error("found " + look + " in bexpr");
		}
	}

	public void expr() {
		switch(look.tag) {
			case '+':
				match('+');
				match('(');
				exprlist();
				match(')');
				break;
			case '-':
				match('-');
				expr();
				expr();
				break;
			case '*':
				match('*');
				match('(');
				exprlist();
				match(')');
				break;
			case '/':
				match('/');
				expr();
				expr();
				break;
			case Tag.NUM:
				match(Tag.NUM);
				break;
			case Tag.ID:
				match(Tag.ID);
				break;
			default:
				error("found " + look + " in expr");
		}
	}

	public void exprlist() {
		switch(look.tag) {
			case '+':
			case '-':
			case '*':
			case '/':
			case Tag.NUM:
			case Tag.ID:
				expr();
				exprlistp();
				break;
			default:
				error("found " + look + " in exprlist");
		}
	}

	public void exprlistp() {
		switch(look.tag) {
			case ',':
				match(',');
				expr();
				exprlistp();
				break;
			case ')':
			case ']':
				break;
			default:
				error("found " + look + " in exprlistp");
		}
	}

	public static void main(String[] args) {
		Lexer lex = new Lexer();
		String path = "input.txt"; // il percorso del file da leggere
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			Parser parser = new Parser(lex, br);
			parser.prog();
			System.out.println("Input OK");
			br.close();
		} catch (IOException e) {e.printStackTrace();}
	}
}