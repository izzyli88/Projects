package option2;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * method to run the actual simulation
 */
public class RideShareSystem implements IProvideDriverInfo {
  private static final Scanner scanner = new Scanner(System.in);
  private static final String EXIT_PROGRAM = "QUIT";

  /**
   * constructor for class
   */
  public RideShareSystem() {
  }

  /**
   * runs the simulation
   */
  public void runSim() {
    printIntro();   // prints intro for simulation

    System.out.println("Prospective driver path: ");
    String driverPath = scanner.nextLine();

    System.out.println("Vehicle path: ");
    String vehiclePath = scanner.nextLine();

    // collect valid drivers
    ArrayList<Driver> drivers = FileProcessor.collectValidDrivers(driverPath, vehiclePath);

    // while loop for user last name/quit input
    while (true) {
      System.out.println("Enter any last name or \"quit\" to quit:\n");
      String output = scanner.nextLine().toLowerCase();

      if (output.equalsIgnoreCase(EXIT_PROGRAM))  // quit
        break;
      else {  // last name --> output stuff
        provideDriverInfo(output, drivers);
      }
    }

    // conclusion
    System.out.println("Program terminating. See you next time.");
    scanner.close();
  }

  /**
   * method to provide driver info for the given last name
   * @param lastName - last name to search for
   * @param drivers - collection of valid drivers
   */
  public void provideDriverInfo(String lastName, ArrayList<Driver> drivers) {

    // filter drivers by last name
    List<Driver> filtered = drivers.stream()
        .filter(x -> x.getName().lastName().equalsIgnoreCase(lastName)).collect(Collectors.toList());

    if (filtered.isEmpty()) {   // no driver found
      System.out.println("No registered driver found.");
    }
    // call print driver info for each of the drivers
    else filtered.stream().forEach(Driver::printDriverInfo);
  }

  /**
   * prints intro statement for program
   */
  private void printIntro(){
    String s = """
    Welcome to the Rideshare System. Here, you'll input a file path for a list of prospective drivers as well
  as a path for their associated vehicles. We will then filter and retrieve valid drivers per the required specifications.
  
    You will then be able to enter last names to see their driver information and history. You can also enter quit to exit the program.
  If the last name you enter is not in our list, we will tell you so.
        """;
    System.out.println(s);
  }

}