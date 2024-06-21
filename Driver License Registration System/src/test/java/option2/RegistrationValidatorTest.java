package option2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import option2.MovingViolation.MovingViolationType;
import option2.Vehicle.VehicleInsurance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationValidatorTest {
  private Driver d1;  // valid
  private Driver d2;
  private Driver d3;    // duplicate of d1
  private Driver d4;  // underage - not valid
  private Driver d5; // not official driver

  @BeforeEach
  void setUp() {
    Name name = new Name("Izzy", "Li");
    License info = new License("123ABC", name, "12345",
        LocalDate.of(1999, 12, 12), "CAN", "ABC",
        LocalDate.of(2005, 12, 2), LocalDate.of(2025, 12, 12));

    ArrayList<Incident> incidents = new ArrayList<>(List.of(new Crash(name, LocalDate.of(2008, 12, 12),
            Crash.CrashType.FENDER_BENDER)));
    ArrayList<Incident> incidents1 = new ArrayList<>(List.of(new MovingViolation(name, LocalDate.of(2008, 12, 12),
        MovingViolationType.DISTRACTED_DRIVING)));

    ArrayList<Incident> incidents2 = new ArrayList<>(List.of(new MovingViolation(name, LocalDate.of(2008, 12, 12),
        MovingViolationType.SPEEDING)));

    VehicleInsurance insurance = new Vehicle.VehicleInsurance(name,
        new ArrayList<>(List.of(new Name("bull", "frog"))), LocalDate.of(2025, 12, 12));

    Vehicle v1 = new Vehicle("12", "12", 2022, name, insurance, incidents);


    d1 = new Driver(name, LocalDate.of(1999, 12, 12), info, incidents1);
    d1.addVehicle(v1);
    d2 = new Driver(name, LocalDate.of(1999, 12, 12), info, incidents2);
    d2.addVehicle(v1);
    d3 = d1;
    d4 = new Driver (name, LocalDate.of(2024, 12, 12), info, incidents1);
    d4.addVehicle(v1);
    d5 = new Driver(new Name ("Hello", "Hello"), LocalDate.of(1999, 12, 12), info, incidents2);
    d5.addVehicle(v1);

  }

  @Test
  void filterDrivers() {
    ArrayList<Driver> validDrivers = RegistrationValidator.retrieveValidDrivers(new ArrayList<>(List.of(d1, d2, d3, d4, d5)));
    assertEquals(1, validDrivers.size());
  }
}