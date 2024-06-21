package option2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * license class for use in Driver class
 */
public final class License {
  private static final String USA = "USA";
  private static final String CAN = "CAN";
  private static final Integer MONTH_LIMIT = 6;
  private static final LocalDate DATE_LIMIT = LocalDate.now().minusMonths(MONTH_LIMIT);
  private final String licenseNum;
  private final Name driverName;
  private final String address;
  private final LocalDate bday;
  private final String countryIssued;
  private final String stateIssued;
  private final LocalDate issuanceDate;
  private final LocalDate expDate;


  /**
   * Create an instance of license info. Bday must be before issuance date, and issuance date must
   * be before expiration date
   * @param licenseNum    - license num (String)
   * @param driverName    - driver name (Name)
   * @param address       - driver address (String)
   * @param bday          - driver bday (LocalDate)
   * @param countryIssued - country issued (String)
   * @param stateIssued   - state issued (String)
   * @param issuanceDate  - issuance date (LocalDate)
   * @param expDate       - expiration date (LocalDate)
   */
  public License(String licenseNum, Name driverName, String address, LocalDate bday,
      String countryIssued, String stateIssued, LocalDate issuanceDate, LocalDate expDate) {
    if (checkAfter(bday, issuanceDate)) {
      throw new IllegalArgumentException("Birthday must be before issuance date.");
    }
    if (checkAfter(issuanceDate, expDate)) {
      throw new IllegalArgumentException("Issuance date must be before expiration date.");
    }
    this.licenseNum = licenseNum;
    this.driverName = driverName;
    this.address = address;
    this.bday = bday;
    this.countryIssued = countryIssued;
    this.stateIssued = stateIssued;
    this.issuanceDate = issuanceDate;
    this.expDate = expDate;
  }

  /**
   * checks whether day1 is after day2 - for use in constructor
   * @param day1 - day 1
   * @param day2 - day 2
   * @return - whether day 1 is after day 2
   */
  private boolean checkAfter(LocalDate day1, LocalDate day2) {
    return day1.isAfter(day2);
  }

  /**
   * returns license num
   * @return - license num (String)
   */
  public String licenseNum() {
    return this.licenseNum;
  }

  /**
   * returns driver name
   * @return - driver name (String)
   */
  public Name driverName() {
    return this.driverName;
  }

  /**
   * returns address
   * @return - address (String)
   */
  public String address() {
    return this.address;
  }

  /**
   * returns bday
   * @return - bday (LocalDate)
   */
  public LocalDate bday() {
    return this.bday;
  }

  /**
   * returns country of issuance
   * @return - country of issuance (String)
   */
  public String countryIssued() {
    return this.countryIssued;
  }

  /**
   * returns state of issuance
   * @return - state of issuance (String)
   */
  public String stateIssued() {
    return this.stateIssued;
  }

  /**
   * returns issuance date
   * @return - issuance date (LocalDate)
   */
  public LocalDate issuanceDate() {
    return this.issuanceDate;
  }

  /**
   * returns expiration date
   * @return -expiration date (LocalDate)
   */
  public LocalDate expDate() {
    return this.expDate;
  }

  /**
   * checks whether country is either USA or CAN, whether the license was issued more than 6 months
   * ago, and whether license has expired
   * @return - whether license meets specs (boolean)
   */
  public boolean checkLicenseMeetsRequirements() {
    return this.countryUSACAN() && this.licenseIssued6MonthsAgo() && this.expCheck();
  }

  /**
   * checks whether country is either USA or CAN
   * @return - is country USA/CAN (boolean)
   */
  private boolean countryUSACAN() {
    return this.countryIssued().equalsIgnoreCase(CAN) || this.countryIssued().equalsIgnoreCase(USA);
  }

  /**
   * checks whether license was issued more than 6 months ago
   * @return - is license issuance date valid (boolean)
   */
  private boolean licenseIssued6MonthsAgo() {
    return this.issuanceDate.isBefore(DATE_LIMIT);
  }

  /**
   * checks whether license has not expired
   * @return - has license expired (boolean)
   */
  private boolean expCheck() {
    return this.expDate.isAfter(LocalDate.now());
  }

  /**
   * checks for equality
   * @param o the reference object with which to compare.
   * @return - whether equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    License that = (License) o;
    return Objects.equals(licenseNum(), that.licenseNum())
        && Objects.equals(driverName(), that.driverName())
        && Objects.equals(address(), that.address()) && Objects.equals(
        bday(), that.bday()) && Objects.equals(countryIssued(),
        that.countryIssued()) && Objects.equals(stateIssued(), that.stateIssued())
        && Objects.equals(issuanceDate(), that.issuanceDate())
        && Objects.equals(expDate(), that.expDate());
  }

  /**
   * return hash of object
   * @return - object hash (int)
   */
  @Override
  public int hashCode() {
    return Objects.hash(licenseNum(), driverName(), address(), bday(),
        countryIssued(),
        stateIssued(), issuanceDate(), expDate());
  }

  /**
   * return string representation of object
   * @return - string rep (String)
   */
  @Override
  public String toString() {
    return "License{" +
        "licenseNum='" + licenseNum + '\'' +
        ", driverName=" + driverName +
        ", address='" + address + '\'' +
        ", bday=" + bday +
        ", countryIssued='" + countryIssued + '\'' +
        ", stateIssued='" + stateIssued + '\'' +
        ", issuanceDate=" + issuanceDate +
        ", expDate=" + expDate +
        '}';
  }
}
