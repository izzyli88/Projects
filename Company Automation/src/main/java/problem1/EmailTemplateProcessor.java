package problem1;

/**
 * Concrete class for processing email templates with placeholders,
 * extends from abstract TemplateProcessor class.
 * This class provides method to load a template from a .txt file,
 * replace placeholders with actual values from a list of customer information,
 * and save the processed output to a specified directory.
 */
public class EmailTemplateProcessor extends TemplateProcessor {

  private static final String FILE_TYPE = "Email";
  /**
   * Constructor for EmailTemplateProcessor class.
   * @param templateFilePath path to the template file.
   * @param outputDirPath directory path where the processed files to be saved.
   */
  public EmailTemplateProcessor(String templateFilePath, String outputDirPath) {
    super(templateFilePath, outputDirPath);
    this.templateFileType = FILE_TYPE;
  }

  /**
   * Returns string representation of object
   * @return - string representation (String)
   */
  @Override
  public String toString() {
    return "EmailTemplateProcessor{" +
        "templateFilePath='" + this.templateFilePath + '\'' +
        ", templateContent='" + this.templateContent + '\'' +
        ", outputDirPath='" + this.outputDirPath + '\'' +
        ", templateFileType='" + this.templateFileType + '\'' +
        "} " + super.toString();
  }
}