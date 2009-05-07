package org.dreamsoft.regexcite.client.util.regex;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * <p>
 * Implementation of the
 * {@link org.dreamsoft.regexcite.client.util.regex.Pattern} class with a
 * wrapper aroung the Javascript <a href="http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Guide:Regular_Expressions"
 * >RegExp</a> object. As most of the methods delegate to the JavaScript RegExp
 * object, certain differences in the declaration and behaviour of regular
 * expressions must be expected.
 * </p>
 * <p>
 * Please note that neither the
 * {@link org.dreamsoft.regexcite.client.util.regex.Pattern#compile(String)}
 * method nor instances are supported. For the later, consider using
 * {@link Pattern#match(String)}.
 * </p>
 * 
 * @author George Georgovassilis
 * 
 */
public class Pattern {

	/**
	 * m Treat the string as multiple lines.
	 */
	public final static int MULTILINE = 1;

	/**
	 * i Do case-insensitive pattern matching.
	 */
	public final static int CASE_INSENSITIVE = 2;

	/**
	 * g Do global pattern matching.
	 */
	public final static int GLOBAL = 4;

	/**
	 * s Treat the string as a single line.
	 */
	public final static int SINGLE_LINE = 8;

	/**
	 * x Ignore whitespace within a pattern.
	 */
	public final static int IGNORE_WHITESPACE = 16;

	@SuppressWarnings("unused")
	private JavaScriptObject regExp;

	private static JavaScriptObject createExpression(String pattern, int flags) {
		String sFlags = "";
		if ((flags & MULTILINE) != 0)
			sFlags += "m";
		if ((flags & CASE_INSENSITIVE) != 0)
			sFlags += "i";
		if ((flags & GLOBAL) != 0)
			sFlags += "g";
		if ((flags & SINGLE_LINE) != 0)
			sFlags += "s";
		if ((flags & IGNORE_WHITESPACE) != 0)
			sFlags += "x";
		return _createExpression(pattern, sFlags);
	}

	private static native JavaScriptObject _createExpression(String pattern, String flags)/*-{
		return new RegExp(pattern, flags);
	}-*/;

	private native void _match(String text, List<?> matches)/*-{
		var result = text.match(this.@org.dreamsoft.regexcite.client.util.regex.Pattern::regExp);
		if (result == null) return;
		for (var i=0;i<result.length;i++)
			matches.@java.util.ArrayList::add(Ljava/lang/Object;)(result[i]);
	}-*/;

	public native int getLastIndex()/*-{
		var result = this.@org.dreamsoft.regexcite.client.util.regex.Pattern::regExp.lastIndex;
		return (typeof result!='undefined')?result:-1;
	}-*/;

	public native static String getLastMatch()/*-{
		var result = RegExp.lastMatch;
		return (typeof result!='undefined')?result:null;
	}-*/;

	public native static String getLeftContext()/*-{
		var result = RegExp.leftContext;
		return (typeof result!='undefined')?result:null;
	}-*/;

	public native static String getRightContext()/*-{
		var result = RegExp.rightContext;
		return (typeof result!='undefined')?result:null;
	}-*/;

	/**
	 * Escape a provided string so that it will be interpreted as a literal in
	 * regular expressions. The current implementation does escape each
	 * character even if not neccessary, generating verbose literals.
	 * 
	 * @param input
	 * @return
	 */
	public static String quote(String input) {
		String output = "";
		for (int i = 0; i < input.length(); i++) {
			output += "\\" + input.charAt(i);
		}
		return output;
	}

	/**
	 * Class constructor
	 * 
	 * @param pattern
	 *            Regular expression
	 */
	public Pattern(String pattern) {
		this(pattern, 0);
	}

	/**
	 * Class constructor
	 * 
	 * @param pattern
	 *            Regular expression
	 * @param flags
	 */
	public Pattern(String pattern, int flags) {
		regExp = createExpression(pattern, flags);
	}

	/**
	 * This method is borrowed from the JavaScript RegExp object. It parses a
	 * string and returns as an array any assignments to parenthesis groups in
	 * the pattern's regular expression
	 * 
	 * @param text
	 * @return Array of strings following java's Pattern convention for groups:
	 *         Group 0 is the entire input string and the remaining groups are
	 *         the matched parenthesis. In case nothing was matched an empty
	 *         array is returned.
	 */
	public String[] match(String text) {
		List<?> matches = new ArrayList<Object>();
		_match(text, matches);
		String arr[] = new String[matches.size()];
		for (int i = 0; i < matches.size(); i++)
			arr[i] = matches.get(i).toString();
		return arr;
	}

	/**
	 * Determines wether a provided text matches the regular expression
	 * 
	 * @param text
	 * @return
	 */
	public native boolean test(String text)/*-{
		return this.@org.dreamsoft.regexcite.client.util.regex.Pattern::regExp.test(text);
	}-*/;

	/**
	 * Returns the regular expression for this pattern
	 * 
	 * @return
	 */
	public native String getSource()/*-{
		var result = this.@org.dreamsoft.regexcite.client.util.regex.Pattern::regExp.source;
		return (typeof result!='undefined')?result:null;
	}-*/;

	private native void _split(String input, List<?> results)/*-{
		var parts = input.split(this.@org.dreamsoft.regexcite.client.util.regex.Pattern::regExp);
		for (var i=0;i<parts.length;i++)
		results.@java.util.ArrayList::add(Ljava/lang/Object;)(parts[i]  );
	}-*/;

	/**
	 * Split an input string by the pattern's regular expression
	 * 
	 * @param input
	 * @return Array of strings
	 */
	public String[] split(String input) {
		List<?> results = new ArrayList<Object>();
		_split(input, results);
		String[] parts = new String[results.size()];
		for (int i = 0; i < results.size(); i++)
			parts[i] = (String) results.get(i);
		return parts;
	}

}