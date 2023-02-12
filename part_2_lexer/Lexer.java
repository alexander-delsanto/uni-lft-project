package part_2_lexer;

import java.io.*;

public class Lexer {

	public static int line = 1;
	private char peek = ' ';
	public static final char EOF = (char) -1;

	private void readch(BufferedReader br) {
		try {
			peek = (char) br.read();
		} catch (IOException exc) {
			peek = (char) -1; // ERROR
		}
	}

	private NumberTok getNumberToken(BufferedReader br) {
		int num = peek - '0';
		readch(br);

		if (num == 0 && Character.isDigit(peek))
			throw new Error("Erroneous character after 0: " + peek);

		while (Character.isDigit(peek)) {
			num = num * 10 + peek - '0';
			readch(br);
		}

		if (Character.isLetter(peek) || peek == '_')
			throw new Error("Erroneous character after number: " + peek);

		return new NumberTok(num);
	}

	private Token getWordToken(BufferedReader br) {
		String s = "";
		boolean invalidWord = true;

		do {
			s += peek;
			if (invalidWord && peek != '_')
				invalidWord = false;
			readch(br);
		} while(Character.isLetterOrDigit(peek) || peek == '_');

		if (invalidWord) throw new Error("Found invalid word (only '_').");

		switch(s) {
		case "assign": return Word.assign;
		case "to": return Word.to;
		case "conditional": return Word.conditional;
		case "option": return Word.option;
		case "do": return Word.dotok;
		case "else": return Word.elsetok;
		case "while": return Word.whiletok;
		case "begin": return Word.begin;
		case "end": return Word.end;
		case "print": return Word.print;
		case "read": return Word.read;
		default: return new Word(Tag.ID, s);
		}

	}


	public Token lexical_scan(BufferedReader br) {
		while (true) {
			while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
				if (peek == '\n') line++;
				readch(br);
			}

			switch (peek) {
			case '(': peek = ' '; return Word.lpt;
			case ')': peek = ' '; return Word.rpt;
			case '[': peek = ' '; return Word.lpq;
			case ']': peek = ' '; return Word.rpq;
			case '{': peek = ' '; return Word.lpg;
			case '}': peek = ' '; return Word.rpg;
			case '+': peek = ' '; return Token.plus;
			case '-': peek = ' '; return Token.minus;
			case '*': peek = ' '; return Token.mult;
			case ';': peek = ' '; return Token.semicolon;
			case ',': peek = ' '; return Token.comma;
			case '!': peek = ' '; return Token.not;

			case '/':
				readch(br);
				if (peek == '/') {
					while (peek != '\n' && peek != EOF) readch(br);
				} else if (peek == '*') {
					char prev;
					peek = ' ';
					do {
						prev = peek;
						readch(br);
					} while (peek != EOF && !(prev == '*' && peek =='/'));
					if (peek == EOF)
						throw new Error("A comment starting with \"/*\" was never closed.");
					peek = ' ';
				} else
					return Token.div;
				break;


			case '&':
				readch(br);
				if (peek == '&') {peek = ' '; return Word.and;}
				else throw new Error("Erroneous character after & : "  + peek );

			case '|':
				readch(br);
				if (peek == '|') {peek = ' '; return Word.or;}
				else throw new Error("Erroneous character after | : "  + peek );

			case '<':
				readch(br);
				if (peek == ' ') {return Word.lt;}
				else if (peek == '=') {peek = ' '; return Word.le;}
				else if (peek == '>') {peek = ' '; return Word.ne;}
				else System.err.println("Erroneous character after < : "  + peek );

			case '>':
				readch(br);
				if (peek == ' ') {return Word.gt;}
				else if (peek == '=') {peek = ' '; return Word.ge;}
				else throw new Error("Erroneous character after > : "  + peek );

			case '=':
				readch(br);
				if (peek == '=') {peek = ' '; return Word.eq;}
				else throw new Error("Erroneous character after > : "  + peek );

			case EOF: return new Token(Tag.EOF);

			default:
				if (Character.isLetter(peek) || peek == '_') {return getWordToken(br);}
				else if (Character.isDigit(peek)) {return getNumberToken(br);}
				else throw new Error("Erroneous character: " + peek );
			}
		}
	}

	public static void main(String[] args) {
		Lexer lex = new Lexer();
		String path = "input.lft";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			Token tok;
			do {
				tok = lex.lexical_scan(br);
				System.out.print(tok + " ");
			} while (tok.tag != Tag.EOF);
			System.out.println();
			br.close();
		} catch (IOException e) {e.printStackTrace();}
	}

}
