public class es1_8 {
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while(state >= 0 && i < s.length()) {
			final char c = s.charAt(i++);

			switch (state) {
				case 0 -> state = getState(c, 2, 1, -1, 3);
				case 1 -> state = getState(c, 2, -1, -1, 3);
				case 2 -> state = getState(c, 2, -1, 5, 3);
				case 3 -> state = getState(c, 4, -1, -1, -1);
				case 4 -> state = getState(c, 4, -1, 5, -1);
				case 5 -> state = getState(c, 7, 6, -1, 8);
				case 6 -> state = getState(c, 7, -1, -1, 8);
				case 7 -> state = getState(c, 7, -1, -1, 8);
				case 8 -> state = getState(c, 9, -1, -1, -1);
				case 9 -> state = getState(c, 9, -1, -1, -1);
			}
		}
		return state == 2 || state == 4 || state == 7 || state == 9;
	}

	public static void main(String[] args) {
		for(String i : args)
			System.out.println(scan(i) ? "OK" : "NO");
	}

	public static boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}

	public static boolean isSign(char c) {
		return c == '+' || c == '-';
	}

	public static boolean is_e(char c) {
		return c == 'e';
	}

	public static boolean isDot(char c) {
		return c == '.';
	}

	public static int getState(char c, int num, int sig, int e, int dot) {
		if(isNumber(c))
			return num;
		if(isSign(c))
			return sig;
		if(is_e((c)))
			return e;
		if(isDot(c))
			return dot;
		return -1;
	}
}
