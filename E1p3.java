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
			if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')))
				state = -1;
			switch (state) {
				case 0: //STATO INIZIALE
					if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 == 0))
						state = 2;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 != 0))
						state = 3;
					else
						state = 1;
					break;
					
				case 1: //POZZO
					if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9'))
						state = 1;
					break;
					
				case 2: //PARI
					if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 == 0))
						state = 2;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 != 0))
						state = 3;
					else if ((ch >= 'A' && ch <= 'K'))
						state = 4;
					else
						state = 1;
					break;
					
				case 3: //DISPARI
					if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 != 0))
						state = 3;
					else if ((ch >= '0' && ch <= '9') && ((ch - '0') % 2 == 0))
						state = 2;
					else if ((ch >= 'L' && ch <= 'Z'))
						state = 4;
					else
						state = 1;
					break;
					
				case 4: //STATO FINALE
					if ((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9'))
						state = 1;
					else
						state = 4;
					break;
			}
		}
		return state == 4;
	}
}