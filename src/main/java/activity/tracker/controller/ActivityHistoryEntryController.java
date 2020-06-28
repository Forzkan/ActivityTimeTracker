package activity.tracker.controller;

import activity.tracker.database.dto.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ActivityHistoryEntryController {

    @FXML
    private Button deleteActivityBtn;

    @FXML
    private Label activityNameLabel;

    @FXML
    private Label activityTimeSpentLabel;

    @FXML
	private Button favoriteActivityButton;

    @FXML
    private Label createdDateLabel;

    @FXML
    private Label lastActiveDateLabel;

	private final ActivityHandler activityHandler;
	private final Activity activity;

	@FXML
	public void initialize() {
		// initialize butons.
		activityNameLabel.setText(activity.getActivityName());
		activityTimeSpentLabel.setText(activity.getTotalMinutesTracked() + "minutes");
		createdDateLabel.setText(activity.getCreatedDate().toString());
		lastActiveDateLabel.setText(activity.getLastTrackedDate().toString());
		bindActivityButtons();
	}

	private void bindActivityButtons() {
		// TODO Auto-generated method stub

	}

	public ActivityHistoryEntryController(ActivityHandler aActivityHandler, Activity aActivity) {
		activityHandler = aActivityHandler;
		activity = aActivity;
	}
}

