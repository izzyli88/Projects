package option2;

import java.util.ArrayList;

/**
 * interface for outputting driver information to terminal
 */
public interface IProvideDriverInfo {
  /**
   * method that outputs all information for
   * drivers with given last name onto terminal
   * @param lastName - given last name
   * @param drivers - collection of drivers
   */
  void provideDriverInfo(String lastName, ArrayList<Driver> drivers);
}
