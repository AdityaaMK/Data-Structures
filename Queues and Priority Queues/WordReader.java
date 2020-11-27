import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;

public class WordReader {
    public static void main(String[] args) {
        taskOne();
        System.out.println("\n-------------------------------------------------------------\n");
        taskTwo();
        System.out.println("\n-------------------------------------------------------------\n");
        taskThree();

    }

    public static void taskOne() {
        Queue<Word> q = new LinkedList<>();
        PriorityQueue<Word> pq = new PriorityQueue<>();
        PriorityQueue<Word> reverse = new PriorityQueue<>();

        try {
            File file = new File("Words.txt");
            // String[] words = Files.readAllLines(file.toPath()).get(0).replace(".",
            // "").replace(",", "").split(" ");
            String[] words = Files.readAllLines(file.toPath()).get(0).split("\\W+");

            for (String word : words) {
                q.add(new Word(word));
                pq.add(new Word(word));
                reverse.add(new DescendingWord(word));
            }
            System.out.printf("%-20s %-20s %s\n", "Original", "Sorted", "Reversed");

            while (!q.isEmpty()) {
                System.out.printf("%-20s %-20s %s\n", q.poll(), pq.poll(), reverse.poll());
            }
        } catch (IOException e) {

        }
    }

    public static void taskTwo() {
        try {
            Queue<Passenger> q = new LinkedList<>();

            File file = new File("PassengerInfo.txt");
            List<String> lines = Files.readAllLines(file.toPath());
            for (int i = 0; i < lines.size(); i += 3) {
                q.add(new Passenger(lines.get(i), lines.get(i + 2), lines.get(i + 1)));
            }
            while (!q.isEmpty()) {
                System.out.println(q.poll());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void taskThree() {
        try {
            PriorityQueue<Passenger> pq = new PriorityQueue<>();

            File file = new File("PassengerInfo.txt");
            List<String> lines = Files.readAllLines(file.toPath());
            for (int i = 0; i < lines.size(); i += 3) {
                pq.add(new Passenger(lines.get(i), lines.get(i + 2), lines.get(i + 1)));
            }
            while (!pq.isEmpty()) {
                System.out.println(pq.poll());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class Word implements Comparable<Word> {
    String word;

    public Word(String word) {
        this.word = word;
    }

    public String toString() {
        return word;
    }

    public int compareTo(Word other) {
        return word.toLowerCase().compareTo(other.toString().toLowerCase());
    }
}

class DescendingWord extends Word {

    public DescendingWord(String word) {
        super(word);
    }

    public int compareTo(Word other) {
        return -toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }
}

class Passenger implements Comparable<Passenger> {

    private String firstName;
    private String lastName;
    private String time;
    private String city;

    public Passenger(String name, String time, String city) {
        this.firstName = name.split(" ")[0];
        this.lastName = name.split(" ")[1];
        this.time = time;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String flightTime() {
        return time;
    }

    public String flightCity() {
        return city;
    }

    public int etdCalc() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String reformattedTime = time.split(":")[0].length() > 1 ? time : "0" + time;
        if ((reformattedTime.substring(6).equals("AM") && !time.split(":")[0].equals("12"))
                || (reformattedTime.substring(6).equals("PM") && time.split(":")[0].equals("12"))) {
            reformattedTime = reformattedTime.substring(0, 6);
        } else
            reformattedTime = (Integer.parseInt(time.split(":")[0]) + 12) + ":" + time.split(":")[1].substring(0);
        try {
            Date flightTime = sdf.parse(reformattedTime);
            Date currentTime = sdf.parse("09:03");
            long delta = TimeUnit.MINUTES.convert(flightTime.getTime() - currentTime.getTime(), TimeUnit.MILLISECONDS);
            if (delta < 0)
                delta += 24L * 60;
            return (int) (delta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String toString() {
        int minutes = etdCalc();
        return lastName + ", " + firstName + " - " + city + " - " + time + " - "
                + (minutes > 60 ? (minutes / 60) + " hour" + (minutes > 120 ? "s" : "") + " and " : "") + (minutes % 60)
                + " minutes from now";
    }

    public int compareTo(Passenger other) {
        int a = etdCalc();
        int b = other.etdCalc();
        if ((a > 60 && b > 60) || a == b)
            return 0;
        return (a - b);
    }

}
