package org.dreamsoft.regexcite.client.ui;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Widget;

public class CarriageReturnListenerAdapter extends KeyboardListenerAdapter {

	private Command command;

	public CarriageReturnListenerAdapter(Command command) {
		this.command = command;
	}

	public void onKeyPress(Widget sender, char keyCode, int modifiers) {
		if (keyCode == KEY_ENTER) {
			onCarriageReturnPress(sender, modifiers);
		}
		super.onKeyPress(sender, keyCode, modifiers);
	}

	protected void onCarriageReturnPress(Widget sender, int modifiers) {
		if (command != null) {
			command.execute();
		}
	}
}
