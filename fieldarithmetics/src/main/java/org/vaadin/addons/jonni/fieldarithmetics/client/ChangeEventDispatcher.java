package org.vaadin.addons.jonni.fieldarithmetics.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.impl.DOMImplStandard;

/**
 * An EventDispatcher that can be used to catch change events before Vaadin
 * handles them
 * 
 * @author jonni
 *
 */
public class ChangeEventDispatcher implements EntryPoint {

	private static Map<Element, EventListener> changeListeners = new HashMap<Element, EventListener>();

	@Override
	public void onModuleLoad() {
    	DOMImplStandard.addCaptureEventDispatchers(getChangeDispatcher());
    }

	private static native JavaScriptObject getChangeDispatcher() /*-{
	   return {
	     change: @org.vaadin.addons.jonni.fieldarithmetics.client.ChangeEventDispatcher::changeEvent(*),
	   };
	 }-*/;

	private static void changeEvent(Event event) {
		EventTarget eventTarget = event.getEventTarget();
		Set<Element> changeListenerElements = changeListeners.keySet();
		
		if (changeListenerElements.contains(eventTarget)) {
			changeListeners.get(eventTarget).onBrowserEvent(event);
		}
	}

	/**
	 * Adds a change listener to a element. Remove the listener using
	 * {@link #removeChangeListener(Element)} e.g. on widget detach to prevent
	 * memory leaks.
	 * 
	 * @param element
	 * @param changeListener
	 */
	public static void addChangeListener(Element element,
			EventListener changeListener) {
		changeListeners.put(element, changeListener);
	}

	/**
	 * Remove a change listener registration
	 * 
	 * @param element
	 */
	public static void removeChangeListener(Element element) {
		changeListeners.remove(element);
	}
}