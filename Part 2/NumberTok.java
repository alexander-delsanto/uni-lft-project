public class NumberTok extends Token {

    private int val;

    public NumberTok(int val) {
        super(Tag.NUM);
        this.val = val;
    }

    public String toString() {
        return "<" + tag + ", " + val + ">";
    }
}
