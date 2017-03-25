/**
 * 
 */
package org.inakirj.imagerulette.screens;

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

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
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
    /**
     * map -> (Image URL, occurrences)
     */
    private Map<String, Integer> statsImageIdOcurrencesMap = new HashMap<>();
    private HorizontalLayout imageLayout;
    public Table statsLayout;
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
		.forEach(img -> statsImageIdOcurrencesMap.put(String.valueOf(((Image) img).getData()), new Integer(0)));
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
	rollBtn.addClickListener(e -> onPickABallClick());
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
    @SuppressWarnings("unchecked")
    private void calculateStats() {
	long totalImagesRendered = statsImageIdOcurrencesMap.values().stream().reduce(0, Integer::sum);
	Map<Image, Double> statsRowsMap = new HashMap<Image, Double>();
	Iterator<Entry<String, Integer>> iterator = statsImageIdOcurrencesMap.entrySet().iterator();
	while (iterator.hasNext()) {
	    // Left fake column
	    Entry<String, Integer> entry = iterator.next();
	    // @formatter:off
	    Image img = lotteryList.stream()
		    .map(i -> (Image) i)
		    .filter(i -> entry.getKey().equals((i.getData())))
		    .findFirst().orElse(null);
	    // @formatter:on
	    Image imgCopy = new Image("", img.getSource());
	    imgCopy.addStyleName("dice-image-stats");
	    double label = getCalculation(entry.getValue(), totalImagesRendered);
	    statsRowsMap.put(imgCopy, label);
	}
	Table popupContent;
	if (statsLayout == null) {
	    popupContent = new Table() {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -7151527404141301908L;

		@Override
		protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {
		    if (colId.equals(oddCol)) {
			return super.formatPropertyValue(rowId, colId, property) + " %";
		    }
		    return super.formatPropertyValue(rowId, colId, property);
		}

	    };
	    popupContent.setSortEnabled(false);
	    popupContent.addStyleName("stats-table");
	    popupContent.setWidth(100, Unit.PERCENTAGE);
	    popupContent.addContainerProperty(imageCol, Image.class, null);
	    popupContent.addContainerProperty(oddCol, BigDecimal.class, null);
	    popupContent.setColumnExpandRatio(imageCol, 3);
	    popupContent.setColumnExpandRatio(oddCol, 7);
	} else {
	    popupContent = statsLayout;
	    popupContent.removeAllItems();
	}
	statsRowsMap.entrySet().stream().forEach(entry -> {
	    Item row1 = popupContent.getItem(popupContent.addItem());
	    row1.getItemProperty(imageCol).setValue(entry.getKey());
	    BigDecimal bg = BigDecimal.valueOf(entry.getValue());
	    row1.getItemProperty(oddCol).setValue(bg);
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
    private double getCalculation(Integer valueToPercentage, long totalImagesRendered) {
	BigDecimal num = BigDecimal.valueOf(valueToPercentage).multiply(BigDecimal.valueOf(100));
	BigDecimal div = BigDecimal.valueOf(totalImagesRendered);
	BigDecimal division = num.divide(div, 2, RoundingMode.HALF_UP);
	BigDecimal percentage = division.setScale(2, RoundingMode.HALF_UP);
	return percentage.doubleValue();
    }

    /**
     * Loop thread.
     */
    private void onPickABallClick() {
	int value = randomizer.nextInt(lotteryList.size());
	Image img = (Image) lotteryList.get(value);
	img.setWidth(78, Unit.PIXELS);
	img.setHeight(81, Unit.PIXELS);
	img.addStyleName("random-image");
	imageLayout.replaceComponent(randomImgToBeReplaced, img);
	randomImgToBeReplaced = img;
	String imgUrlToIncrease = (String) img.getData();
	Integer currentValue = statsImageIdOcurrencesMap.get(imgUrlToIncrease);
	currentValue++;
	statsImageIdOcurrencesMap.put(imgUrlToIncrease, new Integer(currentValue));
	calculateStats();
    }

}
