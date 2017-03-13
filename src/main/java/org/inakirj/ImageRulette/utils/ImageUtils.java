/**
 * 
 */
package org.inakirj.ImageRulette.utils;

import java.io.File;

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

}
