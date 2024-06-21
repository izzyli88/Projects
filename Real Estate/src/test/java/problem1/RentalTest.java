package problem1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import problem1.Rental;

class RentalTest {

  Double expectedAskingPrice;
  Boolean expectedNegotiable;
  Integer expectedTerm;
  Rental testRentalContract;
  @BeforeEach
  void setUp() {
    expectedAskingPrice = 1000.0;
    expectedNegotiable = Boolean.FALSE;
    expectedTerm = 12;
    testRentalContract = new Rental(expectedAskingPrice, expectedNegotiable, expectedTerm);
  }

  @Test
  void getAskingPrice() {
    assertEquals(testRentalContract.getAskingPrice(), expectedAskingPrice);
  }

  @Test
  void expectedNegotiable() {
    assertEquals(testRentalContract.getNegotiable(), expectedNegotiable);
  }

  @Test
  void getTerm() {
    assertEquals(testRentalContract.getTerm(), expectedTerm);
  }

  @Test
  void getContractPrice() {
    assertEquals(testRentalContract.getContractPrice(), expectedAskingPrice*expectedTerm);
  }

  @Test
  void testEquals() {
    assertTrue(testRentalContract.equals(testRentalContract));
  }

  @Test
  void testEqualsDiffDataType() {
    assertFalse(testRentalContract.equals("String"));
  }

  @Test
  void testEqualsNull() {
    assertFalse(testRentalContract.equals(null));
  }

  @Test
  void testEqualsDiffAskingPrice() {
    Rental otherRentalContract = new Rental(1.0, expectedNegotiable, expectedTerm);
    assertFalse(testRentalContract.equals(otherRentalContract));
  }

  @Test
  void testEqualsDiffNegotiable() {
    Rental otherRentalContract = new Rental(expectedAskingPrice, Boolean.TRUE, expectedTerm);
    assertFalse(testRentalContract.equals(otherRentalContract));
  }

  @Test
  void testEqualsDiffTerm() {
    Rental otherRentalContract = new Rental(expectedAskingPrice, expectedNegotiable, 1);
    assertFalse(testRentalContract.equals(otherRentalContract));
  }

  @Test
  void testHashCode() {
    Rental otherRentalContract = new Rental(expectedAskingPrice, expectedNegotiable, expectedTerm);
    assertEquals(testRentalContract.hashCode(), otherRentalContract.hashCode());
  }

  @Test
  void testToString() {
    Rental otherRentalContract = new Rental(expectedAskingPrice, expectedNegotiable, expectedTerm);
    assertEquals(testRentalContract.toString(), otherRentalContract.toString());
  }

  @Test
  void isValidAskingPrice_negativePrice_shouldThrowException() {
    assertThrows(IllegalArgumentException.class, () ->
        new Rental(-1.0, expectedNegotiable, expectedTerm));
  }

  @Test
  void isValidTerm_negativeTerm_shouldThrowException() {
    assertThrows(IllegalArgumentException.class, () ->
        new Rental(expectedAskingPrice, expectedNegotiable, -1));
  }

  @Test
  void testGetAskingPrice() {
    assertEquals(testRentalContract.getAskingPrice(), expectedAskingPrice);
  }

  @Test
  void getNegotiable() {
    assertEquals(testRentalContract.getNegotiable(), expectedNegotiable);
  }
}