package activity.tracker.controller;

import org.controlsfx.control.textfield.CustomTextField;

import activity.tracker.database.DatabaseHandler;
import activity.tracker.database.dto.Activity;
import activity.tracker.properties.PropertiesHandler;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.converter.NumberStringConverter;

public class MainController { // TODO:: pauseBtn, still want to pause - reminders?
	// TODO:: Create a PDF creator that can export the activities of the past day,
	// week, months or a custom range. Include or exclude statistics.

	@FXML
	private Button closeButton;
	@FXML
	private Label minutesLabel;

	@FXML
	private CustomTextField currentActivityTextField;

	@FXML
	private Button newActivityBtn, settingsButton, statisticsButton;

	private final DatabaseHandler dbHandler;
	private final ActivityHandler activityHandler;
	private final PropertiesHandler properties = new PropertiesHandler();

	public MainController() {
		dbHandler = new DatabaseHandler(properties, "testDatabase.db");
		activityHandler = new ActivityHandler(properties, dbHandler);
		// TODO:: Get database information, and send it to the database handler for it
		// to verify that it exists, can be connected to and more..
		// For now the name is hard coded..
		if (dbHandler.verifyDatabaseConnection()) {
			System.out.println("DATABASE CONNECTION VERIFIED.");

		}

	}

	@FXML
	private void initialize() {
		// Load saved activities and add saved activities to the texfields autocomplete
		// feature.
		fillAutoCompleteTextfield(activityHandler.getActivityHistory());
		setTextFieldOnEnterPressed();
		minutesLabel.textProperty().bindBidirectional(activityHandler.activeMinutesProperty(),
				new NumberStringConverter());
		newActivityBtn.setOnAction(e -> activityHandler.onStartActivity(currentActivityTextField.getText()));
	}

	private void fillAutoCompleteTextfield(ObservableList<Activity> activityHistory) {
		// TextFields.bindAutoCompletion(currentActivityTextField, activityHistory);
		SuggestionProvider<Activity> suggestionProvider = SuggestionProvider.create(activityHistory);
		new AutoCompletionTextFieldBinding<>(currentActivityTextField, suggestionProvider);
		activityHistory.addListener((Change<? extends Activity> changes) -> {
			suggestionProvider.clearSuggestions();
			suggestionProvider.addPossibleSuggestions(activityHistory);
		});
	}

	private void setTextFieldOnEnterPressed() {
		currentActivityTextField.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				activityHandler.onStartActivity(currentActivityTextField.getText());
			}
		});
	}






}
//	private ObservableList<FXDisplayable> loadAllActivities() {
//		final ObservableList<FXDisplayable> cells = FXCollections.<FXDisplayable>observableArrayList();
//
//		for (int i = 0; i < 5; i++) {
//			final FXDisplayable c = new Activity(i, "name" + i, "description", new Date(System.currentTimeMillis()),
//					new Date(System.currentTimeMillis()), i);
//			cells.add(c);
//		}
//		return cells;
//	}

// activityList = new ActivityListview(loadAllActivities(), this);
// centerBox.getChildren().add(activityList);
