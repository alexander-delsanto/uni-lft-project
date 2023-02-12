package part_5_1_translator;

import part_2_lexer.*;
import part_5_0_support.*;

import java.io.*;

enum _exprEnum {
	sum, mul, print
};

public class Translator {
	private Lexer lex;
	private BufferedReader pbr;
	private Token look;

	SymbolTable st = new SymbolTable();
	CodeGenerator code = new CodeGenerator();
	int count=0;

	public Translator(Lexer l, BufferedReader br) {
		lex = l;
		pbr = br;
		move();
	}

	private void move() {
		look = lex.lexical_scan(pbr);
		System.out.println("token = " + look);
	}

	private Error error(String s) {
		return new Error("near line " + Lexer.line + ": " + s);
	}

	private void match(int t) {
		if (look.tag == t) {
			if (look.tag != Tag.EOF) move();
		} else throw error("syntax error");
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
				try {
					code.toJasmin();
				}
				catch(java.io.IOException e) {
					System.out.println("IO error\n");
				};
				break;
			default:
				throw error("found " + look + " in prog");
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
			throw error("found " + look + " in statlist");
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
				throw error("found " + look + " in statlist");
		}
	}

	public void stat() {
		switch(look.tag) {
			case Tag.ASSIGN:
				match(Tag.ASSIGN);
				expr();
				match(Tag.TO);
				idlist(true);
				break;
			case Tag.PRINT:
				match(Tag.PRINT);
				match('[');
				exprlist(_exprEnum.print);
				match(']');
				break;
			case Tag.READ:
				match(Tag.READ);
				match('[');
				idlist(false);
				match(']');
				break;
			case Tag.WHILE:
				int lwhile_end = code.newLabel();
				int lwhile_start = code.newLabel();
				code.emitLabel(lwhile_start);
				match(Tag.WHILE);
				match('(');
				bexpr(lwhile_end);
				match(')');
				stat();
				code.emit(OpCode.GOto, lwhile_start);
				code.emitLabel(lwhile_end);
				break;
			case Tag.COND:
				match(Tag.COND);
				match('[');
				int lcond_end = code.newLabel();
				optlist(lcond_end);
				match(']');
				statp();
				code.emitLabel(lcond_end);
				break;
			case '{':
				match('{');
				statlist();
				match('}');
				break;
			default:
				throw error("found " + look + " in stat");
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
				throw error("found " + look + " in statp");
		}
	}

	private void idlist(boolean isAssign) {
		switch(look.tag) {
		case Tag.ID:
			/* Finds the address of the ID the hashmap and creates
			 it if it doesn't exists */
			int id_addr = st.lookupAddress(((Word)look).lexeme);
			if (id_addr == -1) {
				id_addr = count;
				st.insert(((Word)look).lexeme,count++);
			}
			match(Tag.ID);
			/* If isAssign and there is more than one ID in
			 the list dup is needed before the istore instruction */
			if(isAssign && look.tag==',')
				code.emit(OpCode.dup);
			/* Read */
			else if(!isAssign) {
				code.emit(OpCode.invokestatic, 0);
			}
			code.emit(OpCode.istore, id_addr);
			idlistp(isAssign);
			break;
		default:
			throw error("found " + look + " in idlist");
		}
	}

	public void idlistp(boolean isAssign) {
		switch(look.tag) {
			case ',':
				match(',');
				int id_addr = st.lookupAddress(((Word)look).lexeme);
				if (id_addr==-1) {
					id_addr = count;
					st.insert(((Word)look).lexeme,count++);
				}
				match(Tag.ID);
				if(isAssign && look.tag==',')
					code.emit(OpCode.dup);
				else if(!isAssign) {
					code.emit(OpCode.invokestatic, 0);
				}
				code.emit(OpCode.istore, id_addr);
				idlistp(isAssign);
				break;
			case Tag.EOF:
			case Tag.END:
			case Tag.OPTION:
			case ']':
			case '}':
			case ';':
				break;
			default:
				throw error("found " + look + " in idlistp");
		}
	}

	public void optlist(int lcond_end) {
		switch(look.tag) {
			case Tag.OPTION:
				int lnext_opt = code.newLabel();
				optitem(lnext_opt);
				code.emit(OpCode.GOto, lcond_end);
				code.emitLabel(lnext_opt);
				optlistp(lcond_end);
				break;
			default:
				throw error("found " + look + " in optlist");
		}
	}

	public void optlistp(int lcond_end) {
		switch(look.tag) {
			case Tag.OPTION:
				int lnext_opt = code.newLabel();
				optitem(lnext_opt);
				code.emit(OpCode.GOto, lcond_end);
				code.emitLabel(lnext_opt);
				optlistp(lcond_end);
				break;
			case ']':
				break;
			default:
				throw error("found " + look + " in optlistp");
		}
	}

	public void optitem(int lnext_opt) {
		switch(look.tag) {
			case Tag.OPTION:
				match(Tag.OPTION);
				match('(');
				bexpr(lnext_opt);
				match(')');
				match(Tag.DO);
				stat();
				break;
			default:
				throw error("found " + look + " in optitem");
		}
	}

	public void bexpr(int lend_stat) {
		switch(look.tag) {
			case Tag.RELOP:
				String temp = ((Word)look).lexeme;
				match(Tag.RELOP);
				expr();
				expr();
				switch(temp) {
					case "<":
						code.emit(OpCode.if_icmpge, lend_stat);
						break;
					case ">":
						code.emit(OpCode.if_icmple, lend_stat);
						break;
					case "<=":
						code.emit(OpCode.if_icmpgt, lend_stat);
						break;
					case ">=":
						code.emit(OpCode.if_icmplt, lend_stat);
						break;
					case "==":
						code.emit(OpCode.if_icmpne, lend_stat);
						break;
					case "<>":
						code.emit(OpCode.if_icmpeq, lend_stat);
						break;
					default:
						break;
				}
				break;
			default:
				throw error("found " + look + " in bexpr");
		}
	}

	public void expr() {
		switch(look.tag) {
			case '+':
				match('+');
				match('(');
				exprlist(_exprEnum.sum);
				match(')');
				break;
			case '-':
				match('-');
				expr();
				expr();
				code.emit(OpCode.isub);
				break;
			case '*':
				match('*');
				match('(');
				exprlist(_exprEnum.mul);
				match(')');
				break;
			case '/':
				match('/');
				expr();
				expr();
				code.emit(OpCode.idiv);
				break;
			case Tag.NUM:
				code.emit(OpCode.ldc, ((NumberTok)look).number);
				match(Tag.NUM);
				break;
			case Tag.ID:
				int addr = st.lookupAddress(((Word)look).lexeme);
				if (addr == -1)
					throw error("Variable " + ((Word)look).lexeme + " not initialized.");
				code.emit(OpCode.iload, addr);
				match(Tag.ID);
				break;
			default:
				throw error("found " + look + " in expr");
		}
	}

	public void exprlist(_exprEnum instruction) {
		switch(look.tag) {
			case '+':
			case '-':
			case '*':
			case '/':
			case Tag.NUM:
			case Tag.ID:
				expr();
				if(instruction == _exprEnum.print)
					code.emit(OpCode.invokestatic, 1);
				exprlistp(instruction);
				break;
			default:
				throw error("found " + look + " in exprlist");
		}
	}

	public void exprlistp(_exprEnum instruction) {
		switch(look.tag) {
			case ',':
				match(',');
				expr();
				switch(instruction) {
					case sum:
						code.emit(OpCode.iadd);
						break;
					case mul:
						code.emit(OpCode.imul);
						break;
					case print:
						code.emit(OpCode.invokestatic, 1);
						break;
				}
				exprlistp(instruction);
				break;
			case ')':
			case ']':
				break;
			default:
				throw error("found " + look + " in exprlistp");
		}
	}

	public static void main(String[] args) throws IOException {
		Lexer lex = new Lexer();
		String path = "input.lft"; // il percorso del file da leggere

		BufferedReader br = new BufferedReader(new FileReader(path));
		Translator translator = new Translator(lex, br);
		translator.prog();
		System.out.println("Input OK");
		br.close();
	}
}
