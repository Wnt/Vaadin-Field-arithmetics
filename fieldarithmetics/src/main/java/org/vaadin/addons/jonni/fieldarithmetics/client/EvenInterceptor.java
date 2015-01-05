package org.vaadin.addons.jonni.fieldarithmetics.client;

import java.util.logging.Logger;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

public class EvenInterceptor implements EventListener {

	private EventListener originalEventListener;
	private Logger l = Logger.getLogger("EvenInterceptor");
	private EventListener overridingChangeHandler;

	public EvenInterceptor(EventListener originalEventListener,
			EventListener overridingChangeHandler) {
		this.originalEventListener = originalEventListener;
		this.overridingChangeHandler = overridingChangeHandler;
	}

	@Override
	public void onBrowserEvent(Event event) {
		l.warning("onBrowserEvent: " + event.getString());
		if (event.getTypeInt() == Event.ONCHANGE) {
			l.warning("calling overridingChangeHandler.onBrowserEvent");
			overridingChangeHandler.onBrowserEvent(event);
		}
		originalEventListener.onBrowserEvent(event);

	}

}
