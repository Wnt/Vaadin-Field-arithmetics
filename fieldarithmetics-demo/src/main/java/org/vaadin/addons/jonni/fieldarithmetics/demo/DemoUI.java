package org.vaadin.addons.jonni.fieldarithmetics.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.jonni.fieldarithmetics.FieldArithmetics;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("FieldArithmetics Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.addons.jonni.fieldarithmetics.demo.DemoWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		final TextField field = new TextField();
		final FieldArithmetics fieldArithmetics = new FieldArithmetics();
		fieldArithmetics.extend(field);

		field.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification.show(
						"Field value changed to: '" + field.getValue() + "'",
						Notification.Type.TRAY_NOTIFICATION);

			}
		});

		// Show it in the middle of the screen
		final VerticalLayout layout = new VerticalLayout();
		layout.setStyleName("demoContentLayout");
		layout.setSizeFull();
		layout.addComponent(field);
		layout.setComponentAlignment(field, Alignment.MIDDLE_CENTER);
		setContent(layout);

	}

}
