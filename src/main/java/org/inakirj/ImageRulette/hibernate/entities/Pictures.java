/**
 * 
 */
package org.inakirj.ImageRulette.hibernate.entities;

/**
 * @author inaki
 *
 */
public class Pictures {

    private int id;
    private String url;

    /**
     * Instantiates a new pictures.
     */
    public Pictures() {
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

}
