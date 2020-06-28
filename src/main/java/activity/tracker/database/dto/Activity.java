package activity.tracker.database.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.kordamp.ikonli.javafx.FontIcon;

import activity.tracker.fx.FXDisplayable;

// Immutable Activity Object.
public class Activity implements FXDisplayable {

	private final long activityId;
	private final String activityName;
	private final String description;
	private final LocalDateTime createdDate;
	private final LocalDateTime lastTrackedDate;
	private final int totalMinutesTracked;
	private final boolean favorite;
	private final String iconLiteral = "dashicons-tag";
	private final List<ActivityRecord> activityRecords = new ArrayList<ActivityRecord>();

	public Activity(long aActivityId, String aActivityName, String aDescription, LocalDateTime date,
			LocalDateTime date2,
			int aTotalMinutesTracked, boolean aFavorite) {
		activityId = aActivityId;
		activityName = aActivityName;
		description = aDescription;
		createdDate = date;
		lastTrackedDate = date2;
		totalMinutesTracked= aTotalMinutesTracked;
		favorite = aFavorite;
	}


	/**
	 * @return the activityId
	 */
	public long getActivityId() {
		return activityId;
	}

	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the lastTrackedDate
	 */
	public LocalDateTime getLastTrackedDate() {
		return lastTrackedDate;
	}

	/**
	 * @return the totalMinutesTracked
	 */
	public int getTotalMinutesTracked() {
		return totalMinutesTracked;
	}

	/**
	 * @return the activityRecords
	 */
	public List<ActivityRecord> getActivityRecords() {
		return activityRecords;
	}

	public void addRecord(ActivityRecord aRecord) {
		activityRecords.add(aRecord);
	}

	public void addRecords(ActivityRecord... aRecords) {
		for (ActivityRecord record : aRecords) {
			addRecord(record);
		}
	}

	public void addRecords(List<ActivityRecord> aActivityRecords) {
		activityRecords.addAll(aActivityRecords);
	}


	@Override
	public String toString() {
		return activityName;
	}

	/**
	 * @return information of the <code>Activity</code> as a formated <code>String</code>.
	 */
	public String activityInformationToString() {
		return String.format("(id: {%s}) name: {%s}\n{%s}\nCreated:{%s}\nLast Updated:{%s}", activityId, activityName,
				description, createdDate.toString(), lastTrackedDate.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (activityId ^ (activityId >>> 32));
		result = prime * result + ((activityName == null) ? 0 : activityName.hashCode());
		result = prime * result + ((activityRecords == null) ? 0 : activityRecords.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((lastTrackedDate == null) ? 0 : lastTrackedDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;

		if (activityId == other.activityId) {
			return true;
		}
		return false;
	}

	@Override
	public String getDisplayableName() {
		return toString();
	}

	@Override
	public String getHoverText() {
		return activityInformationToString();
	}


	public boolean isFavorite() {
		return favorite;
	}

	@Override
	public FontIcon getDisplayableIcon() {
		return new FontIcon(iconLiteral);

	}
}
