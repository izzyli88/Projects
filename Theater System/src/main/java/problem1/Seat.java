package problem1;

import java.util.Objects;
/**
 * Represents a seat in a row at a theater. Each seat can be optionally reserved for a specific person.
 */
public class Seat {
  private String name; // The name/ID of the seat, a string value representing a capital letter from A to Z.
  private String reservedFor; // The name of the person for whom it has been reserved,
                              // or null if the seat has not been reserved.

  /**
   * Constructor for Seat class with specified name.
   * When first initialized, the seat is not reserved for anyone.
   * @param name The name/ID of the seat.
   */
  public Seat(String name) {
    this.name = name;
    this.reservedFor = null;
  }

  /**
   * Constructor for Seat class with specified name and reservation.
   * @param name The name/ID of the seat.
   * @param reservedFor The name of the person for whom it has been reserved.
   */
  public Seat(String name, String reservedFor) {
    this.name = name;
    this.reservedFor = reservedFor;
  }

  /**
   * Gets the name of the Seat.
   * @return the name of the Seat.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the name of the person for whom it has been reserved.
   * @return the name of the person for whom it has been reserved.
   */
  public String getReservedFor() {
    return reservedFor;
  }

  /**
   * Provides a visual representation of the seat's status for display purposes.
   * 'X' indicates the seat is reserved, '=' for a wheelchair accessible seat that is not reserved,
   * and '_' for a seat that is not reserved and not wheelchair accessible.
   * @param wheelchair Indicates whether the seat is wheelchair accessible.
   * @return A string representing the seat's status.
   */
  public String printSeat(Boolean wheelchair){
    if(this.reservedFor!= null){
      return "X";
    }
    else if(wheelchair){
      return "=";
    }
    return "_";
  }

  /**
   * Determines whether two instances of problem1.Seat are the same.
   * @param o The object to compare with this Seat.
   * @return true if the given object represents a Seat equivalent to this seat, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Seat seat = (Seat) o;
    return Objects.equals(name, seat.name) && Objects.equals(reservedFor,
        seat.reservedFor);
  }

  /**
   * Computes the hash code for this Seat.
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, reservedFor);
  }

  /**
   * Returns string representation of instance
   * @return - string representation (String)
   */
  @Override
  public String toString() {
    return "Seat{" +
        "name='" + name + '\'' +
        ", reservedFor='" + reservedFor + '\'' +
        '}';
  }
}
