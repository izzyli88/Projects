package problem1;

import java.util.Objects;

/**
 * Represents a concrete problem1.Commercial property class, extending the abstract problem1.Property class
 * with additional attributes specific to commercial use.
 * It includes information on the number of offices and whether the property is suitable for retail.
 */
public class Commercial extends Property {
  // The number of offices as a non-negative integer
  Integer numberOfOffices;
  // A boolean flag indicating whether the property is suitable for retail
  Boolean suitableForRetail;

  // The minimum acceptable number of offices (int)
  private static final int OFFICE_MIN = 0;

  /**
   * Constructor for problem1.Commercial class with specified details including
   * address, size, number of offices, and retail suitability.
   * @param address The address of the problem1.Property as a String
   * @param size The size of the problem1.Property in square feet as a non-negative Integer
   * @param numberOfOffices The number of offices as a non-negative integer
   * @param suitableForRetail A boolean flag indicating whether the property is suitable for retail
   */
  public Commercial(String address, Integer size, Integer numberOfOffices,
      Boolean suitableForRetail) {
    super(address, size);
    if(isValidNumberOfOffices(numberOfOffices)){
      this.numberOfOffices = numberOfOffices;
      this.suitableForRetail = suitableForRetail;
    }
  }

  /**
   * Validates the number of offices in the commercial property to ensure it's non-negative.
   * @param numberOfOffices The number of offices to validate
   * @return true if the number of offices is non-negative
   * @throws IllegalArgumentException if the number of offices is negative
   */
  private Boolean isValidNumberOfOffices(Integer numberOfOffices){
    if(numberOfOffices < OFFICE_MIN){
      throw new IllegalArgumentException("The number of offices cannot be negative.");
    }
    else{
      return Boolean.TRUE;
    }
  }

  /**
   * Gets the number of offices
   * @return the number of offices
   */
  public Integer getNumberOfOffices() {
    return this.numberOfOffices;
  }

  /**
   * Gets a boolean indicating whether the problem1.Commercial property is suitable for retail
   * @return true if the problem1.Commercial property is suitable for retail, false otherwise
   */
  public Boolean getSuitableForRetail() {
    return this.suitableForRetail;
  }

  /**
   * Determines whether two instances of AbstractCommercial are the same
   * @param o - other object being compared to
   * @return - whether the two objects are the same (boolean)
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
    Commercial that = (Commercial) o;
    return Objects.equals(this.numberOfOffices, that.numberOfOffices)
        && Objects.equals(this.suitableForRetail, that.suitableForRetail);
  }

  /**
   * Returns hash of object
   * @return - hash of object (int)
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.numberOfOffices, this.suitableForRetail);
  }

  /**
   * Returns string representation of object
   * @return - string representation (String)
   */
  @Override
  public String toString() {
    return "problem1.Commercial{" +
        "numberOfOffices=" + this.numberOfOffices +
        ", suitableForRetail=" + this.suitableForRetail +
        ", address='" + this.address + '\'' +
        ", size=" + this.size +
        '}';
  }
}
