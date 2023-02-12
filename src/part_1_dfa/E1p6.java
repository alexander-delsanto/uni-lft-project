public class E1p6 {
	public static boolean scan(String s) {
		int a = 0;
		int b = 1;
		final int[][] states = {
				/*0*/{2, 1},
				/*1*/{2, 1},
				/*2*/{2, 3},
				/*3*/{3, 4},
				/*4*/{4, 5},
				/*5*/{5, 1}
		};

		int state = 0;
		int i = 0;

		while (state >= 0 && i < s.length()) {
			final char _char = s.charAt(i++);

			if (isA(_char))
				state = states[state][a];
			else if (isB(_char))
				state = states[state][b];
			else
				state = -1;
		}

		return state == 2 || state == 3 || state == 4 || state == 5;
	}

	public static void main(String[] args) {
		for (String i : args)
			System.out.println(i + " " + (scan(i) ? "OK" : "NO"));
	}

	public static boolean isA(char c) {
		return c == 'a';
	}

	public static boolean isB(char c) {
		return c == 'b';
	}

}
