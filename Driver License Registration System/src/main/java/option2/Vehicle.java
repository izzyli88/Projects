package option2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Vehicle class containing information for a regular vehicle and a nested class for a vehicle
 * insurance
 */
public class Vehicle<T> {
  private static final Integer CURR_YEAR = 2024;
  private static final Integer AGE_LIMIT = 15;
  private final String model;
  private final String make;
  private final Integer year;
  private final Name owner;
  private final VehicleInsurance insurance;
  private final ArrayList<Incident<T>> history;

  /**
   * constructor creating an instance of the vehicle class
   */
  public Vehicle(String model, String make, Integer year, Name owner,
      VehicleInsurance insurance, ArrayList<Incident<T>> history) {
    this.model = model;
    this.make = make;
    this.year = year;
    this.owner = owner;
    this.insurance = insurance;
    this.history = history;
  }

  public String getModel() {
    return model;
  }

  public String getMake() {
    return make;
  }

  public Integer getYear() {
    return year;
  }

  public Name getOwner() {
    return owner;
  }

  public VehicleInsurance getInsurance() {
    return insurance;
  }

  public ArrayList<Incident<T>> getHistory() {
    return history;
  }

  /**
   * helper method to check if age is < 15
   * @return
   */
  private boolean vehicleAgeCheck() {
    return CURR_YEAR - this.getYear() + 1 < AGE_LIMIT;
  }

  /**
   * method to check if vehicle is valid (insurance not expired,
   * age good, history is good
   * @return - whether vehicle is good
   */
  public boolean vehicleCheck() {
    return this.vehicleAgeCheck() && this.getInsurance().checkExpired() &&
        this.vehicleHistoryCheck();
  }

  /**
   * prints vehicle information for vehicle
   */
  public void printVehicleInfo() {
    System.out.println("\t" + this.getYear() + " " + this.getModel() + " " + this.getMake());
  }

  /**
   * checks if incidents meet specifications
   * @return - checks if crashes/moving violations occurred more than 6 months ago
   */
  private boolean vehicleHistoryCheck() {
    for (Incident<T> incident : this.getHistory()) {
      if (incident == null || incident.incidentLessThan6Months())   // null check for when user doesn't list incidents in vehicle history
        return false;
    }
    return true;
  }

  /**
   * checks for equality
   * @param o - other object
   * @return - whether objects are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vehicle<T> vehicle = (Vehicle<T>) o;
    return Objects.equals(getModel(), vehicle.getModel()) && Objects.equals(
        getMake(), vehicle.getMake()) && Objects.equals(getYear(), vehicle.getYear())
        && Objects.equals(getOwner(), vehicle.getOwner()) && Objects.equals(getHistory(),
        vehicle.getHistory()) && Objects.equals(getInsurance(), vehicle.getInsurance());
  }

  /**
   * returns hash of object
   * @return - object hash
   */
  @Override
  public int hashCode() {
    return Objects.hash(getModel(), getMake(), getYear(), getOwner(), getInsurance(), getHistory());
  }

  /**
   * returns string representation of object
   * @return - string rep
   */
  @Override
  public String toString() {
    return "Vehicle{" +
        "model='" + model + '\'' +
        ", make='" + make + '\'' +
        ", year=" + year +
        ", owner=" + owner +
        ", insurance=" + insurance +
        ", history=" + history +
        '}';
  }

  /**
   * class for vehicle insurance
   */
  public static class VehicleInsurance {
    private final Name owner;
    private final ArrayList<Name> otherDrivers;
    private final LocalDate expDate;
    private final LocalDate CURR_DATE = LocalDate.now();

    /**
     * constructor creating an instance of vehicle insurance. Owner must not be in the list of other
     * drivers
     * @param owner        - official owner (Name)
     * @param otherDrivers - other drivers (ArrayList<Name>)
     * @param expDate      - expiration date (LocalDate)
     */
    public VehicleInsurance(Name owner, ArrayList<Name> otherDrivers, LocalDate expDate) {
      this.owner = owner;
      this.otherDrivers = otherDrivers;
      this.expDate = expDate;
    }

    /**
     * checks whether vehicle insurance has expired
     * @return - insurance expired
     */
    public boolean checkExpired() {
      return this.expDate.isAfter(CURR_DATE);
    }

    /**
     * returns owner
     * @return - owner (Name)
     */
    public Name getOwner() {
      return owner;
    }

    /**
     * @return - list of other drivers (ArrayList<Name>)
     */
    public ArrayList<Name> getOtherDrivers() {
      return otherDrivers;
    }

    /**
     * returns expiration date
     * @return - exp date (LocalDate)
     */
    public LocalDate getExpDate() {
      return expDate;
    }

    /**
     * checks for equality
     * @param o - object being compared to
     * @return - whether objects are equal
     */
    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      VehicleInsurance that = (VehicleInsurance) o;
      return Objects.equals(getOwner(), that.getOwner()) && Objects.equals(
          getOtherDrivers(), that.getOtherDrivers()) && Objects.equals(getExpDate(),
          that.getExpDate());
    }

    /**
     * returns hash of object
     * @return - object hash
     */
    @Override
    public int hashCode() {
      return Objects.hash(getOwner(), getOtherDrivers(), getExpDate());
    }

    /**
     * returns string rep of insurance
     * @return - string representation
     */
    @Override
    public String toString() {
      return "VehicleInsurance{" +
          "owner=" + owner +
          ", otherDrivers=" + otherDrivers +
          ", expDate=" + expDate +
          ", CURR_DATE=" + CURR_DATE +
          '}';
    }
  }
}
