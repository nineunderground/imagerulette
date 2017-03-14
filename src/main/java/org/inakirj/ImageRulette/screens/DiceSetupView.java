/**
 * 
 */
package org.inakirj.ImageRulette.screens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import org.inakirj.ImageRulette.MyUI;
import org.inakirj.ImageRulette.utils.ImageUtils;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.UI;

/**
 * @author inaki
 *
 */
public class DiceSetupView extends CssLayout {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5623498581320518810L;
    private NavigationView tabContent3;
    private VerticalComponentGroup imagesLayout;

    /**
     * Instantiates a new dice setup view.
     *
     * @param tabContent3
     *            the tab content 3
     */
    public DiceSetupView(NavigationView tabContent3) {
	this.tabContent3 = tabContent3;
	setLayout();
    }

    /**
     * Sets the layout.
     */
    private void setLayout() {
	imagesLayout = new VerticalComponentGroup();
	imagesLayout.setWidth(100, Unit.PERCENTAGE);
	IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15).forEach(i -> {
	    HorizontalLayout sliderLAyout = new HorizontalLayout();
	    if (i % 2 == 0) {
		sliderLAyout.addStyleName("dice-banner-1");
	    } else {
		sliderLAyout.addStyleName("dice-banner-2");
	    }
	    sliderLAyout.setWidth(100, Unit.PERCENTAGE);
	    Image img = ImageUtils.getImage(i);
	    img.addStyleName("dice-image");
	    Slider slider = new Slider();
	    slider.addStyleName("dice-slider");
	    Label total = new Label();
	    total.addStyleName("size-24");// TODO is not working
	    // Adding image
	    sliderLAyout.addComponent(img);
	    // Adding slider
	    slider.setMin(0);
	    slider.setMax(5);
	    slider.setWidth(80, Unit.PERCENTAGE);
	    slider.addValueChangeListener(s -> {
		total.setValue("x " + slider.getValue().intValue());
		enableDiceTabOrNot();
	    });
	    sliderLAyout.addComponent(slider);
	    // Adding label
	    total.setValue("x 0");
	    sliderLAyout.addComponent(total);
	    sliderLAyout.setExpandRatio(img, 2);
	    sliderLAyout.setExpandRatio(slider, 7);
	    sliderLAyout.setExpandRatio(total, 1);
	    sliderLAyout.setComponentAlignment(img, Alignment.BOTTOM_LEFT);
	    sliderLAyout.setComponentAlignment(slider, Alignment.BOTTOM_LEFT);
	    sliderLAyout.setComponentAlignment(total, Alignment.BOTTOM_LEFT);
	    // Adding layout
	    imagesLayout.addComponent(sliderLAyout);
	});
	addComponent(imagesLayout);
    }

    /**
     * Generate lottery list.
     *
     * @return the list
     */
    private List<Object> generateLotteryList() {
	List<Object> randomList = new ArrayList<>();
	Iterator<Component> iterator = imagesLayout.iterator();
	while (iterator.hasNext()) {
	    HorizontalLayout hl = (HorizontalLayout) iterator.next();
	    Image img = (Image) hl.getComponent(0);
	    Slider slider = (Slider) hl.getComponent(1);
	    int rep = slider.getValue().intValue();
	    while (rep > 0) {
		randomList.add(ImageUtils.getImage(img));
		rep--;
	    }
	}
	return randomList;
    }

    /**
     * Enable dice tab or not.
     */
    private void enableDiceTabOrNot() {
	Iterator<Component> iterator = imagesLayout.iterator();
	while (iterator.hasNext()) {
	    HorizontalLayout hl = (HorizontalLayout) iterator.next();
	    Slider slider = (Slider) hl.getComponent(1);
	    if (slider.getValue().intValue() > 0) {
		tabContent3.setEnabled(true);
		MyUI ui = (MyUI) UI.getCurrent();
		ui.setLottery(generateLotteryList());
		return;
	    }
	}
	tabContent3.setEnabled(false);
    }
}
