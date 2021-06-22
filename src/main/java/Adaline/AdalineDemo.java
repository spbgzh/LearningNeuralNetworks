package Adaline;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

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

    public static DataSet createDataSet()
    {
        DataSet data =new DataSet(CHAR_WIDTH * CHAR_HEIGHT, DIGITS.length);
        for (int i = 0; i < DIGITS.length; i++) {
            data.add(createDataSetRow(DIGITS[i],i));
        }
        return data;
    }

    public static DataSetRow createDataSetRow(String[] image,int num){
        double[] input =new double[CHAR_HEIGHT*CHAR_WIDTH];
        for (int row = 0; row < CHAR_HEIGHT; row++) {
            for (int col = 0; col < CHAR_WIDTH; col++) {
                int index = (row * CHAR_WIDTH) + col;
                char ch = image[row].charAt(col);
                input[index] = ch == 'O' ? 1 : -1;
            }
        }
        double[] output = new double[DIGITS.length];
        for (int i = 0; i < output.length; i++)
            output[i] = -1;
        output[num]=1;
        return new DataSetRow(input,output);
    }

}
