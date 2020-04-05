package rf.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Objects;

public class FileUtility {

	// TODO:: https://www.baeldung.com/java-write-to-file   nice help for writing to file..¨
	// TODO:: Create new project files...
	// TODO:: Update existing project files... lock them, update but keep backup of existing and save it as a temporary file. 
	// TODO:: Read existing project files.. lock them while reading.
	
	// TODO:: Better article, both read and write..: https://www.vogella.com/tutorials/JavaIO/article.html
	
	// TODO:: This class requires and overhaul...
	
	/**
	 * @param a_filePath
	 * @throws IOException
	 */
	public void makeFileHidden(Path a_filePath) throws IOException {
		// set hidden attribute
		Files.setAttribute(a_filePath, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
		File file = new File(a_filePath.toAbsolutePath().toString());

		if (file.isHidden()) {
			System.out.println("File is hidden");
		} else {
			System.out.println("File is visible");
		}
	}

	/* TODO, merge with the one below. */
	public File createFile(Path a_path) throws IOException {
		File file = a_path.toFile();
		System.out.println("THE PATH TO CREATE PROPERTIES FILE AT: " + a_path.toString());
		if (file.exists() == false) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();

		return file;
	}

	public File createPathAndFile(String a_directory, String a_filename) throws IOException {

		File directory = new File(a_directory);
		if (directory.exists() == false) {
			directory.mkdirs();
		}
		File file = new File(a_directory + a_filename);
		file.createNewFile();
		return file;
	}

	public boolean isValidFileEnding(Path a_path, ArrayList<String> a_validEndings)
			throws FileNotFoundException {

		if (a_path.getFileName() == null) {
			throw new FileNotFoundException(
					"File ending could not be verified because no file could be found for the path argument: "
							+ a_path.toString());
		}

		StringBuilder pattern = new StringBuilder();
		pattern.append("glob:*.{");
		for (int i = 0; i < a_validEndings.size(); i++) {
			pattern.append(a_validEndings.get(i));
			if (i != a_validEndings.size() - 1) {
				pattern.append(",");
			}
		}
		pattern.append("}");

		PathMatcher matcher = FileSystems.getDefault().getPathMatcher(pattern.toString());

		if (matcher.matches(a_path.getFileName())) {
			return true;
		}
		return false;
	}

	/**
	 * Reads the .exe file to find headers that tell us if the file is 32 or 64 bit.
	 * 
	 * Note: Assumes byte pattern 0x50, 0x45, 0x00, 0x00 just before the byte that
	 * tells us the architecture.
	 * 
	 * From:
	 * https://stackoverflow.com/questions/18069940/how-to-determine-in-java-whether-another-process-or-executable-is-32-bit-or-64
	 * 
	 * @param filepath fully qualified .exe file path.
	 * @return true if the file is a 64-bit executable; false otherwise.
	 * @throws IOException if there is a problem reading the file or the file does
	 *                     not end in .exe.
	 */
	public boolean isExeFile64Bit(String filepath) throws IOException {
		if (!filepath.endsWith(".exe")) {
			throw new IOException("Not a Windows .exe file.");
		}

		byte[] fileData = new byte[1024]; // Should be enough bytes to make it to the necessary header.
		try (FileInputStream input = new FileInputStream(filepath)) {
			int bytesRead = input.read(fileData);
			for (int i = 0; i < bytesRead; i++) {
				if (fileData[i] == 0x50 && (i + 5 < bytesRead)) {
					if (fileData[i + 1] == 0x45 && fileData[i + 2] == 0 && fileData[i + 3] == 0) {
						return fileData[i + 4] == 0x64;
					}
				}
			}
		}
		return false;
	}



//	public static Stream<Path> getAllFilesFromPathWithFileEndings(Path a_path, ArrayList<String> fileEndings){
//		
//			Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\rober\\Desktop\\test_jar"))
//				.filter(p -> isValidFileEnding(p, fileEndings)).sorted();
//		return paths;
//
//		
//	}

	
	
	private String readTextFile(Path aPath) throws IOException {
		String content = new String(Files.readAllBytes(aPath));
		return content;
	}
//
//	private List<String> readTextFileByLines(String fileName) throws IOException {
//		List<String> lines = Files.readAllLines(Paths.get(fileName));
//		return lines;
//	}
//	
	

	public String readGsonStringFromProjectFile(Path aAbsolutePath) throws IOException {
		return readTextFile(aAbsolutePath);
	}
	
	public boolean saveGsonProjectStringToNewFile(String aProjectGson, Path aPath) throws IOException {
		Objects.requireNonNull(aProjectGson);
		Objects.requireNonNull(aPath);
		System.out.println("2222");
		byte[] strToBytes = aProjectGson.getBytes();
		Files.write(aPath, strToBytes);
		return true;
	}
	
	
	/**
	 * TODO:: We should have a lock on the file while it's being open... 
	 * @param aProjectGson
	 * @param aPath
	 * @return
	 * @throws IOException
	 */
	public boolean resaveGsonProjectFile(String aProjectGson, Path aPath) throws IOException {
		Objects.requireNonNull(aProjectGson);
		Objects.requireNonNull(aPath);
		
		byte[] strToBytes = aProjectGson.getBytes();
		Files.write(aPath, strToBytes);
		return true;
	}
	
	
	
}
