package problem1;

import java.util.Objects;

/**
 * Represents an abstract problem1.Contract class with an asking price and a negotiable flag.
 */
public abstract class Contract {
  // An asking price, a non-negative double.
  private Double askingPrice;
  // A boolean flag indicating whether the price is negotiable.
  private Boolean negotiable;

  // minimum acceptable asking price (int)
  private static final int MIN_ASKING_PRICE = 0;

  /**
   * Constructor for the abstract problem1.Contract class with specified asking price and negotiable flag.
   * @param askingPrice The asking price for the contract expressed as a non-negative double.
   * @param negotiable Indicates whether the asking price is negotiable.
   * @throws IllegalArgumentException If the asking price is negative.
   */
  public Contract(Double askingPrice, Boolean negotiable) {
    if(isValidAskingPrice(askingPrice)){
      this.askingPrice = askingPrice;
      this.negotiable = negotiable;
    }
  }

  /**
   * Validates the provided asking price.
   * @param askingPrice The asking price to validate.
   * @return true if the asking price is non-negative, otherwise throws an exception.
   * @throws IllegalArgumentException If the asking price is negative.
   */
  protected Boolean isValidAskingPrice(Double askingPrice){
    if(askingPrice < MIN_ASKING_PRICE){
      throw new IllegalArgumentException("The asking price cannot be negative.");
    }
    else{
      return Boolean.TRUE;
    }
  }

  /**
   * Gets the asking price of this contract.
   * @return the asking price of this contract.
   */
  public Double getAskingPrice() {
    return this.askingPrice;
  }

  /**
   * Gets whether the asking price is negotiable.
   * @return true if the asking price is negotiable, otherwise return false.
   */
  public Boolean getNegotiable() {
    return this.negotiable;
  }

  /**
   * Gets the contract price
   * @return the contract price
   */
  public abstract Double getContractPrice();

  /**
   * Determines whether two instances of problem1.Contract are the same
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
    Contract contract = (Contract) o;
    return Objects.equals(this.askingPrice, contract.askingPrice) && Objects.equals(
        this.negotiable, contract.negotiable);
  }

  /**
   * Returns hash of object
   * @return - hash of object (int)
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.askingPrice, this.negotiable);
  }
}
