package problem1;

/**
 * Exception class for when a given listing isn't found in the agent's portfolio
 */
public class ListingNotFoundException extends Exception {

  /**
   * constructor for exception, sends the given message to the user
   * @param message - given message (String)
   */
  public ListingNotFoundException(String message) {
    super(message);
  }
}
