package problem1;

import java.util.Objects;

/**
 * Represents a concrete problem1.Residential class with an address, a size measurement,
 * and number of bedrooms and bathrooms.
 */
public class Residential extends Property {
  // The number of bedrooms, a non-negative integer.
  private Integer numberOfBedrooms;
  // The number of bathrooms, a non-negative double (because half-baths are a thing).
  private Double numberOfBathrooms;

  // min acceptable number of bedrooms (int)
  private static final int MIN_ROOM = 0;

  // min acceptable number of bathrooms(int)
  private static final int MIN_BATHROOM = 0;

  /**
   * Constructor for problem1.Residential class.
   * @param address The address of the problem1.Property as a String
   * @param size The size of the problem1.Property in square feet as a non-negative Integer
   * @param numberOfBedrooms The number of bedrooms, a non-negative integer
   * @param numberOfBathrooms The number of bathrooms, a non-negative double
   * @throws IllegalArgumentException if the number of bedrooms or bathroom is invalid
   */
  public Residential(String address, Integer size, Integer numberOfBedrooms,
      Double numberOfBathrooms) {
    super(address, size);
    if(isValidNumberOfBedrooms(numberOfBedrooms) && isValidNumberOfBathrooms(numberOfBathrooms)){
      this.numberOfBedrooms = numberOfBedrooms;
      this.numberOfBathrooms = numberOfBathrooms;
    }
  }

  /**
   * Validates the number of bedrooms of the property to ensure it's non-negative.
   * @param numberOfBedrooms The number of bedrooms of a residential property
   * @return true if the number of bedrooms is invalid (non-negative)
   * @throws IllegalArgumentException if the number of bedrooms is invalid
   */
  private Boolean isValidNumberOfBedrooms(Integer numberOfBedrooms){
    if(numberOfBedrooms<MIN_ROOM){
      throw new IllegalArgumentException("The number of bedrooms cannot be negative.");
    }
    else{
      return Boolean.TRUE;
    }
  }

  /**
   * Validates the number of bathrooms of the property to ensure it's non-negative.
   * @param numberOfBathrooms The number of bathrooms of a residential property
   * @return true if the number of bathrooms is invalid (non-negative double)
   * @throws IllegalArgumentException if the number of bathrooms is invalid (negative)
   */
  private Boolean isValidNumberOfBathrooms(Double numberOfBathrooms){
    if(numberOfBathrooms<MIN_BATHROOM){
      throw new IllegalArgumentException("The number of bathrooms cannot be negative.");
    }
    else{
      return Boolean.TRUE;
    }
  }

  /**
   * Gets the number of bedrooms of the problem1.Residential property
   * @return The number of bedrooms of the problem1.Residential property
   */
  public Integer getNumberOfBedrooms() {
    return this.numberOfBedrooms;
  }

  /**
   * Gets the number of bathrooms of the problem1.Residential property
   * @return The number of bathrooms of the problem1.Residential property
   */
  public Double getNumberOfBathrooms() {
    return this.numberOfBathrooms;
  }

  /**
   * Determines whether two instances of problem1.Residential are the same
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
    Residential that = (Residential) o;
    return Objects.equals(this.numberOfBedrooms, that.numberOfBedrooms)
        && Objects.equals(this.numberOfBathrooms, that.numberOfBathrooms);
  }

  /**
   * Returns hash of object
   * @return - hash of object (int)
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.numberOfBedrooms, this.numberOfBathrooms);
  }

  /**
   * Returns string representation of object
   * @return - string representation (String)
   */
  @Override
  public String toString() {
    return "problem1.Residential{" +
        "numberOfBedrooms=" + this.numberOfBedrooms +
        ", numberOfBathrooms=" + this.numberOfBathrooms +
        ", address='" + this.address + '\'' +
        ", size=" + this.size +
        '}';
  }
}
