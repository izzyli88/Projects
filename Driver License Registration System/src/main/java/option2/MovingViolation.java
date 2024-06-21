package option2;

import java.time.LocalDate;
import java.util.Objects;
import option2.MovingViolation.MovingViolationType;

/**
 * Class extending from Violation, represents a moving violation
 */
public class MovingViolation extends Incident<MovingViolationType> {

  /**
   * constructor creating an instance of MovingViolation
   * @param driver - driver name
   * @param date - date of incident
   * @param incidentType - type of moving violation
   */
  public MovingViolation(Name driver, LocalDate date, MovingViolationType incidentType) {
    super(driver, date, incidentType);
  }

  /**
   * checks if type of mv is valid
   * @return - whether mv is valid (distracted/traffic sign failure)
   */
  public boolean isValidMovingViolationType(){
    return this.incidentType.equals(MovingViolationType.DISTRACTED_DRIVING) ||
        this.incidentType.equals(MovingViolationType.TRAFFIC_SIGN_FAILURE);
  }

  /**
   * nested enum class for types of moving violation
   */
  public enum MovingViolationType {
    DISTRACTED_DRIVING,
    RECKLESS_DRIVING,
    SPEEDING,
    DUI,
    TRAFFIC_SIGN_FAILURE,
    MISSING_INSURANCE_AND_LICENSE
  }
}
