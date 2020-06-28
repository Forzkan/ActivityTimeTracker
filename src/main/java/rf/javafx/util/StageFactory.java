package rf.javafx.util;

import java.util.ArrayList;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageFactory {

	// FXML
	private final String MAIN_WINDOW_FXML = "/gui/fxml/root.fxml";
	private final String LISTVIEW_COMPONENT_FXML = "/gui/fxml/acivityHistoryEntry.fxml";

	// CSS
	private final String MAIN_WINDOW_CSS = "/gui/css/style.css";

	private ArrayList<String> generalStylesheets;

	// IMAGES
	// TODO:: private final String REGTEST_LOGO = "/gui/image/logo/regtest.png";

	// TODO:: Log.
	public StageFactory() {
		generalStylesheets = new ArrayList<String>();
		generalStylesheets.add(MAIN_WINDOW_CSS);
	}


	public Stage createMainApplicationWindow(Object aController) throws Exception {
		Stage stage = new JFXStageBuilder.StageBuilder(MAIN_WINDOW_FXML).withController(aController)
				.withDimensions(400, 360) /* width and height in pixels. */
				.withMinWindowSize(400, 360)
				.withPosition(1, 1) /* Top left corner of the main screen. */
				.withCustomResizableBorders()
				.withStageStyle(StageStyle.TRANSPARENT).withTitle("Activity Tracker")
				.withStylesheets(generalStylesheets).build();
		return stage;
	}

	public VBox createListViewComponent(Object aController) throws Exception {
		VBox component = (VBox) JFXStageBuilder.loadFXML(LISTVIEW_COMPONENT_FXML, aController);
		return component;
	}

}
