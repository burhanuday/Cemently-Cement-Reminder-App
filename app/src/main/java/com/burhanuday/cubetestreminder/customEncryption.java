package com.burhanuday.cubetestreminder;


public class customEncryption {

    private String estring = "";
    private String ansf = "";

    public String encode(String s)
    {
        String text1 = s;
        String str = "";
        int m = (int) (Math.random() * 9000);
        m = Math.abs(m);
        int i, c;

        int n = m * 98789;
        String f = Integer.toString(n);
        if (f.length() == 1)
            f = f + "#########";
        else if (f.length() == 2)
            f = f + "########";
        else if (f.length() == 3)
            f = f + "#######";
        else if (f.length() == 4)
            f = f + "######";
        else if (f.length() == 5)
            f = f + "#####";
        else if (f.length() == 6)
            f = f + "####";
        else if (f.length() == 7)
            f = f + "###";
        else if (f.length() == 8)
            f = f + "##";
        //else if (f.length() == 9)
        // f = f + "#";
        String g = "";
        for (i = 0; i < f.length(); i++) {

            char ch = f.charAt(i);
            c = (int) ch;
            int he = c + 478;
            char x = (char) he;
            g = g + x;
        }
        String ekey = "";
        for (i = 0; i < ekey.length(); i++) {

            char ch = ekey.charAt(i);
            c = (int) ch;
            int he = c + 478;
            char x = (char) he;
            g = g + x;
        }
        str = str + g + "!";

        for (i = 0; i < text1.length(); i++) {

            char ch = text1.charAt(i);
            if (ch != ' ') {
                c = (int) ch;
                int he = c + m;
                char x = (char) he;
                str = str + x;
            } else
                str = str + ch;
        }
        estring = str;
        return estring;
    }

    public String decode(String encodedText)
    {
        String test1 = encodedText.substring(0, 9);
        String m1 = "";
        int q;
        int o;

        for (q = 0; q < test1.length(); q++) {

            char ch = test1.charAt(q);
            o = (int) ch;
            int he = o - 478;
            char x = (char) he;
            m1 = m1 + x;
        }

        String kit = "";
        String sd1 = "";
        String kit1 = "";
        while (m1.endsWith("#"))
            m1 = m1.substring(0, m1.length() - 1);

        Long n = Long.valueOf(m1);
        //long re = (n / 98789);
        int m = (int) (n / 98789);
        // ansf = "";
        int i, c;

        for (i = 0; i < encodedText.length(); i++) {
            char ch = encodedText.charAt(i);
            if (ch == '!') {
                kit1 = encodedText.substring(9, i);
                sd1 = encodedText.substring(i + 1);
            }
        }
        for (q = 0; q < kit1.length(); q++) {

            char ch = kit1.charAt(q);
            o = (int) ch;
            int he = o - 478;
            char x = (char) he;
            kit = kit + x;
        }

        for (i = 0; i < sd1.length(); i++) {
            char ch = sd1.charAt(i);
            //String cham;
            if (ch != ' ') {
                c = (int) ch;
                // int h;

                int x = c - m;
                char cha = (char) x;
                ansf = ansf + cha;
            } else
                ansf = ansf + ch;
        }
        String ansf2 = ansf;
        ansf = "";
        return ansf2;

    }

}
