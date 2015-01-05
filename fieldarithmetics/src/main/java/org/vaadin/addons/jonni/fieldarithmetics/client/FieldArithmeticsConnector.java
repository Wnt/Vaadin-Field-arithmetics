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
	private Logger l = Logger.getLogger("FieldArithmeticsConnector");

	/**
	 * Attach handler that replaces the TextField's event handler with our own
	 * version that intercepts on change events.
	 */
	private final AttachEvent.Handler eventListenerReplacer = new AttachEvent.Handler() {

		@Override
		public void onAttachOrDetach(AttachEvent event) {

			Element tfElement = textField.getElement();
			EventListener originalEventListener = DOM
					.getEventListener(tfElement);
			if (originalEventListener instanceof EvenInterceptor) {
				// do not re-add the event listener
				return;
			}
			l.warning("tf attached:" + textField.isAttached());
			l.warning("originalEventListener:" + originalEventListener);

			EvenInterceptor evenInterceptor = new EvenInterceptor(
					originalEventListener, overridingChangeHandler);
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

	native static public String evaluateInput(String inputStr) /*-{
		var str = inputStr;
		var re1 = new RegExp(",", "g");
		// if there are periods in the input string, remove commas (they are probably thousand separators)
		if (str.indexOf(".") != -1) {
			str = str.replace(re1, "");
		}
		else {
			// no periods in input, replace commas with periods (they are probably decimal separators)
			str = str.replace(re1, ".");
		}
		
		// remove everything except numbers, periods, operators (+, *, -, /, ^) and parenthesis
		var re2 = new RegExp("[^\\d\\.\\+\\*\\-/\\^\\(\\)]", "g");
		str = str.replace(re2, "");
		
		// replace x^y expressions with Math.pow(x, y)
		var re3 = new RegExp("(\\d+)\\^(\\d+)", "g");
		str = str.replace(re3, "Math.pow($1, $2)");
		
		try {
			str = eval(str);
		}
		catch (e) {
		}
		return str;
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
