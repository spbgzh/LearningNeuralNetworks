package Adaline;

import org.neuroph.core.data.DataSet;

public class AdalineDemo {
    public final static int CHAR_WIDTH = 5;
    public final static int CHAR_HEIGHT = 7;
    public final static String[][] DIGITS = {
            {" OOO ",
             "O   O",
             "O   O",
             "O   O",
             "O   O",
             "O   O",
             " OOO "},

            {"  O  ",
             " OO  ",
             "O O  ",
             "  O  ",
             "  O  ",
             "  O  ",
             "  O  "},

            {" OOO ",
             "O   O",
             "    O",
             "   O ",
             "  O  ",
             " O   ",
             "OOOOO"},

            {" OOO ",
             "O   O",
             "    O",
             " OOO ",
             "    O",
             "O   O",
             " OOO "},

            {"   O ",
             "  OO ",
             " O O ",
             "O  O ",
             "OOOOO",
             "   O ",
             "   O "},

            {"OOOOO",
             "O    ",
             "O    ",
             "OOOO ",
             "    O",
             "O   O",
             " OOO "},

            {" OOO ",
             "O   O",
             "O    ",
             "OOOO ",
             "O   O",
             "O   O",
             " OOO "},

            {"OOOOO",
             "    O",
             "    O",
             "   O ",
             "  O  ",
             " O   ",
             "O    "},

            {" OOO ",
             "O   O",
             "O   O",
             " OOO ",
             "O   O",
             "O   O",
             " OOO "},

            {" OOO ",
             "O   O",
             "O   O",
             " OOOO",
             "    O",
             "O   O",
             " OOO "}};

    public static void main(String[] args) {
        DataSet data = new DataSet(CHAR_WIDTH*CHAR_HEIGHT,DIGITS.length);

    }

}
