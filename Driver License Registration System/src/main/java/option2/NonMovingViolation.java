package option2;

import java.time.LocalDate;
import java.util.Objects;
import option2.NonMovingViolation.NonMovingViolationType;

/**
 * NonMoving violation class extending from violation
 */
public class NonMovingViolation extends Incident<NonMovingViolationType> {

  /**
   * constructor creating an instance of a non-moving violation object
   * @param driver - driver name
   * @param date - date of incident
   * @param incidentType - type of incident
   */
  public NonMovingViolation(Name driver, LocalDate date, NonMovingViolationType incidentType) {
    super(driver, date, incidentType);
  }

  /**
   * checks whether incident occurred less than 6 months ago,
   * default false b/c this is not used for nmv
   * @return - default false, boolean
   */
  @Override
  protected boolean incidentLessThan6Months() {
    return false;
  }

  /**
   * enum for types of non-moving violations
   */
  public enum NonMovingViolationType {
    PARKING,
    PAPERWORK,
    VEHICLE_PROBLEMS
  }
}
