package org.vaadin.addons.jonni.fieldarithmetics.client;

import java.util.logging.Logger;

import org.vaadin.addons.jonni.fieldarithmetics.FieldArithmetics;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.VTextField;
import com.vaadin.shared.ui.Connect;

@Connect(FieldArithmetics.class)
public class FieldArithmeticsConnector extends AbstractExtensionConnector {

	private VTextField textField;
	private Logger l;
	
	/**
	 * Attach handler that replaces the TextField's event handler with our own version that intercepts on change events.
	 */
	private final AttachEvent.Handler eventListenerReplacer = new AttachEvent.Handler() {
		

		@Override
		public void onAttachOrDetach(AttachEvent event) {
			
			Element tfElement = textField.getElement();
			EventListener originalEventListener = DOM.getEventListener(tfElement);
			// if (originalEventListener instanceof EvenInterceptor) {
			// do not re-add the event listener
			// return;
			// }
			l.warning("tf attached:" + textField.isAttached());
			l.warning("originalEventListener:" + originalEventListener);
			
			EvenInterceptor evenInterceptor = new EvenInterceptor(originalEventListener, overridingChangeHandler);
			DOM.setEventListener(tfElement, evenInterceptor);
			
		}
	};
	/**
	 * EventListener that evaluates the inputed values
	 */
	private final EventListener overridingChangeHandler = new EventListener() {

		@Override
		public void onBrowserEvent(Event event) {
			String originalValue = textField.getText();
			l.warning("evaluating" + originalValue);
			String evaluatedInput = evaluateInput(originalValue);
			l.warning("originalValue: " + originalValue + " evaluatedInput: "
					+ evaluatedInput);
			textField.setText(evaluatedInput);
		}
	};

	public FieldArithmeticsConnector() {
		l = Logger.getLogger("FieldArithmeticsConnector");
	}

	native protected String evaluateInput(String str) /*-{
	    var re1 = new RegExp(",", "g");
	    str = str.replace(re1,".");
	    var re2 = new RegExp("[^\\d\\+\\*\\.\\-/]", "g");
	    str = str.replace(re2, "");
	    return eval(str);
	}-*/;

	@Override
	protected void extend(ServerConnector target) {
		textField = (VTextField) ((ComponentConnector) target).getWidget();
		textField.addAttachHandler(eventListenerReplacer);

	}
		

	native private void consoleLog(JavaScriptObject o) /*-{
		console.log(o);
	}-*/;

}
