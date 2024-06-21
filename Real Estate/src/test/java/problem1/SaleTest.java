package problem1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import problem1.Sale;

class SaleTest {
  Double expectedAskingPrice;
  Boolean expectedNegotiable;
  Sale testSaleContract;
  @BeforeEach
  void setUp() {
    expectedAskingPrice = 900000.0;
    expectedNegotiable = Boolean.FALSE;
    testSaleContract = new Sale(expectedAskingPrice, expectedNegotiable);
  }

  @Test
  void testToString() {
    Sale otherSaleContract = new Sale(expectedAskingPrice, expectedNegotiable);
    assertEquals(testSaleContract.toString(), otherSaleContract.toString());
  }

  @Test
  void getContractPrice() {
    assertEquals(testSaleContract.getContractPrice(), expectedAskingPrice);
  }
}