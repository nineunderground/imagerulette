/**
 * 
 */
package org.inakirj.ImageRulette.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author inaki
 *
 */
@Entity
@Table(schema = "dice_rulette", name = "Picture")
public class Picture {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "url")
    private String url;

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
