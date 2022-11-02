import java.io.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';

    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    private Token tokenToWord(BufferedReader br) {
        String s = "";
        if (peek == '_') {
            do {
                s += peek;
                readch(br);
            } while(peek == '_');
        } if (!Character.isLetterOrDigit(peek)) {
            System.err.println("Identifier cannot be composed by only '_'.");
            return null;
        }
        do {
            s += peek;
            readch(br);
        } while(Character.isLetterOrDigit(peek));

        // Key word
        return switch (s) {
            case "assign" -> Word.assign;
            case "to" -> Word.to;
            case "conditional" -> Word.conditional;
            case "option" -> Word.option;
            case "do" -> Word.dotok;
            case "else" -> Word.elsetok;
            case "while" -> Word.whiletok;
            case "begin" -> Word.begin;
            case "end" -> Word.end;
            case "print" -> Word.print;
            case "read" -> Word.read;
            default -> new Word(Tag.ID, s);
        };

    }

    private void inLineComment(BufferedReader br) {
        boolean found = false;
        while(!found) {
            readch(br);
            if (peek == '\n' || peek == (char) -1)
                found = true;
        }
        peek = ' ';
    }

    private void comment(BufferedReader br) {
        boolean found = false;
        while(!found) {
            readch(br);
            if (peek == '*') {
                while (peek == '*') {
                    readch(br);
                    if (peek == '/')
                        found = true;
                }
            }
        }
        peek = ' ';
    }

    private Token tokenToNumber(BufferedReader br) {
        int val = peek - '0';
        readch(br);

        if (val == 0 && Character.isDigit(peek)) {
            System.err.println("Erroneous character after 0: " + peek);
        }

        while (Character.isDigit(peek)) {
            val *= 10 + peek - '0';
            readch(br);
        }

        if (Character.isLetter(peek)) {
            System.err.println("Erroneous character after number : " + peek);
            return null;
        }

        return new NumberTok(val);
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }

        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

            // ... Gestione dei casi ( ) [ ] { } + - * / ; , ... //

            case '(':
                peek = ' ';
                return Token.lpt;

            case ')':
                peek = ' ';
                return Token.rpt;

            case '[':
                peek = ' ';
                return Token.lpq;

            case ']':
                peek = ' ';
                return Token.rpq;

            case '{':
                peek = ' ';
                return Token.lpg;

            case '}':
                peek = ' ';
                return Token.rpg;

            case '+':
                peek = ' ';
                return Token.plus;

            case '-':
                peek = ' ';
                return Token.minus;

            case '*':
                peek = ' ';
                return Token.mult;

            case '/':
                readch(br);
                if (peek == '/') {
                    inLineComment(br);
                    return Token.comment;
                }
                else if (peek == '*') {
                    comment(br);
                    return Token.comment;
                }
                else {    
                    peek = ' ';
                    return Token.div;
                }

            case ';':
                peek = ' ';
                return Token.semicolon;

            case ',':
                peek = ' ';
                return Token.comma;

            // ... Fine gestione dei casi ( ) [ ] { } + - * / ; , ... //

            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character after & : " + peek);
                    return null;
                }

            // ... Gestione dei casi di || < > <= >= == <> ... //

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character after | : " + peek);
                    return null;
                }

            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                } else if (peek == '>') {
                    peek = ' ';
                    return Word.ne;
                } else if (peek == ' ') {
                    return Word.lt;
                } else {
                    System.err.println("Erroneous character after < : " + peek);
                    return null;
                }

            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else if (peek == ' ') {
                    return Word.gt;
                } else {
                    System.err.println("Erroneous character after > : " + peek);
                    return null;
                }

            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character after = : " + peek);
                    return null;
                }

            // ... Fine gestione dei casi di || < > <= >= == <> ... //

            case (char) -1:
                return new Token(Tag.EOF);

            default:
                if (Character.isLetter(peek) || peek == '_') {
                    return tokenToWord(br);
                } else if (Character.isDigit(peek)) {
                    return tokenToNumber(br);
                } else {
                    System.err.println("Erroneous character: " + peek);
                    return null;
                }
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "In.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            System.out.print("Scan: ");
            do {
                tok = lex.lexical_scan(br);
                System.out.print(tok + " ");
            } while (tok.tag != Tag.EOF);
            System.out.println();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
