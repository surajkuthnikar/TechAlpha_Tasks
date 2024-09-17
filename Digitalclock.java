import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Digitalclock {
    public static void main(String[] args) {
        while (true) {
            LocalTime time = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String currentTime = time.format(formatter);
            System.out.print("\r" + currentTime);
            try {
                Thread.sleep(1000); // update every 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}