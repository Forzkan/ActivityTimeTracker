package activity.tracker.fx;

public interface FXDisplayable {
	public String getDisplayableName();

	public String getHoverText();

	public default String getDisplayableIcon() {
		return "";
	}
}
