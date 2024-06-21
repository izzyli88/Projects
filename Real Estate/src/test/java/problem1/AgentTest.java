package problem1;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import problem1.Agent;
import problem1.Commercial;
import problem1.Listing;
import problem1.ListingNotFoundException;
import problem1.Property;
import problem1.Rental;
import problem1.Residential;

class AgentTest {
  private Agent<Property, Rental> testAgent;
  private String expName;
  private List<Listing<Property, Rental>> expList;
  private Double expRate;
  private Double expEarnings;
  Listing<Property, Rental> commRental;
  Listing<Property, Rental> resRental;


  @BeforeEach
  void setUp() {
    expList = new ArrayList<>();
    expName = "Rin";
    expRate = 0.50;
    expEarnings = 0.0;
    testAgent = new Agent<>("Rin", 0.50);   // constructor 1

    Residential newRes = new Residential("abc", 1, 2, 10.0);
    Commercial newCommercial = new Commercial("abc", 1, 1, Boolean.TRUE);
    Rental newRental = new Rental(2.0, Boolean.FALSE, 12);

    commRental = new Listing<>(newCommercial, newRental);
    resRental = new Listing<>(newRes, newRental);
  }

  @Test
  void testConstructor_RateExceedLimit() {
    assertThrows(IllegalArgumentException.class, () -> new Agent<>(expName,
        expList, 1.50));
  }

  @Test
  void testConstructor2() {
    Agent<Property, Rental> newAgent = new Agent<>(expName, expList, expRate);
    assertEquals(expName, newAgent.getName());
  }

  @Test
  void testConstructor2_RateBelowLimit() {
    assertThrows(IllegalArgumentException.class, () -> new Agent<>(expName, expList,-0.50));
  }

  @Test
  void getName() {
    assertEquals(expName, testAgent.getName());
  }

  @Test
  void getListingCollection() {
    assertEquals(expList, testAgent.getListingCollection());
  }

  @Test
  void getCommissionRate() {
    assertEquals(expRate, testAgent.getCommissionRate());
  }

  @Test
  void getEarnings() {
    assertEquals(expEarnings, testAgent.getEarnings());

  }

  @Test
  void addListing() {
    testAgent.addListing(commRental);
    assertEquals(1, testAgent.getListingCollection().size());
  }

  @Test
  void completeListing() throws ListingNotFoundException {
    testAgent.addListing(resRental);
    testAgent.completeListing(resRental);

    assertEquals(12.0, testAgent.getEarnings(), 0.01);
  }

  @Test
  void completeListing_ListingNotFound() {
    testAgent.addListing(resRental);
    assertThrows(ListingNotFoundException.class, () -> testAgent.completeListing(commRental));
  }

  @Test
  void dropListing() throws ListingNotFoundException {
    testAgent.addListing(commRental);
    testAgent.dropListing(commRental);
    assertEquals(0, testAgent.getListingCollection().size());
  }

  @Test
  void dropListing_ListingNotFound() {
    testAgent.addListing(resRental);
    assertThrows(ListingNotFoundException.class, () -> testAgent.dropListing(commRental));
  }

  @Test
  void getTotalPortfolioValue() {
    testAgent.addListing(commRental);
    testAgent.addListing(resRental);
    assertEquals(24.0, testAgent.getTotalPortfolioValue(), 0.01);
  }

  // TESTING EQUALS, HASHCODE, TOSTRING
  @Test
  void testEquals_SameObject() {
    assertTrue(testAgent.equals(testAgent));
  }

  @Test
  void testEquals_Null() {
    assertFalse(testAgent.equals(null));
  }

  @Test
  void testEquals_DiffClass() {
    assertFalse(testAgent.equals("String"));
  }

  @Test
  void testEquals_DiffName() {
    Agent<Property, Rental> otherAgent = new Agent<>("123", expRate);
    assertFalse(testAgent.equals(otherAgent));
  }

  @Test
  void testEquals_DiffRate() {
    Agent<Property, Rental> otherAgent = new Agent<>(expName, 0.90);
    assertFalse(testAgent.equals(otherAgent));
  }

  @Test
  void testEquals_DiffList() {
    testAgent.addListing(resRental);
    Agent<Property, Rental> otherAgent = new Agent<>(expName, expRate);
    assertFalse(testAgent.equals(otherAgent));
  }
//
  @Test
  void testEquals_DiffObjects_SameFields() {
    Agent<Property, Rental> otherAgent = new Agent<>(expName,expList, expRate);
    assertTrue(testAgent.equals(otherAgent));
  }


  @Test
  void testHashCode() {
    testAgent.addListing(commRental);
    int expHash = Objects.hash(expName, expRate, expEarnings);
    expHash += commRental.hashCode();
    assertEquals(expHash, testAgent.hashCode());
  }

  @Test
  void testToString() {
    String expString = "problem1.Agent{" +
        "name='" + expName + '\'' +
        ", listingCollection=" + expList +
        ", commissionRate=" + expRate +
        ", earnings=" + expEarnings +
        '}';

    assertEquals(expString, testAgent.toString());
  }
}