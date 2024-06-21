package problem1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 * A utility class for processing customer information stored in CSV files.
 */
public class CustomerInfoProcessor {

  public CustomerInfoProcessor() {
  }

  /**
   * Reads a CSV file and returns its contents as a list of rows, each represented as a list of strings.
   * Empty lines are ignored.
   *
   * @param filePath the path to the CSV file to be read
   * @return a list of rows from the CSV file, or null if the file does not exist or an error occurs
   */
  public static List<List<String>> readCSVFile(String filePath) {
    // Check if the file is null
    if (filePath == null) {
      System.out.println("File path is null.");
      return null;
    }
    Path path = Paths.get(filePath);
    // Check if the file exists
    if (!Files.exists(path)) {
      System.out.println("File does not exist: " + filePath);
      return null;
    }

    List<List<String>> data = new ArrayList<>();

    try (BufferedReader inputFile = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = inputFile.readLine()) != null) {
        // Ignore empty lines
        if (!line.trim().isEmpty()) {
          // Parse CSV line and add to data
          List<String> row = readCSVLine(line);
          data.add(row);
        }
      }
    } catch (IOException e) {
      return null;
    }
    return data;
  }

  /**
   * Reads a line from a CSV file and returns its contents as a list of strings.
   *
   * @param line the line from the CSV file to be read
   * @return a list of strings representing the cells of the CSV line
   */
  public static List<String> readCSVLine(String line) {
    List<String> row = new ArrayList<>();
    if (line.trim().isEmpty()) {
      // Return empty row for empty lines
      return row;
    }
    // Regular expression to parse CSV line
    Pattern pattern = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?:,|$)");
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      // Add matched cell to row
      String cell = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
      row.add(cell.trim());
    }
    return row;
  }

}
