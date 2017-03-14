/**
 * 
 */
package org.inakirj.ImageRulette.screens;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.inakirj.ImageRulette.utils.ImageUtils;

import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
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
    private Random randomizer = new Random(Calendar.getInstance().getTime().getTime());
    private List<Object> lotteryList;
    private Image randomImgToBeReplaced;
    private Map<Integer, Integer> statsImageIdOcurrencesMap = new HashMap<>();
    private HorizontalLayout imageLayout;
    private VerticalLayout statsLayout;
    private VerticalComponentGroup mainLayout;

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
	mainLayout = new VerticalComponentGroup();
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
	rollBtn.setWidth(100, Unit.PERCENTAGE);
	rollBtn.setHeight(50, Unit.PIXELS);
	rollBtn.addStyleName("dice-button-roll");
	rollBtn.setIcon(FontAwesome.TROPHY);

	mainLayout.addComponent(imageLayout);
	mainLayout.addComponent(rollBtn);
	calculateStats();
	addComponent(mainLayout);
    }

    /**
     * Calculate stats.
     */
    private void calculateStats() {
	long totalImagesRendered = statsImageIdOcurrencesMap.values().stream().reduce(0, Integer::sum);
	Map<Integer, Integer> statsMapSorted = new LinkedHashMap<>();
	Comparator<? super Entry<Integer, Integer>> sorting = Map.Entry.<Integer, Integer> comparingByValue()
		.reversed();
	statsImageIdOcurrencesMap.entrySet().stream().sorted(sorting)
		.forEachOrdered(entry -> statsMapSorted.put(entry.getKey(), entry.getValue()));
	List<HorizontalLayout> statsRows = new ArrayList<>();
	Iterator<Entry<Integer, Integer>> iterator = statsMapSorted.entrySet().iterator();
	// for (Entry<Integer, Integer> entry : statsMapSorted.entrySet()) {
	int rorCount = 0;
	while (iterator.hasNext()) {
	    // Left fake column
	    Entry<Integer, Integer> entry = iterator.next();
	    HorizontalLayout row = new HorizontalLayout();
	    if (rorCount % 2 == 0) {
		row.addStyleName("stats-row-1");
	    } else {
		row.addStyleName("stats-row-2");
	    }
	    Image img = ImageUtils.getImage(entry.getKey());
	    img.addStyleName("dice-image-stats");
	    Label label = new Label(getCalculation(entry.getValue(), totalImagesRendered));
	    row.addComponent(img);
	    row.addComponent(label);
	    row.setComponentAlignment(img, Alignment.BOTTOM_LEFT);
	    row.setComponentAlignment(label, Alignment.BOTTOM_LEFT);
	    if (iterator.hasNext()) {
		entry = iterator.next();
		Image img2 = ImageUtils.getImage(entry.getKey());
		img2.addStyleName("dice-image-stats");
		Label label2 = new Label(getCalculation(entry.getValue(), totalImagesRendered));
		row.addComponent(img2);
		row.addComponent(label2);
		row.setComponentAlignment(img2, Alignment.BOTTOM_LEFT);
		row.setComponentAlignment(label2, Alignment.BOTTOM_LEFT);
		statsRows.add(row);
	    } else {
		statsRows.add(row);
		break;
	    }
	    rorCount++;
	}

	VerticalLayout popupContent = new VerticalLayout();
	statsRows.stream().forEach(popupContent::addComponent);
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
