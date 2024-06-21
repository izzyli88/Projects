package problem1;

import java.util.Objects;

/**
 * Represents a property listing consisting of a property and a contract.
 * @param <T> The type of property.
 * @param <U> The type of contract.
 */
public class Listing<T extends Property, U extends Contract> {
  private T property;
  private U contract;

  /**
   * Constructs a new problem1.Listing with the specified property and contract.
   * @param property The property associated with the listing.
   * @param contract The contract associated with the listing.
   */
  public Listing(T property, U contract) {
    this.property = property;
    this.contract = contract;
  }

  /**
   * Retrieves the property associated with this listing.
   * @return The property associated with this listing.
   */
  public T getProperty() {
    return this.property;
  }

  /**
   * Retrieves the contract associated with this listing.
   * @return The contract associated with this listing.
   */
  public U getContract() {
    return this.contract;
  }

  /**
   * Calculates the listing price based on the associated contract.
   * @return The listing price.
   */
  public double calcListingPrice() {
    return contract.getContractPrice();
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   * @param o The reference object with which to compare.
   * @return true if this object is the same as the obj argument; false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Listing<?, ?> listing)) {
      return false;
    }
    return Objects.equals(this.property, listing.property) && Objects.equals(this.contract,
        listing.contract);
  }

  /**
   * Returns a hash code value for the object.
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.property, this.contract);
  }

  /**
   * Returns a string representation of the object.
   * @return A string representation of the object.
   */
  @Override
  public String toString() {
    return "problem1.Listing{" +
        "property=" + this.property +
        ", contract=" + this.contract +
        '}';
  }
}
