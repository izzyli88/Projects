package option2;

import java.util.ArrayList;

/**
 * class to retrieve valid drivers from a list of all drivers
 */
public class RegistrationValidator {

  /**
   * retrieves drivers approved by the system and stores in a new
   * collection
   * @param drivers - collection of all drivers
   * @return - collection of drivers that are filtered
   */
  public static ArrayList<Driver> retrieveValidDrivers(ArrayList<Driver> drivers) {
    ArrayList<Driver> validDrivers = new ArrayList<>();
    for (Driver driver : drivers) {
      if (driver.isValidDriver() && !validDrivers.contains(driver))
        validDrivers.add(driver);
    }
    return validDrivers;
  }
}