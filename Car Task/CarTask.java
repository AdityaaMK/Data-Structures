import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.io.*;
import java.util.Stack;

public class CarTask {
    public static void main(String[] args) {
        taskOne();
    }

    public static void taskOne() {
        Queue<Car> carQ = new LinkedList<>();
        Stack<Car> carS = new Stack<>();
        PriorityQueue<Car> carPQ = new PriorityQueue<>();

        try {
            BufferedReader input = new BufferedReader(new FileReader(new File("CarData.txt")));
            String text;
            input.readLine();

            while ((text = input.readLine()) != null) {
                String[] props = text.split("\\s+");
                carQ.add(new Car(Integer.parseInt(props[5]), Integer.parseInt(props[1]), Integer.parseInt(props[3]),
                        Integer.parseInt(props[2]), Integer.parseInt(props[4]), Integer.parseInt(props[7]),
                        Integer.parseInt(props[0]), Integer.parseInt(props[6])));
            }

            System.out.printf("%-10s %-7s %-15s %-7s %-10s %-17s %-10s %-15s\n", "Car ID", "MPG", "Engine Size", "HP",
                    "Weight", "Acceleration", "Origin", "# of Cylinders");

            while (!carQ.isEmpty() || carQ.peek() != null) {
                System.out.println(carS.push(carQ.poll()));
            }
            System.out.println("\n-------------------------------------------------------------\n");
            System.out.printf("%-10s %-7s %-15s %-7s %-10s %-17s %-10s %-15s\n", "Car ID", "MPG", "Engine Size", "HP",
                    "Weight", "Acceleration", "Origin", "# of Cylinders");
            while (!carS.empty()) {
                Car temp = carS.pop();
                carPQ.add(temp);
                System.out.println(temp);
            }
            System.out.println("\n-------------------------------------------------------------\n");
            System.out.printf("%-10s %-7s %-15s %-7s %-10s %-17s %-10s %-15s\n", "Car ID", "MPG", "Engine Size", "HP",
                    "Weight", "Acceleration", "Origin", "# of Cylinders");
            while (!carPQ.isEmpty() || carPQ.peek() != null) {
                System.out.println(carPQ.poll());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Car implements Comparable<Car> {
    private int accel;
    private int mpg;
    private int hp;
    private int engineSize;
    private int weight;
    private int cylinders;
    private int id;
    private int origin;

    public Car(int accel, int mpg, int hp, int engineSize, int weight, int cyclinders, int id, int origin) {
        this.accel = accel;
        this.mpg = mpg;
        this.hp = hp;
        this.engineSize = engineSize;
        this.weight = weight;
        this.cylinders = cyclinders;
        this.id = id;
        this.origin = origin;
    }

    public int getAccel() {
        return accel;
    }

    public int getMPG() {
        return mpg;
    }

    public int getHP() {
        return hp;
    }

    public int getEngineSize() {
        return engineSize;
    }

    public int getCyclinders() {
        return cylinders;
    }

    public int getOrigin() {
        return origin;
    }

    public int getWeight() {
        return weight;
    }

    public int getID() {
        return id;
    }

    public int compareTo(Car other) {
        if (accel != other.accel)
            return accel - other.accel;
        if (mpg != other.getMPG())
            return mpg - other.getMPG();
        if (hp != other.getHP())
            return hp - other.getHP();
        if (engineSize != other.getEngineSize())
            return engineSize - other.getEngineSize();
        if (weight != other.getWeight())
            return weight - other.getWeight();
        if (cylinders != other.getCyclinders())
            return cylinders - other.getCyclinders();
        if (id != other.getID())
            return id - other.getID();
        return 0;
    }

    public String toString() {
        return String.format("%-10d %-7d %-15d %-7d %-10d %-17d %-10d %-15d\n", id, mpg, engineSize, hp, weight, accel,
                origin, cylinders);
    }
}