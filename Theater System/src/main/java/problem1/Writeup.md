## HW9 Writeup
We decided to take advantage of creational patterns in this assignment
by using the Singleton pattern for the Theater class.
This ensures that the ReservationSystem class will only have one instance of
Theater at a time. In terms of implementing the Singleton pattern, we decided to
use lazy allocation,  where the theater instance is only created when it is needed for the
first time. Within the getInstance method, if the theater instance is currently
null (first time needed), we call upon the private Theater constructor, which
passes in the name and collection of rows to fully instantiate the theater’s fields.

Because only one instance of Theater is expected to exist at a time, there is no need for an
equals/hashcode method. Consequently, these methods are omitted from the Theater class.
```java
public class Theater {
  private static Theater instance;
  // fields
  private final String name;
  private final ArrayList<Row> rowCollection;
  private final ArrayList<Integer> wheelchairRows;
  private static final int NUM_SEATS_EMPTY = 0;


  public static synchronized Theater getInstance(String name, ArrayList<Row> rowCollection){
    if (instance == null)
      instance = new Theater(name, rowCollection);

    return instance;
  }
  /**
   * Constructor creating an instance of the theater class, consists of a name
   * and a collection of rows.
   * Creates a collection of rows that are wheelchair-accessible
   * @param name - theater name
   * @param rowCollection - collection of rows (ArrayList<Row>)
   */
  private Theater(String name, ArrayList<Row> rowCollection) {
    this.name = name;
    this.rowCollection = rowCollection;
    this.wheelchairRows = createWheelchairRows(rowCollection);
  }
}

```

## Note: Singleton Testing for Theater/ReservationService
If you run the theater test class and ReservationService test class individually, they will pass. However,
if you run them simultaneously using build.gradle, they will fail. This is likely due to the
Singleton implementation of the Theater class, which will only allow one instance of the Theater class to 
exist at a time.

## Note: Theater.reserveSeats method
The Theater.reserveSeats() method is a bit long. After discussing the method with Prof. Bonaci, she said that it was good and that we could proceed. Logic behind method:
Checks for valid numSeats 
1. If reservation: no wheelchair seats needed
2. Loops from middle seats outward, checking for non-wheelchair seats
3. If enough seats in row, makes reservation, else cont. to next row
4. If not enough non-wheelchair seats available, proceed to step 5: wheelchair seats
5. For wheelchair reservations & non-wheelchair reservations that couldn’t find enough non-wheelchair seats
6. Loops from middle seats outward, checking for wheelchair seats
7. If enough seats in row, makes reservation, else cont. to next row
8. If everything else fails, return false: reservation unsuccessful

## Note: ReservationServiceTest: Conflicting Byte Streams

If you look at the ReservationServiceTest.java file, you will see that the
second testReserveSeats() method is commented out. This is because if the
entire HW9 build file is run with it uncommented, you may see that the
testInvalidReservation() method in ReservationServiceTest.java fails due to an
IllegalStateException that states that the Scanner is closed.
However, if you run the test individually, the test passes.
After we discussed this issue with Prof. Bonaci, she stated that this is due
to a race condition from the previous testReserveSeats() method. When the testReserveSeats()
method ends, it closes the scanner, preventing the input from being successfully read in
testInvalidReservation(). Prof. Bonaci said that our current state is sufficient
and that we are allowed to proceed.
