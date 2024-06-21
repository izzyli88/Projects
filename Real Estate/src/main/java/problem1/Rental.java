package problem1;

import java.util.Objects;

/**
 * Represents a concrete problem1.Rental class that extends the problem1.Contract class to
 * include an asking price, a negotiable flag, and terms specific to rental agreements.
 */
public class Rental extends Contract {
  // The rental term in months expressed as an integer greater than 0,
  // which indicates the length of the contract.
  Integer term;

  // min term can't be equal to or below 0 (int)
  private static final int MIN_TERM = 0;

  /**
   * Constructor for the problem1.Rental class with specified asking price, negotiable flag, and term.
   * @param askingPrice The asking price for the contract expressed as a non-negative double.
   * @param negotiable Indicates whether the asking price is negotiable.
   * @param term The rental term in months expressed as an integer greater than 0, which indicates
   *             the length of the contract.
   * @throws IllegalArgumentException If the asking price is negative
   *                                  or the term is less or equal to 0.
   */
  public Rental(Double askingPrice, Boolean negotiable, Integer term) {
    super(askingPrice, negotiable);
    if(isValidTerm(term)){
      this.term = term;
    }
  }

  /**
   * Validates the term of the problem1.Rental contract is greater than 0.
   * @param term The rental term in months expressed as an integer greater than 0, which indicates
   *             the length of the contract.
   * @return true if the term is valid (greater than 0) and return false otherwise.
   */
  private Boolean isValidTerm(Integer term){
    if(term <= MIN_TERM){
      throw new IllegalArgumentException("The term has to be greater than 0. ");
    }
    else{
      return Boolean.TRUE;
    }
  }

  /**
   * Gets the term of the problem1.Rental contract.
   * @return the term of the problem1.Rental contract.
   */
  public Integer getTerm() {
    return this.term;
  }

  /**
   * Gets the contract price based on asking price and rental term.
   * @return the contract price.
   */
  @Override
  public Double getContractPrice() {
    return getAskingPrice()*getTerm();
  }

  /**
   * Determines whether two instances of problem1.Rental are the same
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
    Rental rental = (Rental) o;
    return Objects.equals(this.term, rental.term);
  }

  /**
   * Returns hash of object
   * @return - object hash (int)
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.term);
  }

  /**
   * Returns string representation of object
   * @return - string representation (String)
   */
  @Override
  public String toString() {
    return "problem1.Rental{" +
        "term=" + this.term +
        "} " + super.toString();
  }

}
