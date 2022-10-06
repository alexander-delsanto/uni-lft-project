public class E1p2 {
	public static void main(String[] args) {
		if (args.length == 0)
			System.out.println("You didn't enter any argument.");
		for(int j = 0; j < args.length; j++) {
			System.out.println(args[j] + " " + (scan(args[j]) ? "OK" : "NOPE"));
		}
		/*try {
			System.out.println(scan(args[0]) ? "OK" : "NOPE");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("You didn't enter any argument.");
		}*/
	}
	
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while(state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);
			if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9') || (ch == '_')))
				state = -1;
			switch (state) {
				case 0:
					if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
						state = 3;
					else if (ch == '_')
						state = 2;
					else if (ch >= '0' && ch <= '9')
						state = 1;
					break;
					
				case 1:
					if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9') || (ch == '_'))
						state = 1;
					break;
					
				case 2:
					if (ch == '_')
						state = 2;
					else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch >= '0' && ch <= '9')
						state = 3;
					break;
					
				case 3:
					if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9') || (ch == '_'))
						state = 3;
					break;
			}
		}
		return state == 3;
	}
}