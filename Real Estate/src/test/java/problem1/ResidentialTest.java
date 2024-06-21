package problem1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import problem1.Residential;

class ResidentialTest {
  String expectedAddress;
  Integer expectedSize;
  Integer expectedNumberOfBedrooms;
  Double expectedNumberOfBathrooms;
  Residential testResidentialProperty;
  @BeforeEach
  void setUp() {
    expectedAddress = "360 Huntington Ave, Boston, MA 02115";
    expectedSize = 5000;
    expectedNumberOfBedrooms = 2;
    expectedNumberOfBathrooms = 1.5;
    testResidentialProperty = new Residential(expectedAddress, expectedSize,
        expectedNumberOfBedrooms, expectedNumberOfBathrooms);
  }

  @Test
  void isValidSize() {
    assertThrows(IllegalArgumentException.class, () ->
        new Residential(expectedAddress, -10,
            expectedNumberOfBedrooms, expectedNumberOfBathrooms));
  }

  @Test
  void getAddress() {
    assertEquals(testResidentialProperty.getAddress(), expectedAddress);
  }

  @Test
  void getSize() {
    assertEquals(testResidentialProperty.getSize(), expectedSize);
  }

  @Test
  void testEquals() {
    assertTrue(testResidentialProperty.equals(testResidentialProperty));
  }

  @Test
  void testEqualsNull() {
    assertFalse(testResidentialProperty.equals(null));
  }

  @Test
  void testEqualsDiffDataStructure() {
    assertFalse(testResidentialProperty.equals("String"));
  }

  @Test
  void testEqualsDiffAddress() {
    Residential otherResidentialProperty = new Residential("expectedAddress", expectedSize,
        expectedNumberOfBedrooms, expectedNumberOfBathrooms);
    assertFalse(testResidentialProperty.equals(otherResidentialProperty));
  }

  @Test
  void testEqualsDiffSize() {
    Residential otherResidentialProperty = new Residential(expectedAddress, 1000,
        expectedNumberOfBedrooms, expectedNumberOfBathrooms);
    assertFalse(testResidentialProperty.equals(otherResidentialProperty));
  }

  @Test
  void testEqualsDiffBedrooms() {
    Residential otherResidentialProperty = new Residential(expectedAddress, expectedSize,
        5, expectedNumberOfBathrooms);
    assertFalse(testResidentialProperty.equals(otherResidentialProperty));
  }

  @Test
  void testEqualsDiffBathrooms() {
    Residential otherResidentialProperty = new Residential(expectedAddress, expectedSize,
        expectedNumberOfBedrooms, 3.25);
    assertFalse(testResidentialProperty.equals(otherResidentialProperty));
  }

  @Test
  void testEqualsDiffObjects() {
    Residential otherResidentialProperty = new Residential(expectedAddress, expectedSize,
        expectedNumberOfBedrooms, expectedNumberOfBathrooms);
    assertTrue(testResidentialProperty.equals(otherResidentialProperty));
  }

  @Test
  void testHashCode() {
    Residential otherResidentialProperty = new Residential(expectedAddress, expectedSize,
        expectedNumberOfBedrooms, expectedNumberOfBathrooms);
    assertEquals(testResidentialProperty.hashCode(), otherResidentialProperty.hashCode());
  }

  @Test
  void getNumberOfBedrooms() {
    assertEquals(testResidentialProperty.getNumberOfBedrooms(), expectedNumberOfBedrooms);
  }

  @Test
  void getNumberOfBathrooms() {
    assertEquals(testResidentialProperty.getNumberOfBathrooms(), expectedNumberOfBathrooms);
  }

  @Test
  void testToString() {
    Residential otherResidentialProperty = new Residential(expectedAddress, expectedSize,
        expectedNumberOfBedrooms, expectedNumberOfBathrooms);
    assertEquals(testResidentialProperty.toString(), otherResidentialProperty.toString());
  }

  @Test
  void isValidNumberOfBedrooms() {
    assertThrows(IllegalArgumentException.class, () ->
        new Residential(expectedAddress, expectedSize,
            -1, expectedNumberOfBathrooms));
  }

  @Test
  void isValidNumberOfBathrooms() {
    assertThrows(IllegalArgumentException.class, () ->
        new Residential(expectedAddress, expectedSize,
            expectedNumberOfBedrooms, -1.5));
  }


}