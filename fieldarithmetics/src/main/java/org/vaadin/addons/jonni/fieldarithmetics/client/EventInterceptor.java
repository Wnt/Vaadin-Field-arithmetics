package org.vaadin.addons.jonni.fieldarithmetics.client;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

public class EventInterceptor implements EventListener {

	private EventListener originalEventListener;
//	private Logger l = Logger.getLogger("EvenInterceptor");
	private EventListener overridingChangeHandler;

	public EventInterceptor(EventListener originalEventListener,
			EventListener overridingChangeHandler) {
		this.originalEventListener = originalEventListener;
		this.overridingChangeHandler = overridingChangeHandler;
	}

	@Override
	public void onBrowserEvent(Event event) {
		if (event.getTypeInt() == Event.ONCHANGE) {
			overridingChangeHandler.onBrowserEvent(event);
		}
		originalEventListener.onBrowserEvent(event);

	}

}
