import java.io.*;

public class FileTemplate {
    public FileTemplate() {
        File fileName = new File("/Users/adityaamk/Downloads/DataStructures/FileTemplate Demo/example.txt");

        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;
            int sum = 0;

            while ((text = input.readLine()) != null) {
                String[] pieces = text.split(",");

                int num;
                try {

                    for (int x = 0; x < pieces.length; x++) {
                        num = Integer.parseInt(pieces[x]);
                        sum += num;
                    }

                } catch (NumberFormatException nfe) {

                }
            }
            System.out.println(sum);

        } catch (IOException ioe) {
            System.out.println("File not found.");
        }

    }

    public static void main(String[] args) {
        FileTemplate app = new FileTemplate();
    }

}