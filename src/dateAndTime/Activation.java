
package dateAndTime;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author Andrei
 */
public class Activation {

    private LocalDateTime start;
    private LocalDateTime end;

    private Activation(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public static Activation getInstance(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }
        if (!start.isBefore(end)) {
            return null;
        }
        if (start.getDayOfWeek() == DayOfWeek.SUNDAY || end.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return null;
        }
        return new Activation(start, end);
    }

    public Activation getInstance(LocalDateTime start, Duration d) {
        if (d == null) {
            return null;
        }
        return getInstance(start, start.plus(d));
    }

    public Activation getInstance(LocalDateTime start, int zile, int ore, int minute, int secunde) {
        Duration d = Duration.ofDays(zile).plusHours(ore).plusMinutes(minute).plusSeconds(secunde);
        return getInstance(start, d);
    }

    public Activation getInstance(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return getInstance(startDate.atTime(startTime), endDate.atTime(endTime));
    }

    // (Start1 <= End2) && (End1 >= Start2)
    public boolean overlaps(Activation a) {
        return !start.isAfter(a.end) && !end.isBefore(a.start);
    }

    public String toString() {
        String s = "Activarea incepe " + formatareActivare(start);
        s += " si se incheie ";
        s += formatareActivare(end);
        return s;
    }

    private String formatareActivare(LocalDateTime ldt) {
        Locale RO = new Locale("ro", "RO");
        return ldt.format(DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy 'ora' HH:mm:ss", RO));
    }

    public static void main(String[] args) {
        Activation a = Activation.getInstance(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        System.out.println(a.toString());
    }
}
