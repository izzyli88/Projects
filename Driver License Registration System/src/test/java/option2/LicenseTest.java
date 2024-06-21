package option2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LicenseTest {
  private License test;
  private String expNum;
  private Name expName;
  private LocalDate expBday;
  private String expAdd;
  private String expCountry;
  private String expState;
  private LocalDate expIssuance;
  private LocalDate expExpired;

  @BeforeEach
  void setUp() {
    expNum = "111";
    expName = new Name("Rin", "Li");
    expBday = LocalDate.of(1999, 12, 12);
    expAdd = "111";
    expCountry = "CAN";
    expState = "WA";
    expIssuance = LocalDate.of(2005, 12, 12);
    expExpired = LocalDate.of(2025, 12, 12);
    test = new License(expNum, expName, expAdd, expBday, expCountry, expState,expIssuance, expExpired);
  }

  @Test
  void invalidLicense_IssuanceBeforeBday(){
    assertThrows(IllegalArgumentException.class,
        () -> new License(expNum, expName, expAdd, expBday, expCountry,
            expState,LocalDate.of(1888, 12, 12), expExpired));
  }

  @Test
  void invalidLicense_ExpBeforeIssuance(){
    assertThrows(IllegalArgumentException.class,
        () -> new License(expNum, expName, expAdd, expBday, expCountry,
            expState,expIssuance, LocalDate.of(1999, 12, 12)));
  }

  @Test
  void getLicenseNum() {
    assertEquals(expNum, test.licenseNum());
  }

  @Test
  void getDriverName() {
    assertEquals(expName, test.driverName());
  }

  @Test
  void getAddress() {
    assertEquals(expAdd, test.address());
  }

  @Test
  void getBday() {
    assertEquals(expBday, test.bday());
  }

  @Test
  void getCountryIssued() {
    assertEquals(expCountry, test.countryIssued());
  }

  @Test
  void getStateIssued() {
    assertEquals(expState, test.stateIssued());
  }

  @Test
  void getIssuanceDate() {
    assertEquals(expIssuance, test.issuanceDate());
  }

  @Test
  void getExpDate() {
    assertEquals(expExpired, test.expDate());
  }

  @Test
  void licenseCheck_Fail_Canada() {
    assertTrue(test.checkLicenseMeetsRequirements());
  }

  @Test
  void licenseCheck_Fail_USA(){
    License other = new License(expNum, expName, expAdd, expBday, "USA", expState,expIssuance, expExpired);
    assertTrue(other.checkLicenseMeetsRequirements());
  }

  @Test
  void licenseCheck_Fail_IssuanceLessThan6Months(){
    License other = new License(expNum, expName, expAdd, expBday, "USA", expState, LocalDate.now(), expExpired);
    assertFalse(other.checkLicenseMeetsRequirements());
  }
  @Test
  void licenseCheck_Pass_Exactly6Months(){
    License other = new License(expNum, expName, expAdd, expBday, "USA", expState, LocalDate.now().minusMonths(6), expExpired);
    assertFalse(other.checkLicenseMeetsRequirements());
  }

  @Test
  void licenseCheck_Fail_Exp(){
    License other = new License(expNum, expName, expAdd, expBday, "VALID", expState,expIssuance, LocalDate.of(2023, 12, 12));
    assertFalse(other.checkLicenseMeetsRequirements());
  }

  @Test
  void testEquals_SameObject() {
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
  void testEquals_DiffNum(){
    License other = new License("OTHER", expName, expAdd, expBday, expCountry, expState,expIssuance, expExpired);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffName(){
    License other = new License(expNum, new Name("i", "i"), expAdd, expBday, expCountry, expState,expIssuance, expExpired);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffAddress(){
    License other = new License(expNum, expName, "OTHER", expBday, expCountry, expState,expIssuance, expExpired);
    assertFalse(other.equals(test));

  }

  @Test
  void testEquals_DiffBday(){
    License other = new License(expNum, expName, expAdd, LocalDate.of(1999, 12, 15), expCountry, expState,expIssuance, expExpired);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffCountry(){
    License other = new License(expNum, expName, expAdd, expBday, "expCountry", expState,expIssuance, expExpired);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffState(){
    License other = new License(expNum, expName, expAdd, expBday, expCountry, "expState",expIssuance, expExpired);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffIssuance(){
    License other = new License(expNum, expName, expAdd, expBday, expCountry, expState,LocalDate.now(), expExpired);
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_DiffExpirationDate(){
    License other = new License(expNum, expName, expAdd, expBday, expCountry, expState,expIssuance, LocalDate.of(2029, 12, 12));
    assertFalse(other.equals(test));
  }

  @Test
  void testEquals_SameFields(){
    License other = new License(expNum, expName, expAdd, expBday, expCountry, expState,expIssuance, expExpired);
    assertTrue(other.equals(test));

  }

  @Test
  void testHashCode() {
    int expHash = Objects.hash(expNum, expName, expAdd, expBday, expCountry, expState,
        expIssuance, expExpired);
    assertEquals(expHash, test.hashCode());
  }

  @Test
  void testToString() {
    String expString = "License{" +
        "licenseNum='" + expNum + '\'' +
        ", driverName=" + expName +
        ", address='" + expAdd + '\'' +
        ", bday=" + expBday +
        ", countryIssued='" + expCountry + '\'' +
        ", stateIssued='" + expState + '\'' +
        ", issuanceDate=" + expIssuance +
        ", expDate=" + expExpired +
        '}';

    assertEquals(expString, test.toString());
  }
}