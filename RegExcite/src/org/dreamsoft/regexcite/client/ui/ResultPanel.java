package org.dreamsoft.regexcite.client.ui;

import com.google.gwt.user.client.ui.HTML;

/**
 * Create a result panel
 */
public class ResultPanel extends HTML {
	public ResultPanel() {
		setStyleName("result");
		setSize("100%", "100%");
	}

	public void setHTML(String html) {
		super.setHTML("<pre>" + html + "</pre>");
	}

	public void setError(String error) {
		super.setHTML("<pre class='error'>" + error + "</pre>");
	}
}
