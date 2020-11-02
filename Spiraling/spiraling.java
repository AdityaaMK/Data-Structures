import java.io.*;

public class spiraling {
    public static void main(String[] args) {
        spiraling app = new spiraling();
    }

    public spiraling() {
        File filename = new File("/Users/adityaamk/Desktop/DataStructures/Spiraling/spiralingdata.txt");

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;

            while ((text = input.readLine()) != null) {

                int startRow = 0, startColumn = 0, endRow = Integer.parseInt(text) - 1,
                        endColumn = Integer.parseInt(text) - 1;

                String[][] spiral = new String[endRow + 1][endColumn + 1];

                for (int i = 0; i <= endRow; i++) {
                    for (int k = 0; k <= endColumn; k++) {
                        spiral[i][k] = "- ";
                        // System.out.print(spiral[i][k]);
                    }
                    // System.out.println();
                }

                while (startRow <= endRow && startColumn <= endColumn) {

                    // Going Right
                    for (int c = startColumn; c <= endColumn; c++) {
                        spiral[startRow][c] = "* ";
                        // System.out.println(spiral[startRow][c]);
                    }
                    startRow++;

                    if (startColumn >= 1)
                        startColumn++;

                    // Going Down
                    for (int r = startRow; r <= endRow; r++) {
                        spiral[r][endColumn] = "* ";
                        // System.out.println(spiral[r][endColumn]);
                    }
                    endColumn--;
                    startRow++;

                    // Going Left
                    for (int c = endColumn; c >= startColumn; c--) {
                        spiral[endRow][c] = "* ";
                        // System.out.println(spiral[endRow][c]);
                    }
                    endColumn--;
                    endRow--;

                    // Going Up
                    for (int r = endRow; r >= startRow; r--) {
                        spiral[r][startColumn] = "* ";
                        // System.out.println(spiral[r][startColumn]);
                    }
                    endRow--;
                    startColumn++;
                }

                if (spiral.length % 4 == 2) {
                    spiral[(spiral.length) / 2][(spiral.length - 2) / 2] = "- ";
                }

                for (int i = 0; i <= spiral.length - 1; i++) {
                    for (int k = 0; k <= spiral.length - 1; k++) {
                        System.out.print(spiral[i][k]);
                    }
                    System.out.println();
                }

            }

        } catch (IOException e) {

        }

    }

}