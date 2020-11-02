import java.io.*;
import java.util.ArrayList;

public class guitarhero {

    public static void main(String[] args) {
        guitarhero app = new guitarhero();
    }

    public guitarhero() {
        File filename = new File("/Users/adityaamk/Desktop/DataStructures/Guitar Hero/guitartabfile.txt");

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;

            String[][] keyArr = { { "E", "A", "D", "G", "B", "E" }, { "F", "A#", "D#", "G#", "C", "F" },
                    { "F#", "B", "E", "A", "C#", "F#" }, { "G", "C", "F", "A#", "D", "G" },
                    { "G#", "C#", "F#", "B", "D#", "G#" } };

            int row = 0;
            String[][] finalGrid = null;
            while ((text = input.readLine()) != null) {

                String[] allmeasures = text.split(",");

                if (finalGrid == null) {
                    finalGrid = new String[keyArr.length * keyArr[0].length + 1][allmeasures.length + 1];

                    finalGrid[0][0] = "Measure";
                    int count = 1;
                    String prevString = "";

                    // i is row, j is col
                    for (int j = 5; j >= 0; j--) {
                        for (int i = 4; i >= 0; i--) {
                            // save previous string and check if duplicate to get rid of extra B
                            if (keyArr[i][j] != prevString) {
                                finalGrid[count][0] = keyArr[i][j];
                                prevString = keyArr[i][j];
                                count++;
                            }
                        }
                    }
                }

                for (int i = 0; i < allmeasures.length; i++) {
                    for (int j = 0; j < allmeasures[i].length(); j++) {
                        String test = Character.toString(allmeasures[i].charAt(j));
                        int helperIndex = ((keyArr[0].length - j - 1) * keyArr.length) + (keyArr.length - row);
                        // need else if because we removed extra B
                        if ((test.equals("o") || test.equals("*")) && helperIndex <= 10) {
                            finalGrid[helperIndex][i + 1] = "O";
                        } else if ((test.equals("o") || test.equals("*")) && helperIndex > 10) {
                            finalGrid[helperIndex - 1][i + 1] = "O";
                        }
                    }
                }
                row++;
            }

            for (int i = 1; i < finalGrid[0].length; i++) {
                finalGrid[0][i] = Integer.toString(i);
            }

            for (int i = 0; i < finalGrid.length; i++) {
                for (int j = 0; j < finalGrid[0].length; j++) {
                    if (finalGrid[i][j] != null)
                        System.out.print(finalGrid[i][j] + "\t");
                    else
                        System.out.print("\t");
                }
                System.out.println();
            }

        } catch (IOException e) {

        }
    }
}
