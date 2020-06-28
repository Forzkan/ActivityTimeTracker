package rf.javafx.util;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
// import regtest.controller.NewTestController;

public class JFXChooserUtil {

  private static final Logger LOGGER = LogManager.getLogger(JFXChooserUtil.class);

  public static Path directoryChooser(Stage a_stage, String a_title, File a_startDir)
      throws SecurityException, InvalidPathException {
    LOGGER.trace("Opening directory chooser.");
    if (a_startDir.isDirectory() == false) {
      throw new InvalidPathException("The provided path argument is not a directory.",
          a_startDir.getAbsolutePath().toString());
    }

    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle(a_title);
    chooser.setInitialDirectory(a_startDir);

    while (true) {
      File selectedDirectory = chooser.showDialog(a_stage);

      if (selectedDirectory != null) {
        return selectedDirectory.toPath();
      }
    }
  }



  // TODO:: Add filter possibilities.
  public static Path fileChooser(Stage a_stage, String a_title, File a_startDir)
      throws SecurityException, InvalidPathException {
    LOGGER.trace("Opening file chooser.");
    if (a_startDir.isDirectory() == false) {
      throw new InvalidPathException("The provided path argument is not a directory.",
          a_startDir.getAbsolutePath().toString());
    }

    FileChooser chooser = new FileChooser();
    chooser.setTitle(a_title);
    chooser.setInitialDirectory(a_startDir);

    while (true) {
      File selectedFile = chooser.showOpenDialog(a_stage);

      if (selectedFile != null) {
        return selectedFile.toPath();
      }
    }
  }

}
