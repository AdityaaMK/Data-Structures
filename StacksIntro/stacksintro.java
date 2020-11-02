import java.util.Stack;
import java.io.BufferedReader;
import java.io.*;
import java.util.Scanner;

public class stacksintro {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // Decimal to Binary Converter
        // Stack<Integer> binary = new Stack<Integer>();
        // int dec = reader.nextInt();
        // reader.close();
        // while (dec / 2 != 0) {
        // binary.push(dec % 2);
        // dec /= 2;
        // if (dec / 2 == 0)
        // binary.push(dec);
        // }
        // while (!binary.empty()) {
        // System.out.print(binary.pop());
        // }

        // Reverse a String
        Stack<Character> allChars = new Stack<Character>();
        String input = "applepiepizza";
        for (char oneChar : input.toCharArray()) {
            allChars.push(oneChar);
        }
        while (!allChars.empty()) {
            System.out.print(allChars.pop());
        }
        System.out.println();

        // Star Wars Character Stacks
        Stack<StarWarsCharacter> maleCharacters = new Stack<StarWarsCharacter>();
        Stack<StarWarsCharacter> femaleCharacters = new Stack<StarWarsCharacter>();
        Stack<StarWarsCharacter> droids = new Stack<StarWarsCharacter>();
        Stack<StarWarsCharacter> validYears = new Stack<StarWarsCharacter>();

        File starWars = new File("/Users/adityaamk/Desktop/DataStructures/StacksIntro/StarWarsCharacters.csv");
        try {
            BufferedReader readFile = new BufferedReader(new FileReader(starWars));
            String text;
            readFile.readLine();

            while ((text = readFile.readLine()) != null) {
                String[] values = text.split(",");
                if (values.length == 9) {
                    if (values[6].equals("male"))
                        maleCharacters
                                .push(new StarWarsCharacter(values[0], values[5], values[6], values[7], values[8]));
                    else
                        femaleCharacters
                                .push(new StarWarsCharacter(values[0], values[5], values[6], values[7], values[8]));
                    if (values[8].equals("Droid"))
                        droids.push(new StarWarsCharacter(values[0], values[5], values[6], values[7], values[8]));
                    if (!values[5].equals("NA"))
                        validYears.push(new StarWarsCharacter(values[0], values[5].substring(0, values[5].length() - 3),
                                values[6], values[7], values[8]));
                } else if (values.length == 10) {
                    if (values[7].equals("male"))
                        maleCharacters
                                .push(new StarWarsCharacter(values[0], values[6], values[7], values[8], values[9]));
                    else
                        femaleCharacters
                                .push(new StarWarsCharacter(values[0], values[6], values[7], values[8], values[9]));
                    if (values[8].equals("Droid"))
                        droids.push(new StarWarsCharacter(values[0], values[6], values[7], values[8], values[9]));
                    if (!values[6].equals("NA"))
                        validYears.push(new StarWarsCharacter(values[0], values[6].substring(0, values[6].length() - 3),
                                values[7], values[8], values[9]));
                }
            }
            readFile.close();
        } catch (IOException e) {
            System.out.println("file not found");
        }

        System.out.println("Male Characters");
        System.out.println(String.format("%s%50s", "Name", "HomeWorld"));
        while (!maleCharacters.empty()) {
            System.out.println(maleCharacters.pop());
        }

        System.out.println("\nFemale Characters");
        System.out.println(String.format("%s%50s", "Name", "HomeWorld"));
        while (!femaleCharacters.empty()) {
            System.out.println(femaleCharacters.pop());
        }

        System.out.println("\nDroids");
        System.out.println(String.format("%s%50s", "Name", "HomeWorld"));
        while (!droids.empty()) {
            System.out.println(droids.pop());
        }

        System.out.println("\nAges");
        System.out.println(String.format("%s%50s%25s", "Name", "HomeWorld", "Birth Year (BBY)"));
        while (!validYears.empty()) {
            StarWarsCharacter temp = validYears.pop();
            if (!temp.homeWorld.equals("NA"))
                if (temp.birthYear.contains("."))
                    System.out.println(String.format("%-45s%-18s%s", temp.name, temp.homeWorld, temp.birthYear));
                else
                    System.out.println(String.format("%-45s%-18s%s", temp.name, temp.homeWorld, temp.birthYear + ".0"));
            else {
                if (temp.birthYear.contains("."))
                    System.out.println(String.format("%-45s%-18s%s", temp.name, "Unknown", temp.birthYear));
                else
                    System.out.println(String.format("%-45s%-18s%s", temp.name, "Unknown", temp.birthYear + ".0"));
            }
        }

    }

    public void readCSV() {
        File filename = new File("/Users/adityaamk/Desktop/DataStructures/StacksIntro/StarWarsCharacters.csv");
    }
}

class StarWarsCharacter {
    public String name;
    public String birthYear;
    public String gender;
    public String homeWorld;
    public String species;

    public StarWarsCharacter(String name, String birthYear, String gender, String homeWorld, String species) {
        this.name = name;
        this.birthYear = birthYear;
        this.gender = gender;
        this.homeWorld = homeWorld;
        this.species = species;
    }

    public String toString() {
        if (!homeWorld.equals("NA")) {
            return String.format("%-45s%-5s", name, homeWorld);
        }
        return String.format("%-45s%-5s", name, "Unknown");
    }
}
