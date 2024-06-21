package option2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import option2.Crash.CrashType;
import option2.Vehicle.VehicleInsurance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleTest {
  private Vehicle<CrashType> test;
  private String model;
  private String make;
  private Integer year;
  private Name expOwner;
  private VehicleInsurance expInsurance;
  private ArrayList<Incident<CrashType>> expHistory;


  @BeforeEach
  void setUp() {
    model = "Honda";
    make = "Civic";
    year = 2024;
    expOwner = new Name("Rin", "Li");
    expInsurance = new VehicleInsurance(expOwner,
        new ArrayList<>(List.of(new Name("Frog", "Frog"))), LocalDate.of(2026, 12, 12));

    expHistory = new ArrayList<>(List.of(new Crash(expOwner, LocalDate.now().minusMonths(6), Crash.CrashType.FENDER_BENDER)));
    test = new Vehicle<>(model, make, year, expOwner, expInsurance, expHistory);
  }

  @Test
  void incidentNull(){
    expHistory.add(null);
    assertFalse(test.vehicleCheck());
  }

  @Test
  void getModel() {
    assertEquals(model, test.getModel());
  }

  @Test
  void getMake() {
    assertEquals(make, test.getMake());
  }
  @Test
  void testVehicleHistoryCheck_CrashLessThan6Months(){
    assertTrue(test.vehicleCheck());
  }

  @Test
  void testVehicleCheck_Age(){
    Vehicle other = new Vehicle(model, make, 2000, expOwner, expInsurance, expHistory);
    assertFalse(other.vehicleCheck());
  }

  @Test
  void testVehicleCheck_ExpiredInsurance(){
    VehicleInsurance other = new VehicleInsurance(expOwner,
        new ArrayList<>(List.of(new Name("Frog", "Frog"))), LocalDate.of(2022, 12, 12));
    Vehicle<CrashType> otherVehicle = new Vehicle<>(model, make, year, expOwner, other, expHistory);
    assertFalse(otherVehicle.vehicleCheck());
  }

  @Test
  void testSameObject(){
    assertTrue(test.equals(test));
  }
  @Test
  void testEquals_Null(){
    assertFalse(test.equals(null));
  }

  @Test
  void testEquals_DiffClass(){
    assertFalse(test.equals("String"));
  }


  @Test
  void testEquals_DiffMake(){
    Vehicle<CrashType> other = new Vehicle<>("hello", make, year, expOwner, expInsurance, expHistory);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffModel(){
    Vehicle<CrashType> other = new Vehicle<>(model, "make", year, expOwner, expInsurance, expHistory);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffYear(){
    Vehicle<CrashType> other = new Vehicle<>(model, make, 100, expOwner, expInsurance, expHistory);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffOwner(){
    Vehicle<CrashType> other = new Vehicle<>(model, make, year, new Name("Hello", "Hello"), expInsurance, expHistory);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffInsurance(){
    VehicleInsurance otherInsurance = new VehicleInsurance(expOwner,
        new ArrayList<>(List.of(new Name("Frog", "Frog"))), LocalDate.of(100, 1, 1));

    Vehicle<CrashType> other = new Vehicle<>(model, make, year, expOwner, otherInsurance, expHistory);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffHistory(){
    Vehicle<CrashType> other = new Vehicle<>(model, make, year, expOwner, expInsurance, new ArrayList<>());
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_SameFields(){
    Vehicle<CrashType> other = new Vehicle<>(model, make, year, expOwner, expInsurance, expHistory);
    assertTrue(other.equals(test));
  }

  @Test
  void testToString() {
    String expString = "Vehicle{" +
        "model='" + model + '\'' +
        ", make='" + make + '\'' +
        ", year=" + year +
        ", owner=" + expOwner +
        ", insurance=" + expInsurance +
        ", history=" + expHistory +
        '}';

    assertEquals(expString, test.toString());
  }

  @Test
  void testHashCode() {
    int expHash = Objects.hash(model, make, year, expOwner, expInsurance, expHistory);
    assertEquals(expHash, test.hashCode());
  }
}