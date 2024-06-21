package problem1;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Theater class with fields for name, a collection of rows,
 * and a collection of wheelchair rows. Is able to
 * print out theater layout and reserve seats
 */
public class Theater {
  private static Theater instance;
  // fields
  private final String name;
  private final ArrayList<Row> rowCollection;
  private final ArrayList<Integer> wheelchairRows;
  private static final int NUM_SEATS_EMPTY = 0;

  /**
   * Lazy allocation - singleton implementation of Theater. Creates an instance of
   * theater if instance is null, else returns current instance
   * @param name - theater name (String)
   * @param rowCollection - row collection (ArrayList<Row>)
   * @return - instance of theater (Theater)
   */
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

  /**
   * private helper method to create arraylist of wheelchair-accessible rows
   * @param rowCollection - rows in theater (ArrayList<Row>)
   * @return - collection of wheelchair accessible rows in theater (Arraylist<Integer>)
   */
  private ArrayList<Integer> createWheelchairRows(ArrayList<Row> rowCollection) {
    ArrayList<Integer> wheelchairRows = new ArrayList<>();
    for (Row row : rowCollection) {   // iterate through rows
      if (row.isWheelchair()) {   // if row's wheelchair flag true, add row number to array list
        wheelchairRows.add(row.getRowNumber());
      }
    }
    return wheelchairRows;    // default: non-empty b/c one row must be wheelchair accessible
  }

  /**
   * Returns theater name
   * @return - theater name (String)
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns collection of rows
   * @return - row collection (ArrayList<row>)
   */
  public ArrayList<Row> getRowCollection() {
    return this.rowCollection;
  }

  /**
   * Returns collection of wheelchair accessible rows
   * @return - collection of wheelchair rows (ArrayList<row>)
   */
  public ArrayList<Integer> getWheelchairRows() {
    return this.wheelchairRows;
  }

  /**
   * Makes a reservation and updates the theater accordingly
   * @param name - name of res (String)
   * @param numSeats - num of seats (int)
   * @param wheelchair - whether seats must be wheelchair accessible (boolean)
   * @return - whether reservation was successful (boolean)
   */
  public boolean makeReservation(String name, int numSeats, boolean wheelchair) {
    int middleRow = rowCollection.size() / 2;   // find middle row
    // case 1. invalid numSeats
    if (numSeats <= NUM_SEATS_EMPTY || numSeats > this.rowCollection.get(middleRow).size())
      return false;

    // case 2. no wheelchair
    if (!wheelchair) {
      for (int i = 0; i <= middleRow; i++) {
        int row1 = middleRow - i; // closer to screen (front row)
        int row2 = middleRow + i; // closer to back (back row)

        // fill seats yields true (success), return true
        Row r1 = this.rowCollection.get(row1);
        if (!r1.isWheelchair() && r1.fillSeats(name, numSeats)) {
          return true;
        }

        // same as above
        if (row2 < this.rowCollection.size()) {   // maintain in bounds
          Row r2 = this.rowCollection.get(row2);
          // if row2 within upper bounds, not wheelchair row, && fill seats success --> return true
          if (!r2.isWheelchair() && r2.fillSeats(name, numSeats)) {
            return true;
          }
        }
      }
    }

    // case 3. wheelchair or (no-wheelchair && no available seats)
    for (int i = 0; i <= middleRow; i++) { // allow subtraction to find seats closest to middle
      int row1 = middleRow - i; // closer to screen (front row), to get index
      int row2 = middleRow + i; // closer to back (back row), to get index

      Row r1 = this.rowCollection.get(row1);
      if (r1.isWheelchair() && r1.fillSeats(name, numSeats)) {
        return true;
      }
      if (row2 < this.rowCollection.size()) {
        Row r2 = this.rowCollection.get(row2);
        // if row2 within upper bounds, not wheelchair row, && fill seats success --> return true
        if (r2.isWheelchair() && r2.fillSeats(name, numSeats)) {
          return true;
        }
      }
    }
    // case 4. not possible to reserve
    return false;
  }

  /**
   * Returns a diagram for the seating arrangements in the theater
   * @return - theater seating (String)
   */
  public String printTheater() {
    StringBuilder sb = new StringBuilder();
    for (Row row: rowCollection) {
      sb.append(row.printRow());
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * returns string representation of object
   * @return - string representation (String)
   */
  @Override
  public String toString() {
    return "Theater{" +
        "name='" + this.name + '\'' +
        ", rowCollection=" + this.rowCollection +
        ", wheelchairRows=" + this.wheelchairRows +
        '}';
  }
}