import java.io.*;
import java.math.*;
import java.util.*;

public class fantasy {

    public static void main(String[] args) {
        fantasy app = new fantasy();
    }

    public fantasy() {
        File filename = new File("/Users/adityaamk/Desktop/DataStructures/Fantasy Football/ffldraftaverages.txt");

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;

            input.readLine();
            ArrayList<Player> players = new ArrayList<Player>();
            while ((text = input.readLine()) != null) {
                String[] arr = text.split(";");
                ArrayList<String> temp = new ArrayList<String>();
                for (int i = 0; i < arr.length; i++) {
                    if (i == 1 || i == 5 || i == 7 || i == 8) {
                        temp.add(arr[i]);
                        // System.out.println(arr[i]);
                    }
                }
                players.add(new Player(temp.get(0), temp.get(2), temp.get(3), temp.get(1)));
            }

            for (int i = 1; i < players.size(); i++) {
                Player temp = players.get(i);
                int j = i - 1;
                while (j >= 0 && (temp.compareTo(players.get(j)) == -1)) {
                    players.set(j + 1, players.get(j));
                    j--;
                }
                players.set(j + 1, temp);
            }

            for (Player player : players)
                System.out.println(player);

        } catch (IOException e) {
            System.out.println("File Not Found");
        }

    }

    public class Player implements Comparable<Player> {
        private String hiRd, lowRd, draftPos, name;

        public Player(String name, String hiRd, String lowRd, String draftPos) {
            this.name = name;
            this.hiRd = hiRd;
            this.lowRd = lowRd;
            this.draftPos = draftPos;
        }

        public String gethiRd() {
            return hiRd;
        }

        public String getlowRd() {
            return lowRd;
        }

        public String getdraftPos() {
            return draftPos;
        }

        public int compareTo(Player p2) {
            String[] hi1 = hiRd.split("\\.");
            String[] low1 = lowRd.split("\\.");
            String[] hi2 = p2.gethiRd().split("\\.");
            String[] low2 = p2.getlowRd().split("\\.");
            int dif1, dif2;

            if (hi1[1].equals("1"))
                hi1[1] = "10";
            if (low1[1].equals("1"))
                low1[1] = "10";
            if (hi2[1].equals("1"))
                hi2[1] = "10";
            if (low2[1].equals("1"))
                low2[1] = "10";

            dif1 = Math.abs(Integer.parseInt(hi1[0]) - Integer.parseInt(low1[0])) * 12
                    + Math.abs(Integer.parseInt(hi1[1]) - Integer.parseInt(low1[1]));
            dif2 = Math.abs(Integer.parseInt(hi2[0]) - Integer.parseInt(low2[0])) * 12
                    + Math.abs(Integer.parseInt(hi2[1]) - Integer.parseInt(low2[1]));

            if (dif1 < dif2) {
                return -1;
            } else if (dif1 == dif2) {
                if (Double.parseDouble(draftPos) < Double.parseDouble(p2.getdraftPos())) {
                    return -1;
                } else if (Double.parseDouble(draftPos) > Double.parseDouble(p2.getdraftPos())) {
                    return 1;
                }
            }
            return 0;
        }

        public String toString() {
            return String.format("Name: %-25s HiRd: %-8s LowRd: %-8s Overall Draft Position: %-8s", name, hiRd, lowRd,
                    draftPos);
        }
    }

}
