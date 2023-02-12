package part_1_dfa;

public class E1p9 {
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while(state >= 0 && i < s.length()) {
			final char c = s.charAt(i++);

			switch (state) {
				case 0 ->state = getState(c, 1, -1, -1);
				case 1 ->state = getState(c, -1 , 2, -1);
				case 2 ->state = getState(c, 2, 3, 2);
				case 3 ->state = getState(c, 4, 3, 2);
				case 4 ->state = getState(c, -1, -1, -1);
			}
		}
		return state == 4;
	}

	public static void main(String[] args) {
		for(String i : args)
			System.out.println(scan(i) ? "OK" : "NO");
	}

	public static int getState(char c, int slash, int star, int a) {
		if(c == '/') return slash;
		if(c == '*') return star;
		else return a;
	}
}
