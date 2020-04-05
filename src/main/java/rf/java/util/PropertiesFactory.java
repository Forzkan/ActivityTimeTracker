package rf.java.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Properties;


public class PropertiesFactory {
  private FileUtility fileUtility;

  public PropertiesFactory() {
    fileUtility = new FileUtility();
  }

  // TODO:: Log.
  public Properties createPropertiesFile(HashMap<String, String> a_properties, Path a_path,
      boolean overwrite) throws IOException, FileNotFoundException {

    if ((a_path.toFile().exists() && overwrite == true) || a_path.toFile().exists() == false) {
      fileUtility.createFile(a_path);
    }
    // TODO, should also verify the integrity of the properties files, values may not be null, and
    // all "properties" must be present in the file or otherwise they should be added with their
    // default value.
    OutputStream output = getOutputStreamStream(a_path);
    Properties properties = loadPropertiesFile(a_path);

    a_properties.forEach((key, value) -> properties.setProperty(key, value));

    saveProperties(output, properties);
    closeOutputStream(output);
    return properties;
  }


  public Properties loadPropertiesFromFile(Path a_path) throws IOException, FileNotFoundException {
    if (a_path.toFile().exists() == false) {
      throw new FileNotFoundException("File not found at: " + a_path.toAbsolutePath().toString());
    }
    return loadPropertiesFile(a_path);
  }


  public void saveProperties(Path a_path, Properties a_properties)
      throws IOException, FileNotFoundException {

    OutputStream output = getOutputStreamStream(a_path);
    saveProperties(output, a_properties);
    closeOutputStream(output);
  }



  private Properties loadPropertiesFile(Path a_path) throws IOException, FileNotFoundException {
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(a_path.toAbsolutePath().toString()));
    } catch (IOException e) {
      throw new IOException("An error occured while attempting to load properties from path :"
          + a_path.toAbsolutePath().toString() + "\\n" + e.getMessage());
    }
    return properties;
  }


  private static OutputStream getOutputStreamStream(Path a_path)
      throws IOException, FileNotFoundException {
    OutputStream output = null;
    try {
      output = new FileOutputStream(a_path.toAbsolutePath().toString());
    } catch (IOException e) {
      throw new IOException("An error occured while creating a properties FileOutPutStream." + "\\n"
          + e.getMessage());
    }
    return output;
  }

  private static void saveProperties(OutputStream a_output, Properties a_properties)
      throws IOException, FileNotFoundException {
    try {
      a_properties.store(a_output, null);
    } catch (IOException e) {
      throw new IOException(
          "An error occured while attempting to save properties." + "\\n" + e.getMessage());
    }
  }

  private static void closeOutputStream(OutputStream a_output)
      throws IOException, FileNotFoundException {
    if (a_output != null) {
      try {
        a_output.close();
      } catch (IOException e) {
        throw new IOException(
            "An error occured while closing properties OutPutStream." + "\\n" + e.getMessage());
      }
    }
  }



}
