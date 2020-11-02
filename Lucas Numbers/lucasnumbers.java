import java.io.*;
import java.math.*;

public class lucasnumbers {

    public static void main(String[] args) {
        lucasnumbers app = new lucasnumbers();
    }

    public lucasnumbers() {
        File fileName = new File("/Users/adityaamk/Desktop/DataStructures/Lucas Numbers/lucasnumbersfile.txt");

        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;

            while ((text = input.readLine()) != null) {
                BigInteger a = lucas(Integer.parseInt(text));
                System.out.println(a);
            }

        } catch (IOException ioe) {
            System.out.println("File not found.");
        }
    }

    public BigInteger lucas(int x) {
        BigInteger a = BigInteger.TWO, b = BigInteger.ONE, c;

        if (x == 0)
            return a;

        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(BigInteger.valueOf(x)) <= 0; i = i.add(BigInteger.ONE)) {
            c = a.add(b);
            a = b;
            b = c;
        }
        return b;
    }

}
