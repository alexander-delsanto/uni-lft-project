public class E1p2 {
	public static void main(String[] args) {
		if (args.length == 0)
			System.out.println("You didn't enter any argument.");
		for(int j = 0; j < args.length; j++) {
			System.out.println(args[j] + " " + (scan(args[j]) ? "OK" : "NOPE"));
		}
	}
	
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while(state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			state = switch (state) {
				case 0 -> getstate(ch, 2, -1, 1);
				case 1 -> getstate(ch, 1, 1, 1);
				case 2 -> getstate(ch, 2, 1, 1);
				case -1 -> getstate(ch, -1,-1,-1);
			}
		}
		return state == 1;
	}

	public static int getstate(char c, int is_, int isNum, int isLetter) {
		if(c == '_') return is_;
		if(c >= '0' && c <= '9') return isNum;
		if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) return isLetter;
	}
}
