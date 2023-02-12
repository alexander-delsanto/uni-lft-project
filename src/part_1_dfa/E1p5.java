package part_1_dfa;

public class E1p5 {
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
			if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')))
				state = -1;
			switch (state) {
				case 0: //STATO INIZIALE
					if (ch >= 'A' && ch <= 'K')
						state = 2;
					else if (ch >= 'L' && ch <= 'Z')
						state = 3;
					else
						state = 1;
					break;

				case 1: //POZZO
					if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9'))
						state = 1;
					break;

				case 2: //COGNOMI A-K
					if (ch >= 'a' && ch <= 'z')
						state = 2;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 == 0))
						state = 4;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 != 0))
						state = 5;
					else
						state = 1;
					break;

				case 3: //COGNOMI L-Z
					if (ch >= 'a' && ch <= 'z')
						state = 3;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 == 0))
						state = 6;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 != 0))
						state = 7;
					else
						state = 1;
					break;

				case 4: //A-K PARI (STATO FINALE)
					if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 == 0))
						state = 4;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 != 0))
						state = 5;
					else
						state = 1;
					break;

				case 5: //A-K DISPARI
					if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 == 0))
						state = 4;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 != 0))
						state = 5;
					else
						state = 1;
					break;

				case 6: //L-Z PARI
					if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 == 0))
						state = 6;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 != 0))
						state = 7;
					else
						state = 1;
					break;

				case 7: //L-Z DISPARI (STATO FINALE)
 					if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 == 0))
						state = 6;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 != 0))
						state = 7;
					else
						state = 1;
					break;

			}
		}
		return (state == 4 || state == 7);
	}
}