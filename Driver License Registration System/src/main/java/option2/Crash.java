package option2;

import java.time.LocalDate;
import java.util.Objects;
import option2.Crash.CrashType;

/**
 * Crash class extending from Incident parent class. Contains
 * info for specific typeOfCrash of crash (enum)
 */
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
