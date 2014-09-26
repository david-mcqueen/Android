/*
 * David McQueen
 * 10153465@stu.mmu.ac.uk
 */
package co.uk.davemcqueen.Assignment;

import android.provider.BaseColumns;

public class Event {
/*
 * Define the database column names, can be called throughout the program to eliminate any typing errors
 */

public static final class EventItem implements BaseColumns
{
	
private EventItem() {}

public static final String TABLE_NAME = "events";

public static final String COLUMN_NAME_DESCRIPTION = "eventDescription";

public static final String COLUMN_NAME_EVENT = "eventName";

public static final String COLUMN_NAME_DATE = "eventDate";

public static final String COLUMN_NAME_TIME = "eventTime";

public static final String COLUMN_NAME_ID = "_id";

public static final String COLUMN_NAME_LOCATION = "eventLocation";

public static final String DEFAULT_SORT_ORDER = Event.EventItem.COLUMN_NAME_DATE + " DESC";

public static final String COLUMN_NAME_IMAGE = "eventImage";

}

}
