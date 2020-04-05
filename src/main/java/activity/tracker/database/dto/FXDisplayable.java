package activity.tracker.database.dto;

public interface FXDisplayable {
	public String getDisplayableName();

	public String getHoverText();

	public default String getDisplayableIcon() {
		return "";
	}
}
