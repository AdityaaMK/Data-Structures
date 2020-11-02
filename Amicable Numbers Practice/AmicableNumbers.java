import java.io.*;

public class AmicableNumbers {

    public static void main(String[] args) {
        AmicableNumbers app = new AmicableNumbers();
    }

    public AmicableNumbers() {
        File fileName = new File(
                "/Users/adityaamk/Desktop/DataStructures/Amicable Numbers Practice/AmicableNumsFile.txt");

        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;

            while ((text = input.readLine()) != null) {
                String factors1, factors2;
                factors1 = factors2 = "";
                int num1, num2, sum1, sum2;
                num1 = num2 = sum1 = sum2 = 0;
                String[] pieces = text.split("\\s+");
                String last1, last2;
                last1 = last2 = "";
                try {
                    num1 = Integer.parseInt(pieces[0]);
                    num2 = Integer.parseInt(pieces[1]);

                    for (int x = 1; x < num1; x++) {
                        if (num1 % x == 0) {
                            sum1 += x;
                            factors1 += Integer.toString(x) + ", ";
                            last1 = Integer.toString(x);
                        }
                    }

                    for (int x = 1; x < num2; x++) {
                        if (num2 % x == 0) {
                            sum2 += x;
                            factors2 += Integer.toString(x) + ", ";
                            last2 = Integer.toString(x);
                        }
                    }

                } catch (NumberFormatException nfe) {

                }
                if (num1 == sum2 && num2 == sum1) {
                    System.out.println("The numbers " + num1 + " and " + num2 + " are amicable.");
                } else {
                    System.out.println("The numbers " + num1 + " and " + num2 + " are not amicable.");
                }

                if (factors1.length() > 6) {
                    System.out.println("\tFactors of " + num1 + " are "
                            + factors1.substring(0, factors1.length() - last1.length() - 2) + "and " + last1
                            + ". Sum is " + sum1 + ".");
                } else if (factors1.length() > 3) {
                    System.out.println("\tFactors of " + num1 + " are "
                            + factors1.substring(0, factors1.length() - last1.length() - 4) + " and " + last1
                            + ". Sum is " + sum1 + ".");
                } else {
                    System.out.println("\tFactor of " + num1 + " is " + last1 + ". Sum is " + sum1 + ".");
                }

                if (factors2.length() > 6) {
                    System.out.println("\tFactors of " + num2 + " are "
                            + factors2.substring(0, factors2.length() - last2.length() - 2) + "and " + last2
                            + ". Sum is " + sum2 + ".");
                } else if (factors2.length() > 3) {
                    System.out.println("\tFactors of " + num2 + " are "
                            + factors2.substring(0, factors2.length() - last2.length() - 4) + " and " + last2
                            + ". Sum is " + sum2 + ".");
                } else {
                    System.out.println("\tFactor of " + num2 + " is " + last2 + ". Sum is " + sum2 + ".");
                }

            }

        } catch (IOException ioe) {
            System.out.println("File not found.");
        }
    }

}
