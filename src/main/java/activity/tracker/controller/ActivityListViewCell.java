package activity.tracker.controller;

import org.kordamp.ikonli.javafx.FontIcon;

import activity.tracker.database.dto.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import rf.javafx.util.StageFactory;

public class ActivityListViewCell extends ListCell<Activity> {

	@FXML
	private VBox activityRootBox;

	@FXML
	private Label nameLabel, timeSpentLabel, createdDateLabel, lastActiveDateLabel;

	@FXML
	private Button deleteButton, favoriteButton;

	@FXML
	private FontIcon timeIcon, favoriteIcon;

	private StageFactory stageFactory;

	public ActivityListViewCell(StageFactory aStageFactory) {
		stageFactory = aStageFactory;

	}

	@Override
	protected void updateItem(Activity activity, boolean empty) {
		super.updateItem(activity, empty);

		if (empty || activity == null) {

			setText(null);
			setGraphic(null);

		} else {

			// Load the component to this class instance.
			try {
				stageFactory.createListViewComponent(this);
				setCellValues(activity);

			} catch (Exception e) {
				System.out.println("An error ocurred while trying to load a custom activity cell.");
				System.out.println("Error => " + e.getMessage());
			}
		}

	}

	private void setCellValues(Activity aActivity) {
		// set all the labels and such.
		nameLabel.setText(aActivity.getActivityName());
		timeSpentLabel.setText(aActivity.getTotalMinutesTracked() + " minutes");
		createdDateLabel.setText(aActivity.getCreatedDate().toString());
		lastActiveDateLabel.setText(aActivity.getLastTrackedDate().toString());
		this.setTooltip(new Tooltip(aActivity.getHoverText()));

		setText(null);
		setGraphic(activityRootBox);
	}

}
