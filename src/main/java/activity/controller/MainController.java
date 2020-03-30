package activity.controller;

import org.controlsfx.control.textfield.CustomTextField;

import activity.tracker.dto.Activity;
import activity.tracker.fx.component.ActivityListview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainController {

	@FXML
	private Button closeButton;

	@FXML
	private TextField currentActivityTextField;

	@FXML
	private CustomTextField newActivityTextField;

	@FXML
	private Button newActivityBtn;

	@FXML
	private VBox centerBox;

	@FXML
	private Button settingsButton;

	@FXML
	private Button statisticsButton;

	private ActivityListview activityList;

	@FXML
	private void initialize() {
		activityList = new ActivityListview(loadAllActivities(), this);
		centerBox.getChildren().add(activityList);
	}

	private ObservableList<Activity> loadAllActivities() {
		final ObservableList<Activity> cells = FXCollections.<Activity>observableArrayList();

		for (int i = 0; i < 5; i++) {
			final Activity c = new Activity("text" + i);
			cells.add(c);
		}
		return cells;
	}

	public void startActivity() {

	}

	@FXML
	private void onAddActivity() {

	}

	@FXML
	private void onStartActivity() {

	}
}

