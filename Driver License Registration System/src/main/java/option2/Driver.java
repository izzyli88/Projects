package option2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Driver class implementing comparable interface that
 * allows drivers to be compared based off name
 */
public class Driver<T> implements Comparable<Driver<T>> {
  private final Name name;
  private final LocalDate birthDay;
  private final License license;
  private final ArrayList<Vehicle<T>> vehicles;
  private final ArrayList<Incident<T>> history;

  private static final Integer AGE_LIMIT = 21;
  private static final LocalDate DATE_LIMIT = LocalDate.now().minusYears(AGE_LIMIT);

  /**
   * Constructor creating an instance of the Driver class. Instantiates
   * vehicle list to be an empty array
   * @param name - driver name (Name)
   * @param birthDay - driver birthDay (LocalDate)
   * @param license - driver license (License)
   * @param history - driver vehicle history (ArrayList<Incident>)
   */
  public Driver(Name name, LocalDate birthDay, License license,
      ArrayList<Incident<T>> history) {
    this.name = name;
    this.birthDay = birthDay;
    this.vehicles = new ArrayList<>();
    this.license = license;
    this.history = history;
  }

  /**
   * returns name
   * @return - name (Name)
   */
  public Name getName() {
    return this.name;
  }

  /**
   * returns birthday
   * @return - birthday (LocalDate)
   */
  public LocalDate getBirthDay() {
    return this.birthDay;
  }

  /**
   * returns license info
   * @return - license info (License)
   */
  public License getLicenseInfo() {
    return this.license;
  }

  /**
   * returns list of vehicles
   * @return - vehicles (List<Vehicle>)
   */
  public ArrayList<Vehicle<T>> getVehicles() {
    return this.vehicles;
  }

  /**
   * returns driver history
   * @return - driver history (ArrayList<Incident>)
   */
  public ArrayList<Incident<T>> getHistory() {
    return this.history;
  }

  /**
   * Method to check if a driver is valid for registration purposes
   * @return - whether driver is valid
   */
  public boolean isValidDriver() {
    return this.driverAgeLicenseVehicleOwnerCheck() &&
        this.license.checkLicenseMeetsRequirements() &&
        this.vehicleInsuranceAgeCheck();
  }

  /**
   * helper method to check if driver info itself is valid
   * (bday/name matches, vehicle history check works, insurance check)
   * @return - driver info is valid
   */
  private boolean driverAgeLicenseVehicleOwnerCheck(){
    return this.driverAgeCheck() && this.licenseNameCheck() && this.licenseBdayCheck()
        && this.ownerOrInsured() && this.invalidMovingViolation();
  }

  /**
   * adds vehicle to vehicle collection
   * @param vehicle - given vehicle (Vehicle)
   */
  public void addVehicle(Vehicle<T> vehicle) {
    this.vehicles.add(vehicle);
  }

  /**
   * method to print out driver info for simulation
   */
  public void printDriverInfo(){
    System.out.println(this.getName().lastName() + ", " + this.getName().firstName() + "\n\t" + this.getLicenseInfo().licenseNum());
    printAllVehicleInfo();    // print out vehicle info

    if (this.history.isEmpty()){    // no violations - empty arr
      System.out.println("\tNo violations made.");
      return;
    }

    // print out violation & violation info
    System.out.println("\tViolations:");
    this.history.stream().map(Incident -> "\t\t" + Incident.outputIncidentType()).forEach(
        System.out::println);
  }

  /**
   * helper method that prints out all information for collection of vehicles
   */
  private void printAllVehicleInfo(){
    this.getVehicles().forEach(Vehicle::printVehicleInfo);
  }

  /**
   * helper method checking whether a driver's age is within limits (>= 21)
   * @return - whether driver's age is valid (boolean)
   */
  private boolean driverAgeCheck() {
    return this.birthDay.isBefore(DATE_LIMIT);
  }

  /**
   * checks whether name matches w/ license
   * @return - whether license name matches (boolean)
   */
  private boolean licenseNameCheck() {
    return this.name.equals(this.license.driverName());
  }

  /**
   * Checks whether bday matches with license
   * @return - whether license birthday matches (boolean)
   */
  private boolean licenseBdayCheck() {
    return this.birthDay.equals(this.license.bday());
  }

  /**
   * checks whether all vehicles are valid (age/insurance exp/crash before 6 months)
   * @return - whether all vehicles are valid (boolean)
   */
  private boolean vehicleInsuranceAgeCheck() {
    for (Vehicle<T> vehicle : this.vehicles) {
      if (!vehicle.vehicleCheck())    // if no pass,immediately return false
        return false;
    }
    return true;  // all vehicles valid
  }

  /**
   * Checks if driver is owner/insured
   * @return - whether driver is owner/insured (boolean)
   */
  private boolean ownerOrInsured() {
    for (Vehicle<T> vehicle : this.vehicles) {
      if (!this.name.equals(vehicle.getOwner())) {   // not owner, check insurance
        if (!vehicle.getInsurance().getOtherDrivers().contains(this.name))    // is not covered by insurance, return false
          return false;
      }
    }
    return true;  // is owner/covered by insurance
  }

  /**
   * checks whether driver has any valid moving incidents
   * @return - whether drive has valid moving incident (boolean)
   */
  private boolean invalidMovingViolation(){
    for (Incident<T> incident : this.history) {
      if (!incident.isValidMovingViolationType())
        return false;
    }
    return true;
  }

  /**
   * compares name w/ another, last name prioritized, then checks FN
   * @param other the object to be compared.
   * @return - int representing degree of comparison
   */
  public int compareTo(Driver other) {
    int lastNameComparison = this.getName().lastName().compareTo(other.getName().lastName());
    if (lastNameComparison != 0) {
      return lastNameComparison;
    } else return this.getName().firstName().compareTo(other.getName().firstName());
  }

  /**
   * checks for equality
   * @param o - other object
   * @return - whether object are equal (boolean)
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Driver<T> driver = (Driver<T>) o;
    return Objects.equals(getName(), driver.getName()) && Objects.equals(
        getBirthDay(), driver.getBirthDay()) && Objects.equals(getLicenseInfo(),
        driver.getLicenseInfo()) && Objects.equals(getVehicles(), driver.getVehicles())
        && Objects.equals(getHistory(), driver.getHistory());
  }

  /**
   * returns hash of object
   * @return - object hash (int)
   */
  @Override
  public int hashCode() {
    return Objects.hash(getName(), getBirthDay(), getLicenseInfo(), getVehicles(), getHistory());
  }

  /**
   * returns string representation
   * @return - string representation (String)
   */
  @Override
  public String toString() {
    return "Driver{" +
        "name=" + name +
        ", birthDay=" + birthDay +
        ", license=" + license +
        ", vehicles=" + vehicles +
        ", history=" + history +
        '}';
  }
}
