package activity.tracker.fx;

import org.kordamp.ikonli.javafx.FontIcon;

public interface FXDisplayable {
	public String getDisplayableName();

	public String getHoverText();

	public default FontIcon getDisplayableIcon() {
		return new FontIcon("");
	}
}
