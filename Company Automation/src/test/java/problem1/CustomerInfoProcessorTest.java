package problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerInfoProcessorTest {
  CustomerInfoProcessor testProcessor;
  String templateFilePath;

  @BeforeEach
  void setUp() {
    testProcessor = new CustomerInfoProcessor();
    templateFilePath = "/Users/yijiazhan/Desktop/CS5004_TEAM/HW8/src/main/java"
        + "/problem1/insurance_test.csv";
  }

  @Test
  void readCSVFile() {
    List<List<String>> csvData = testProcessor.readCSVFile(templateFilePath);
    assertEquals(3, csvData.size());
    assertEquals("James", csvData.get(1).get(0));
    assertEquals("Butt", csvData.get(1).get(1));
    assertEquals("Brighton", csvData.get(2).get(4));
  }

  @Test
  void readCSVFileWithNullPath() {
    List<List<String>> csvData = CustomerInfoProcessor.readCSVFile(null);
    assertNull(csvData);
  }

  @Test
  void readCSVFileWithInvalidPath() {
    List<List<String>> csvData = CustomerInfoProcessor.readCSVFile("invalid_path.csv");
    assertNull(csvData);
  }
  @Test
  void readCSVFileWithIOException() {
    String nonExistentFilePath = "non_existent_file.csv";
    List<List<String>> csvData = CustomerInfoProcessor.readCSVFile(nonExistentFilePath);
    assertNull(csvData);
  }

  @Test
  void readCSVFileWithRestrictedAccess() {
    File file = new File("/Users/yijiazhan/Desktop/untitled folder/insurance_test.csv");
    try {
      file.createNewFile();
      file.setReadable(false);
      List<List<String>> csvData = CustomerInfoProcessor.readCSVFile(file.getPath());
      assertNull(csvData);
    } catch (IOException e) {
      fail("Failed to create test file: " + e.getMessage());
    } finally {
      file.delete();
    }
  }

  @Test
  void testRegexWithQuotedCell() {
    String line = "\"Shen\",Zhou,\"Company, Inc.\"";
    Pattern pattern = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?:,|$)");
    Matcher matcher = pattern.matcher(line);
    assertTrue(matcher.find());
    assertEquals("Shen", matcher.group(1) != null ? matcher.group(1) : matcher.group(2));
  }

  @Test
  void testRegexWithoutQuotedCell() {
    String line = "Shen,Zhou,31,Male";
    List<String> row = CustomerInfoProcessor.readCSVLine(line);

    assertEquals(4, row.size());
    assertEquals("Shen", row.get(0));
    assertEquals("Zhou", row.get(1));
    assertEquals("31", row.get(2));
    assertEquals("Male", row.get(3));
  }

  @Test
  void testEmptyLines() {
    List<List<String>> csvData = CustomerInfoProcessor.readCSVFile(templateFilePath);
    assertNotNull(csvData);
    assertFalse(csvData.contains(List.of()));
  }
  @Test
  void testReadCSVLine_EmptyLine() {
    String line = "";
    List<String> row = CustomerInfoProcessor.readCSVLine(line);
    assertTrue(row.isEmpty());
  }

  @Test
  void testReadCSVLine_NonEmptyLine() {
    String line = "Shen,Zhou,31,Male";
    List<String> row = CustomerInfoProcessor.readCSVLine(line);

    assertFalse(row.isEmpty());
    assertEquals(4, row.size());
    assertEquals("Shen", row.get(0));
    assertEquals("Zhou", row.get(1));
    assertEquals("31", row.get(2));
    assertEquals("Male", row.get(3));
  }
}


