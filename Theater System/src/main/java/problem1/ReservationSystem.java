package problem1;

import java.util.ArrayList;

/**
 * Reservation system class for theater. Creates a new instance of theater
 * and implements ReservationService method.
 */
public class ReservationSystem {
  private static final int NUM_SEATS = 10;
  private static final int LAST_NO_WC_ROW = 13;
  private static final int FIRST_NO_WC_ROW = 1;
  private static final int LAST_ROW = 15;
  private static final String THEATER_NAME = "Cinemark";

  /**
   * Main method for program
   * @param args - args given to command line upon run (none given)
   */
  public static void main(String[] args) {

    // create rows
    ArrayList<Row> rows = new ArrayList<>();
    int i;
    for (i = FIRST_NO_WC_ROW; i <= LAST_NO_WC_ROW; i++) {
      rows.add(new Row(i, false, NUM_SEATS));
    }

    for (i = LAST_NO_WC_ROW + 1; i <= LAST_ROW; i++) {
      rows.add(new Row(i, true, NUM_SEATS));
    }

    // instantiate theater
    Theater theater = Theater.getInstance(THEATER_NAME, rows);
    ReservationsService.reserveSeats(theater);  // call res. method for theater
  }
}
