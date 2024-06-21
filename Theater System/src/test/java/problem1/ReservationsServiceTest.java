package problem1;

import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Please note these two methods need to be tested separately.
// Please also comment out TheaterTest to run these two methods below.
public class ReservationsServiceTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void testReserveSeats() {
    ArrayList<Row> rows = new ArrayList<>();
    Row row = new Row(1, false, 5);
    rows.add(row);
    Theater theater = Theater.getInstance("Test Theater", rows);

    ByteArrayInputStream in = new ByteArrayInputStream("reserve 2\nTest\nno\nshow\ndone\n".getBytes());
    System.setIn(in);

    ReservationsService.reserveSeats(theater);

    String expectedOutput = "What would you like to do?\n" +
        "What's your name?\n" +
        "Do you need wheelchair accessible seats? (yes/no)\n" +
        "Seats reserved successfully!\n" +
        "What would you like to do?\n" +
        "1 X X _ _ _\n" +
        "\n"+
        "What would you like to do?\n" +
        "Have a nice day!\n";

    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  public void testInvalidReservation() {
    ArrayList<Row> rows = new ArrayList<>();
    Row row = new Row(1, false, 5);
    rows.add(row);
    Theater theater = Theater.getInstance("Test Theater", rows);

    ByteArrayInputStream in2 = new ByteArrayInputStream("reserve 10\nJohn\nno\nshow\ndone\n".getBytes());
    System.setIn(in2);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ReservationsService.reserveSeats(theater);

    assertEquals(5, theater.getRowCollection().get(0).getNumSeatsAvailable());
  }
}

