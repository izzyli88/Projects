package problem1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RowTest {
  Integer expectedRowNumber;
  boolean expectedWheelchair;
  Integer expectedNumSeats;
  Row testRow;

  @BeforeEach
  void setUp() {
    expectedRowNumber = 1;
    expectedWheelchair = Boolean.TRUE;
    expectedNumSeats = 10;
    testRow = new Row(expectedRowNumber, expectedWheelchair, expectedNumSeats);
  }

  @Test
  void populateRow() {
    Row otherRow = new Row(expectedRowNumber, expectedWheelchair, expectedNumSeats);
    assertEquals(testRow, otherRow);
  }

  @Test
  void fillSeats() {
    testRow.fillSeats("John", 5);
    Row otherRow = new Row(expectedRowNumber, expectedWheelchair, expectedNumSeats);
    otherRow.fillSeats("John", 5);
    assertEquals(testRow, otherRow);
  }

  @Test
  void fillSeatsPrints() {
    testRow.fillSeats("John", 5);
    testRow.printRow();
    testRow.fillSeats("Anna", 4);
    assertEquals("1 X X X X X X X X X =", testRow.printRow());
  }

  @Test
  void fillSeatsFalse() {
    assertFalse(testRow.fillSeats("John", expectedNumSeats+1));
  }

  @Test
  void fillSeatsFalseWithSome() {
    testRow.fillSeats("Anna", 5);
    assertFalse(testRow.fillSeats("John", 6));
  }

  @Test
  void printRow() {
    assertEquals(testRow.printRow(), "1 = = = = = = = = = =");
  }

  @Test
  void printRowNonEmpty() {
    testRow.fillSeats("John", 5);
    assertEquals(testRow.printRow(), "1 X X X X X = = = = =");
  }

  @Test
  void getRowNumber() {
    assertEquals(testRow.getRowNumber(), expectedRowNumber);
  }

  @Test
  void isWheelchair() {
    assertEquals(testRow.isWheelchair(), expectedWheelchair);
  }

  @Test
  void getNumSeats() {
    assertEquals(testRow.getNumSeats(), expectedNumSeats);
  }

  @Test
  void getNumSeatsAvailableEmpty() {
    assertEquals(testRow.getNumSeatsAvailable(), expectedNumSeats);
  }

  @Test
  void getNumSeatsAvailable() {
    testRow.fillSeats("John", 5);
    assertEquals(testRow.getNumSeatsAvailable(), 5);
  }

  @Test
  void testEquals() {
    assertTrue(testRow.equals(testRow));
  }

  @Test
  void testEqualsDiffObject() {
    Row otherRow = new Row(expectedRowNumber, expectedWheelchair, expectedNumSeats);
    assertTrue(testRow.equals(otherRow));
    assertTrue(otherRow.equals(testRow));
  }

  @Test
  void testEqualsNull() {
    assertFalse(testRow.equals(null));
  }

  @Test
  void testEqualsDiffDataType() {
    assertFalse(testRow.equals("string"));
  }

  @Test
  void testEqualsDiffRowNum() {
    Row otherRow = new Row(5, expectedWheelchair, expectedNumSeats);
    assertFalse(testRow.equals(otherRow));
  }

  @Test
  void testEqualsDiffWheelchair() {
    Row otherRow = new Row(expectedRowNumber, Boolean.FALSE, expectedNumSeats);
    assertFalse(testRow.equals(otherRow));
  }

  @Test
  void testEqualsDiffNumSeats() {
    Row otherRow = new Row(expectedRowNumber, expectedWheelchair, 20);
    assertFalse(testRow.equals(otherRow));
  }

  @Test
  void testEqualsDiffSeatsAvailable() {
    Row otherRow = new Row(expectedRowNumber, expectedWheelchair, expectedNumSeats);
    testRow.fillSeats("John", 5);
    assertFalse(testRow.equals(otherRow));
  }

  @Test
  void testEqualsDiffSeatsName() {
    Row otherRow = new Row(expectedRowNumber, expectedWheelchair, expectedNumSeats);
    testRow.fillSeats("John", 5);
    otherRow.fillSeats("Anna", 5);
    assertFalse(testRow.equals(otherRow));
  }

  @Test
  void testHashCode() {
    Row otherRow = new Row(expectedRowNumber, expectedWheelchair, expectedNumSeats);
    assertEquals(testRow.hashCode(), otherRow.hashCode());
  }

  @Test
  void testToString() {
    Row otherRow = new Row(expectedRowNumber, expectedWheelchair, expectedNumSeats);
    assertEquals(testRow.toString(), otherRow.toString());
  }

  @Test
  void testSeatNameAssignmentFirst(){
    assertEquals(testRow.get(0).getName(), "A");
  }

  @Test
  void testSeatNameAssignmentLast(){
    assertEquals(testRow.get(expectedNumSeats-1).getName(), "J");
  }

  @Test
  void testSeatNameAssignment(){
    StringBuilder expectedNames = new StringBuilder();
    for(Seat seat: testRow){
      expectedNames.append(seat.getName());
    }
    assertEquals(expectedNames.toString(), "ABCDEFGHIJ");
  }

  @Test
  void testIsValidNumOfSeatsNegative(){
    assertThrows(IllegalArgumentException.class,
        ()->{
          Row otherRow = new Row(1,Boolean.FALSE, -5);
        });
  }

  @Test
  void testIsValidNumOfSeatsZero(){
    assertThrows(IllegalArgumentException.class,
        ()->{
          Row otherRow = new Row(1,Boolean.FALSE, 0);
        });
  }

  @Test
  void testIsValidNumOfSeatsOverMax(){
    assertThrows(IllegalArgumentException.class,
        ()->{
          Row otherRow = new Row(1,Boolean.FALSE, 30);
        });
  }
}