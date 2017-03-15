/**
 * 
 */
package org.inakirj.ImageRulette.screens;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.inakirj.ImageRulette.utils.ImageUtils;

import com.vaadin.data.Item;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class DicePlay.
 * 
 * @author inaki
 *
 */
public class DicePlayView extends CssLayout {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5317042828250752413L;
    private static final String imageCol = "DIE";
    private static final String oddCol = "ODDS";
    private Random randomizer = new Random(Calendar.getInstance().getTime().getTime());
    private List<Object> lotteryList;
    private Image randomImgToBeReplaced;
    private Map<Integer, Integer> statsImageIdOcurrencesMap = new HashMap<>();
    private HorizontalLayout imageLayout;
    private Table statsLayout;
    private VerticalLayout mainLayout;

    /**
     * Instantiates a new dice play.
     */
    public DicePlayView() {
	setLayout();
    }

    /**
     * @param lotteryList
     */
    public void setupLottery(List<Object> lotteryListGenerated) {
	this.lotteryList = new ArrayList<Object>(lotteryListGenerated);
	statsImageIdOcurrencesMap.clear();
	this.lotteryList.stream()
		.forEach(img -> statsImageIdOcurrencesMap.put(ImageUtils.getId((Image) img), new Integer(1)));
    }

    /**
     * Sets the layout.
     */
    private void setLayout() {
	mainLayout = new VerticalLayout();
	mainLayout.setWidth(100, Unit.PERCENTAGE);
	FileResource resource = new FileResource(
		new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/image/nope.png"));
	Image none = new Image("", resource);
	randomImgToBeReplaced = none;
	randomImgToBeReplaced.setWidth(78, Unit.PIXELS);
	randomImgToBeReplaced.setHeight(81, Unit.PIXELS);
	randomImgToBeReplaced.addStyleName("random-image");

	imageLayout = new HorizontalLayout();
	imageLayout.addComponent(randomImgToBeReplaced);
	imageLayout.setWidth(100, Unit.PERCENTAGE);
	imageLayout.setComponentAlignment(randomImgToBeReplaced, Alignment.TOP_CENTER);

	Button rollBtn = new Button();
	rollBtn.addClickListener(this::onPickABallClick);
	rollBtn.setWidth(25, Unit.PERCENTAGE);
	rollBtn.setHeight(50, Unit.PIXELS);
	rollBtn.setIcon(FontAwesome.CUBE);
	rollBtn.addStyleName("dice-button-roll");

	mainLayout.addComponent(imageLayout);
	mainLayout.addComponent(rollBtn);
	mainLayout.setComponentAlignment(rollBtn, Alignment.BOTTOM_CENTER);

	calculateStats();
	addComponent(mainLayout);
    }

    /**
     * Calculate stats.
     */
    private void calculateStats() {
	long totalImagesRendered = statsImageIdOcurrencesMap.values().stream().reduce(0, Integer::sum);
	Map<Image, Label> statsRowsMap = new HashMap<Image, Label>();
	Iterator<Entry<Integer, Integer>> iterator = statsImageIdOcurrencesMap.entrySet().iterator();
	int rowCount = 0;
	while (iterator.hasNext()) {
	    // Left fake column
	    Entry<Integer, Integer> entry = iterator.next();
	    Image img = ImageUtils.getImage(entry.getKey());
	    img.addStyleName("dice-image-stats");
	    Label label = new Label(getCalculation(entry.getValue(), totalImagesRendered));
	    statsRowsMap.put(img, label);
	    rowCount++;
	}
	Table popupContent;
	if (statsLayout == null) {
	    popupContent = new Table();
	    popupContent.setSortEnabled(false);
	    popupContent.addStyleName("stats-table");
	    popupContent.setWidth(100, Unit.PERCENTAGE);
	    popupContent.addContainerProperty(imageCol, Image.class, null);
	    popupContent.addContainerProperty(oddCol, Label.class, "0.00 %");
	} else {
	    popupContent = statsLayout;
	    popupContent.removeAllItems();
	}
	statsRowsMap.entrySet().stream().forEach(entry -> {
	    Item row1 = popupContent.getItem(popupContent.addItem());
	    row1.getItemProperty(imageCol).setValue(entry.getKey());
	    row1.getItemProperty(oddCol).setValue(entry.getValue());
	});
	popupContent.setPageLength(statsRowsMap.size());
	popupContent.sort(new String[] { oddCol }, new boolean[] { false });
	if (statsLayout == null) {
	    mainLayout.addComponent(popupContent);
	} else {
	    mainLayout.replaceComponent(statsLayout, popupContent);
	}
	statsLayout = popupContent;
    }

    /**
     * Gets the calculation.
     *
     * @param valueTopercentage
     *            the value to percentage
     * @param totalImagesRendered
     *            the total images rendered
     * @return the calculation
     */
    private String getCalculation(Integer valueToPercentage, long totalImagesRendered) {
	BigDecimal num = BigDecimal.valueOf(valueToPercentage).multiply(BigDecimal.valueOf(100));
	BigDecimal div = BigDecimal.valueOf(totalImagesRendered);
	BigDecimal division = num.divide(div, 2, RoundingMode.HALF_UP);
	BigDecimal percentage = division.setScale(2, RoundingMode.HALF_UP);
	String calculation = percentage.doubleValue() + " %";
	return calculation;
    }

    /**
     * On pick A ball click.
     *
     * @param e
     *            the e
     */
    private void onPickABallClick(ClickEvent e) {
	int value = randomizer.nextInt(lotteryList.size());
	Image img = ImageUtils.getImage((Image) lotteryList.get(value));
	img.setWidth(78, Unit.PIXELS);
	img.setHeight(81, Unit.PIXELS);
	img.addStyleName("random-image");
	imageLayout.replaceComponent(randomImgToBeReplaced, img);
	randomImgToBeReplaced = img;
	int imgIdToIncrease = (int) img.getData();
	Integer currentValue = statsImageIdOcurrencesMap.get(imgIdToIncrease);
	currentValue++;
	statsImageIdOcurrencesMap.put(imgIdToIncrease, new Integer(currentValue));
	calculateStats();
    }

}
