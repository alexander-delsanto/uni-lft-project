public class E1p10 {
    public static boolean scan(String s)
    {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            state = switch (state) {
                case 0 -> getstate(ch, 1, 0);
                case 1 -> getstate(ch, 2, 0);
                case 2 -> getstate(ch, 3, 0);
                case 3 -> getstate(ch, 3, 3);
		        default-> -1;
            };
        }
        return state == 3;
    }

    public static int getstate(char c, int is0, int is1) {
        return switch(c) {
            case '0' -> is0;
            case '1' -> is1;
            default -> -1;
        };
    }

    public static void main(String[] args) {
        if (args.length == 0) System.out.println("You didn't enter any argument.");
	for(int j = 0; j < args.length; j++) {
		System.out.println(args[j] + " " + (scan(args[j]) ? "OK" : "NOPE"));
	}
    }
}
