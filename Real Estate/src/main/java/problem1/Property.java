package problem1;

import java.util.Objects;

/**
 * Represents an abstract problem1.Property class with an address and a size measurement.
 */
public abstract class Property {
  // The address of the problem1.Property as a String
  protected String address;
  // The size of the problem1.Property in square feet as a non-negative Integer
  protected Integer size;

  // minimum acceptable size (int)
  private static final int MIN_SIZE = 0;

  /**
   * Constructs for problem1.Property with the specified address and size.
   * It validates the size to ensure it's non-negative before setting the values.
   * @param address The address of the problem1.Property as a String
   * @param size The size of the problem1.Property in square feet as a non-negative Integer
   * @throws IllegalArgumentException if the size is negative/invalid
   */
  public Property(String address, Integer size) {
    if(isValidSize(size)){
    this.address = address;
    this.size = size;
    }
  }

  /**
   * Validates the size of the property to ensure it's non-negative.
   * @param size The size of the property to validate, in square feet.
   * @return true if the size is non-negative
   * @throws IllegalArgumentException if the size is negative/invalid
   */
  protected Boolean isValidSize(Integer size){
    if(size < MIN_SIZE){
      throw new IllegalArgumentException("The size of the property has to be non-negative.");
    }
    else{
      return Boolean.TRUE;
    }
  }

  /**
   * Gets the address of the problem1.Property.
   * @return the address of the problem1.Property as a String
   */
  public String getAddress() {
    return this.address;
  }

  /**
   * Gets the size of the problem1.Property
   * @return the size of the problem1.Property as an Integer
   */
  public Integer getSize() {
    return this.size;
  }

  /**
   * Determines whether two instances of problem1.Property are the same
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
    Property property = (Property) o;
    return Objects.equals(this.address, property.address) && Objects.equals(this.size,
        property.size);
  }

  /**
   * Returns hash of object
   * @return - hash of object (int)
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.address, this.size);
  }
}
