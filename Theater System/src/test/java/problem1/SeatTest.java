package problem1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SeatTest {
  String expectedName;
  Seat testSeat;
  @BeforeEach
  void setUp() {
    expectedName = "Z";
    testSeat = new Seat(expectedName);
  }

  @Test
  void getName() {
    assertEquals(testSeat.getName(), expectedName);
  }

  @Test
  void getReservedForNull() {
    assertEquals(testSeat.getReservedFor(), null);
  }

  @Test
  void getReservedFor() {
    Seat otherSeat = new Seat("Z", "John");
    assertEquals(otherSeat.getReservedFor(), "John");
  }

  @Test
  void printSeatEmptyReg() {
    assertEquals(testSeat.printSeat(Boolean.FALSE), "_");
  }

  @Test
  void printSeatEmptyWheelchair() {
    assertEquals(testSeat.printSeat(Boolean.TRUE), "=");
  }

  @Test
  void printSeatReserved() {
    Seat otherSeat = new Seat("Z", "John");
    assertEquals(otherSeat.printSeat(Boolean.TRUE), "X");
  }

  @Test
  void testEquals() {
    assertTrue(testSeat.equals(testSeat));
  }

  @Test
  void testEqualsNull() {
    assertFalse(testSeat.equals(null));
  }

  @Test
  void testEqualsDiffDataType() {
    assertFalse(testSeat.equals("string"));
  }

  @Test
  void testEqualsDiffName() {
    Seat otherSeat = new Seat("A");
    assertFalse(testSeat.equals(otherSeat));
  }

  @Test
  void testEqualsDiffWheelchair() {
    Seat otherSeat1 = new Seat("Z", "John");
    Seat otherSeat2 = new Seat("Z", "Anna");
    assertFalse(otherSeat1.equals(otherSeat2));
  }
  @Test
  void testHashCode() {
    Seat otherSeat = new Seat("Z");
    assertEquals(testSeat.hashCode(), otherSeat.hashCode());
  }

  @Test
  void testToString() {
    String expectedString = "Seat{" +
        "name='" + testSeat.getName() + '\'' +
        ", reservedFor='" + testSeat.getReservedFor() + '\'' +
        '}';
    assertEquals(testSeat.toString(), expectedString);
  }
}