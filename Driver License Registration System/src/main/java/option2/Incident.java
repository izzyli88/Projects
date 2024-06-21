package option2;

import java.time.LocalDate;
import java.util.Objects;

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

  /**
   * constructor creating an instance of the incident object
   * @param driver - driver name
   * @param date - date of incident
   * @param incidentType - type of incident (enum)
   */
  protected Incident(Name driver, LocalDate date, T incidentType) {
    this.driver = driver;
    this.date = date;
    this.incidentType = incidentType;
  }

  /**
   * returns driver
   * @return - driver (Name)
   */
  protected Name getDriver() {
    return this.driver;
  }

  /**
   * returns date of incident
   * @return - date (LocalDate)
   */
  protected LocalDate getDate() {
    return this.date;
  }

  /**
   * returns type of incident
   * @return - incident type
   */
  protected T getIncidentType() {
    return this.incidentType;
  }

  /**
   * checks whether the incident occurred within the last 6 months.
   * Only used for crashes and moving violations.
   * @return - whether incident occurred in the last 6 months
   */
  protected boolean incidentLessThan6Months() {
    return this.date.isAfter(DATE_LIMIT);
  }

  /**
   * checks if incident is a valid type of moving violation (distracted/traffic signs)
   * default true for crash/non-moving b/c they're not moving violations to begin w/.
   * method will be overridden in the MovingViolation class
   * @return - whether type is a valid moving violation (default true)
   */
  protected boolean isValidMovingViolationType() {
    return true;
  }

  /**
   * method to output incident information for incident
   * @return - output incident info string
   */
  protected String outputIncidentType() {
    String output = this.incidentType.toString().replace("_", " ");
    return formatType(output);
  }

  /**
   * helper method for outputIncident type, formats DISTRACTED DRIVING -> Distracted Driving
   * @param incidentType - string for incident type
   * @return - formatted string
   */
  protected String formatType(String incidentType) {
    String[] words = incidentType.split(" ");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      word = word.toLowerCase();
      sb.append(Character.toUpperCase(word.charAt(0)));
      sb.append(word.substring(1));
      sb.append(" ");
    }
    return sb.toString().trim();
  }

  /**
   * checks object for equality
   * @param o - object being compared to
   * @return - whether objects are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Incident<?> incident = (Incident<?>) o;
    return Objects.equals(getDriver(), incident.getDriver()) && Objects.equals(
        getDate(), incident.getDate()) && Objects.equals(getIncidentType(),
        incident.getIncidentType());
  }

  /**
   * returns hash of object
   * @return - object hash
   */
  @Override
  public int hashCode() {
    return Objects.hash(getDriver(), getDate(), getIncidentType());
  }

  /**
   * string representation of object
   * @return - string of object
   */
  @Override
  public String toString() {
    return "Incident{" +
        "driver=" + driver +
        ", date=" + date +
        ", incidentType=" + incidentType +
        '}';
  }
}
