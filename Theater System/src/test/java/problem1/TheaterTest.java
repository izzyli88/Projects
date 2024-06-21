package problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TheaterTest {
  private Theater testTheater;
  private ArrayList<Row> expRows;
  private ArrayList<Integer> expWheelchairRows;
  private String expName;

  @BeforeEach
  void setUp() {
    expRows = new ArrayList<>();
    int i;
    for (i = 1; i <= 3; i++) {
      expRows.add(new Row(i, false, 5));
    }
    for (i = 4; i <= 6; i++) {
      expRows.add(new Row(i, true, 5));
    }
    expWheelchairRows = new ArrayList<>(Arrays.asList(4, 5, 6));    // initialize arraylist nums
    expName = "new theater";
    testTheater = Theater.getInstance(expName, expRows);
  }

  @Test
  void getName() {
    assertEquals(expName, testTheater.getName());
  }

  @Test
  void getRowCollection() {
    assertEquals(expRows, testTheater.getRowCollection());
  }

  @Test
  void getWheelchairRows() {
    assertEquals(expWheelchairRows, testTheater.getWheelchairRows());
  }

  @Test
  void testMakeReservation_LargeNum() {
    assertFalse(testTheater.makeReservation("Izzy", 100, false));
  }
  @Test
  void testMakeReservation_SmallNum() {
    assertFalse(testTheater.makeReservation("Izzy", -100, false));
  }

  @Test
  void testMakeReservation_NoSeatsLeft(){
    testTheater.makeReservation("i", 5, false);
    testTheater.makeReservation("i", 5, false);
    testTheater.makeReservation("i", 5, false);
    testTheater.makeReservation("i", 5, true);
    testTheater.makeReservation("i", 5, true);
    testTheater.makeReservation("i", 5, true);
    assertFalse(testTheater.makeReservation("i", 5, false));
  }


  @Test
  void printTheater() {
    String expPrintout = """
        1 _ _ _ _ _
        2 _ _ _ _ _
        3 _ _ _ _ _
        4 = = = = =
        5 = = = = =
        6 = = = = =
        """;
    assertEquals(expPrintout, testTheater.printTheater());
  }

  @Test
  void testToString() {
    String expString = "Theater{" +
        "name='" + expName + '\'' +
        ", rowCollection=" + expRows +
        ", wheelchairRows=" + expWheelchairRows +
        '}';

    assertEquals(expString, testTheater.toString());
  }
}