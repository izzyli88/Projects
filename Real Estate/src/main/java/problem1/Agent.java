package problem1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * problem1.Agent class implementing the problem1.AgentTasks interface. Employs generics for
 * specifying agent's property/contract specializations
 * @param <T> -property specialization
 * @param <U> - contract specialization
 */
public class Agent<T extends Property, U extends Contract> implements AgentTasks<T,U> {
  private String name;
  private List<Listing<T, U>> listingCollection;
  private Double commissionRate;
  private Double earnings;

  // static variables
  private static final Double INITIAL_EARNINGS = 0.0;
  private static final Double RATE_MIN = 0.0;
  private static final Double RATE_MAX = 1.0;
  private static final Double PORTFOLIO_STARTING_VAL = 0.0;

  /**
   * Constructor (1/2) creating an instance of the problem1.Agent class with an empty
   * listingCollection. The default earnings is set to 0.0
   * @param name - agent's name (String)
   * @param commissionRate - agent's commission rate (Double) - 0.0 - 1.0
   */
  public Agent(String name, Double commissionRate) {
    if (isValid(commissionRate)) {
      this.name = name;
      this.listingCollection = new ArrayList<>();   // initialize to empty
      this.commissionRate = commissionRate;
      this.earnings = INITIAL_EARNINGS;
    }
  }

  /**
   * Constructor (2/2) creating an instance of the problem1.Agent class with a given listingCollection
   * The default earnings is set to 0.0
   * @param name - agent's name (String)
   * @param listingCollection - listing collection (List of problem1.Listing<T,U>)
   * @param commissionRate - agent's commission rate (Double) 0.0 - 1.0
   */
  public Agent(String name, List<Listing<T,U>> listingCollection, Double commissionRate) {
    if (isValid(commissionRate)) {
      this.name = name;
      this.listingCollection = listingCollection;
      this.commissionRate = commissionRate;
    }
  }

  /**
   * Returns agent's name
   * @return - agent name (String)
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns listing collection
   * @return - listing collection (List<problem1.Listing<T,U>>)
   */
  public List<Listing<T, U>> getListingCollection() {
    return this.listingCollection;
  }

  /**
   * Returns agent's commission rate
   * @return - commission rate (Double)
   */
  public Double getCommissionRate() {
    return this.commissionRate;
  }

  /**
   * Returns agent's total earnings
   * @return - total earnings (Double)
   */
  public Double getEarnings() {
    return this.earnings;
  }

  /**
   * Adds the given listing to the agent's collection
   * @param listing - given listing (problem1.Listing)
   */
  @Override
  public void addListing(Listing<T, U> listing) {
    this.listingCollection.add(listing);
  }

  /**
   * Removes the given listing from the collection & adds the appropriate
   * commission earnings to the agent's total earnings. problem1.Listing is then removed
   * @param listing - given listing (problem1.Listing)
   * @throws ListingNotFoundException - thrown when the given listing is not in the collection
   */
  @Override
  public void completeListing(Listing<T,U> listing) throws ListingNotFoundException {
    if (!this.listingCollection.contains(listing))
      throw new ListingNotFoundException("problem1.Listing not in agent's collection");

    this.earnings += this.commissionEarnings(listing);
    this.listingCollection.remove(listing);
  }

  /**
   * Removes a given listing from the agent's collection without adjusting
   * their earnings
   * @param listing - given listing (problem1.Listing)
   * @throws ListingNotFoundException - thrown when the given listing is not in the collection
   */
  @Override
  public void dropListing(Listing<T,U> listing) throws ListingNotFoundException {
    if (!this.listingCollection.contains(listing))
      throw new ListingNotFoundException("problem1.Listing not in agent's collection");

    this.listingCollection.remove(listing);
  }

  /**
   * Returns the amount of money the agent would make if
   * they successfully completed all the listings in their collection
   */
  @Override
  public Double getTotalPortfolioValue() {
    double value = PORTFOLIO_STARTING_VAL;
    for (Listing<T,U> listing : this.listingCollection) {
      value += this.commissionEarnings(listing);
    }
    return value;
  }

  /**
   * Helper method for use in completeListing() method.
   * Calculates the commission earnings for a given listing
   * @param listing - given listing (problem1.Listing)
   * @return - commission earnings (Double)
   */
  private Double commissionEarnings(Listing<T,U> listing) {
    return this.commissionRate * listing.calcListingPrice();
  }

  /**
   * Helper method for use in constructor
   * Checks whether the given commission rate is within range (0.0 - 1.0)
   * @param rate - given commission rate (Double)
   * @return - whether rate is valid (Boolean)
   */
  private Boolean isValid(Double rate) {
    if (rate >= RATE_MIN && rate <= RATE_MAX)
      return Boolean.TRUE;
    else throw new IllegalArgumentException("Commission rate must be between 0.0 and 1.0");
  }

  /**
   * Helper method for use in equals method determining whether two listing collections
   * are equal
   * @param otherCollection - listing collection being compared to (List <problem1.Listing<T,U>>)
   * @return - whether the two listing collections are equal (Boolean)
   */
  private Boolean isSameCollection(List<Listing<T,U>> otherCollection) {
    if (this.listingCollection.size() != otherCollection.size())
      return Boolean.FALSE;

    // Geeks for Geeks: Java Program to Convert List to Hashset
    Set<Listing<T, U>> set1 = new HashSet<>(this.listingCollection);    // only unique values
    Set<Listing<T, U>> set2 = new HashSet<>(otherCollection);

    return set1.equals(set2);
  }

  /**
   * Determines whether two instances of problem1.Agent are the same
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

    // below warning will generate an unsafe operation message: Prof. Bonaci said that it was okay
    Agent<T, U> agent = (Agent<T, U>) o;    // class is already checked for, should be same as problem1.Agent class, warning doesn't matter
    return Objects.equals(getName(), agent.getName()) &&
        Objects.equals(getCommissionRate(), agent.getCommissionRate()) &&   // does nto include earnings b/c default is 0.0
        this.isSameCollection(agent.getListingCollection());
  }

  /**
   * Returns hash of object
   * @return - hash of object (int)
   */
  @Override
  public int hashCode() {
    int expHash = Objects.hash(getName(), getCommissionRate(), getEarnings());
    for (Listing<T,U> listing : this.listingCollection) {
      expHash += listing.hashCode();
    }
    return expHash;
  }

  /**
   * Returns string representation of instance
   * @return - string representation (String)
   */
  @Override
  public String toString() {
    return "problem1.Agent{" +
        "name='" + this.name + '\'' +
        ", listingCollection=" + this.listingCollection +
        ", commissionRate=" + this.commissionRate +
        ", earnings=" + this.earnings +
        '}';
  }
}
