package activity.tracker.fx;

import activity.tracker.database.dto.FXDisplayable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class ActivityFxDisplayable extends HBox {

	private final Label name;
	private final Button delete = new Button();
	private final Button start = new Button();

	public ActivityFxDisplayable(FXDisplayable displayable) {
		name = new Label(displayable.getDisplayableName());
		this.getChildren().addAll(delete, name, start);
		name.setTooltip(new Tooltip(displayable.getHoverText()));
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
