package part_1_dfa;

public class E1p6{
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
			if (!(ch == 'a' || ch == 'b'))
				state = -1;
			switch (state) {
				case 0:
					if (ch == 'b')
						state = 0;
					else
						state = 1;
					break;
				case 1:
					if (ch == 'b')
						state = 2;
					else
						state = 1;
					break;
				case 2:
					if (ch == 'b')
						state = 3;
					else
						state = 1;
					break;
				case 3:
					if (ch == 'b')
						state = 0;
					else
						state = 1;
					break;
			}
		}
		return (state >= 1 && state <= 3);
	}
}
