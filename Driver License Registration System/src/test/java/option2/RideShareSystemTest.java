package option2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class RideShareSystemTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Test
  void runSimulation() {
    System.setOut(new PrintStream(outContent));
    ByteArrayInputStream input = getByteArrayInputStream();
    System.setIn(input);

    String expOutput =
        """
              Welcome to the Rideshare System. Here, you'll input a file path for a list of prospective drivers as well
            as a path for their associated vehicles. We will then filter and retrieve valid drivers per the required specifications.

              You will then be able to enter last names to see their driver information and history. You can also enter quit to exit the program.
            If the last name you enter is not in our list, we will tell you so.

            Prospective driver path:\s
            Vehicle path:\s
            Enter any last name or "quit" to quit:

            Ratio, Dr.
            	RATIO123
            	2024 Tesla S
            	Violations:
            		Distracted Driving
            		Traffic Sign Failure
            		Fender Bender
            		No Bodily Injury
            		Bodily Injury
            Enter any last name or "quit" to quit:

            Yang, Caelus
            	CAELUS777
            	2024 Tesla S
            	2023 Honda Odyssey
            	Violations:
            		Paperwork
            		Vehicle Problems
            		Parking
            Yang, Welt
            	WELT999
            	2023 Honda Odyssey
            	No violations made.
            Enter any last name or "quit" to quit:

            No registered driver found.
            Enter any last name or "quit" to quit:

            Program terminating. See you next time.
            """;


    RideShareSystem system = new RideShareSystem();
    system.runSim();
    assertEquals(expOutput, outContent.toString());
  }

  private static ByteArrayInputStream getByteArrayInputStream() {
    String driverPath = "/Users/izzy.li/Desktop/CS5004_ILI_Repo/ILI_Final_2024/src/main/java/option2/Files/Drivers.csv";
    String vehiclePath = "/Users/izzy.li/Desktop/CS5004_ILI_Repo/ILI_Final_2024/src/main/java/option2/Files/Vehicles.csv";
    String LAST_NAME_TEST1 = "Ratio";
    String LAST_NAME_TEST2 = "Yang";
    String LAST_NAME_INVALID = "HELLO INVALID LAST NAME";
    String QUIT = "quit";
    String programInput = driverPath + "\n" + vehiclePath + "\n" + LAST_NAME_TEST1 + "\n" + LAST_NAME_TEST2
        + "\n" + LAST_NAME_INVALID + "\n" + QUIT + "\n";
    return new ByteArrayInputStream(programInput.getBytes());
  }

}