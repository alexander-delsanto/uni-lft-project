package part_1_dfa;

public class E1p8{
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
			if (!((ch >= '0' && ch <= '9') || (ch == 'e') || (ch == '.') || (ch == '+') || (ch == '-')))
				state = -1;
			switch (state) {
				case 0:
					if (ch == '+' || ch == '-')
						state = 1;
					else if (ch >= '0' && ch <= '9')
						state = 2;
					else if (ch == '.')
						state = 3;
					else
						state = 10;
					break;

				case 1:
					if (ch >= '0' && ch <= '9')
						state = 2;
					else if (ch == '.')
						state = 3;
					else
						state = 10;
					break;

				case 2:
					if (ch >= '0' && ch <= '9')
						state = 2;
					else if (ch == 'e')
						state = 5;
					else if (ch == '.')
						state = 3;
					else
						state = 10;
					break;

				case 3:
					if (ch >= '0' && ch <= '9')
						state = 4;
					else
						state = 10;
					break;

				case 4:
					if (ch >= '0' && ch <= '9')
						state = 4;
					else if (ch == 'e')
						state = 5;
					else
						state = 10;
					break;

				case 5:
					if (ch == '+' || ch == '-')
						state = 6;
					else if (ch >= '0' && ch <= '9')
						state = 7;
					else if (ch == '.')
						state = 8;
					else
						state = 10;
					break;

				case 6:
					if (ch >= '0' && ch <= '9')
						state = 7;
					else
						state = 10;
					break;

				case 7:
					if (ch >= '0' && ch <= '9')
						state = 7;
					else if (ch == '.')
						state = 8;
					else
						state = 10;
					break;

				case 8:
					if (ch >= '0' && ch <= '9')
						state = 9;
					else state = 10;
					break;

				case 9:
					if (ch >= '0' && ch <= '9')
						state = 9;
					else state = 10;
					break;

				case 10:
					break;
			}
		}
		return (state == 7 || state == 2 || state == 4 || state == 9);
	}
}
