package problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandLineParserTest {
  private CommandLineParser testParse;
  private String expEmailPath;
  private String expLetterPath;
  private String expOutputPath;
  private String expCsvPath;
  private Boolean expGenEmail;
  private Boolean expGenLetter;
  private String[] expArgs;

  @BeforeEach
  void setUp() {
    expArgs = new String[]{"--letter-template", "letter/path", "--email", "--csv-file",
        "/csv/path",
        "--email-template", "/email/path", "--letter", "--output-dir", "output/path/"};
    testParse = new CommandLineParser(expArgs);
    expEmailPath = "/email/path";
    expLetterPath = "letter/path";
    expOutputPath = "output/path/";
    expCsvPath = "/csv/path";
    expGenEmail = Boolean.TRUE;
    expGenLetter = Boolean.TRUE;
  }

  @Test
  void processArgs_InvalidCommand() {
    String[] args = new String[] {"hello"};
    assertThrows(IllegalArgumentException.class, () -> new CommandLineParser(args));
  }

  @Test
  void processArgs_TooFewArgs() {
    String[] invalidArg = new String[] {};
    assertThrows(IllegalArgumentException.class, () -> new CommandLineParser(invalidArg));
  }

  @Test
  void processArgs_MissingEmailPath() {
    String[] otherArgs = new String[] {"--email", "--csv-file",
        "/csv/path", "/email/path","--output-dir", "output/path/", "--email-template"};
    assertThrows(IllegalArgumentException.class, () -> new CommandLineParser(otherArgs));
  }

  @Test
  void processArgs_InvalidEmailPath() {
    String[] otherArgs = new String[] {"--letter-template", "letter/path", "--email", "--csv-file",
        "/csv/path",
        "--email-template", "/////", "--letter", "--output-dir", "output/path/"};
    assertThrows(IllegalArgumentException.class, () -> new CommandLineParser(otherArgs));
  }

  @Test
  void processArgs_NoLetterTemplate() {
    String[] args = new String[] {"--email", "--csv-file",
        "/csv/path",
        "--email-template", "/email/path", "--letter", "--output-dir", "output/path/"};
    assertThrows(IllegalArgumentException.class, () -> new CommandLineParser(args));
  }

  @Test
  void processArgs_LetterTemplate_NoLetter() {
    String[] args = new String[] {"--letter-template", "letter/path", "--email", "--csv-file",
        "/csv/path",
        "--email-template", "/email/path", "--output-dir", "output/path/"};
    assertThrows(IllegalArgumentException.class, () -> new CommandLineParser(args));
  }

  @Test
  void processArgs_NoEmailTemplate() {
    String[] args = new String[] {"--letter-template", "letter/path", "--email", "--csv-file",
        "/csv/path", "--letter", "--output-dir", "output/path/"};
    assertThrows(IllegalArgumentException.class, () -> new CommandLineParser(args));
  }
  @Test
  void processArgs_EmailTemplate_NoEmail() {
    String[] args = new String[] {"--letter-template", "letter/path", "--csv-file",
        "/csv/path",
        "--email-template", "/email/path", "--letter", "--output-dir", "output/path/"};
    assertThrows(IllegalArgumentException.class, () -> new CommandLineParser(args));
  }
  @Test
  void processArgs_NoEmailGeneration() {
    String[] args = new String[] {"--letter-template", "letter/path", "--csv-file",
        "/csv/path", "--letter", "--output-dir", "output/path/"};
    CommandLineParser parser = new CommandLineParser(args);
    assertNull(parser.getEmailPath());
  }
  @Test
  void processArgs_NoLetterGeneration() {
    String[] args = new String[] {"--email", "--csv-file",
        "/csv/path",
        "--email-template", "/email/path", "--output-dir", "output/path/"};
    CommandLineParser parser = new CommandLineParser(args);
    assertNull(parser.getLetterPath());
  }

  @Test
  void processArgs_NoCSV() {
    String[] args = new String[]{"--letter-template", "letter/path", "--email",
        "--email-template", "/email/path", "--letter", "--output-dir", "output/path/"};
    assertThrows(IllegalArgumentException.class, () -> new CommandLineParser(args));
  }

  @Test
  void processArgs_NoOutput() {
    String[] args = new String[] {"--letter-template", "letter/path", "--email", "--csv-file",
        "/csv/path",
        "--email-template", "/email/path", "--letter"};
    assertThrows(IllegalArgumentException.class, () -> new CommandLineParser(args));
  }

  @Test
  void getEmailPath() {
    assertEquals(expEmailPath, testParse.getEmailPath());
  }

  @Test
  void getLetterPath() {
    assertEquals(expLetterPath, testParse.getLetterPath());
  }

  @Test
  void getOutputPath() {
    assertEquals(expOutputPath, testParse.getOutputPath());
  }

  @Test
  void getCsvPath() {
    assertEquals(expCsvPath, testParse.getCsvPath());
  }

  @Test
  void isGenEmail() {
    assertEquals(expGenEmail, testParse.isGenEmail());
  }

  @Test
  void isGenLetter() {
    assertEquals(expGenLetter, testParse.isGenLetter());
  }

  @Test
  void testEquals_SameObject() {
    assertTrue(testParse.equals(testParse));
  }

  @Test
  void testEquals_Null() {
    assertFalse(testParse.equals(null));
  }

  @Test
  void testEquals_DifferentClass() {
    assertFalse(testParse.equals("String"));
  }

  @Test
  void testEquals_DiffEmailPath() {
    String[] otherArgs = new String[] {"--letter-template", "letter/path", "--email", "--csv-file",
        "/csv/path",
        "--email-template", "HELLO123", "--letter", "--output-dir", "output/path/"};
    CommandLineParser otherParse = new CommandLineParser(otherArgs);
    assertFalse(otherParse.equals(testParse));
  }
  @Test
  void testEquals_DiffLetterPath() {
    String[] otherArgs = new String[] {"--letter-template", "HELLO123", "--email", "--csv-file",
        "/csv/path",
        "--email-template", "/email/path", "--letter", "--output-dir", "output/path/"};
    CommandLineParser otherParse = new CommandLineParser(otherArgs);
    assertFalse(otherParse.equals(testParse));
  }

  @Test
  void testEquals_DiffOutputPath() {
    String[] otherArgs = new String[] {"--letter-template", "letter/path", "--email", "--csv-file",
        "/csv/path",
        "--email-template", "/email/path", "--letter", "--output-dir", "HELLO123"};
    CommandLineParser otherParse = new CommandLineParser(otherArgs);
    assertFalse(otherParse.equals(testParse));
  }

  @Test
  void testEquals_DiffCSVPath() {
    String[] otherArgs = new String[] {"--letter-template", "letter/path", "--email", "--csv-file",
        "HELLO123",
        "--email-template", "/email/path", "--letter", "--output-dir", "output/path/"};

    CommandLineParser otherParse = new CommandLineParser(otherArgs);
    assertFalse(otherParse.equals(testParse));
  }


  @Test
  void testEquals_DiffObjects_SameFields() {
    CommandLineParser otherParse = new CommandLineParser(expArgs);
    assertTrue(otherParse.equals(testParse));
  }

  @Test
  void testHashCode() {
    int expHash = Objects.hash(expEmailPath, expLetterPath, expOutputPath, expCsvPath,
        expGenEmail, expGenLetter);
    assertEquals(expHash, testParse.hashCode());
  }

  @Test
  void testToString() {
    String expString ="CommandLineParser{" +
        "emailPath='" + expEmailPath + '\'' +
        ", letterPath='" + expLetterPath + '\'' +
        ", outputPath='" + expOutputPath+ '\'' +
        ", csvPath='" + expCsvPath + '\'' +
        ", genEmail=" + expGenEmail +
        ", genLetter=" + expGenLetter +
        '}';

    assertEquals(expString, testParse.toString());
  }
}