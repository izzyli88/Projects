package option2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Objects;
import option2.MovingViolation.MovingViolationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovingViolationTest {
  private Incident<MovingViolationType> test;
  private Name expName;
  private LocalDate expDate;
  private MovingViolationType expType;

  @BeforeEach  void setUp() {
    expName = new Name("Rin", "Li");
    expDate = LocalDate.of(1999, 12, 12);
    expType = MovingViolationType.DISTRACTED_DRIVING;
    test = new MovingViolation(expName, expDate, expType);
  }

  @Test
  void isIncidentLessThan6Months(){
    Incident<MovingViolationType> other = new MovingViolation(expName, LocalDate.now().minusMonths(6), MovingViolationType.RECKLESS_DRIVING);
    assertFalse(other.incidentLessThan6Months());
  }

  @Test
  void isValidViolationType() {
    assertTrue(test.isValidMovingViolationType());
  }
  @Test
  void isInvalidViolation_Reckless(){
    Incident<MovingViolationType> other = new MovingViolation(expName, expDate, MovingViolationType.RECKLESS_DRIVING);
    assertFalse(other.isValidMovingViolationType());
  }

  @Test
  void incidentLessThan6Months() {
    assertFalse(test.incidentLessThan6Months());
  }

  @Test
  void getType() {
    assertEquals(expType, test.getIncidentType());
  }

  @Test
  void testHash(){
    assertEquals(Objects.hash(expName, expDate, expType), test.hashCode());
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
  void testEquals_DiffDriver(){
    Incident<MovingViolationType> other = new MovingViolation(new Name("i", "i"), expDate, expType);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffDate(){
    Incident<MovingViolationType> other = new MovingViolation(expName, LocalDate.now(), expType);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffType(){
    Incident<MovingViolationType> other = new MovingViolation(expName, expDate, MovingViolationType.SPEEDING);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_SameFields(){
    Incident <MovingViolationType> other = new MovingViolation(expName, expDate, expType);
    assertTrue(test.equals(other));
  }
}