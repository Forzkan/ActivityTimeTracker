package activity.tracker.controller;

import java.util.Timer;
import java.util.TimerTask;

import org.kordamp.ikonli.javafx.FontIcon;

import activity.tracker.database.DatabaseHandler;
import activity.tracker.database.dto.Activity;
import activity.tracker.properties.PropertiesHandler;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import rf.javafx.util.StageFactory;

// TODO:: Create an ActivityEntry with the current date, but with no end date, to be used in case the timer is not working while the computer is closed/sleeping.
public class ActivityHandler {
	private final DatabaseHandler dbHandler;
	private final PropertiesHandler properties;
	private final BooleanProperty isTracking = new SimpleBooleanProperty(false);
	private Label currentActivityTitleLabel;

	public boolean getIsTracking() {
		return isTracking.get();
	}

	private final BooleanProperty isPaused = new SimpleBooleanProperty(false);

	public boolean getIsPaused() {
		return isPaused.get();
	}

	private StageFactory stageFactory;
	private IntegerProperty activeSeconds = new SimpleIntegerProperty(0);
	private IntegerProperty activeMinutes = new SimpleIntegerProperty(0);
	private ObjectProperty<Activity> currentActivity = new SimpleObjectProperty<Activity>();
	private ObservableList<Activity> activityHistory;
	private FontIcon activityBtnFontIcon;

	private Timer timer;

	public ActivityHandler(PropertiesHandler aProperties, DatabaseHandler aDbHandler, FontIcon aActivityBtnFontIcon,
			Label aCurrentActivityTitleLabel, StageFactory aStageFactory) {
		dbHandler = aDbHandler;
		properties = aProperties;
		// activityHistory = dbHandler.getSavedActivities();
		activityBtnFontIcon = aActivityBtnFontIcon;
		currentActivityTitleLabel = aCurrentActivityTitleLabel;
		stageFactory = aStageFactory;
		activityHistory = dbHandler.getSavedActivities();
		initializeAllListViews();
	}

	public ObjectProperty<Activity> currentActivityProperty() {
		return currentActivity;
	}

	public IntegerProperty activeMinutesProperty() {
		return activeMinutes;
	}

	public ObservableList<Activity> getActivityHistory() {
		return activityHistory;
	}

	public void onStartActivity(String aActivityName) {
		if (getIsTracking()) {
			if (getIsPaused()) {
				unpauseTracking();
			} else {
				pauseTracking();
			}
		} else {
			// if one existing activity was running, then create a new entry.

			// Check if activity name is not the same as the current activity and not empty
			// string, if it is then we do nothing..
			if (aActivityName.strip().isEmpty() || (currentActivity.get() != null
					&& aActivityName.equalsIgnoreCase(currentActivity.get().getActivityName()))) {
				return;
			}
			// else... check if exist in history and if so, add activityRecord and update
			// activity minutes and lastActiveDate.
			Activity existingActivity = dbHandler.getActivityOnName(aActivityName);
			if (existingActivity != null) {
				// get the activity form the database...
				currentActivity.set(existingActivity);
				// set the currentActivity to the one to start..

				// STart that activity.

			} else {
				// otherwise, create a new activity only.
				Activity newActivty = addNewActivityToDatabase(aActivityName);
				System.out.println(newActivty.activityInformationToString());
				activityHistory.add(newActivty);
				currentActivity.set(newActivty);
			}

			// currentActivity.set(createNewActivity(aActivityName));
			startActivityTimer();
			activityBtnFontIcon.setIconLiteral("dashicons-controls-pause");
		}

	}

	private void startActivityTimer() {
		System.out.println("STARTING TIMER");
		restartActivityTimer(0, 0);
	}

	private void restartActivityTimer(int aStartAtMinute, int aStartSecond) {
		currentActivityTitleLabel.setText("Current Activity '" + currentActivity.get().getActivityName() + "'");
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		isTracking.set(true);
		isPaused.set(false);
		activeSeconds.set(aStartSecond);
		activeMinutes.set(aStartAtMinute);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			boolean first = true;

			@Override
			public void run() {
				Platform.runLater(() -> {
					if (first) {
						first = false;
						return;
					}
					activeSeconds.set(activeSeconds.get() + 1);
					if (activeSeconds.get() >= 60) {
						activeSeconds.set(0);
						activeMinutes.set(activeMinutes.get() + 1);
					}
				});
			}
		}, 0, 1000);
	}

	private Activity addNewActivityToDatabase(String aActivityName) {
		System.out.println("Starting new activity with the name: " + aActivityName);
		return dbHandler.insertNewActivity(aActivityName, "Test description...");
	}

	public void pauseTracking() {
		isPaused.set(true);
		timer.cancel();
		activityBtnFontIcon.setIconLiteral("dashicons-controls-play");
		currentActivityTitleLabel.setText("Current Activity '" + currentActivity.get().getActivityName() + "' Paused");
	}

	public void unpauseTracking() {
		restartActivityTimer(activeMinutes.get(), activeSeconds.get());
	}

	public void stopTracking() {
		// TODO:: create new activityRecord...
		currentActivityTitleLabel.setText("No active Activity.");
		isTracking.set(false);
		isPaused.set(false);

	}

//	TOOD::
//
//	Uppdatera så
//	att varje
//	minut så
//	kollar vi
//	hur många timmar,
//	minuter det
//	var sen
//	vi startade aktiviteten.

	private ListView<Activity> historyListView;
	private ListView<Activity> favoritesListView;

	private ListView<Activity> initializeListViews() {
		ListView<Activity> aListView = new ListView<Activity>();
		aListView.setItems(activityHistory);
		aListView.setCellFactory(studentListView -> new ActivityListViewCell(stageFactory));
		aListView.setCellFactory(new Callback<ListView<Activity>, ListCell<Activity>>() {
			@Override
			public ListCell<Activity> call(ListView<Activity> aListView) {
				return new ActivityListViewCell(stageFactory);
			}
		});
		return aListView;
	}

	private void initializeAllListViews() {
		historyListView = initializeListViews();
		favoritesListView = initializeListViews();
	}

	public ListView<Activity> getHistoryListView() {
		return historyListView;
	}

	public ListView<Activity> getFavoritesListView() {
		return favoritesListView;
	}

}
