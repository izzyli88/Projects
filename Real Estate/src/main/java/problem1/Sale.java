package problem1;

import problem1.Contract;

/**
 * Represents a concrete problem1.Sale class that extends the problem1.Contract class to
 * include an asking price and a negotiable flag.
 */
public class Sale extends Contract {

  /**
   * Constructor for the problem1.Sale class with specified asking price and negotiable flag.
   * @param askingPrice The asking price for the contract expressed as a non-negative double.
   * @param negotiable Indicates whether the asking price is negotiable.
   * @throws IllegalArgumentException If the asking price is negative.
   */
  public Sale(Double askingPrice, Boolean negotiable) {
    super(askingPrice, negotiable);
  }

  /**
   * Gets the contract price based on the asking price
   * @return the contract price
   */
  @Override
  public Double getContractPrice() {
    return getAskingPrice();
  }

  /**
   * Returns a string representation of the problem1.Sale object.
   * @return a string representation of the problem1.Sale object.
   */
  @Override
  public String toString() {
    return "problem1.Sale{} " + super.toString();
  }

}
