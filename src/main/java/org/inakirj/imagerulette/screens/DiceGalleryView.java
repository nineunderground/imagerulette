/**
 * 
 */
package org.inakirj.imagerulette.screens;

import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.inakirj.imagerulette.MyUI;
import org.inakirj.imagerulette.utils.ImageUtils;
import org.inakirj.imagerulette.utils.CookieManager;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author inaki
 *
 */
public class DiceGalleryView extends CssLayout {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3339347895009704574L;
    private static final int TOTAL_URL_LIMIT = 30;
    private BeanItemContainer<DiceItem> newDataSource = new BeanItemContainer<>(DiceItem.class);;
    private final Object[] propertyIds = { "id" };
    private final boolean[] ascendingIds = { true };
    public boolean hasReachLimitImages = newDataSource.size() == TOTAL_URL_LIMIT;

    /**
     * Instantiates a new gallery.
     */
    public DiceGalleryView() {
	CookieManager cookieMng = new CookieManager();
	setLayout(cookieMng.getAllURLs());
    }

    /**
     * Sets the layout.
     *
     * @param urlList
     *            the new layout
     */
    private void setLayout(List<String> urlList) {
	removeAllComponents();
	VerticalLayout layout = new VerticalLayout();
	layout.setWidth(100, Unit.PERCENTAGE);
	// Table
	Table urlTable = new Table();
	for (String url : urlList) {
	    addRow(url);
	}
	newDataSource.sort(propertyIds, ascendingIds);
	urlTable.setContainerDataSource(newDataSource);
	urlTable.setVisibleColumns("url", "img", "validateImg", "deleteImg");
	urlTable.setColumnHeaders("PATH", "IMAGE", "SAVE", "DELETE");
	urlTable.setColumnExpandRatio("url", 5);
	urlTable.setColumnExpandRatio("img", 3);
	urlTable.setColumnExpandRatio("validateImg", 1);
	urlTable.setColumnExpandRatio("deleteImg", 1);
	urlTable.setWidth(100, Unit.PERCENTAGE);
	layout.addComponent(urlTable);
	addComponent(layout);
    }

    /**
     * Adds the row.
     */
    public void addRow(String urlEntry) {
	DiceItem itemCreated = new DiceItem();
	Button validateButton = new Button();
	validateButton.setIcon(FontAwesome.ARROW_DOWN);
	validateButton.addClickListener(e -> validateUrl(itemCreated));
	itemCreated.setValidateImg(validateButton);
	Image imgRow = null;
	String url = null;
	if (urlEntry != null) {
	    StringTokenizer urlEntryTokenizer = new StringTokenizer(urlEntry, " ");
	    url = urlEntryTokenizer.nextToken();
	    ExternalResource resource = new ExternalResource(url);
	    imgRow = new Image("", resource);
	    itemCreated.setId(getNextId());
	} else {
	    imgRow = new Image("", null);
	}
	imgRow.addStyleName("dice-image");
	itemCreated.setImg(imgRow);
	TextField textRow = new TextField();
	if (url != null) {
	    textRow.setValue(url);
	}
	itemCreated.setUrl(textRow);
	itemCreated.setValid(imgRow != null);

	Button deleteButton = new Button();
	deleteButton.setIcon(FontAwesome.REMOVE);
	itemCreated.setDeleteImg(deleteButton);
	deleteButton.addClickListener(e -> deleteUrl(itemCreated));

	newDataSource.addBean(itemCreated);
	hasReachLimitImages = newDataSource.size() == TOTAL_URL_LIMIT;
    }

    /**
     * Delete url.
     *
     * @param itemCreated
     *            the url to be removed
     */
    private void deleteUrl(DiceItem itemToBeRemoved) {
	newDataSource.removeItem(itemToBeRemoved);
	String url = itemToBeRemoved.getUrl().getValue();
	if (ImageUtils.isValidImageURI(url)) {
	    CookieManager cm = new CookieManager();
	    cm.removeFromProperties(url);
	}
	refreshIDs();
	if (hasReachLimitImages) {
	    MyUI ui = (MyUI) UI.getCurrent();
	    Button addButton = (Button) ui.tabContent1.getRightComponent();
	    addButton.setEnabled(true);
	}
    }

    /**
     * Gets the next id.
     *
     * @return the next id
     */
    private long getNextId() {
	return newDataSource.size();
    }

    /**
     * Refresh IDs.
     */
    private void refreshIDs() {
	long id = 0;
	for (DiceItem item : newDataSource.getItemIds()) {
	    item.setId(id++);
	}
    }

    /**
     * Validate url.
     *
     * @param itemCreated
     *            the item created
     */
    private void validateUrl(DiceItem item) {
	boolean isValid = false;
	String urlInput = item.getUrl().getValue();
	if (urlInput != null && ImageUtils.isValidImageURI(urlInput)) {
	    Image imgRow = null;
	    if (urlInput != null) {
		ExternalResource resource = new ExternalResource(urlInput);
		imgRow = new Image("", resource);
		imgRow.addStyleName("dice-image");
		isValid = true;
		CookieManager cm = new CookieManager();
		cm.saveAllURLs(newDataSource.getItemIds().stream().map(di -> di.getUrl().getValue())
			.collect(Collectors.toList()));
	    }
	    item.setImg(imgRow);
	}
	item.setValid(isValid);
	newDataSource.sort(propertyIds, ascendingIds);
    }

    /**
     * 
     * @author inaki
     *
     */
    public class DiceItem {

	private long id;
	private TextField url;
	private Image img;
	private Button validateImg;
	private Button deleteImg;
	private boolean isValid;

	public long getId() {
	    return id;
	}

	public void setId(long id) {
	    this.id = id;
	}

	public TextField getUrl() {
	    return url;
	}

	public void setUrl(TextField url) {
	    this.url = url;
	}

	public Image getImg() {
	    return img;
	}

	public void setImg(Image img) {
	    this.img = img;
	}

	public Button getValidateImg() {
	    return validateImg;
	}

	public void setValidateImg(Button validateImg) {
	    this.validateImg = validateImg;
	}

	public Button getDeleteImg() {
	    return deleteImg;
	}

	public void setDeleteImg(Button deleteImg) {
	    this.deleteImg = deleteImg;
	}

	public boolean isValid() {
	    return isValid;
	}

	public void setValid(boolean isValid) {
	    this.isValid = isValid;
	}

    }

}
