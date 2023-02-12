public class E1p3 {
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
				case 0 -> getstate(ch, 1, 2, -1, -1);
				case 1 -> getstate(ch, 1, 2, 3, -1);
				case 2 -> getstate(ch, 1, 2, -1, 3);
				case 3 -> getstate(ch, -1, -1, 3, 3);
				default->-1;
			};
		}
		return state == 3;
	}

	public static int getstate(char c, int isEven, int isOdd, int isGA, int isGB) {
		if(c == '0' || c == '2' || c == '4' || c == '6' || c == '8') return isEven;
		if(c == '1' || c == '3' || c == '5' || c == '7' || c == '9') return isOdd;
		if((c >= 'a' && c <= 'k') || (c >= 'A' && c <= 'K')) return isGA;
		if((c >= 'l' && c <= 'z') || (c >= 'L' && c <= 'Z')) return isGB;
		return -1;
	}
}