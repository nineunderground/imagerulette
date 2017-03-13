/**
 * 
 */
package org.inakirj.ImageRulette.screens;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.inakirj.ImageRulette.MyUI;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author inaki
 *
 */
public class DiceSetupView extends CssLayout {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5623498581320518810L;
    private List<HorizontalLayout> iconItemLayoutList;
    private NavigationView tabContent3;

    /**
     * Basic constructor
     * 
     * @param tabContent3
     * 
     * @param tabManager
     */
    public DiceSetupView(NavigationView tabContent3) {
	iconItemLayoutList = new ArrayList<>();
	this.tabContent3 = tabContent3;
	setLayout();
    }

    /**
     * Sets the layout.
     */
    private void setLayout() {
	VerticalLayout layout = new VerticalLayout();
	layout.setSizeFull();

	Label title = new Label();
	title.setValue("Select your dice configuration");
	title.setSizeFull();

	VerticalComponentGroup imagesLayout = new VerticalComponentGroup();
	imagesLayout.setWidth(100, Unit.PERCENTAGE);

	// System.out.println("Width: " +
	// UI.getCurrent().getPage().getBrowserWindowWidth());
	// System.out.println("Height: " +
	// UI.getCurrent().getPage().getBrowserWindowHeight());

	IntStream.of(1, 3, 5, 7, 9, 11, 13, 15).forEach(i -> {
	    HorizontalLayout hl = new HorizontalLayout();
	    hl.addComponent(addDiceImage(i));
	    hl.addComponent(addDiceImage(i + 1));
	    imagesLayout.addComponent(hl);
	});

	layout.addComponents(title, imagesLayout);

	layout.setExpandRatio(title, 0.2f);
	layout.setExpandRatio(imagesLayout, 8.8f);
	// layout.setExpandRatio(buttons, 1);

	addComponent(layout);

    }

    /**
     * On go click.
     */
    public void startLottery() {
	MyUI ui = (MyUI) UI.getCurrent();
	ui.setLottery(generateLotteryList());
	// ui.getController().goTo(ViewController.VIEW_DICE_PLAY, randomList);
    }

    /**
     * Generate lottery list.
     *
     * @return the list
     */
    private List<Object> generateLotteryList() {
	List<Object> randomList = new ArrayList<>();
	for (HorizontalLayout hl : iconItemLayoutList) {
	    Image img = (Image) hl.getComponent(0);
	    ComboBox cmb = (ComboBox) hl.getComponent(1);
	    int rep = Integer.parseInt((String) cmb.getValue());
	    while (rep > 0) {
		randomList.add(img);
		rep--;
	    }
	}
	return randomList;
    }

    /**
     * Adds the dice image.
     *
     * @param value
     *            the value
     * @return the component
     */
    private Component addDiceImage(int value) {
	FileResource resource = new FileResource(new File(
		VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/image/" + value + ".png"));
	Image image = new Image("", resource);
	image.setData(value);
	image.setSizeFull();

	ComboBox cmb = new ComboBox();
	List<String> cmbValues = IntStream.of(0, 1, 2, 3, 4, 5).mapToObj(i -> String.valueOf(i))
		.collect(Collectors.toList());
	cmb.addItems(cmbValues);
	cmb.setNullSelectionAllowed(false);
	cmb.setTextInputAllowed(false);
	cmb.setValue(cmbValues.get(0));
	cmb.setWidth("70px");
	cmb.setHeight(100, Unit.PERCENTAGE);
	cmb.addValueChangeListener(this::enableDiceTabOrNot);

	HorizontalLayout iconItemLayout = new HorizontalLayout();
	iconItemLayout.setSizeFull();
	iconItemLayout.addComponent(image);
	iconItemLayout.addComponent(cmb);
	iconItemLayout.setComponentAlignment(image, Alignment.BOTTOM_LEFT);
	iconItemLayout.setComponentAlignment(cmb, Alignment.BOTTOM_LEFT);
	iconItemLayout.setExpandRatio(image, 3);
	iconItemLayout.setExpandRatio(cmb, 7);
	iconItemLayoutList.add(iconItemLayout);
	return iconItemLayout;
    }

    /**
     * Check values.
     *
     * @param e
     *            the e
     */
    private void enableDiceTabOrNot(ValueChangeEvent e) {
	for (HorizontalLayout hl : iconItemLayoutList) {
	    ComboBox cmb = (ComboBox) hl.getComponent(1);
	    if (!((String) cmb.getValue()).equals("0")) {
		tabContent3.setEnabled(true);
		startLottery();
		return;
	    }
	}
	tabContent3.setEnabled(false);
    }

}
