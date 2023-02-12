package part_1_dfa;

public class E1p1 {
    public static boolean scan(String s)
    {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            if (ch != '0' && ch != '1')
                state = -1;
            switch (state) {
            case 0:
                if (ch == '0')
                    state = 1;
                else if (ch == '1')
                    state = 0;
                break;
            case 1:
                if (ch == '0')
                    state = 2;
                else if (ch == '1')
                    state = 0;
                break;

            case 2:
                if (ch == '0')
                    state = 3;
                else if (ch == '1')
                    state = 0;
                break;

            case 3:
                if (ch == '0' || ch == '1')
                    state = 3;
                break;
            }
        }
        return ((state == 0 || state == 1 || state == 2) && s.length() != 0);
    }

    public static void main(String[] args) {
        if (args.length == 0)
			System.out.println("You didn't enter any argument.");
		for(int j = 0; j < args.length; j++) {
			System.out.println(args[j] + " " + (scan(args[j]) ? "OK" : "NOPE"));
		}
    }
}