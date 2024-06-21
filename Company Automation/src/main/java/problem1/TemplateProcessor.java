package problem1;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.*;
import java.io.*;

/**
 * Abstract class for processing templates with placeholders.
 * This class provides method to load a template from a .txt file,
 * replace placeholders with actual values from a list of customer information,
 * and save the processed output to a specified directory.
 */
public abstract class TemplateProcessor {
  private static final String TEMPLATE_FILE_TYPE = ""; // set to empty for the abstract class.
  protected String templateFilePath;
  protected String templateContent;
  protected String outputDirPath;
  protected String templateFileType;

  // Regular expression to identify placeholder pattern [[text]].
  protected static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\[\\[(.*?)\\]\\]");

  /**
   * Constructor for abstract TemplateProcessor class.
   * @param templateFilePath path to the template file.
   * @param outputDirPath directory path where the processed files to be saved.
   */
  public TemplateProcessor(String templateFilePath, String outputDirPath) {
    this.templateFilePath = templateFilePath;
    this.outputDirPath = outputDirPath;
    this.templateFileType = TEMPLATE_FILE_TYPE;
    loadTemplate();
  }

  /**
   * Loads the template content from the file at templateFilePath into templateContent
   * expressed as string.
   */
  protected void loadTemplate(){
    StringBuilder contentBuilder = new StringBuilder();
    try (BufferedReader inputFile = new BufferedReader(new FileReader(this.templateFilePath))) {
      String line;
      while ((line = inputFile.readLine()) != null) {
        contentBuilder.append(line).append(System.lineSeparator());
      }
    } catch (FileNotFoundException fnfe) {
      System.out.println("A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }
    this.templateContent = contentBuilder.toString();
  }

  /**
   * Processes the customer information, replacing placeholders in the template with
   * actual clients' information, and saves the output to files in the specified directory.
   * Each customer would have its own file.
   * @param customerInfo List of customer information, where each list item represents one customer.
   * The first item in the list is the headers with contents categories.
   */
  public void replacePlaceholdersSaveOutput(List<List<String>> customerInfo) {
    // Map each header to its index
    Map<String, Integer> headerFieldIndex = new HashMap<>();
    List<String> headers = customerInfo.get(0);
    for (int i = 0; i < headers.size(); i++) {
      headerFieldIndex.put(headers.get(i), i);
    }

    // Process each customer starting from the second line to skip headers
    for (int index = 1; index < customerInfo.size(); index++) {
      List<String> currCustomer = customerInfo.get(index);

      // Set the file name based on customer information
      String fileName = constructFileName(index, currCustomer, headerFieldIndex);

      String tempTemplate = this.templateContent;
      Matcher matcher = PLACEHOLDER_PATTERN.matcher(tempTemplate);

      // Replace all placeholders in the file based on client's information
      while (matcher.find()) {
        String placeholder = matcher.group(1);
        Integer placeholderIndex = headerFieldIndex.get(placeholder);
        if (placeholderIndex != null && placeholderIndex >= 0
            && placeholderIndex < currCustomer.size()) {
          String replacement = currCustomer.get(placeholderIndex);
          tempTemplate = tempTemplate.replace("[[" + placeholder + "]]", replacement);
        }
      }
      generateOutput(this.outputDirPath, tempTemplate, fileName);
    }
  }

  /**
   * Helper method constructs a file name based on customer information.
   * File name to be "customer's index_customer's first name_customer's last name_File type.txt"
   *
   * @param index Index of the customer in the customerInfo list.
   * @param currCustomer Current customer's information expressed as a list of strings.
   * @param headerFieldIndex Map of headers to the corresponding index.
   * @return Constructed file name.
   */
  protected String constructFileName(int index, List<String> currCustomer, Map<String, Integer> headerFieldIndex) {
    Integer firstNameIndex = headerFieldIndex.get("first_name");
    Integer lastNameIndex = headerFieldIndex.get("last_name");
    if (firstNameIndex != null && lastNameIndex != null &&
        firstNameIndex < currCustomer.size() && lastNameIndex < currCustomer.size()) {
      return index + "_" +
          currCustomer.get(firstNameIndex) + "_" +
          currCustomer.get(lastNameIndex) + "_" + this.templateFileType + ".txt";
    } else {
      throw new IndexOutOfBoundsException("\"'first_name' or 'last_name' do not exists or out of bound.\"");
    }
  }

  /**
   * Helper method takes template from the user and write the contents to the file
   * with user information replaced.
   * Then save the file to the output directory provided by the user.
   * @param outputFilePath path for outputs directory.
   * @param outputContents output contents to be written to the file.
   * @param fileName the output file name.
   */
  protected void generateOutput(String outputFilePath, String outputContents, String fileName){
    createFile(outputFilePath, fileName);
    writeToFile(outputFilePath, fileName, outputContents);
  }

  /**
   * Helper method to create new file for writing contents.
   * @param outputFilePath directory path where the processed files to be saved.
   * @param fileName name of the file to be created.
   */
  protected void createFile(String outputFilePath, String fileName){
    try {
      File newFile = new File(outputFilePath + "/" + fileName);
      newFile.createNewFile();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  /**
   * Helper method to write to the file based on customer information and file template.
   * @param outputFilePath directory path where the processed files to be saved.
   * @param fileName name of the file to be written.
   * @param outputContents outputs to be written in the file, provided as String.
   */
  protected void writeToFile(String outputFilePath, String fileName, String outputContents){
    try {
      FileWriter myWriter = new FileWriter(outputFilePath + "/" + fileName);
      myWriter.write(outputContents);
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred");
      e.printStackTrace();
    }
  }

  /**
   * Gets the templateFilePath Path to the template file.
   * @return the templateFilePath Path to the template file.
   */
  public String getTemplateFilePath() {
    return this.templateFilePath;
  }

  /**
   * Gets the templateContent.
   * @return the templateContent as String.
   */
  public String getTemplateContent() {
    return this.templateContent;
  }

  /**
   * Gets the directory path where the processed files to be saved.
   * @return the directory path where the processed files to be saved.
   */
  public String getOutputDirPath() {
    return this.outputDirPath;
  }

  /**
   * Gets the templateFileType.
   * @return the templateFileType.
   */
  public String getTemplateFileType() {
    return this.templateFileType;
  }

  /**
   * Determines whether two instances of TemplateProcessor are the same
   * @param o - other object being compared to
   * @return - whether the two objects are the same (boolean)
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateProcessor that = (TemplateProcessor) o;
    return Objects.equals(this.templateFilePath, that.templateFilePath)
        && Objects.equals(this.templateContent, that.templateContent)
        && Objects.equals(this.outputDirPath, that.outputDirPath) && Objects.equals(
        this.templateFileType, that.templateFileType);
  }

  /**
   * Returns hash of object
   * @return - hash of object
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.templateFilePath, this.templateContent,
        this.outputDirPath, this.templateFileType);
  }

}

