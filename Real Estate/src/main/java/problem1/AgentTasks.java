package problem1;

/**
 * Interface describing methods to be performed by all agents
 */
public interface AgentTasks <T extends Property, U extends Contract> {

  /**
   * Adds the given listing to the agent's collection
   * @param listing - given listing (problem1.Listing)
   */
  void addListing(Listing<T, U> listing);

  /**
   * Removes the given listing from the collection & adds the appropriate
   * commission earnings to the agent's total earnings. problem1.Listing is then removed
   * @param listing - given listing (problem1.Listing)
   * @throws ListingNotFoundException - thrown when the given listing is not in the collection
   */
  void completeListing(Listing<T, U> listing) throws ListingNotFoundException;

  /**
   * Removes a given listing from the agent's collection without adjusting
   * their earnings
   * @param listing - given listing (problem1.Listing)
   * @throws ListingNotFoundException - thrown when the given listing is not in the collection
   */
  void dropListing(Listing<T, U> listing) throws ListingNotFoundException;

  /**
   * Returns the amount of money the agent would make if
   * they successfully completed all the listings in their collection
   */
  Double getTotalPortfolioValue();
}
