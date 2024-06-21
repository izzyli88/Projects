package option2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import option2.NonMovingViolation.NonMovingViolationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NonMovingViolationTest {
  Incident <NonMovingViolationType> test;
  Name expName;
  LocalDate expDate;
  NonMovingViolationType expType;


  @BeforeEach
  void setUp() {
    expName = new Name("Rin", "Li");
    expDate = LocalDate.of(1999, 12, 12);
    expType = NonMovingViolationType.VEHICLE_PROBLEMS;
    test = new NonMovingViolation(expName, expDate, expType);
  }

  @Test
  void isValidViolationType() {
    assertTrue(test.isValidMovingViolationType());
  }

  @Test
  void getDriver() {
    assertEquals(expName, test.getDriver());
  }

  @Test
  void getDate() {
    assertEquals(expDate, test.getDate());
  }

  @Test
  void incidentLessThan6Months() {
    assertFalse(test.incidentLessThan6Months());
  }

}