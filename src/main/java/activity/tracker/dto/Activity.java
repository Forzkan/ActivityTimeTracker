package activity.tracker.dto;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Activity extends HBox {

	private final Label name;
	private final Button delete = new Button();
	private final Button start = new Button();

	public Activity(final String aName) {
		name = new Label(aName);
		this.getChildren().addAll(delete, name, start);
	}

	public StringProperty getNameProperty() {
		return name.textProperty();
	}

	public ObjectProperty<EventHandler<ActionEvent>> getDeleteActionProperty() {
		return delete.onActionProperty();
	}

	public ObjectProperty<EventHandler<ActionEvent>> getStartActionProperty() {
		return start.onActionProperty();
	}

}
