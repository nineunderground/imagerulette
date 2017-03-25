/**
 * 
 */
package org.inakirj.imagerulette.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import javax.servlet.http.Cookie;

import com.vaadin.server.VaadinService;

/**
 * @author inaki
 *
 */
public class SettingsManager {

    private static final String COOKIE_ID = "DICE_RULETTE_COOKIE";
    private static final String COOKIE_VALUE_SEPARATOR = " ";
    private Cookie appCookie;

    /**
     * Instantiates a new cookie manager.
     */
    public SettingsManager() {
	matchCookie();
    }

    /**
     * Match cookie or create a new one.
     */
    private void matchCookie() {
	Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
	Cookie newCookie = new Cookie(COOKIE_ID, "");
	appCookie = Stream.of(cookies).filter(c -> COOKIE_ID.equals(c.getName())).findFirst().orElse(newCookie);
	appCookie.setPath("/");
    }

    /**
     * Gets the all URLs.
     *
     * @return the all URLs
     */
    public List<String> getAllURLs() {
	List<String> result = new ArrayList<>();
	StringTokenizer tokenizer = new StringTokenizer(appCookie.getValue(), COOKIE_VALUE_SEPARATOR);
	while (tokenizer.hasMoreTokens()) {
	    result.add(tokenizer.nextToken());
	}
	return result;
    }

    /**
     * Save all URLs.
     *
     * @param urlList
     *            the url list
     */
    public void saveAllURLs(List<String> urlList) {
	String cookieValue = urlList.stream().reduce("", (t, u) -> {
	    if (t.isEmpty()) {
		return u;
	    }
	    return t + COOKIE_VALUE_SEPARATOR + u;
	});
	appCookie.setValue(cookieValue);
	VaadinService.getCurrentResponse().addCookie(appCookie);
    }

    /**
     * Removes the from cookies.
     *
     * @param idToRemove
     *            the id to remove
     */
    public void removeFromProperties(String urlToRemove) {
	List<String> listCopied = getAllURLs();
	String itemToBeRemoved = listCopied.stream().filter(url -> urlToRemove.equals(url)).findFirst().orElse(null);
	listCopied.remove(itemToBeRemoved);
	saveAllURLs(listCopied);
    }

}