package problem1;

import java.util.Scanner;

/**
 * ReservationsService class provides methods to interactively reserve seats in a theater.
 * It utilizes a Theater object to manage seat reservations.
 */
public class ReservationsService {
  private static final int RESERVE_COMMAND_LENGTH = 2;
  private static final Scanner scanner = new Scanner(System.in);

  /**
   *  Allows the user to reserve seats in the provided theater.
   *  Continuously prompts the user for input until they choose to exit.
   *
   * @param theater The Theater object representing the theater to reserve seats in.
   */
  public static void reserveSeats(Theater theater) {
    // Loop until the user chooses to exit
    try {
      while (true) {
        if (!scanner.hasNextLine()) {
          throw new IllegalStateException("Scanner is closed");
        }
        System.out.println("What would you like to do?");
        String input = scanner.nextLine().trim().toLowerCase();
        // Check user input for 'reserve' command
        if (input.startsWith("reserve")) {
          // Split input into tokens
          String[] tokens = input.split(" ");
          if (tokens.length == RESERVE_COMMAND_LENGTH) {
            try {
              int numberOfSeats = Integer.parseInt(tokens[1]);
              System.out.println("What's your name?");
              String name = scanner.nextLine().trim();
              System.out.println("Do you need wheelchair accessible seats? (yes/no)");
              String wheelchairInput = scanner.nextLine().trim().toLowerCase();
              boolean wheelchairAccessible = wheelchairInput.equals("yes");
              boolean reservationSuccess = theater.makeReservation(name, numberOfSeats, wheelchairAccessible);
              // Notify user of reservation status
              if (reservationSuccess) {
                System.out.println("Seats reserved successfully!");
              } else {
                System.out.println("Sorry, unable to reserve seats. Please try again.");
              }
              // Handle invalid input for number of seats
            } catch (NumberFormatException e) {
              System.out.println("Invalid input. Please enter a valid number of seats to reserve.");
            }
          } else {
            // Notify user of incorrect syntax for 'reserve' command
            System.out.println("Invalid input. Please enter in the format 'reserve <number>'.");
          }
        } else if (input.equals("show")) {
          // Display the current state of the theater
          System.out.println(theater.printTheater());
        } else if (input.equals("done")) {
          // Exit the reservation system
          System.out.println("Have a nice day!");
          break;
        } else {
          // Notify user of invalid input
          System.out.println("Invalid input. Please enter 'reserve <number>', 'show', or 'done'.");
        }
      }
    } finally {
      // Close the scanner when done to prevent resource leak
      scanner.close();
    }
  }
}
