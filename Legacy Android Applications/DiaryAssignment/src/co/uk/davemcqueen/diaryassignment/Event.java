package co.uk.davemcqueen.diaryassignment;

public class Event {
int eventId;
String eventName;
String eventDescription;

public Event()
{
}

public Event(int id, String name, String description)
{
this.eventId = id;
this.eventName = name;
this.eventDescription = description;
}
	
public Event(String name, String description)
{
	this.eventName = name;
	this.eventDescription = description;
}

public int getID()
{
	return this.eventId;
}

public void setID(int id)
{
	this.eventId = id;
}

public String getName()
{
	return this.eventName;
}

public void setName(String name)
{
	this.eventName = name;
}

public String getDescription()
{
	return this.eventDescription;
}

public void setDescription(String description)
{
	this.eventDescription = description;
}
	
}
