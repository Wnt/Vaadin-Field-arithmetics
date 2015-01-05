package org.vaadin.addons.jonni.fieldarithmetics.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.jonni.fieldarithmetics.FieldArithmetics;
import org.vaadin.risto.stepper.FloatStepper;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
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
		FieldArithmetics.extend(field);

		field.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification.show(
						"TextField value changed to: '" + field.getValue() + "'",
						Notification.Type.TRAY_NOTIFICATION);

			}
		});

		final VerticalLayout layout = new VerticalLayout();
		layout.setStyleName("demoContentLayout");
		layout.setSizeFull();
		layout.addComponent(field);
		
		final TextArea ta = new TextArea();
		FieldArithmetics.extend(ta);
		layout.addComponent(ta);

		ta.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification.show(
						"TextArea value changed to: '" + ta.getValue() + "'",
						Notification.Type.TRAY_NOTIFICATION);

			}
		});

		
		final FloatStepper stepper = new FloatStepper();
		FieldArithmetics.extend(stepper);
		layout.addComponent(stepper);

		stepper.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification.show(
						"FloatStepper value changed to: '" + stepper.getValue() + "'",
						Notification.Type.TRAY_NOTIFICATION);

			}
		});
		
		
		setContent(layout);

	}

}
