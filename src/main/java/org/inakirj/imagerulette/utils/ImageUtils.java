/**
 * 
 */
package org.inakirj.imagerulette.utils;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.common.net.MediaType;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;

/**
 * @author inaki
 *
 */
public final class ImageUtils {

    /**
     * Gets the image.
     *
     * @param id
     *            the id
     * @return the image
     */
    public static Image getImage(int id) {
	FileResource resource = new FileResource(new File(
		VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/image/" + id + ".png"));
	Image image = new Image("", resource);
	image.setData(id);
	return image;
    }

    /**
     * Gets the image.
     *
     * @param img
     *            the img
     * @return the image
     */
    public static Image getImage(Image img) {
	return getImage((int) img.getData());
    }

    /**
     * Gets the id.
     *
     * @param img
     *            the img
     * @return the id
     */
    public static int getId(Image img) {
	return (int) img.getData();
    }

    /**
     * Gets the image URL.
     *
     * @return the image URL
     */
    public static List<Image> getAllImageURL() {
	List<Image> result = new ArrayList<>();
	SettingsManager sm = new SettingsManager();
	Image img;
	for (String url : sm.getAllURLs()) {
	    ExternalResource resource = new ExternalResource(url);
	    img = new Image("", resource);
	    result.add(img);
	}
	return result;
    }

    /**
     * Gets the image URL.
     *
     * @param url
     *            the url
     * @return the image URL
     */
    public static Image getImageURL(String url) {
	ExternalResource resource = new ExternalResource(url);
	return new Image("", resource);
    }

    /**
     * Validate HTTP URI.
     *
     * @param uri
     *            the uri
     * @return true, if successful
     */
    public static boolean isValidURI(String uri) {
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
