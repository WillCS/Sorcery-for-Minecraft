package sorcery.lib;

import java.util.ArrayList;

public class EventScheduler {
	
	public ArrayList<ScheduledEvent> events = new ArrayList<ScheduledEvent>();

	public void scheduleEvent(String tag, EventObject object, int delay) {
		events.add(new ScheduledEvent(tag, delay, object));
	}
	
	public boolean isEventScheduled(String tag) {
		for(int i = 0; i < this.events.size(); i++) {
			if(this.events.get(i).tag.equals(tag))
				return true;
		}
		return false;
	}
	
	public static class ScheduledEvent {
		public int delay;
		public EventObject event;
		public final String tag;
		
		public ScheduledEvent(String tag, int delay, EventObject event) {
			this.tag = tag;
			this.delay = delay;
			this.event = event;
		}
	}
	
	public static abstract class EventObject {
		public Object[] args;
		
		public EventObject(Object[] args) {
			this.args = args;
		}
		
		public abstract void call();
	}
}
