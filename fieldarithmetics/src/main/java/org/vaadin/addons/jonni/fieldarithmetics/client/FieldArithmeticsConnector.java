package org.vaadin.addons.jonni.fieldarithmetics.client;

import java.util.logging.Logger;

import org.vaadin.addons.jonni.fieldarithmetics.FieldArithmetics;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.DOM;
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

	public FieldArithmeticsConnector() {
		l = Logger.getLogger("FieldArithmeticsConnector");
	}
	@Override
	protected void extend(ServerConnector target) {
		target.addStateChangeHandler(new StateChangeEvent.StateChangeHandler() {

			/**
			 * Called when field value has been changed from the server-side
			 */
			@Override
			public void onStateChanged(StateChangeEvent stateChangeEvent) {
				l.warning("onStateChanged");
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						l.warning("onStateChanged ScheduledCommand");
					}
				});
			}
		});
		textField = (VTextField) ((ComponentConnector) target).getWidget();
		textField.addAttachHandler(new AttachEvent.Handler() {
			
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				l.warning("onAttachOrDetach");
				
			}
		});
		textField.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				l.warning("onBlur");
				textField.setValue(textField.getValue() + "test");
			}
		});

	}

}
