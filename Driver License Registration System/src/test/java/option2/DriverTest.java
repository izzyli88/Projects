package option2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import option2.MovingViolation.MovingViolationType;
import option2.NonMovingViolation.NonMovingViolationType;
import option2.Vehicle.VehicleInsurance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DriverTest {
  private Driver testDriver;
  private Name expName;
  private LocalDate expBday;
  private License expLicense;
  private ArrayList<Incident> expHistory;
  private VehicleInsurance insurance;
  private ArrayList<Incident> vehicleHistory;
  private ArrayList<Vehicle> expVehicles;


  @BeforeEach
  void setUp() {
    expName = new Name("Izzy", "Li");
    expBday = LocalDate.of(1999, 12, 12);
    expLicense = new License("abc123", expName, "111", expBday, "CAN", "wa", LocalDate.of(2005, 12, 12),
        LocalDate.of(2025, 12, 12));

    vehicleHistory = new ArrayList<>(List.of(new Crash(expName, LocalDate.of(2000, 12, 12), Crash.CrashType.FENDER_BENDER),
        new MovingViolation(expName, LocalDate.of(2005, 12, 12), MovingViolationType.DISTRACTED_DRIVING)));

    insurance = new VehicleInsurance(expName, new ArrayList<>(List.of(new Name("Bull", "Frog"))), LocalDate.of(2025, 12, 12));

    expHistory = new ArrayList<>(List.of(new MovingViolation(expName, LocalDate.of(2000, 12, 12), MovingViolationType.DISTRACTED_DRIVING),
        new NonMovingViolation(expName, LocalDate.of(2000, 12, 12), NonMovingViolationType.PAPERWORK)));

    Vehicle expVehicle = new Vehicle("Honda", "Civic", 2024, expName, insurance, vehicleHistory);
    expVehicles = new ArrayList<>(List.of(expVehicle));

    testDriver = new Driver(expName, expBday, expLicense, expHistory);
    testDriver.addVehicle(expVehicle);
  }

  @Test
  void getName() {
    assertEquals(expName, testDriver.getName());
  }

  @Test
  void getBday() {
    assertEquals(expBday, testDriver.getBirthDay());
  }

  @Test
  void getLicenseInfo() {
    assertEquals(expLicense, testDriver.getLicenseInfo());
  }

  @Test
  void getVehicle() {
    assertEquals(expVehicles, testDriver.getVehicles());
  }

  @Test
  void getDriversHistory() {
    assertEquals(expHistory, testDriver.getHistory());
  }

  @Test
  void isValidDriver() {
    assertTrue(testDriver.isValidDriver());
  }

  @Test
  void invalidDriver_BdayNoMatch() {
    Driver other = new Driver(expName, expBday,
        new License("abc123", expName, "111", LocalDate.of(1999, 12, 15), "123", "wa",
            LocalDate.of(2005, 12, 12),
            LocalDate.of(2025, 12, 12)), expHistory);
    assertFalse(other.isValidDriver());
  }
  @Test
  void invalidDriver_LicenseCheck() {
    Driver other = new Driver(expName, expBday,
        new License("abc123", expName, "111", expBday, "123", "wa",
            LocalDate.now(),
            LocalDate.of(2025, 12, 12)), expHistory);
    assertFalse(other.isValidDriver());
  }

  @Test
  void invalidDriver_VehicleCheck(){
    ArrayList<Incident> otherHistory = new ArrayList<>(List.of(new Crash(expName, LocalDate.now(), Crash.CrashType.FENDER_BENDER)));
    Vehicle otherVehicle = new Vehicle("Honda", "Civic", 2024, expName, insurance, otherHistory);
    Driver other = new Driver(expName, expBday, expLicense, expHistory);
    other.addVehicle(otherVehicle);
    assertFalse(other.isValidDriver());

  }

  @Test
  void invalidDriver_OfficialOwnerCheck(){
    Vehicle otherVehicle = new Vehicle("Honda", "Civic", 2024, new Name("a", "a"), insurance, vehicleHistory);
    Driver other = new Driver(expName, expBday, expLicense, expHistory);
    other.addVehicle(otherVehicle);
    assertFalse(other.isValidDriver());
  }


  @Test
  void testSameObject(){
    assertTrue(testDriver.equals(testDriver));
  }
  @Test
  void testEquals_Null(){
    assertFalse(testDriver.equals(null));
  }

  @Test
  void testEquals_DiffClass(){
    assertFalse(testDriver.equals("String"));
  }

  @Test
  void testHashCode() {
    int expHash = Objects.hash(expName, expBday, expLicense, expVehicles, expHistory);
    assertEquals(expHash, testDriver.hashCode());
  }

  @Test
  void testToString(){
    String s = "Driver{" +"name=" + expName +
          ", birthDay=" + expBday +
          ", license=" + expLicense +
          ", vehicles=" + expVehicles +
          ", history=" + expHistory +
          '}';
    assertEquals(s, testDriver.toString());
  }
}