package org.vaadin.addons.jonni.fieldarithmetics;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.AbstractTextField;

public class FieldArithmetics extends AbstractExtension {
	private static final long serialVersionUID = 7800897191451963254L;

	public static void extend(AbstractTextField field) {
		new FieldArithmetics().extend((AbstractClientConnector) field);
	}
}