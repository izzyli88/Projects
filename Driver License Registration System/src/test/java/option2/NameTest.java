package option2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NameTest {
  private Name testName;
  private String expFirstName;
  private String expLastName;

  @BeforeEach
  void setUp() {
    testName = new Name("Rin", "Li");
    expFirstName = "Rin";
    expLastName = "Li";
  }

  @Test
  void testGetFirstName() {
    assertEquals(expFirstName, testName.firstName());
  }


  @Test
  void testGetLastName() {
    assertEquals(expLastName, testName.lastName());
  }

  @Test
  void testEquals_SameObjects(){
    assertTrue(testName.equals(testName));
  }

  @Test
  void testEquals_NullObject(){
    assertFalse(testName.equals(null));
  }

  @Test
  void testEquals_DifferentDateType(){
    assertFalse(testName.equals("null"));
  }

  @Test
  void testEquals_DifferentFirstName(){
    Name otherName = new Name("New", expLastName);
    assertFalse(testName.equals(otherName));
  }


  @Test
  void testEquals_DifferentLastName(){
    Name otherName = new Name(expFirstName, "New");
    assertFalse(testName.equals(otherName));
  }

  @Test
  void testEquals_DifferentObjectsSameFields() {
    Name otherName = new Name(expFirstName, expLastName);
    assertTrue(testName.equals(otherName));
  }

  @Test
  void testHashCode() {
    int expHashCode = Objects.hash(testName.firstName(), testName.lastName());
    assertEquals(expHashCode, testName.hashCode());
  }

  @Test
  void testToString() {
    String expString = "Name{" +
        "firstName='" + expFirstName + '\'' +
        ", lastName='" + expLastName + '\'' +
        '}';
    assertEquals(expString, testName.toString());
  }
}