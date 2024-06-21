package problem1;
import java.io.IOException;
import java.util.List;

/**
 * Main program class for processing a customer information file and
 * outputting custom emails/letters from templates as desired
 */
public class Main {
  /**
   * Main program method to create custom emails/letters
   * @param args - command line arguments given for processing (String[])
   */
  public static void main(String[] args){
    // create command line parser to process command line arguments
    CommandLineParser parser = new CommandLineParser(args);

    // retrieve customer information list
    List<List<String>> customerInfo = CustomerInfoProcessor.readCSVFile(parser.getCsvPath());

    // processing for emails
    processFileType(parser.isGenEmail(), new EmailTemplateProcessor(parser.getEmailPath(),
            parser.getOutputPath()), customerInfo);

    // processing for letters
    processFileType(parser.isGenLetter(), new LetterTemplateProcessor(parser.getLetterPath(),
            parser.getOutputPath()), customerInfo);
  }

  /**
   * Method to process email/letter templates and create new files if file type generation is required
   * @param isGenType - is type of file being generated (Boolean)
   * @param processor - template processor for specific type of letter/email
   * @param customerInfo - customer info path (String)
   */
  private static void processFileType(Boolean isGenType, TemplateProcessor processor,
      List<List<String>> customerInfo) {
    if (isGenType) {
      processor.replacePlaceholdersSaveOutput(customerInfo);    // process/output new files
    }
  }
}