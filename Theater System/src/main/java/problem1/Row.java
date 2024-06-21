package problem1;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a row of seats within a theater. Row class extends ArrayList class.
 */
public class Row extends ArrayList<Seat> {
  private final int MAX_NUM_SEATS = 26;    // Maximum number of seats per row
  private final int MIN_NUM_SEATS = 1;     // Minimum number of seat per row
  private final char FIRST_SEAT_NAME = 'A';// Name of first seat in a row
  private Integer rowNumber;               // The row number
  private boolean wheelchair;              // Indicates if the row is wheelchair accessible
  private Integer numSeats;                // Total number of seats in the row
  private Integer numSeatsAvailable;       // Number of unreserved seats available

  /**
   * Constructor for Row class with specified properties.
   * @param rowNumber The number of the row.
   * @param wheelchair Indicates whether the row is wheelchair accessible.
   * @param numOfSeats The total number of seats in the row.
   */
  public Row(Integer rowNumber, boolean wheelchair, Integer numOfSeats) {
    if(isValidNumOfSeats(numOfSeats)){
      this.rowNumber = rowNumber;
      this.wheelchair = wheelchair;
      this.numSeats = numOfSeats;
      this.numSeatsAvailable = numOfSeats;
      this.populateRow();
    }
  }

  /**
   * Helper method to validate the number of seats in the row.
   * @param numOfSeats The number of seats to validate.
   * @return True if the number is within the valid range, false otherwise.
   */
  private Boolean isValidNumOfSeats(Integer numOfSeats){
    if (numOfSeats<=MAX_NUM_SEATS && numOfSeats > MIN_NUM_SEATS){
      return Boolean.TRUE;
    }
    else{
      throw new IllegalArgumentException(
          "The minimum number of seats is "+MIN_NUM_SEATS +
              " and the maximum number of seat is " +MAX_NUM_SEATS+".");
    }
  }

  /**
   * Populates the row with seats. Each seat is initially unreserved.
   * Seats are named sequentially from 'A' onward up to the number of seats.
   */
  private void populateRow() {
    char seatName = FIRST_SEAT_NAME;
    for(int i = 0; i < numSeats; i++){
      Seat newSeat = new Seat(String.valueOf(seatName));
      seatName ++;
      this.add(newSeat);
    }
  }

  /**
   * Fill the specified number of consecutive seats with reservation person's name.
   * If there is not enough seats available, return false.
   * @param name The name for the reservation.
   * @param numOfSeatsToFill The number of seats to reserve.
   * @return True if the reservation is successful, false otherwise.
   */
  Boolean fillSeats(String name, Integer numOfSeatsToFill) {
    if(numSeatsAvailable < numOfSeatsToFill){
      return Boolean.FALSE;
    }
    else{
      int start = this.numSeats - numSeatsAvailable;
      for(int i=start; i<start + numOfSeatsToFill; i++){
        Seat newSeat = new Seat(this.get(i).getName(), name);
        this.set(i, newSeat);
      }
      this.numSeatsAvailable -= numOfSeatsToFill;
      return Boolean.TRUE;
    }
  }

  /**
   * Returns a string representing the row number and seats.
   * @return A string detailing the row number and each seat's status.
   */
  public String printRow(){
    StringBuilder row = new StringBuilder();
    row.append(this.rowNumber.toString());
    for(int i = 0; i < this.numSeats; i++){
      row.append(" ");
      row.append(this.get(i).printSeat(this.wheelchair));
    }
    return row.toString();
  }

  /**
   * Gets the row number.
   * @return The row number.
   */
  public Integer getRowNumber() {
    return rowNumber;
  }

  /**
   * Gets whether this row is wheelchair accessible.
   * @return True if this row is wheelchair accessible, false otherwise.
   */
  public boolean isWheelchair() {
    return wheelchair;
  }

  /**
   * Gets the number of seats.
   * @return The number of seats.
   */
  public Integer getNumSeats() {
    return numSeats;
  }

  /**
   * Gets the number of seats available.
   * @return The number of seats available.
   */
  public Integer getNumSeatsAvailable() {
    return numSeatsAvailable;
  }

  /**
   * Determines whether two instances of problem1.Row are the same.
   * @param o The object to be compared for equality with this list.
   * @return True if the two objects are the same, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Row seats = (Row) o;
    return wheelchair == seats.wheelchair
        && Objects.equals(rowNumber, seats.rowNumber)
        && Objects.equals(numSeats, seats.numSeats)
        && Objects.equals(numSeatsAvailable, seats.numSeatsAvailable);
  }

  /**
   * Returns hash of object
   * @return - hash of object (int)
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), rowNumber, wheelchair, numSeats, numSeatsAvailable);
  }

  /**
   * Returns string representation of instance
   * @return - string representation (String)
   */
  @Override
  public String toString() {
    return "Row{" +
        "rowNumber=" + rowNumber +
        ", wheelchair=" + wheelchair +
        ", numSeats=" + numSeats +
        ", numSeatsAvailable=" + numSeatsAvailable +
        "} " + super.toString();
  }
}
