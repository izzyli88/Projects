package option2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import option2.Crash.CrashType;
import option2.MovingViolation.MovingViolationType;
import option2.NonMovingViolation.NonMovingViolationType;
import option2.Vehicle.VehicleInsurance;

/**
 * Class to process the driver/vehicle CSVs and populate a valid list of drivers
 */
public class FileProcessor {
  private static final List<Map<String, String>> driverList = new ArrayList<>();    // collection of maps for drivers
  private static final List<Map<String, String>> vehicleList = new ArrayList<>(); // collection of maps for vehicles

  // categories to set as map key
  // driver categories
  private static final String FIRST_NAME = "First Name";
  private static final String LAST_NAME = "Last Name";
  private static final String BDAY = "Birthday";
  private static final String LICENSE_NUM = "License Number";
  private static final String LICENSE_FN = "License First Name";
  private static final String LICENSE_LN = "License Last Name";
  private static final String LICENSE_ADD = "Address";
  private static final String LICENSE_BDAY = "License Birthday";
  private static final String LICENSE_COUNTRY = "Country";
  private static final String LICENSE_STATE = "State";
  private static final String ISSUANCE_DATE = "License Issuance Date";
  private static final String EXP_DATE = "License Expiration Date";
  private static final String INCIDENT_DATE = "Incident Date";
  private static final String INCIDENT_TYPE = "Incident Type";
  
  // vehicle fields
  private static final String MODEL = "Model";
  private static final String MAKE = "Make";
  private static final String YEAR = "Year";
  private static final String OWNER_FN = "Owner First Name";
  private static final String OWNER_LN = "Owner Last Name";
  private static final String INSURANCE_FN = "Insurance Owner First Name";
  private static final String INSURANCE_LN = "Insurance Owner Last Name";
  private static final String OTHER_FN = "Other Covered First Name";
  private static final String OTHER_LN = "Other Covered Last Name";
  private static final String INSURANCE_EXP = "Insurance Expiration Date";
  private static final String INCIDENT_FN = "Incident First Name";
  private static final String INCIDENT_LN = "Incident Last Name";
  private static final String VEHICLE_INCIDENT_DATE = "Vehicle Incident Date";
  private static final String VEHICLE_INCIDENT_TYPE = "Vehicle Incident Type";

  // incident types
  private static final String DISTRACTED = "DistractedDriving";
  private static final String RECKLESS = "RecklessDriving";
  private static final String SPEEDING = "Speeding";
  private static final String DRIVING_UNDER_INFLUENCE = "DrivingUnderInfluence";
  private static final String RESPECT_TRAFFIC_SIGNS = "RespectSigns";
  private static final String LICENSE_INSURANCE_GONE = "NoLicenseOrInsurance";
  private static final String PARKING_VIOLATION = "ParkingViolation";
  private static final String PAPERWORK_PROBLEMS = "PaperworkIssues";
  private static final String PROBLEMS_WITH_VEHICLE = "ProblemsWithVehicle";
  private static final String FENDER_BENDER = "FenderBender";
  private static final String NO_BODILY_INJURIES = "NoBodilyInjuries";
  private static final String YES_BODILY_INJURIES = "BodilyInjuries";

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

  /**
   * reads a given path (vehicle/driver) and populates appropriate list of maps
   * @param filePath - given file path
   * @param isDriver - bool to choose which list of maps to populate
   */
  private static void collectFileData(String filePath, boolean isDriver) {
    if (filePath == null) {
      System.out.println("File path is null.");
      return;
    }
    Path path = Paths.get(filePath);

    // check: file exists
    if (!Files.exists(path)) {
      System.out.println("File does not exist: " + filePath);
      return;
    }

    try(BufferedReader inputFile = new BufferedReader(new FileReader(filePath))) {
      String headerLine = inputFile.readLine();  // fields
      Pattern pattern = Pattern.compile("\"([^\"]*)\"");    // pattern: "XXXXX" "YYYYYY"
      Matcher headerMatcher  = pattern.matcher(headerLine);


      List<String> fields = new ArrayList<>();    // list for fields
      while (headerMatcher.find()) {
        fields.add(headerMatcher.group(1)); // populates fields list
      }

      int numFields = fields.size();
      String line;

      // read remaining lines
      while ((line = inputFile.readLine()) != null) {
        if (!line.trim().isEmpty()) {

          // make a map for every driver or vehicle
          Map<String, String> driverOrVehicleMap = makeMap(line, numFields, fields);

          // add map to driver list or vehicle list depending on variable
          if (isDriver)
            driverList.add(driverOrVehicleMap);
          else vehicleList.add(driverOrVehicleMap);
        }
      }
    }
    catch (IOException e) {
      System.out.println("Something went wrong.");
    }
  }

  /**
   * creates a map for each driver or vehicle
   * @param line - given line (String)
   * @param numFields - num fields (int)
   * @param fields - categories (List<String>)
   * @return - map for driver (Map<String, String>)
   */
  private static Map<String, String> makeMap(String line, int numFields,
      List<String> fields) {
    Map<String, String> data = new HashMap<>(); // map for driver or vehicle

    Pattern pattern = Pattern.compile("\"([^\"]*)\"");
    Matcher matcher = pattern.matcher(line);

    ArrayList<String> info = new ArrayList<>();
    while (matcher.find()) {    // separate into each field
      info.add(matcher.group(1)); //
    }

    // populate map for driver or vehicle
    for (int i = 0; i < numFields; i++) {   // add info to map
      data.put(fields.get(i), info.get(i));   //field: field name: info: actual data
    }
    return data;
  }

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

  /**
   * creates a license object using map data
   * @param driverMap - driver map
   * @return - new license for driver
   */
  private static License getLicense(Map<String, String> driverMap){
    String num = driverMap.get(LICENSE_NUM);
    Name name = new Name(driverMap.get(LICENSE_FN), driverMap.get(LICENSE_LN));
    String address = driverMap.get(LICENSE_ADD);
    LocalDate bday = LocalDate.parse(driverMap.get(LICENSE_BDAY));
    String country = driverMap.get(LICENSE_COUNTRY);
    String state = driverMap.get(LICENSE_STATE);
    LocalDate issuance = LocalDate.parse(driverMap.get(ISSUANCE_DATE));
    LocalDate expDate = LocalDate.parse(driverMap.get(EXP_DATE));

    return new License(num, name, address, bday, country, state, issuance, expDate);

  }

  /**
   * returns collection of incidents
   * @param map - given map
   * @param driverName - driver name
   * @return - list of incidents
   */
  private static ArrayList<Incident> createDriverVio(Map<String, String> map, Name driverName) {
    ArrayList<Incident> violations = new ArrayList<>();

    // checks for blank violations: no violations
    if (Objects.equals(map.get(INCIDENT_DATE), ""))
      return violations;

    String[] violationDates = map.get(INCIDENT_DATE).split(", ");    // split into arr of dates & types
    String[] violationTypes = map.get(INCIDENT_TYPE).split(", ");
    int numViolations = violationDates.length;

    // add violations to list
    for (int i = 0; i < numViolations; i++) {
      LocalDate date = LocalDate.parse(violationDates[i]);
      String type = violationTypes[i];
      Incident incident = createIncident(driverName, date, type);
      violations.add(incident);
    }
    return violations;
  }

  /**
   * create a collection of drivers
   * @param file - given file path
   * @return - collection of drivers
   */
  private static ArrayList<Vehicle> collectVehicles(String file) {
    FileProcessor.collectFileData(file, false);
    ArrayList<Vehicle> vehicles = new ArrayList<>();

    for (Map<String, String> map : vehicleList) {   // cycle through maps
      vehicles.add(FileProcessor.createVehicle(map)); // add vehicle to collection
    }
    return vehicles;
  }

  /**
   * create a vehicle object from a map
   * @param map - map w/ vehicle data
   * @return - vehicle
   */
  private static Vehicle createVehicle(Map<String, String> map) {
    String model = map.get(MODEL);
    String make = map.get(MAKE);
    Integer year = Integer.parseInt(map.get(YEAR));
    Name owner = new Name(map.get(OWNER_FN), map.get(OWNER_LN));
    VehicleInsurance insurance = getVehicleInsurance(map);
    ArrayList<Incident> vehicleHistory = getViolations(map);
    return new Vehicle(model, make, year, owner, insurance, vehicleHistory);
  }

  /**
   * create a vehicle insurance object from a map
   * @param map - map w/ vehicle data
   * @return - vehicle insurance object
   */
  private static VehicleInsurance getVehicleInsurance(Map<String, String> map){
    Name name = new Name(map.get(INSURANCE_FN), map.get(INSURANCE_LN));
    ArrayList<Name> othersCovered = otherPeopleCovered(map);
    LocalDate expDate = LocalDate.parse(map.get(INSURANCE_EXP));
    return new VehicleInsurance(name, othersCovered, expDate);
  }

  /**
   * collect list of people who are insured
   * @param map - map w/ vehicle data
   * @return - list of ppl covered by insurance
   */
  private static ArrayList<Name> otherPeopleCovered(Map<String, String> map) {
    ArrayList<Name> otherCovered = new ArrayList<>();
    if (Objects.equals(map.get(OTHER_FN), ""))  // checks for blank list
      return otherCovered;

    String[] firstNames = map.get(OTHER_FN).split(", ");
    String[] lastNames = map.get(OTHER_LN).split(", ");

    for (int i = 0; i < firstNames.length; i++) {
      otherCovered.add(new Name(firstNames[i], lastNames[i]));
    }
    return otherCovered;
  }

  /**
   * collect a list of violations
   * @param map - vehicle map
   * @return - list of violations
   */
  private static ArrayList<Incident> getViolations(Map<String, String> map){
    ArrayList<Incident> incidents = new ArrayList<>();

    if (Objects.equals(map.get(INCIDENT_FN), ""))   // checks for blank violations
      return incidents;

    // split into fields by vehicle
    String[] firstName = map.get(INCIDENT_FN).split(", ");
    String[] lastName = map.get(INCIDENT_LN).split(", ");
    String[] dates = map.get(VEHICLE_INCIDENT_DATE).split(", ");
    String[] types = map.get(VEHICLE_INCIDENT_TYPE).split(", ");

    // per vehicle
    for (int i = 0; i < firstName.length; i++) {
      Name name = new Name(firstName[i], lastName[i]);
      LocalDate date = LocalDate.parse(dates[i]);
      String type = types[i];
      Incident incident = createIncident(name, date, type);
      incidents.add(incident);
    }
    return incidents;
  }

  /**
   * returns a incident object depending on incident type
   * @param name - name
   * @param date - date
   * @param incidentType - incident type
   * @return - incident object
   */
  private static Incident createIncident(Name name, LocalDate date, String incidentType) {
    Incident incident = null;
    switch (incidentType){
      case DISTRACTED:
        incident = new MovingViolation(name, date, MovingViolationType.DISTRACTED_DRIVING);
        break;
      case RECKLESS:
        incident = new MovingViolation(name, date, MovingViolationType.RECKLESS_DRIVING);
        break;
      case SPEEDING:
        incident = new MovingViolation(name, date, MovingViolationType.SPEEDING);
        break;
      case DRIVING_UNDER_INFLUENCE:
        incident = new MovingViolation(name, date, MovingViolationType.DUI);
        break;
      case RESPECT_TRAFFIC_SIGNS:
        incident = new MovingViolation(name, date, MovingViolationType.TRAFFIC_SIGN_FAILURE);
        break;
      case LICENSE_INSURANCE_GONE:
        incident = new MovingViolation(name, date, MovingViolationType.MISSING_INSURANCE_AND_LICENSE);
        break;
      case PARKING_VIOLATION:
        incident = new NonMovingViolation(name, date, NonMovingViolationType.PARKING);
        break;
      case PAPERWORK_PROBLEMS:
        incident = new NonMovingViolation(name, date, NonMovingViolationType.PAPERWORK);
        break;
      case PROBLEMS_WITH_VEHICLE:
        incident = new NonMovingViolation(name, date, NonMovingViolationType.VEHICLE_PROBLEMS);
        break;
      case FENDER_BENDER:
        incident = new Crash(name, date, CrashType.FENDER_BENDER);
        break;
      case NO_BODILY_INJURIES:
        incident = new Crash(name, date, CrashType.NO_BODILY_INJURY);
        break;
      case YES_BODILY_INJURIES:
        incident = new Crash(name, date, CrashType.BODILY_INJURY);
    }
    return incident;
  }

  /**
   * helper method to match driver with vehicle depending on driver name &
   * owner name/insurance covered names
   * @param drivers - collection of drivers
   * @param vehicles - collection of vehicles
   */
  private static void matchDriversVehicles(ArrayList<Driver> drivers, ArrayList<Vehicle> vehicles) {
    for (Vehicle vehicle : vehicles) {  // cycle through vehicles
      Name owner = vehicle.getOwner();
      for (Driver d : drivers) { // cycle through drivers
        Name name = d.getName();

        // add if matches w/ owner name or in list of people covered by insurance
        if (name.equals(owner) || vehicle.getInsurance().getOtherDrivers().contains(name))
          d.addVehicle(vehicle);
      }
    }
  }
}
