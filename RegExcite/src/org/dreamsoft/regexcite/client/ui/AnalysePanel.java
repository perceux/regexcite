package org.dreamsoft.regexcite.client.ui;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AnalysePanel extends VerticalPanel {
	public final TextBox regexpTextBox = new TextBox();

	final Tree regexpTree = new Tree();

	final HTML nodeInfoHtml = new HTML();

	final TreeItem root = regexpTree.addItem(regexpTextBox.getText());

	final Command executeCommand = new Command() {
		public void execute() {
			root.removeItems();
			try {
				parse(root, regexpTextBox.getText());
			} catch (Exception e) {
			}
			root.setState(true, false);
			regexpTree.addItem(root.getChild(0));
			if (regexpTree.getItemCount() > 1)
				regexpTree.removeItem(regexpTree.getItem(0));
			if (regexpTree.getItemCount() > 0) {
				detailItem(regexpTree.getItem(0));
			}
		}
	};

	private boolean initialized;

	private void detailItem(TreeItem item) {
		NodeInfo ni = (NodeInfo) item.getUserObject();
		if (ni != null) {
			try {
				// Decoupage pour placer un span!
				String resultString = regexpTextBox.getText();
				String s1 = resultString.substring(0, ni.startIndex);
				String s2 = resultString.substring(ni.startIndex, ni.endIndex);
				String s3 = resultString.substring(ni.endIndex, resultString.length());
				// TODO Utiliser DOM.createElement(...) ??
				resultString = s1 + "<span>" + s2 + "</span>" + s3;
				resultString += "<hr>" + ni.toString();
				nodeInfoHtml.setHTML("<pre>" + resultString + "</pre>");

				// FIXME ca marche pas!!
				regexpTextBox.setFocus(true);
				regexpTextBox.setSelectionRange(ni.startIndex, ni.endIndex - ni.startIndex);

			} catch (Exception e) {
				Window.alert(e.getMessage());
			}
		}
	}

	public AnalysePanel() {
		super();
	}

	public void showPanel() {
		if (!initialized) {
			initialized = true;
			regexpTextBox.setWidth("100%");
			regexpTree.setAnimationEnabled(true);
			regexpTree.addStyleName("smallcar");
			regexpTree.setSize("100%", "100%");
			nodeInfoHtml.addStyleName("result");

			SimplePanel spanel = new SimplePanel();
			spanel.setWidget(nodeInfoHtml);
			spanel.setWidth("100%");

			SimplePanel spanel2 = new SimplePanel();
			spanel2.setWidget(regexpTree);
			spanel2.setWidth("100%");

			KeyboardListenerAdapter CRListener = new CarriageReturnListenerAdapter(executeCommand);
			regexpTextBox.addKeyboardListener(CRListener);

			HorizontalSplitPanel hspanel = new HorizontalSplitPanel();
			hspanel.setRightWidget(spanel);
			hspanel.setLeftWidget(spanel2);

			regexpTree.addTreeListener(new TreeListener() {
				public void onTreeItemSelected(TreeItem item) {
					detailItem(item);
				}

				public void onTreeItemStateChanged(TreeItem item) {
				}
			});

			add(regexpTextBox);
			setCellVerticalAlignment(regexpTextBox, ALIGN_TOP);
			add(hspanel);
			setCellVerticalAlignment(hspanel, ALIGN_TOP);
			setCellHeight(hspanel, "100%");
			setSize("100%", "100%");

			hspanel.setSplitPosition("33%");
		}
		executeCommand.execute();
	}

	public static class NodeInfo {
		int startIndex;

		int endIndex;

		String name;

		boolean conbineable;

		NodeInfo(int startIndex, int endIndex, String name, boolean conbineable) {
			this.conbineable = conbineable;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.name = name;
		}

		@Override
		public String toString() {
			String result = "";
			result += "<br>startIndex = " + this.startIndex;
			result += "<br>endIndex = " + this.endIndex;
			result += "<br>name = " + this.name;
			result += "<br>conbineable = " + this.conbineable;
			return result;
		}
	}

	public static NodeInfo createNodeInfo(int startIndex, int endIndex, String name, boolean conbineable) {
		return new NodeInfo(startIndex, endIndex, name, conbineable);
	}

	public native TreeItem parse(TreeItem treeItem, String re)/*-{
				var buildTree = function(node, parentNode) {
					if(node != null){
						var nodeName = ((node.value==null)?node.nodeName:node.value);
						var tmpNode = parentNode.@com.google.gwt.user.client.ui.TreeItem::addItem(Ljava/lang/String;)(nodeName);
						for(var i in node.children){
							buildTree(node.children[i], tmpNode);
						}
						tmpNode.@com.google.gwt.user.client.ui.TreeItem::setState(Z)(true);
						if (undefined != node.startIndex) {
							var tmpObj = @org.dreamsoft.regexcite.client.ui.AnalysePanel::createNodeInfo(IILjava/lang/String;Z)(node.startIndex,node.endIndex,node.nodeName,node.combineable);
							tmpNode.@com.google.gwt.user.client.ui.TreeItem::setUserObject(Ljava/lang/Object;)(tmpObj);
						}
					}
				}
				var state = $wnd.PatternJS.parse(re);
				var v = state.getAST();
				buildTree(v, treeItem);

				return treeItem;
				}-*/;

	public void initTest() {
		regexpTextBox.setText("([ae]+)|([lc]+)|\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
		executeCommand.execute();
	}

}
