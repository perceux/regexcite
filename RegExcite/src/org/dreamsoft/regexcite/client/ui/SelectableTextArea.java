package org.dreamsoft.regexcite.client.ui;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RichTextArea;

public class SelectableTextArea extends RichTextArea {
	Element elem;

	public void setInnerHTML(String html) {
		elem = getElement();
		_setInnerHTML(html);
	}
	
	native void _setInnerHTML(String html) /*-{
    	var elem = this.@org.dreamsoft.regexcite.client.ui.SelectableTextArea::elem;
	    var w = elem.contentWindow;
	    var d = w.document;
	    d.body.innerHTML = html;
	}-*/;
	
	public void moveSelectionRange(int start, int end) {
		elem = getElement();
		_moveSelectionRange(start, end);
	}

	// FIXME buggy method, is there no way to select a part of a text with this fucking firefox?
	native void _moveSelectionRange(int startRelativeIndex, int endRelativeIndex) /*-{
	    	var elem = this.@org.dreamsoft.regexcite.client.ui.SelectableTextArea::elem;
	    	var w = elem.contentWindow;
	    	var d = w.document;
	    	if (typeof w.getSelection != 'undefined') {
  				var selectio = w.getSelection();
   				selectio.removeAllRanges();
      			var range = d.createRange();
      			var base = d.body;
      			range.selectNode(base);
//      			alert(base.selectionStart + "," + base.selectionEnd);
      			selectio.addRange(range);
  			}
  			else if (typeof d.selection != 'undefined') {
  				d.selection.empty();
	    		var textRange = d.selection.createRange();
    			textRange.moveStart('character', startRelativeIndex);
    			textRange.moveEnd('character', endRelativeIndex);
				textRange.select();
			}
	  }-*/;

}
