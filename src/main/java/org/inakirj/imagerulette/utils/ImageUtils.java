package org.inakirj.imagerulette.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.common.net.MediaType;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Image;

/**
 * @author inaki
 *
 */
public final class ImageUtils {

    /**
     * Gets the image URL.
     *
     * @return the image URL
     */
    public static List<Image> getAllImageURL() {
	List<Image> result = new ArrayList<>();
	CookieManager sm = new CookieManager();
	Image img;
	for (String url : sm.getAllURLs()) {
	    ExternalResource resource = new ExternalResource(url);
	    img = new Image("", resource);
	    img.setData(url);
	    result.add(img);
	}
	return result;
    }

    /**
     * Gets the image URL.
     *
     * @param id
     *            the id
     * @return the image URL
     */
    public static Image getImageURL(String url) {
	return getAllImageURL().stream().filter(i -> ((String) i.getData()) == url).findFirst().orElse(null);
    }

    /**
     * Validate HTTP URI.
     *
     * @param uri
     *            the URI
     * @return true, if given URI is an static image
     */
    public static boolean isValidImageURI(String uri) {
	HttpURLConnection con;
	try {
	    URL obj = new URL(uri);
	    con = (HttpURLConnection) obj.openConnection();
	    String contentType = con.getContentType();
	    MediaType mt = MediaType.parse(contentType);
	    return mt.is(MediaType.ANY_IMAGE_TYPE);
	} catch (Exception e) {
	    return false;
	}
    }

}
