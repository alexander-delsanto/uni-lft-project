public class E1p10{
	public static void main(String[] args){
		if (args.length == 0)
			System.out.println("You didn't enter any argument.");
		for (int j = 0; j < args.length; j++)
			System.out.println(args[j] + " " + (scan(args[j]) ? "OK" : "NOPE"));
	}

	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);
			if (!(ch == 'a' || ch == '/' || ch == '*'))
				state = -1;
			switch (state) {
				case 0:
					if (ch == '/')
						state = 1;
					else 
						state = 0;
					break;
				
				case 1:
					if (ch == '/')
						state = 1;
					else if (ch == '*')
						state = 2;
					else 
						state = 0;
					break;
				
				case 2:
					if (ch == '*')
						state = 3;
					else
						state = 2;
					break;
				
				case 3:
					if (ch == '/')
						state = 4;
					else if (ch == '*')
						state = 3;
					else
						state = 2;
					break;
					
				case 4:
					if (ch == '/')
						state = 1;
					else
						state = 0;
					break;
			}
		}
		return (state == 0 || state == 1 || state == 4);
	}
}
