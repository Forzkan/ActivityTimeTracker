package activity.tracker.fx.component;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

// Thanks Jamed_D:
// https://stackoverflow.com/questions/23137131/javafx-listview-with-button-in-each-cell
public class ActivityCell extends ListCell<String> {
	private final Button actionBtn;
	private final Label name;
	private final HBox pane;

	public ActivityCell(final String text) {
		super();

		setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent event) {
				// do something
			}
		});

		actionBtn = new Button("my action");
		actionBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				System.out.println("Action: " + getItem());
			}
		});
		name = new Label(text);
		pane = new HBox();
		HBox.setHgrow(name, Priority.ALWAYS);
		pane.getChildren().addAll(name, actionBtn);
		// pane.add(actionBtn, 0, 1);
		setText(text);
	}

	@Override
	public void updateItem(final String item, final boolean empty) {
		super.updateItem(item, empty);
		setEditable(false);
		if (item != null) {
			name.setText(item);
			setGraphic(pane);
		} else {
			setGraphic(null);
		}
	}
}