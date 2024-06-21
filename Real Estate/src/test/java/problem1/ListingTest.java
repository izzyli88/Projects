package problem1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import problem1.Listing;
import problem1.Residential;
import problem1.Sale;

class ListingTest {
  private Residential residential;
  private Sale sale;
  private Listing<Residential, Sale> expectedListing;

  @BeforeEach
  void setUp() {
    residential = new Residential("123 9th Ave", 1500,
        4, 1.5);
    sale= new Sale (20000.0, true);
    expectedListing = new Listing<>(residential, sale);
  }

  @Test
  void getProperty() {
    assertEquals(residential, expectedListing.getProperty());
  }

  @Test
  void getContract() {
    assertEquals(sale, expectedListing.getContract());
  }

  @Test
  void calcListingPrice() {
    assertEquals(20000.0, expectedListing.calcListingPrice(), 0.01);
  }

  @Test
  void testEquals_Same() {
    Residential residential1 = new Residential("123 9th Ave", 1500,
        4, 1.5);
    Sale sale1= new Sale (20000.0, true);
    Listing<Residential, Sale> expectedListing1 = new Listing<>(residential1, sale1);
    assertTrue(expectedListing.equals(expectedListing1));
  }

  @Test
  void testEquals_Null() {
    assertFalse(expectedListing.equals(null));
  }

  @Test
  void testEquals_DifferentClass() {
    Object differentObject = new Object();
    assertFalse(expectedListing.equals(differentObject));
  }

  @Test
  void testEquals_SameObject() {
    assertTrue(expectedListing.equals(expectedListing));
  }
  @Test
  void testEquals_Different() {
    Residential residential2 = new Residential("456 10th AVE", 2000,
        4, 1.5);
    Sale sale2 = new Sale(25000.0, false);
    Listing<Residential, Sale> expectedListing2 = new Listing<>(residential2, sale2);
    assertFalse(expectedListing.equals(expectedListing2));
  }
  @Test
  void testEquals_DifferentPropertiesSameContract() {
    Residential differentResidential = new Residential("123 9th Ave", 1600,
        3, 1.0);
    Sale sameSale = new Sale(20000.0, true);
    Listing<Residential, Sale> differentPropertiesListing = new Listing<>
        (differentResidential, sameSale);

    assertFalse(expectedListing.equals(differentPropertiesListing)); // Should return false
  }

  @Test
  void testEquals_SamePropertiesDifferentContract() {
    Residential sameResidential = new Residential("123 9th Ave", 1500,
        4, 1.5);
    Sale differentSale = new Sale(110000.0, false);
    Listing<Residential, Sale> differentContractListing = new Listing<>
        (sameResidential, differentSale);

    assertFalse(expectedListing.equals(differentContractListing));
  }
  @Test
  void testHashCode() {
    Residential residential1 = new Residential("123 9th Ave", 1500,
        4, 1.5);
    Sale sale1= new Sale (20000.0, true);
    Listing<Residential, Sale> expectedListing1 = new Listing<>(residential1, sale1);
    assertEquals(expectedListing.hashCode(), expectedListing1.hashCode());

  }

  @Test
  void testToString() {
    String expectedString = "problem1.Listing{" +
        "property=" + residential +
        ", contract=" + sale +
        '}';
    assertEquals(expectedString, expectedListing.toString());
  }
}