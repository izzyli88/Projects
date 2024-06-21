package option2;

import java.util.Objects;

/**
 * Name class for a driver
 */
public final class Name {
  private final String firstName;
  private final String lastName;

  /**
   * constructor creating an instance of a name object
   */
  public Name(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  /**
   * returns first name
   * @return - first name (String)
   */
  public String firstName() {
    return this.firstName;
  }

  /**
   * returns last name
   * @return - last name (String)
   */
  public String lastName() {
    return this.lastName;
  }

  /**
   * compares curr object with another Name instance for equality
   * @param o - other instance
   * @return - whether the two objects are the same
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Name name = (Name) o;
    return Objects.equals(firstName(), name.firstName()) && Objects.equals(
        lastName(), name.lastName());
  }

  /**
   * returns hash of object
   * @return - object hash (int)
   */
  @Override
  public int hashCode() {
    return Objects.hash(firstName(), lastName());
  }

  /**
   * returns string representation of object
   * @return - string representation
   */
  @Override
  public String toString() {
    return "Name{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }
}
