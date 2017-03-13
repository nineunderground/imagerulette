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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.inakirj.ImageRulette.utils.ImageUtils;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
    private VerticalLayout layout;
    private Map<Integer, Integer> statsImageIdOcurrencesMap = new HashMap<>();

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
	layout = new VerticalLayout();
	layout.setSizeFull();
	Label title = new Label("Dice Rulette");
	title.setSizeFull();

	FileResource resource = new FileResource(
		new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/image/nope.png"));
	Image none = new Image("", resource);

	randomImgToBeReplaced = none;
	randomImgToBeReplaced.setWidth(78, Unit.PIXELS);
	randomImgToBeReplaced.setHeight(81, Unit.PIXELS);

	HorizontalLayout buttons = new HorizontalLayout();
	buttons.setSizeFull();
	Button goBtn = new Button("LOTTERY");
	goBtn.addClickListener(this::onPickABallClick);
	buttons.addComponent(goBtn);

	Button stats = new Button("STATS");
	stats.addClickListener(this::showStats);

	layout.addComponent(title);
	layout.addComponent(randomImgToBeReplaced);
	layout.addComponent(buttons);
	layout.addComponent(stats);

	layout.setExpandRatio(title, 1);
	layout.setExpandRatio(randomImgToBeReplaced, 2);
	layout.setExpandRatio(buttons, 1);
	layout.setExpandRatio(stats, 6);

	addComponent(layout);
    }

    /**
     * Show stats.
     *
     * @param e
     *            the e
     */
    private void showStats(ClickEvent e) {
	Window popup = new Window("STATS");
	popup.setModal(true);
	popup.setResizable(false);
	popup.setDraggable(false);
	popup.setSizeFull();
	long totalImagesRendered = statsImageIdOcurrencesMap.values().stream().reduce(0, Integer::sum);
	Map<Integer, Integer> statsMapSorted = new LinkedHashMap<>();
	Comparator<? super Entry<Integer, Integer>> sorting = Map.Entry.<Integer, Integer> comparingByValue()
		.reversed();
	statsImageIdOcurrencesMap.entrySet().stream().sorted(sorting)
		.forEachOrdered(entry -> statsMapSorted.put(entry.getKey(), entry.getValue()));
	List<HorizontalLayout> statsRows = new ArrayList<>();
	for (Entry<Integer, Integer> entry : statsMapSorted.entrySet()) {
	    HorizontalLayout row = new HorizontalLayout();
	    Image img = ImageUtils.getImage(entry.getKey());
	    Label label = new Label(getCalculation(entry.getValue(), totalImagesRendered));
	    row.addComponent(img);
	    row.addComponent(label);
	    row.setComponentAlignment(img, Alignment.BOTTOM_LEFT);
	    row.setComponentAlignment(label, Alignment.BOTTOM_LEFT);
	    statsRows.add(row);
	}

	VerticalLayout popupContent = new VerticalLayout();
	statsRows.stream().forEach(popupContent::addComponent);

	popup.setContent(popupContent);
	UI.getCurrent().addWindow(popup);
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
	layout.replaceComponent(randomImgToBeReplaced, img);
	randomImgToBeReplaced = img;
	refreshStats((int) img.getData());
    }

    /**
     * Refresh stats.
     *
     * @param imgToIncrease
     *            the img to increase
     */
    private void refreshStats(int imgIdToIncrease) {
	Integer currentValue = statsImageIdOcurrencesMap.get(imgIdToIncrease);
	currentValue++;
	statsImageIdOcurrencesMap.put(imgIdToIncrease, new Integer(currentValue));
    }

}
