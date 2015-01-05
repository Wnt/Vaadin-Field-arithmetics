package org.vaadin.addons.jonni.fieldarithmetics.client;

import org.vaadin.addons.jonni.fieldarithmetics.FieldArithmetics;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.VTextField;
import com.vaadin.shared.ui.Connect;

@Connect(FieldArithmetics.class)
public class FieldArithmeticsConnector extends AbstractExtensionConnector {

	private TextBoxBase textField;
//	private Logger l = Logger.getLogger("FieldArithmeticsConnector");

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
			String evaluatedInput = evaluateInput(originalValue);
			textField.setText(evaluatedInput);
		}
	};

	native static public String evaluateInput(String inputStr) /*-{
		var str = inputStr;
		var re1 = new RegExp(",", "g");
		
		// instead of relying to the presence of periods could use something
		// like http://stackoverflow.com/a/1308446 to figure out the separator
		// characters in browsers that support it
		
		// if there are periods in the input string, remove commas (they are
		// probably thousand separators)
		if (str.indexOf(".") != -1) {
			str = str.replace(re1, "");
		}
		else {
			// no periods in input, replace commas with periods (they are
			// probably decimal separators)
			str = str.replace(re1, ".");
		}
		
		// remove everything except numbers, periods, operators (+, *, -, /, ^)
		// and parenthesis
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
		Widget widget = ((ComponentConnector) target).getWidget();
		if (widget instanceof TextBoxBase) {
			textField = (TextBoxBase) widget;
		}
		else {
			textField = findTextFieldFromWidget(widget);
		}
		textField.addAttachHandler(eventListenerReplacer);

	}

	private native TextBoxBase findTextFieldFromWidget(Widget widget) /*-{
		
		return widget.@org.vaadin.risto.stepper.widgetset.client.ui.AbstractStepper::textBox;
	}-*/;

}
