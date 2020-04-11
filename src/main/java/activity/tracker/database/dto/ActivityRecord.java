package activity.tracker.database.dto;

import java.sql.Date;

import activity.tracker.fx.FXDisplayable;

public class ActivityRecord implements FXDisplayable {

	private final long recordId;
	private final long activityId;
	private final Date createdDate;
	private final int minutesTracked;

	public ActivityRecord(long recordId, long activityId, Date createdDate, int minutesTracked) {
		this.recordId = recordId;
		this.activityId = activityId;
		this.createdDate = createdDate;
		this.minutesTracked = minutesTracked;
	}

	/**
	 * @return the recordId
	 */
	public long getRecordId() {
		return recordId;
	}

	/**
	 * @return the activityId
	 */
	public long getActivityId() {
		return activityId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the minutesTracked
	 */
	public int getMinutesTracked() {
		return minutesTracked;
	}

	public int getMinutesPassedLastTrackedHour() {
		return getMinutesTracked() - (getHoursTracked() * 60);
	}

	/**
	 * @return the amount of tracked hours, rounded down.
	 */
	public int getHoursTracked() {
		return (int) Math.floor(minutesTracked / 60);
	}

	@Override
	public String getDisplayableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHoverText() {
		// TODO Auto-generated method stub
		return null;
	}

}
