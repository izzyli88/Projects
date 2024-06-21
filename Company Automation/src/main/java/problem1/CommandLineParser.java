package problem1;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for command line parser. Determines if letter/email will be sent
 * and template/output/csv source file paths
 */
public class CommandLineParser {
  // fields
  private String emailPath;
  private String letterPath;
  private String outputPath;
  private String csvPath;
  private Boolean genEmail;
  private Boolean genLetter;

  // static string vars for commands
  private static final String EMAIL = "--email";
  private static final String EMAIL_TEMPLATE = "--email-template";
  private static final String LETTER = "--letter";
  private static final String LETTER_TEMPLATE = "--letter-template";
  private static final String OUTPUT = "--output-dir";
  private static final String CSV = "--csv-file";

  /**
   * Constructor creating an instance of the commandLineParser class. Attempts
   * to process command line input arguments. If unsuccessful, throws an exception
   * @param args - command line arguments given at start of program (String[])
   */
  public CommandLineParser(String[] args) {
    // initiate vars as false
    this.genEmail = Boolean.FALSE;
    this.genLetter = Boolean.FALSE;

    // try/catch to process args
    try {
      this.processArgs(args);

    } catch (IllegalArgumentException error) {    // catch error
      // output error message by throwing exc
      throw new IllegalArgumentException(error.getMessage() + "\n" + this.printUsageMessage());
    }
  }

  /**
   * Helper method for use in constructor to process command line arguments. Throws
   * exceptions if arguments do not fulfill requirements
   * @param args - command line arguments (String[])
   */
  private void processArgs(String[] args) {
    if (args.length == 0) {     // empty string
      throw new IllegalArgumentException("\nIssue: Too few arguments given.");
    }

    // process each string in args
    for (int i = 0; i < args.length; i++) {
      String input = args[i];

      // switch for command identification
      switch (input) {
        case EMAIL:
          this.genEmail = Boolean.TRUE;
          break;

        case LETTER:
          this.genLetter = Boolean.TRUE;
          break;

        case EMAIL_TEMPLATE:
          this.emailPath = this.populateField(i++, args);   // post increment i to skip path and go to next command
          break;

        case LETTER_TEMPLATE:
          this.letterPath = this.populateField(i++, args);
          break;

        case OUTPUT:
          this.outputPath = this.populateField(i++, args);
          break;

        case CSV:
          this.csvPath = this.populateField(i++, args);
          break;

        default:
          throw new IllegalArgumentException("\nIssue: Invalid command given.");
      }
    }

    // null checks
    if (this.genLetter && this.letterPath == null) {
      throw new IllegalArgumentException("\nIssue: --letter provided but no --letter-template was given.");
    }

    if (this.genEmail && this.emailPath == null) {
      throw new IllegalArgumentException("\nIssue: --email provided but no --email-template was given.");
    }

    if (this.outputPath == null) {
      throw new IllegalArgumentException("\nIssue: Output directory not given.");
    }

    if (this.csvPath == null) {
      throw new IllegalArgumentException("\nIssue: CSV file not given.");
    }

    if (!this.genEmail && this.emailPath != null) {
      throw new IllegalArgumentException("\nIssue: Email path given but --email not listed.");
    }

    if (!this.genLetter && this.letterPath != null) {
      throw new IllegalArgumentException("\nIssue: Letter path given but --letter not listed.");
    }
  }

  /**
   * Returns email template path
   * @return - email template path (String)
   */
  public String getEmailPath() {
    return this.emailPath;
  }

  /**
   * Returns letter template path
   * @return - letter template path (String)
   */
  public String getLetterPath() {
    return this.letterPath;
  }

  /**
   * Returns output folder path
   * @return - output folder path (String)
   */
  public String getOutputPath() {
    return this.outputPath;
  }

  /**
   * Returns CSV file path
   * @return - csv file path (String)
   */
  public String getCsvPath() {
    return this.csvPath;
  }

  /**
   * Returns whether emails will be generated
   * @return - whether emails will be generated (boolean)
   */
  public boolean isGenEmail() {
    return this.genEmail;
  }

  /**
   * Returns whether letters will be generated
   * @return - whether letters will be generated (boolean)
   */
  public boolean isGenLetter() {
    return this.genLetter;
  }

  /**
   * Helper method to populate a path field. Throws an exception if path given
   * has an invalid file format
   * @param argIndex - index currently at in args (int)
   * @param args - command line arguments (String[])
   * @return - string for path
   */
  private String populateField(int argIndex, String[] args) {
    if (argIndex + 1 < args.length) {   // make sure file path (arg + i) is in bounds
      if (isValidPath(args[argIndex + 1]))    // check valid file path
        return args[argIndex+1];
      else throw new IllegalArgumentException("\nIssue: Invalid file path format.");

    } else return null;   // this branch will not be fulfilled
  }

  /**
   * Helper method to determine if path is in valid format
   * @param path - given path (String)
   * @return - whether path has correct format (String)
   */
  private Boolean isValidPath(String path) {
    Pattern pathPattern = Pattern.compile("^/?(?:[^/]+/)*[^/]+/?$");
    Matcher match = pathPattern.matcher(path);
    return match.matches();
  }

  /**
   * Checks two command parser objects for equality
   * @param o - object being compared to instance of command parser
   * @return - whether the two objects are equal (boolean)
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommandLineParser that = (CommandLineParser) o;
    return Objects.equals(getEmailPath(), that.getEmailPath())
        && Objects.equals(getLetterPath(), that.getLetterPath())
        && Objects.equals(getOutputPath(), that.getOutputPath())
        && Objects.equals(getCsvPath(), that.getCsvPath());
  }

  /**
   * Returns hash of object
   * @return - hash of object (int)
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.getEmailPath(), this.getLetterPath(), this.getOutputPath(),
        this.getCsvPath(), this.isGenEmail(), this.isGenLetter());
  }

  /**
   * Returns string representation of object
   * @return - string representation (String)
   */
  @Override
  public String toString() {
    return "CommandLineParser{" +
        "emailPath='" + this.emailPath + '\'' +
        ", letterPath='" + this.letterPath + '\'' +
        ", outputPath='" + this.outputPath + '\'' +
        ", csvPath='" + this.csvPath + '\'' +
        ", genEmail=" + this.genEmail +
        ", genLetter=" + this.genLetter +
        '}';
  }

  /**
   * Helper method to print out usage message listing valid commands. For use in
   * processArgs method
   * @return - usage message (String)
   */
  private String printUsageMessage() {
    return """
        \nUsage:
        --email Generate email messages. If this option is provided, then
        \t--email-template must also be provided.
        --email-template <path/to/file> A filename for the email template.
        --letter Generate letters. If this option is provided, then
        \t--letter-template must also be provided.
        --letter-template <path/to/file> A filename for the letter template.
        --output-dir <path/to/folder> The folder to store all generated
        \tfiles. This option is required.
        --csv-file <path/to/folder> The CSV file to process. This option is
        \trequired.

        Examples:
        --email --email-template email-template.txt --output-dir emails
        \t--csv-file customer.csv

        --letter --letter-template letter-template.txt --output-dir letters
        \t--csv-file customer.csv""";
    }
}