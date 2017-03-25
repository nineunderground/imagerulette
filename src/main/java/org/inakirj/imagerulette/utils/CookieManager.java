/**
 * 
 */
package org.inakirj.imagerulette.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;

import com.vaadin.server.VaadinService;

/**
 * @author inaki
 *
 */
public class CookieManager {

    private static final String COOKIE_ID = "DICE_RULETTE_COOKIE";
    private static final String COOKIE_VALUE_SEPARATOR = " ";
    private Cookie appCookie;

    /**
     * Instantiates a new cookie manager.
     */
    public CookieManager() {
    }

    /**
     * Match cookie or create a new one.
     */
    private void matchCookie() {
	Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
	if (cookies == null) {
	    appCookie = createCookie();
	} else {
	    for (Cookie cookie : cookies) {
		if (COOKIE_ID.equals(cookie.getName())) {
		    appCookie = cookie;
		    break;
		}
	    }
	    if (appCookie == null) {
		appCookie = createCookie();
	    }
	}
    }

    /**
     * Creates the cookie.
     *
     * @return the cookie
     */
    private Cookie createCookie() {
	Cookie newCookie = new Cookie(COOKIE_ID, "");
	newCookie.setComment("Saving urls for dice rulette");
	newCookie.setPath("/");
	newCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
	VaadinService.getCurrentResponse().addCookie(newCookie);
	return newCookie;
    }

    /**
     * Gets the all URLs.
     *
     * @return the all URLs
     */
    public List<String> getAllURLs() {
	matchCookie();
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
	matchCookie();
	String cookieValue = urlList.stream().reduce("", (t, u) -> {
	    if (t.isEmpty()) {
		return u;
	    }
	    return t + COOKIE_VALUE_SEPARATOR + u;
	});
	// This is needed, because when getting the cookie from browser those
	// attributes are not set
	appCookie.setValue(cookieValue);
	appCookie.setComment("Saving urls for dice rulette");
	appCookie.setPath("/");
	appCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
	VaadinService.getCurrentResponse().addCookie(appCookie);
    }

    /**
     * Removes the from cookies.
     *
     * @param idToRemove
     *            the id to remove
     */
    public void removeFromProperties(String urlToRemove) {
	matchCookie();
	List<String> listCopied = getAllURLs();
	String itemToBeRemoved = listCopied.stream().filter(url -> urlToRemove.equals(url)).findFirst().orElse(null);
	listCopied.remove(itemToBeRemoved);
	saveAllURLs(listCopied);
    }

}