package problem1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import problem1.Commercial;

class CommercialTest {
  String expectedAddress;
  Integer expectedSize;
  Integer expectedNumberOfOffices;
  Boolean expectedSuitableForRetail;
  Commercial testCommercialProperty;
  @BeforeEach
  void setUp() {
    expectedAddress = "360 Huntington Ave, Boston, MA 02115";
    expectedSize = 5000;
    expectedNumberOfOffices = 6;
    expectedSuitableForRetail = Boolean.FALSE;
    testCommercialProperty = new Commercial(expectedAddress, expectedSize, expectedNumberOfOffices,
        expectedSuitableForRetail);
  }

  @Test
  void isValidSize() {
    assertThrows(IllegalArgumentException.class, () ->
        new Commercial(expectedAddress, -1, expectedNumberOfOffices,
            expectedSuitableForRetail));
  }

  @Test
  void isValidNumberOfOffice() {
    assertThrows(IllegalArgumentException.class, () ->
        new Commercial(expectedAddress, expectedSize, -1,
            expectedSuitableForRetail));
  }

  @Test
  void getAddress() {
    assertEquals(testCommercialProperty.getAddress(), expectedAddress);
  }

  @Test
  void getSize() {
    assertEquals(testCommercialProperty.getSize(), expectedSize);
  }

  @Test
  void testEquals() {
    assertTrue(testCommercialProperty.equals(testCommercialProperty));
  }

  @Test
  void testEqualsDiffObjects() {
    Commercial otherCommercialProperty = new Commercial(expectedAddress, expectedSize,
        expectedNumberOfOffices, expectedSuitableForRetail);
    assertTrue(testCommercialProperty.equals(otherCommercialProperty));
  }

  @Test
  void testEqualsNull() {
    assertFalse(testCommercialProperty.equals(null));
  }

  @Test
  void testEqualsDiffDataStructure() {
    assertFalse(testCommercialProperty.equals("String"));
  }

  @Test
  void testEqualsDiffAddress() {
    Commercial otherCommercialProperty = new Commercial("expectedAddress", expectedSize,
        expectedNumberOfOffices, expectedSuitableForRetail);
    assertFalse(testCommercialProperty.equals(otherCommercialProperty));
  }

  @Test
  void testEqualsDiffSize() {
    Commercial otherCommercialProperty = new Commercial(expectedAddress, 100,
        expectedNumberOfOffices, expectedSuitableForRetail);
    assertFalse(testCommercialProperty.equals(otherCommercialProperty));
  }

  @Test
  void testEqualsDiffNumOfOffices() {
    Commercial otherCommercialProperty = new Commercial(expectedAddress, expectedSize,
        2, expectedSuitableForRetail);
    assertFalse(testCommercialProperty.equals(otherCommercialProperty));
  }

  @Test
  void testEqualsDiffRetail() {
    Commercial otherCommercialProperty = new Commercial(expectedAddress, expectedSize,
        expectedNumberOfOffices, Boolean.TRUE);
    assertFalse(testCommercialProperty.equals(otherCommercialProperty));
  }

  @Test
  void testHashCode() {
    Commercial otherCommercialProperty = new Commercial(expectedAddress, expectedSize, expectedNumberOfOffices,
        expectedSuitableForRetail);
    assertEquals(testCommercialProperty.hashCode(), otherCommercialProperty.hashCode());
  }

  @Test
  void testToString() {
    Commercial otherCommercialProperty = new Commercial(expectedAddress, expectedSize, expectedNumberOfOffices,
        expectedSuitableForRetail);
    assertEquals(testCommercialProperty.toString(), otherCommercialProperty.toString());
  }

  @Test
  void getNumberOfOffices() {
    assertEquals(testCommercialProperty.getNumberOfOffices(), expectedNumberOfOffices);
  }

  @Test
  void getSuitableForRetail() {
    assertEquals(testCommercialProperty.getSuitableForRetail(), expectedSuitableForRetail);
  }
}