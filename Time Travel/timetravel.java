import java.io.*;
import java.math.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class timetravel {

    public static void main(String[] args) {
        timetravel app = new timetravel();
    }

    public timetravel() {
        File filename = new File("/Users/adityaamk/Desktop/DataStructures/Time Travel/travelfile.txt");

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;
            int count = 0;

            while ((text = input.readLine()) != null) {
                count++;
                System.out.println("Trip " + count + ":");

                String[] arr = text.split("\\s+");
                int minutes = Integer.parseInt(arr[2]);
                int hours = Integer.parseInt(arr[1]);
                int days = Integer.parseInt(arr[0]);

                Date d1 = new Date();
                Calendar cl = Calendar.getInstance();
                cl.setTime(d1);

                // departure date formatting
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

                String[] a1 = simpleDateFormat.format(cl.getTime()).split("-");
                if (a1[0].indexOf("0") == 0)
                    a1[0] = a1[0].substring(1);
                if (a1[1].indexOf("0") == 0)
                    a1[1] = a1[1].substring(1);
                String formattedDate1 = a1[0] + "/" + a1[1] + "/" + a1[2];

                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm a");
                String[] b1 = simpleDateFormat2.format(cl.getTime()).split(":");
                if (b1[0].indexOf("0") == 0)
                    b1[0] = b1[0].substring(1);
                String formattedTime1 = b1[0] + ":" + b1[1];
                System.out.println("\tDeparture Date and Time: " + formattedTime1 + " on " + formattedDate1);

                cl.add(Calendar.MINUTE, minutes);
                cl.add(Calendar.HOUR, hours);
                cl.add(Calendar.DATE, days);

                // arrival date formatting
                String[] a = simpleDateFormat.format(cl.getTime()).split("-");
                if (a[0].indexOf("0") == 0)
                    a[0] = a[0].substring(1);
                if (a[1].indexOf("0") == 0)
                    a[1] = a[1].substring(1);
                String formattedDate = a[0] + "/" + a[1] + "/" + a[2];

                String[] b = simpleDateFormat2.format(cl.getTime()).split(":");
                if (b[0].indexOf("0") == 0)
                    b[0] = b[0].substring(1);
                String formattedTime = b[0] + ":" + b[1];
                System.out.println("\tArrival Date and Time: " + formattedTime + " on " + formattedDate);

            }

        } catch (IOException e) {
            System.out.println("File Not Found");
        }

    }

}
