package org.dreamsoft.regexcite.client.ui;

import org.dreamsoft.regexcite.client.util.regex.Pattern;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

/**
 * Create the search panel
 */
public class SearchPanel extends DockPanel {

	ResultPanel resultPanel = new ResultPanel();

	final TextArea textArea = new TextArea();

	final TextBox regexpTextBox = new TextBox();

	final TextBox replaceTextBox = new TextBox();

	final ToggleButton autoExec = new ToggleButton("Execute");

	final ToggleButton showSplit = new ToggleButton("Split");

	final ToggleButton showHighlight = new ToggleButton("Highlight");

	final ToggleButton showFirstMatch = new ToggleButton("Match");

	private VerticalSplitPanel vspanel = new VerticalSplitPanel();

	private boolean initialized;

	public SearchPanel() {
		super();
		setSize("100%", "100%");
	}

	protected String getInputText() {
		return textArea.getText();
	}

	public void initTest() {
		regexpTextBox.setText("([ae]+)|([lc]+)|\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
		replaceTextBox.setText("$0");
		textArea.setText("One of the welcome developments in GWT 1.5 was the inclusion of some decent looking CSS themes -- standard. chrome and dark."
				+ "How can you use them in your GWT app? Just include one of the following lines in your apps .xml file:");
		autoExec.setDown(false);
	}

	public void showPanel() {

		if (!initialized) {
			initialized = true;
			final Command executeCommand = new Command() {
				public void execute() {
					try {
						String replaceString = replaceTextBox.getText();
						if (showHighlight.isDown()) {
							replaceString = "<span>" + replaceTextBox.getText() + "</span>";
						}
						String resultString = getInputText().replaceAll(regexpTextBox.getText(), replaceString);

						if (showFirstMatch.isDown()) {
							resultString += "<hr>";
							Pattern p = new Pattern(regexpTextBox.getText(), Pattern.GLOBAL | Pattern.MULTILINE);
							String result[] = p.match(getInputText());
							for (int i = 0; i < result.length; i++) {
								String string = result[i];
								resultString += "<br> match(" + i + ") = " + string;
							}
						}

						if (showSplit.isDown()) {
							resultString += "<hr>";
							Pattern p = new Pattern(regexpTextBox.getText(), Pattern.GLOBAL | Pattern.MULTILINE);
							String result[] = p.split(getInputText());
							for (int i = 0; i < result.length; i++) {
								String string = result[i];
								resultString += "<br> split(" + i + ") = " + string;
							}
						}
						resultPanel.setHTML(resultString);

					} catch (Exception e) {
						resultPanel.setError(e.getLocalizedMessage());
					}
				}
			};
			final Timer autoSearchTimer = new Timer() {
				public void run() {
					if (autoExec.isDown()) {
						executeCommand.execute();
					}
				}
			};

			EnterPressHandler enterPressHandler = new EnterPressHandler(executeCommand);
			regexpTextBox.addKeyPressHandler(enterPressHandler);
			replaceTextBox.addKeyPressHandler(enterPressHandler);

			regexpTextBox.setWidth("100%");

			autoExec.setDown(true);
			showHighlight.setDown(true);
			regexpTextBox.setWidth("100%");
			textArea.setSize("100%", "100%");

			HorizontalPanel hpanel = new HorizontalPanel();
			hpanel.setSpacing(2);
			hpanel.add(regexpTextBox);
			hpanel.add(replaceTextBox);
			hpanel.add(autoExec);
			hpanel.add(showHighlight);
			hpanel.add(showFirstMatch);
			hpanel.add(showSplit);
			hpanel.setSize("100%", "100%");

			vspanel.setWidth("100%");
			vspanel.setHeight("100%");
			vspanel.setTopWidget(textArea);
			vspanel.setBottomWidget(resultPanel);

			add(hpanel, DockPanel.NORTH);
			add(vspanel, DockPanel.CENTER);
			setCellHeight(vspanel, "100%");

			vspanel.setSplitPosition("40%");

			autoSearchTimer.scheduleRepeating(1000);
		}

		// FIXME Why the split position change when help tab is selected?
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				vspanel.setSplitPosition("50%");
			}
		});

	}

}
