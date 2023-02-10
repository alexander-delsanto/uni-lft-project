import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    private void checkGuide(int func) {
        switch (func) {
            case statlistp:
                switch(look.tag) {
                    case Tag.ASSIGN, Tag.PRINT, Tag.READ, Tag.WHILE, Tag.COND, '{', ';' -> {}
                    default -> error("Expected statement, condition or ;");
                }
                break;
            case stat:
        }
    }
    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
        throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else error("syntax error" + look);
    }

    void prog() {
        switch(look.tag) {
            case Tag.ASSIGN, Tag.PRINT, Tag.READ, Tag.WHILE, Tag.COND, '{' -> {}
            default -> error("Expected statement or condition");
        }

        statlist();
        match(Tag.EOF);
    }

    void statlist() {
        switch(look.tag) {
            case Tag.ASSIGN, Tag.PRINT, Tag.READ, Tag.WHILE, Tag.COND, '{', ';' -> {}
            default -> error("Expected statement, condition or ;");
        }

        stat();
        statlistp();
    } 

    void statlistp() {
        switch(look.tag) {
            case Tag.ASSIGN, Tag.PRINT, Tag.READ, Tag.WHILE, Tag.COND, '{', ';' -> {}
            default -> error("Expected statement, condition or ;");
        }

        switch(look.tag) {
            case ';':
                match(';');
                stat();
                statlistp();
                break;
            default:
                break;
        }
    } 

    void stat() {
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
                exprlist();
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
                condp();
                match(Tag.END);
                break;    
            case '{':
                match('{');
                statlist();
                match('}');
                break;
        }
    } 
    void condp() {
        switch(look.tag) {
            case Tag.ELSE, Tag.END-> {}
            default -> error("Expected else or end");
        }

        switch(look.tag) {
            case Tag.ELSE:
                match(Tag.ELSE);
                stat();
                break;
            default:
                break;
        }
    }
    void idlist() {
        match(Tag.ID);
        idlistp();
    }

    void idlistp() {
        switch(look.tag) {
            case ']', ',' -> {}
            default -> error("Expected ] or ,");
        }
        switch(look.tag) {
            case ',':
                match(',');
                break;
            default:
                break;
        }
    } 

    void optlist() {
        switch(look.tag) {
            case Tag.OPTION -> {}
            default -> error("Expected option");
        }
        optitem();
        optlistp();
    }
    void optlistp() {
        switch(look.tag) {
            case Tag.OPTION -> {}
            default -> error("Expected option");
        }
        switch(look.tag) {
            case Tag.OPTION:
                match(Tag.OPTION);
                optitem();
                optlistp();
                break;
            default:
                break;
        }
    } 
    void optitem() {
        switch(look.tag) {
            case Tag.OPTION -> {}
            default -> error("Expected option");
        }
        match(Tag.OPTION);
        match('(');
        bexpr();
        match(')');
        match(Tag.DO);
        stat();
    } 
    void bexpr() {
        switch(look.tag) {
            case Tag.RELOP -> {}
            default -> error("Expected relational operator");
        }
        match(Tag.RELOP);
        expr();
        expr();
    } 
    void expr() {
        switch(look.tag) {
            case '+': 
                match('+');
                match('(');
                exprlist();
                match(')');
                break;
            case '*':
                match('*');
                match('(');
                exprlist();
                match(')');
                break;
            case '-': 
                match('-'); 
                expr();
                expr();
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
                error("Expected operator, number or identifier");
        }
    } 
    void exprlist() {
        switch(look.tag)
        expr();
        exprlistp();
    }
    void exprlistp() {
        switch(look.tag) {
            case ',':
                match(',');
                expr();
                exprlistp();
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "file"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}