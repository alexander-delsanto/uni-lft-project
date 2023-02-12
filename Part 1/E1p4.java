public class E1p4 {
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
				case 0 -> getstate(c, 1, 2, 0, -1, -1, -1);
				case 1 -> getstate(c, 1, 2, 3, 5, -1, -1);
				case 2 -> getstate(c, 1, 2, 4, -1, 5, -1);
				case 3 -> getstate(c, -1, -1, 3, 5, -1, -1);
				case 4 -> getstate(c, -1, -1, 4, -1, 5, -1);
				case 5 -> getstate(c, -1, -1, 6, -1, -1, 5);
				case 6 -> getstate(c, -1, -1, 6, 5, 5, -1);
				case -1 -> getstate(c, -1, -1, -1, -1, -1, -1);
			}
		}
		return state == 5 || state == 6;
	}

	public static int getstate(char c, int isEven, int isOdd, int isSpace, int isGA, int isGB, int isMin) {
		if(c == '0' || c == '2' || c == '4' || c == '6' || c == '8') return isEven;
		if(c == '1' || c == '3' || c == '5' || c == '7' || c == '9') return isOdd;
		if(c == ' ') return isSpace;
		if(c >= 'a' || c <= 'z') return isMin;
		if(c >= 'A' || c <= 'K') return isGA;
		if(c >= 'L' || c <= 'Z') return isGB;
		return -1;
	}
}