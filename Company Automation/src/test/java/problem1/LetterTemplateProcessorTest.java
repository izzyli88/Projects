package problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LetterTemplateProcessorTest {
  String templateFilePath;
  String outputDirPath;
  String csvFilePath;
  LetterTemplateProcessor testTempProcessor;
  List<List<String>> customerInfo;
  @BeforeEach
  void setUp() throws IOException {
    templateFilePath = "/Users/lipeipeisun/Desktop/CS 5004/_Git_Team_Repo/Team_repo_Isabelle_Lipeipei_Yijia/HW8/src/main/java/problem1/letter-template.txt";
    outputDirPath = "/Users/lipeipeisun/Desktop/CS 5004/_Git_Team_Repo/Test_Letter";
    csvFilePath = "/Users/lipeipeisun/Desktop/CS 5004/_Git_Team_Repo/Team_repo_Isabelle_Lipeipei_Yijia/HW8/src/main/java/problem1/insurance-company-members.csv";
    testTempProcessor = new LetterTemplateProcessor(templateFilePath, outputDirPath);
    customerInfo = CustomerInfoProcessor.readCSVFile(csvFilePath);
    System.out.println("Number of lists in customer inforamtion list: " + customerInfo.size());
  }

  @Test
  void replacePlaceholdersSaveOutput() {
    testTempProcessor.loadTemplate();
    testTempProcessor.replacePlaceholdersSaveOutput(customerInfo);
  }

  @Test
  void testEquals() {
    assertTrue(testTempProcessor.equals(testTempProcessor));
  }

  @Test
  void testHashCode() {
    LetterTemplateProcessor otherLetterTemplateProcessor = new LetterTemplateProcessor(templateFilePath, outputDirPath);
    assertEquals(testTempProcessor.hashCode(), otherLetterTemplateProcessor.hashCode());
  }

  @Test
  void testToString() {
    LetterTemplateProcessor otherLetterTemplateProcessor = new LetterTemplateProcessor(templateFilePath, outputDirPath);
    assertEquals(testTempProcessor.toString(), otherLetterTemplateProcessor.toString());
  }

}