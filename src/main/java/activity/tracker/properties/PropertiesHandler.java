package activity.tracker.properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Properties;

import rf.java.util.PropertiesFactory;

public class PropertiesHandler {

	// TODO:: Log.
	private static Properties generalProperties;
	// TODO:: This should be a relative path from the location of the installation.
	private static final String GENERAL_PROPERTIES_PATH = "C:" + File.separator + "Program Files" + File.separator
			+ "RForsgren" + File.separator + "preferences" + File.separator + "general.properties";

	private static PropertiesFactory propFactory = new PropertiesFactory();;

	public void loadAllProperties() throws FileNotFoundException, IOException {

		generalProperties = propFactory.createPropertiesFile(GENERAL.defaultHashMap(),
				Paths.get(GENERAL_PROPERTIES_PATH), false);
		// TODO, validate properties method for all of these....

	}

	public static void saveGeneralProperty(GENERAL a_property, String a_value)
			throws IOException, FileNotFoundException {
		propFactory.saveProperties(Paths.get(GENERAL_PROPERTIES_PATH), generalProperties);
	}

	public static String getGeneralProperty(GENERAL a_property) {
		return generalProperties.getProperty(a_property.name());
	}

	public enum GENERAL {

		/**
		 * GENERAL SETTINGS
		 */
		DARK_MODE, SOME_SETTING, SOME_SETTING2;

		public static String getDefaultValue(GENERAL a_property) {
			switch (a_property) {
			case DARK_MODE:
				return "TRUE";
			case SOME_SETTING:
				return "543";
			case SOME_SETTING2:
				return "somevalue";
			default:
				return "";
			}
		}

		public static HashMap<String, String> defaultHashMap() {

			HashMap<String, String> map = new HashMap<String, String>();

			EnumSet.allOf(GENERAL.class).forEach(property -> map.put(property.toString(), getDefaultValue(property)));

			return map;
		}

	}

}
