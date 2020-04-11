package activity.tracker.controller;

import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;

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

// TODO:: Create an ActivityEntry with the current date, but with no end date, to be used in case the timer is not working while the computer is closed/sleeping.
public class ActivityHandler {
	private final DatabaseHandler dbHandler;
	private final PropertiesHandler properties;
	private final BooleanProperty pausedProperty = new SimpleBooleanProperty(false);
	private IntegerProperty activeMinutes = new SimpleIntegerProperty(0);
	private ObjectProperty<Activity> currentActivity = new SimpleObjectProperty<Activity>();
	private ObservableList<Activity> activityHistory;

	private Timer timer;

	public ActivityHandler(PropertiesHandler aProperties, DatabaseHandler aDbHandler) {
		dbHandler = aDbHandler;
		properties = aProperties;
		activityHistory = dbHandler.getSavedActivities();
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
		// if one existing activity was running, then create a new entry.

		// Check if activity name is not the same as the current activity and not empty
		// string, if it is then we do nothing..
		if (aActivityName.strip().isEmpty()
				|| (currentActivity.get() != null
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
	}


	private void startActivityTimer() {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		System.out.println("STARTING TIMER");
		timer.scheduleAtFixedRate(new TimerTask() {
			boolean first = true;
			@Override
			public void run() {
				Platform.runLater(() -> {
					if (first) {
						first = false;
						return;
					}
					activeMinutes.set(activeMinutes.get() + 1);
				});
			}
		}, 0, 60000);
	}

	private Activity addNewActivityToDatabase(String aActivityName) {
		System.out.println("Starting new activity with the name: " + aActivityName);
		return dbHandler.insertNewActivity(aActivityName, "Test description...");
	}

	private Activity createNewActivity(String aActivityName) {
		// TODO...
		return new Activity(1, "activity_name", "description", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 0);
	}

}
