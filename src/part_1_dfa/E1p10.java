public class E1p10 {
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while(state >= 0 && i < s.length()) {
			final char c = s.charAt(i++);

			switch (state) {
				case 0 ->state = getState(c, 0, 0, 1);
				case 1 ->state = getState(c, 0, 2, 1);
				case 2 ->state = getState(c, 2, 3, 2);
				case 3 ->state = getState(c, 2, 3, 4);
				case 4 ->state = getState(c, 0, 0, 1);
			}
		}
		return state == 0 || state == 1 || state == 4;
	}

	public static void main(String[] args) {
		for(String i : args)
			System.out.println(scan(i) ? "OK" : "NO");
	}

	public static int getState(char c, int a, int star, int slash) {
		if(c == '/') return slash;
		if(c == '*') return star;
		if(c == 'a') return a;
		return -1;
	}
}
