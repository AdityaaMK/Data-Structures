import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Task {

    public Task() {
        try {
            TreeMap<Integer, PriorityQueue<Bowler>> map = new TreeMap<>();
            String line;
            BufferedReader input = new BufferedReader(new FileReader("BowlingData.txt"));
            while ((line = input.readLine()) != null) {
                String[] data = line.split("\\s+");
                PriorityQueue<Bowler> temp = new PriorityQueue<>();
                if (!map.containsKey(Integer.parseInt(data[2]))) {
                    temp.add(new Bowler(data[0], data[1], Integer.parseInt(data[2])));
                } else {
                    temp = map.get(Integer.parseInt(data[2]));
                    temp.add(new Bowler(data[0], data[1], Integer.parseInt(data[2])));
                }
                map.put(Integer.parseInt(data[2]), temp);
            }

            Iterator<Integer> keys = map.keySet().iterator();
            Iterator<Integer> keys2 = map.keySet().iterator();
            Iterator entrySet = map.entrySet().iterator();
            Iterator<Integer> keys4 = map.keySet().iterator();
            System.out.print("{");
            while (keys.hasNext()) {
                int key = keys.next();
                System.out.print(key + "=");
                if (keys.hasNext())
                    System.out.print(map.get(key) + ", ");
                else
                    System.out.println(map.get(key) + "}");
                // PriorityQueue<Bowler> temp = new PriorityQueue<>();
                // temp.addAll(map.get(key));
                // while (!temp.isEmpty()) {
                // if (temp.size() != 1)
                // System.out.print(temp.poll() + ", ");
                // else if (keys.hasNext())
                // System.out.print(temp.poll() + "], ");
                // else
                // System.out.print(temp.poll() + "]");
                // }
            }
            System.out.println("+++++++++++++++++++++++++++++++++++");
            System.out.println("++++++++++++++++KEYS+++++++++++++++");
            System.out.println("+++++++++++++++++++++++++++++++++++");
            while (keys2.hasNext()) {
                System.out.println(keys2.next());
            }
            System.out.println("+++++++++++++++++++++++++++++++++++");
            System.out.println("+++++++++++++ENTRY SET+++++++++++++");
            System.out.println("+++++++++++++++++++++++++++++++++++");
            while (entrySet.hasNext()) {
                System.out.println(entrySet.next());
                // int key = keys3.next();
                // System.out.print(key + "=[");
                // PriorityQueue<Bowler> temp = new PriorityQueue<>();
                // temp.addAll(map.get(key));
                // while (!temp.isEmpty()) {
                // if (temp.size() != 1)
                // System.out.print(temp.poll() + ", ");
                // else
                // System.out.print(temp.poll() + "]\n");
                // }
            }
            System.out.println("+++++++++++++++++++++++++++++++++++");
            System.out.println("+++++++++++++++VALUES++++++++++++++");
            System.out.println("+++++++++++++++++++++++++++++++++++");
            while (keys4.hasNext()) {
                int key = keys4.next();
                System.out.print("[");
                PriorityQueue<Bowler> temp = new PriorityQueue<>();
                temp.addAll(map.get(key));
                while (!temp.isEmpty()) {
                    if (temp.size() != 1)
                        System.out.print(temp.poll() + ", ");
                    else
                        System.out.print(temp.poll() + "]\n");
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    public static void main(String[] args) {
        new Task();
    }
}

class Bowler implements Comparable<Bowler> {
    String firstName, lastName;
    int score;

    public Bowler(String first, String last, int score) {
        firstName = first;
        lastName = last;
        this.score = score;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public int compareTo(Bowler other) {
        int last = lastName.compareTo(other.getLastName());
        return last != 0 ? last : firstName.compareTo(other.getFirstName());
    }
}
