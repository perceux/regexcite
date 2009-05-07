package org.dreamsoft.regexcite.client.ui;

import com.google.gwt.user.client.ui.NamedFrame;
import com.google.gwt.user.client.ui.SimplePanel;

public class FramePanel extends SimplePanel {
	public FramePanel(String id, String url) {
		super();
		NamedFrame nf = new NamedFrame(id);
		nf.setUrl(url);
		nf.setSize("100%", "100%");
		setWidget(nf);
		setSize("100%", "100%");
	}
}
