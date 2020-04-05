package rf.java.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtil {


	private final static String PROJECT_FILE_ENDING = ".rtproj";


	public static Path toAbsoluteProjectPath(Path aRootPath, String aName) throws IOException {
		try {
			return Paths.get(aRootPath.toAbsolutePath().toString() + File.separatorChar + aName + PROJECT_FILE_ENDING);
		}catch(Exception e) {
			System.out.println("An error occurred while creating the path to the project");
			System.out.println(e.getMessage());
			throw new IOException(e);
		}
	}
	
	
	public static String getProjectRoot() {
    	return System.getProperty("user.dir");
    }
	
	
	public static Path getUserHomePath() {
		return Paths.get(System.getProperty("user.home"));
	}
	
}
