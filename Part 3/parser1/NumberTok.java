public class NumberTok extends Token {
	public int number;
	public NumberTok(int number) {super(Tag.NUM); this.number = number;}
	public String toString() {return "<" + tag + ", " + number + ">";}
	public int getNumber() {return number;}
}
