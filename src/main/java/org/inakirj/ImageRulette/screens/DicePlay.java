/**
 * 
 */
package org.inakirj.ImageRulette.screens;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.inakirj.ImageRulette.MyUI;
import org.inakirj.ImageRulette.engine.ViewController;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author inaki
 *
 */
public class DicePlay extends CssLayout {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5317042828250752413L;
	private Random randomizer = new Random(Calendar.getInstance().getTime().getTime());
	private List<Object> lotteryList;
	private Label randomValue;

	/**
	 * Instantiates a new dice play.
	 */
	public DicePlay() {
		setLayout();
	}

	public void setupLottery(Object lotteryList) {
		this.lotteryList = (List<Object>) lotteryList;
		randomValue.setValue("");
	}
	
	private void setLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		
		Label title = new Label("Dice Rulette");
		title.setSizeFull();
		
		randomValue = new Label("");
		randomValue.setSizeFull();
		
		HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSizeFull();
        Button backBtn = new Button("BACK");
        backBtn.addClickListener(this::onBackClick);
        buttons.addComponent(backBtn);        
        Button goBtn = new Button("LOTTERY");
        goBtn.addClickListener(this::onPickABallClick);
        buttons.addComponent(goBtn);
		
		layout.addComponents(title, randomValue, buttons);
		addComponent(layout);
	}
	
	private void onBackClick(ClickEvent e) {
		MyUI ui = (MyUI)UI.getCurrent();
		ui.getController().goTo(ViewController.VIEW_SETUP_DICE);
	}
	
	private void onPickABallClick(ClickEvent e) {
		int value = randomizer.nextInt(lotteryList.size());
		String valueSelected = (String)lotteryList.get(value);
		randomValue.setValue("New Value -> " + valueSelected);
	}

}
