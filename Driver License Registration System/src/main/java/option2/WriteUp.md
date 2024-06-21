# Writeup
## Problem Overview: Option 2
This project addresses option 2, where I have implemented a prospective driver rideshare system. Here, the program is interactive and allows the user to input in two file paths: one for a list of drivers and one for a list of associated vehicles. The program then processes this data and matches vehicles to the drivers, and links any additional pertinent information to the driver such as birthday, country, and license information.

The program then filters through these drivers based on a predefined list of requirements to reveal a list of "valid drivers". From then, the user can then further interact with the program by inputting in last names .The program will then output all drivers with their relevant information to the terminal. If the last name does not exist, then the program will tell the user as well.
## Challenges
There were several challenges that I encountered while writing this program. In particular, since I don't have too much experience in parsing throug csv files, I found that parsing the driver data to be especially difficult. At first, I decided to have a single file with Driver information. This resulted in a lot of issues, since when the driver had a multiple vehicles/people covered by insurance, I had to deal with multiple layers of nesting. I later decided that it would be a lot easier to just implement two files: one for drivers and one for vehicles. I would collect information for both and then link the vehicles to the drivers later.

Another challenge that I faced was how to go about dealing with the different types of incidents (nonmoving, moving, and crashes). I noticed that they all had similar features (name, date, and some specific enum type specific to each subclass). I later decided to take advantage of generics and subtype polymorphism to allow the program to achieve the desired requirements.
## Project Instructions: Setup

1. FORMAT OF FILES must follow "xxx" "yyy" "zz". If you need multiple incidents, separate each by a comma for each of the fields relevant to the incident (date/type)
2. Dates in the csv should follow the format: YYYY-mm-dd
3. To determine the exact incident type to input into the csv, refer to the FileProcessor static variables 
4. Ideally, the incidents the driver has should match with the vehicle's incidents that are associated with that driver in the files
5. Input exact file path to program
## Resources
In addition to the PPT slides and notes from the class, here are a list of resources I used for this project:
1. https://www.geeksforgeeks.org/localdate-parse-method-in-java-with-examples/
2. https://www.geeksforgeeks.org/software-engineering-coupling-and-cohesion/
3. https://www.rexegg.com/regex-quickstart.html
4. https://docs.oracle.com/javase/8/docs/api/java/io/ByteArrayOutputStream.html
5. https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
## Assumptions
1. Driver history list are all incidents. Vehicle history objects are aso all incidents.
## Software System Responses
### 1. Inheritance & Composition
#### Inheritance: System with Incident, MovingViolation, NonMovingViolation, and Crash classes
```java
/**
 * abstract class Incident extending to Crash and Violation, uses generics
 * for type of incident (enum)
 */
public abstract class Incident<T> {

  protected Name driver;
  protected LocalDate date;
  protected T incidentType;   // type of incident: distracted/fender bender/etc.
  private static final Integer SIX_MTH_LIMIT = 6;
  private static final LocalDate DATE_LIMIT = LocalDate.now().minusMonths(SIX_MTH_LIMIT);
}
/**
 * Crash class extending from Incident parent class. Contains
 * info for specific typeOfCrash of crash (enum)
 */
public class Crash extends Incident<CrashType> {

  /**
   * Constructor creating an instance of the crash class
   * @param driver - driver (Name)
   * @param date - date of crash (LocalDate)
   * @param crashType - type of crash (CrashType Enum)
   */
  public Crash(Name driver, LocalDate date, CrashType crashType) {
    super(driver, date, crashType);
  }
}
```
#### Composition: Driver class containing Name class, Vehicle class, and Incidents as fields
```java
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
}
```
### 2. Interfaces
#### Interface for IProvideDriverInfo method
````java
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
````
### 3. Method Overriding & Method Overloading
#### Method Overriding: overriding Incident class's isValidMovingViolationType() method
Incident class:
```java
  /**
   * checks if incident is a valid type of moving violation (distracted/traffic signs)
   * default true for crash/non-moving b/c they're not moving violations to begin w/.
   * method will be overridden in the MovingViolation class
   * @return - whether type is a valid moving violation (default true)
   */
  protected boolean isValidMovingViolationType() {
    return true;
  }
```
MovingViolation class:
```java
  /**
   * checks if type of mv is valid
   * @return - whether mv is valid (distracted/traffic sign failure)
   */
  public boolean isValidMovingViolationType(){
    return this.incidentType.equals(MovingViolationType.DISTRACTED_DRIVING) ||
        this.incidentType.equals(MovingViolationType.TRAFFIC_SIGN_FAILURE);
  }
```
#### Method Overloading: Not implemented to my knowledge; none of my methods require different parameter types/quantities
### 4. Encapsulation
#### Fields are all private and only getters are used to access these fields
```java
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
}
```
#### Implementation details of isValidDriver() is hidden through private helper methods
```java
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
```
### 5. Subtype Polymorphism
#### Utilized throughout Incident superclass/its child classes to determine whether the incidents in the driver's history list/vehicle history fulfill driver specs
Method in driver class relying on subtype polymorphism, where static type is Incident
```java
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
```
Part of method to create new instances of Incident, where dynamic type is Crash/MovingViolation/NonMovingViolation
```java
/**
   * returns a incident object depending on incident type
   * @param name - name
   * @param date - date
   * @param incidentType - incident type
   * @return - incident object
   */
  private static Incident createIncident(Name name, LocalDate date, String incidentType) {
    Incident incident = null;
    switch (incidentType) {
      case DISTRACTED:
        incident = new MovingViolation(name, date, MovingViolationType.DISTRACTED_DRIVING);
        break;
      case RECKLESS:
        incident = new MovingViolation(name, date, MovingViolationType.RECKLESS_DRIVING);
        break;
      case SPEEDING:
        incident = new MovingViolation(name, date, MovingViolationType.SPEEDING);
        break;
    }
  }
```
### 6. Generics
#### Used generics for Incident enum types (CrashType/MovingViolationType/NonMovingViolationType)
```java
/**
 * abstract class Incident extending to Crash and Violation, uses generics
 * for type of incident (enum)
 */
public abstract class Incident<T> {

  protected Name driver;
  protected LocalDate date;
  protected T incidentType;   // type of incident: distracted/fender bender/etc.
}
```
```java
/**
 * Crash class extending from Incident parent class. Contains
 * info for specific typeOfCrash of crash (enum)
 */
public class Crash extends Incident<CrashType> {
  /**
   * Constructor creating an instance of the crash class
   * @param driver - driver (Name)
   * @param date - date of crash (LocalDate)
   * @param crashType - type of crash (CrashType Enum)
   */
  public Crash(Name driver, LocalDate date, CrashType crashType) {
    super(driver, date, crashType);
  }
}
```
### 7. Java Collections Framework
#### ArrayList<X> used to store vehicles and incident history for Driver class
```java
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
}
```
### 8. Iterable & Iterator Interfaces: Not Used - Used For Each Loop/Streams Instead
### 9. Comparable/Comparator
#### Comparable interface implemented by class Driver to allow for comparisons by name
```java
/**
 * Driver class implementing comparable interface that
 * allows drivers to be compared based off name
 */
public class Driver<T> implements Comparable<Driver<T>> {
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
}
```
#### Used to sort names in order in collectValidDrivers method:
```java
/**
   * method to collect all valid drivers
   * @param driverFile - path to driver file
   * @param vehicleFile - path to vehicle file
   * @return - list of valid drivers (ArrayList<Driver>)
   */
  public static ArrayList<Driver> collectValidDrivers(String driverFile, String vehicleFile){
    ArrayList<Driver> allDrivers = FileProcessor.collectDrivers(driverFile);    // pop. drivers
    ArrayList<Vehicle> allVehicles = FileProcessor.collectVehicles(vehicleFile); // pop. vehicles

    // populate all drivers' vehicles
    matchDriversVehicles(allDrivers, allVehicles);

    Collections.sort(allDrivers);   // sort by name

    return RegistrationValidator.retrieveValidDrivers(allDrivers);  // return valid drivers
  }
```
#### Comparator class not implemented, as comparable already utilized
### 10. Regex
#### Regex used to detect patterns in csv files in FileProcessor class
File format: "First Name" "Last Name" "Birthday" "License Number"
```java
    try(BufferedReader inputFile = new BufferedReader(new FileReader(filePath))){
        String headerLine = inputFile.readLine();  // fields
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");    // pattern: "XXXXX" "YYYYYY"
        Matcher headerMatcher = pattern.matcher(headerLine);
      }
```
### 11. Nested Classes
#### nested class for Crash Enum type to promote encapsulation of related functionality
```java
public class Crash extends Incident<CrashType>{
  /**
   * Constructor creating an instance of the crash class
   * @param driver - driver (Name)
   * @param date - date of crash (LocalDate)
   * @param crashType - type of crash (CrashType Enum)
   */
  public Crash(Name driver, LocalDate date, CrashType crashType) {
    super(driver, date, crashType);
  }
  /**
   * enum for crash types
   */
  public enum CrashType {
    FENDER_BENDER,
    NO_BODILY_INJURY,
    BODILY_INJURY
  }
}
```
### 12. Functional Programming
#### Used in provideDriverInfo method
```java
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
```
### 13. Design Patterns
No design patterns were used in this project. This is because design patterns may increase the complexity of the system by too much. Since
this project is relatively small, implementing many design patterns may lead to over-engineering. It can also further complicate testing.

### 14. MVC Architecture
MVC architecture was not used in this project. While it could have been beneficial, I decided to 
have portions of the view aspect of MVC (terminal output/driver info output) part of the Driver class
since that portion of the project was specific to drivers.
### Data & Stamp Coupling
#### Data Coupling: passing around data structure (driverMap) to other methods
```java
  /**
   * create a collection of drivers
   * @param file - given  file path
   * @return - list of drivers (ArrayList<Driver>)
   */
  private static ArrayList<Driver> collectDrivers(String file) {
    FileProcessor.collectFileData(file, true); // populate file data
    // create arr of drivers
    ArrayList<Driver> prospectiveDrivers = new ArrayList<>();

    // iterate through list of maps
    for (Map<String, String> driverMap : driverList) {
      prospectiveDrivers.add(createDriver(driverMap));    // add driver to collection
    }
    return prospectiveDrivers;
  }
  /**
   * create a driver using map data
   * @param driverMap - driver map (Map<String, String>)
   * @return - new driver (Driver)
   */
  private static Driver createDriver(Map<String, String> driverMap){
    Name driverName = new Name(driverMap.get(FIRST_NAME), driverMap.get(LAST_NAME));
    LocalDate driverBday =  LocalDate.parse(driverMap.get(BDAY));

    License driverLicense = getLicense(driverMap);
    ArrayList<Incident> driverViolations = createDriverVio(driverMap, driverName);

    return new Driver(driverName, driverBday, driverLicense, driverViolations);
  }
```
#### Stamp Coupling: I do not believe that there is an example of stamp coupling in my code. Stamp coupling occurs when entire data structures are passed between modules. Since th components of my code all communicate via method calls and don't share data structures, there doesn't seem to be visible signs of stamp coupling in the code. 
