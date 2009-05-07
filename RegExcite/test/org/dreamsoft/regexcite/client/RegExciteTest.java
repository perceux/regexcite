package org.dreamsoft.regexcite.client;

import java.util.ArrayList;

import org.dreamsoft.regexcite.client.util.regex.Pattern;

import com.google.gwt.junit.client.GWTTestCase;

public class RegExciteTest extends GWTTestCase {
	@Override
	public String getModuleName() {
		return "org.dreamsoft.regexcite.RegExcite";
	}

	public void testPattern() throws Exception {
		String regex = "(voi(...)e|miam|co(.)on)";
		String input = "la voiture est sur la voie du coton, mon cochon se regale: miam";

		Pattern p = new Pattern(regex, Pattern.GLOBAL);
		String[] res = p.match(input);
		assertNotNull(res);
		int lastIndex = p.getLastIndex();
		assertEquals(lastIndex, 63);
	}

	public void testMultiMatch() throws Exception {
		String regex = "ain";
		String input = "The rain in Spain stays mainly in the plain";
		ArrayList<Integer> l = new ArrayList<Integer>();
		Pattern p = new Pattern(regex, Pattern.GLOBAL);
		for (int i = 0; i < 4; i++) {
			p.test(input);
			l.add(p.getLastIndex());
		}
		assertTrue(l.toString().equals("[8, 17, 28, 43]"));
	}
}
