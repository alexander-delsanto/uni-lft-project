package part_1_dfa;

public class E1p7v3 {
    public static boolean scan(String s, String name) {
        int state = 0;
        int i = 0;
        while(state >= 0 && i < s.length() && i < name.length()) {
            final char nc = name.charAt(i);
            final char c = s.charAt(i++);

            if(state == -1)
                state = -1;
            else if(state < name.length())
                state = getState(c, nc, state+1, state + name.length() + 1);
            else
                state = getState(c, nc, state+1, -1);
        }
        return (i == name.length()) && (state == name.length() || state == name.length() * 2);
    }

    public static void main(String[] args) {
        for(String i : args)
            System.out.println(scan(i, "Vito") ? "OK" : "NO");
    }

    public static int getState(char c, char nameChar, int r, int w) {
        if(c == nameChar)
            return r;
        else
            return w;
    }
}
