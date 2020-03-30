package activity.tracker.fx.component;

import activity.controller.MainController;
import activity.tracker.dto.Activity;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ActivityListview extends VBox {

	private ObjectProperty<Activity> selectedProperty;

	private int currentListIndex = 0;
	private Button arrowLeft = new Button();
	private Region filler = new Region();
	private Button arrowRight = new Button();
	private final MainController mainController;
	final ObservableList<Activity> activities;

	public ActivityListview(final ObservableList<Activity> aActivities, final MainController mainController) {
		this.mainController = new MainController();
		activities = aActivities;
		handleObservableListChange();
		setActivities();
	}

	private void setActivities() {

	}

	private void handleObservableListChange() {
		activities.addListener((final ListChangeListener.Change<? extends Activity> c) -> {
			// handle change, such as for example an activity being removed or added then we
			// need to handle the currentListIndex
			// to full list in the custom list view.
		});
	}

	public void startActivity(final Activity aActivity) {
		mainController.startActivity();
	}

	public void deleteActivity(final Activity aActivity) {
		activities.remove(aActivity);
	}

	public void addActivity(final Activity aActivity) {
		activities.add(aActivity);
	}

	public void addBackAndForthArrows() {
		HBox arrowContainers = new HBox();
		arrowContainers.getChildren().addAll(arrowLeft, filler, arrowRight);
		this.getChildren().add(arrowContainers);
	}

}
