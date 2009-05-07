package org.dreamsoft.regexcite.client;

import org.dreamsoft.regexcite.client.ui.AnalysePanel;
import org.dreamsoft.regexcite.client.ui.FramePanel;
import org.dreamsoft.regexcite.client.ui.ReplacePanel;
import org.dreamsoft.regexcite.client.ui.SearchPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RegExcite implements EntryPoint {

	SearchPanel searchPanel = new SearchPanel();

	ReplacePanel replacePanel = new ReplacePanel();

	AnalysePanel analysePanel = new AnalysePanel();

	FramePanel regexpressoPanel = new FramePanel("regexpresso", "regexpresso/regexpresso.html");

	FramePanel helpPanel = new FramePanel("doc", "doc/index.html");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.add(searchPanel, "Search");
		tabPanel.add(replacePanel, "Replace");
		tabPanel.add(analysePanel, "Analyse");
		tabPanel.add(regexpressoPanel, "Regexpresso");
		tabPanel.add(helpPanel, "Help");
		tabPanel.selectTab(1);
		tabPanel.setAnimationEnabled(true);
		tabPanel.setHeight("100%");
		tabPanel.setWidth("100%");
		tabPanel.getDeckPanel().setSize("100%", "100%");
		// Lazy load for big panels!
		tabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
				int tabIndex = event.getItem();
				switch (tabIndex) {
				case 0:
					searchPanel.showPanel();
					break;
				case 1:
					break;
				case 2:
					analysePanel.showPanel();
					break;
				default:
					break;
				}
			}
		});

		RootPanel.get("content").add(tabPanel);

		// Test value
		analysePanel.initTest();
		searchPanel.initTest();
		replacePanel.initTest();

	}

}
