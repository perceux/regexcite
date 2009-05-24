package org.dreamsoft.regexcite.client.ui;

import org.dreamsoft.regexcite.client.util.regex.Pattern;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * Create the search panel
 */
public class ReplacePanel extends DockPanel {

	private ResultPanel errorPanel = new ResultPanel();

	final SelectableTextArea textArea = new SelectableTextArea();

	final TextBox regexpTextBox = new TextBox();

	final TextBox replaceTextBox = new TextBox();

	final ToggleButton autoExec = new ToggleButton("Execute");

	final ToggleButton showSplit = new ToggleButton("Split");

	final ToggleButton showHighlight = new ToggleButton("Highlight");

	final ToggleButton showFirstMatch = new ToggleButton("Match");

	public ReplacePanel() {

		final Command executeCommand = new Command() {

			public void execute() {
				try {

					String resultString = getInputText();
					if (showHighlight.isDown()) {
						// FIXME CSS ??
						String startHTML = "<span style='background-color: #DBFF00;'>";
						String endHTML = "</span>";
						String bound = "";
						for (int i = 0; i < 15; i++) {
							Integer.toHexString(Random.nextInt(255));
						}
						String start = "s" + bound + "s";
						String end = "e" + bound + "e";
						resultString = resultString.replaceAll(regexpTextBox.getText(), start + replaceTextBox.getText() + end);
						resultString = escapeHTML(resultString);
						resultString = resultString.replaceAll(start, startHTML);
						resultString = resultString.replaceAll(end, endHTML);
						textArea.setInnerHTML(resultString.replaceAll("\n", "<br>"));
					} else {
						//
					}

					resultString = "";

					if (showFirstMatch.isDown()) {
						resultString += "<hr>";
						Pattern p = new Pattern(regexpTextBox.getText(), Pattern.MULTILINE | Pattern.GLOBAL);
						String result[] = p.match(getInputText());
						for (int i = 0; i < result.length; i++) {
							String string = result[i];
							resultString += "<br> match(" + i + ") = " + string;
						}
					}

					if (showSplit.isDown()) {
						resultString += "<hr>";
						Pattern p = new Pattern(regexpTextBox.getText(), Pattern.MULTILINE);
						String result[] = p.split(getInputText());
						for (int i = 0; i < result.length; i++) {
							String string = result[i];
							resultString += "<br> split(" + i + ") = " + string;
						}
					}

					errorPanel.setHTML(resultString);

				} catch (Exception e) {
					errorPanel.setError(e.getLocalizedMessage());
				}
			}
		};

		EnterPressHandler enterPressHandler = new EnterPressHandler(executeCommand);
		regexpTextBox.addKeyPressHandler(enterPressHandler);
		replaceTextBox.addKeyPressHandler(enterPressHandler);

		regexpTextBox.setWidth("100%");
		final Timer autoSearchTimer = new Timer() {
			public void run() {
				if (autoExec.isDown()) {
					executeCommand.execute();
				}
			}
		};

		autoExec.setDown(true);
		showHighlight.setDown(true);
		regexpTextBox.setWidth("100%");
		textArea.setSize("100%", "100%");
		textArea.addStyleName("result");

		HorizontalPanel hpanel = new HorizontalPanel();
		hpanel.setSpacing(2);
		hpanel.add(regexpTextBox);
		hpanel.add(replaceTextBox);
		hpanel.add(autoExec);
		hpanel.add(showHighlight);
		hpanel.add(showFirstMatch);
		hpanel.add(showSplit);
		hpanel.setSize("100%", "100%");

		add(hpanel, DockPanel.NORTH);
		setCellHeight(hpanel, "1%");
		add(textArea, DockPanel.CENTER);
		setCellHeight(textArea, "98%");
		add(errorPanel, DockPanel.SOUTH);
		setCellHeight(errorPanel, "1%");
		setSize("100%", "100%");

		autoSearchTimer.scheduleRepeating(1000);
	}

	protected String escapeHTML(String text) {
		String result = text;
		String replace[][] = { { "\\&", "&amp;" }, { "\\\"", "&quot;" }, { "\\ ", "&nbsp;" }, { "\\<", "&lt;" }, { "\\>", "&gt;" }, { "\\'", "&#39;" } };
		for (int i = 0; i < replace.length; i++) {
			String[] repl = replace[i];
			result = result.replaceAll(repl[0], repl[1]);
		}
		return result;
	}

	protected String getInputText() {
		return textArea.getText();
	}

	public void initTest() {
		regexpTextBox.setText("([ae]+)|([lc]+)|\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
		replaceTextBox.setText("$&");
		textArea.setText("One of the welcome developments in GWT 1.5 was the inclusion of some decent looking CSS themes -- standard. chrome and dark."
				+ "How can you use them in your GWT app? Just include one of the following lines in your apps .xml file:");
		autoExec.setDown(false);
	}

}
