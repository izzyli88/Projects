package problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailTemplateProcessorTest {
  String templateFilePath;
  String outputDirPath;
  String csvFilePath;
  TemplateProcessor testTempProcessor;
  List<List<String>> customerInfo;
  Pattern PLACEHOLDER_PATTERN;

  @BeforeEach
  void setUp() throws IOException {
    templateFilePath = "/Users/lipeipeisun/Desktop/CS 5004/_Git_Team_Repo/Team_repo_Isabelle_Lipeipei_Yijia/HW8/src/main/java/problem1/email-template.txt";
    outputDirPath = "/Users/lipeipeisun/Desktop/CS 5004/_Git_Team_Repo/Test_Email";
    csvFilePath = "/Users/lipeipeisun/Desktop/CS 5004/_Git_Team_Repo/Team_repo_Isabelle_Lipeipei_Yijia/HW8/src/main/java/problem1/insurance-company-members.csv";
    testTempProcessor = new EmailTemplateProcessor(templateFilePath, outputDirPath);
    customerInfo = CustomerInfoProcessor.readCSVFile(csvFilePath);
    PLACEHOLDER_PATTERN = Pattern.compile("\\[\\[(.*?)\\]\\]");
  }

  @Test
  void testLoadTemplate() {
    testTempProcessor.loadTemplate();
    EmailTemplateProcessor otherTempProcessor = new EmailTemplateProcessor(templateFilePath, outputDirPath);
    otherTempProcessor.loadTemplate();
    assertEquals(testTempProcessor.templateContent, otherTempProcessor.templateContent);
  }

  @Test
  void testReplacePlaceholdersSaveOutput() {
    testTempProcessor.loadTemplate();
    testTempProcessor.replacePlaceholdersSaveOutput(customerInfo);
  }

  @Test
  void testReplacePlaceholdersSaveOutputFileExists() {
    EmailTemplateProcessor otherTempProcessor = new EmailTemplateProcessor(
        templateFilePath, "/Users/lipeipeisun/Desktop/CS 5004/_Git_Team_Repo/Test_with files");
    testTempProcessor.replacePlaceholdersSaveOutput(customerInfo);
  }

  @Test
  void testConstructFileName() {
    // Map header to index
    Map<String, Integer> headerFieldIndex = new HashMap<>();
    List<String> headers = customerInfo.get(0);
    for (int i = 0; i < headers.size(); i++) {
      headerFieldIndex.put(headers.get(i), i);
    }
    String testFileName = testTempProcessor.constructFileName(1,customerInfo.get(1),headerFieldIndex);
    String expectedFileName = "1_James_Butt_Email.txt";
    assertEquals(expectedFileName, testFileName);
  }

  @Test
  void constructFileNameIndexOutOfBoundsExceptionNull() {
    Map<String, Integer> otherHeaderFieldIndex = new HashMap<>();
    assertThrows(IndexOutOfBoundsException.class, () ->
        testTempProcessor.constructFileName(1,customerInfo.get(1),otherHeaderFieldIndex));
  }

  @Test
  void testConstructFileNameIndexOutOfBoundsExceptionNullFn() {
    Map<String, Integer> otherHeaderFieldIndex = new HashMap<>();
    List<String> headers = customerInfo.get(0);
    for (int i = 0; i < headers.size(); i++) {
      otherHeaderFieldIndex.put(headers.get(i), i);
    }
    otherHeaderFieldIndex.put("first_name", null);
    assertThrows(IndexOutOfBoundsException.class, () ->
        testTempProcessor.constructFileName(1,customerInfo.get(1),otherHeaderFieldIndex));
  }

  @Test
  void testConstructFileNameIndexOutOfBoundsExceptionNullLn() {
    Map<String, Integer> otherHeaderFieldIndex = new HashMap<>();
    List<String> headers = customerInfo.get(0);
    for (int i = 0; i < headers.size(); i++) {
      otherHeaderFieldIndex.put(headers.get(i), i);
    }
    otherHeaderFieldIndex.put("last_name", null);
    assertThrows(IndexOutOfBoundsException.class, () ->
        testTempProcessor.constructFileName(1,customerInfo.get(1),otherHeaderFieldIndex));
  }

  @Test
  void testConstructFileNameIndexOutOfBoundsExceptionLargeFn() {
    Map<String, Integer> otherHeaderFieldIndex = new HashMap<>();
    List<String> headers = customerInfo.get(0);
    for (int i = 0; i < headers.size(); i++) {
      otherHeaderFieldIndex.put(headers.get(i), i);
    }
    otherHeaderFieldIndex.put("first_name", 50);
    assertThrows(IndexOutOfBoundsException.class, () ->
        testTempProcessor.constructFileName(1,customerInfo.get(1),otherHeaderFieldIndex));
  }

  @Test
  void testConstructFileNameIndexOutOfBoundsExceptionLargeLn() {
    Map<String, Integer> otherHeaderFieldIndex = new HashMap<>();
    List<String> headers = customerInfo.get(0);
    for (int i = 0; i < headers.size(); i++) {
      otherHeaderFieldIndex.put(headers.get(i), i);
    }
    otherHeaderFieldIndex.put("last_name", 50);
    assertThrows(IndexOutOfBoundsException.class, () ->
        testTempProcessor.constructFileName(1,customerInfo.get(1),otherHeaderFieldIndex));
  }

  @Test
  void testEquals() {
    assertTrue(testTempProcessor.equals(testTempProcessor));
  }

  @Test
  void testEqualsDiffDataType() {
    assertFalse(testTempProcessor.equals("String"));
  }

  @Test
  void testEqualsNull() {
    assertFalse(testTempProcessor.equals(null));
  }

  @Test
  void testEqualsDiffObjectSameField() {
    EmailTemplateProcessor otherTempProcessor = new EmailTemplateProcessor(
        templateFilePath, outputDirPath);
    assertTrue(testTempProcessor.equals(otherTempProcessor));
  }

  @Test
  void testEqualsDiffFilePath() {
    EmailTemplateProcessor otherTempProcessor = new EmailTemplateProcessor(
        "templateFilePath", outputDirPath);
    assertFalse(testTempProcessor.equals(otherTempProcessor));
  }

  @Test
  void testEqualsDiffOutput() {
    EmailTemplateProcessor otherTempProcessor = new EmailTemplateProcessor(
        templateFilePath, "outputDirPath");
    assertFalse(testTempProcessor.equals(otherTempProcessor));
  }

  @Test
  void testEqualsDiffContent() {
    EmailTemplateProcessor otherTempProcessor = new EmailTemplateProcessor(
        templateFilePath, outputDirPath);
    otherTempProcessor.templateContent = "";
    assertFalse(testTempProcessor.equals(otherTempProcessor));
  }

  @Test
  void testEqualsDiffFileType() {
    EmailTemplateProcessor otherTempProcessor = new EmailTemplateProcessor(
        templateFilePath, outputDirPath);
    otherTempProcessor.templateFileType = "Letter";
    assertFalse(testTempProcessor.equals(otherTempProcessor));
  }

  @Test
  void testHashCode() {
    EmailTemplateProcessor otherTempProcessor = new EmailTemplateProcessor(
        templateFilePath, outputDirPath);
    assertEquals(testTempProcessor.hashCode(), otherTempProcessor.hashCode());
  }

  @Test
  void testToString() {
    EmailTemplateProcessor otherTempProcessor = new EmailTemplateProcessor(
        templateFilePath, outputDirPath);
    assertEquals(testTempProcessor.toString(),otherTempProcessor.toString());
  }

  @Test
  void getTemplateFilePath() {
    assertEquals(testTempProcessor.getTemplateFilePath(), templateFilePath);
  }

  @Test
  void getTemplateContent() {
    EmailTemplateProcessor otherTempProcessor = new EmailTemplateProcessor(
        templateFilePath, outputDirPath);
    assertEquals(testTempProcessor.getTemplateContent(), otherTempProcessor.getTemplateContent());
  }

  @Test
  void getOutputDirPath() {
    assertEquals(testTempProcessor.getOutputDirPath(), outputDirPath);
  }

  @Test
  void getTemplateFileType() {
    assertEquals(testTempProcessor.getTemplateFileType(), "Email");
  }

  @Test
  void createFile() {
    testTempProcessor.createFile("/Users/lipeipeisun/Desktop/CS 5004/_Git_Team_Repo/Test_ReadOnly", "TestCreateFile.txt");
  }


  @Test
  void writeToFile() {
    testTempProcessor.writeToFile("/Users/lipeipeisun/Desktop/CS 5004/_Git_Team_Repo/Test_ReadOnly", "email-template.txt", "test");
  }

}