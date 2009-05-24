package org.dreamsoft.regexcite.client.ui;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Command;

public class EnterPressHandler implements KeyPressHandler {

	private Command command;

	public EnterPressHandler(Command command) {
		this.command = command;
	}

	public void onKeyPress(KeyPressEvent event) {
		if (event.getCharCode() == KeyCodes.KEY_ENTER) {
			onEnterPress(event);
		}
	}

	protected void onEnterPress(KeyPressEvent event) {
		if (command != null) {
			command.execute();
		}
	}
}
