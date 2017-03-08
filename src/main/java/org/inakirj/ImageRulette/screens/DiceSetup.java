/**
 * 
 */
package org.inakirj.ImageRulette.screens;

import java.util.ArrayList;
import java.util.List;

import org.inakirj.ImageRulette.MyUI;
import org.inakirj.ImageRulette.engine.ViewController;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author inaki
 *
 */
public class DiceSetup extends CssLayout {

	/**
	 * Basic constructor
	 */
	public DiceSetup() {
		setLayout();
	}


	/**
	 * @param children
	 */
	public DiceSetup(Component... children) {
		super(children);
		// TODO Auto-generated constructor stub
	}
	
	private void setLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
        
		Label title = new Label();
        title.setValue("Select your dice configuration");
        
        VerticalLayout selection = new VerticalLayout();
        selection.setSizeFull();
        selection.addComponent(addSelection("Paris x "));
        selection.addComponent(addSelection("Barcelona x "));
        selection.addComponent(addSelection("Istanbul x "));
        selection.addComponent(addSelection("Berlin x "));
        
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSizeFull();
        Button backBtn = new Button("BACK");
        backBtn.addClickListener(this::onBackClick);
        buttons.addComponent(backBtn);        
        Button goBtn = new Button("GO");
        goBtn.addClickListener(this::onGoClick);
        buttons.addComponent(goBtn);
        layout.addComponents(title, selection, buttons);
		
		addComponent(layout);
		
	}

	private void onBackClick(ClickEvent e) {
		MyUI ui = (MyUI)UI.getCurrent();
		ui.getController().goTo(ViewController.VIEW_MAIN_MENU);
	}
	
	private void onGoClick(ClickEvent e) {
		List<Object> randomList = generateLotteryList();
		MyUI ui = (MyUI)UI.getCurrent();
		ui.getController().goTo(ViewController.VIEW_DICE_PLAY, randomList);
	}


	private List<Object> generateLotteryList() {
		List<Object> randomList = new ArrayList<>();
		randomList.add(new String("A"));
		randomList.add(new String("A"));
		randomList.add(new String("B"));
		randomList.add(new String("C"));
		randomList.add(new String("C"));
		randomList.add(new String("D"));
		randomList.add(new String("D"));
		randomList.add(new String("D"));
		randomList.add(new String("D"));
		randomList.add(new String("D"));
		randomList.add(new String("E"));
		return randomList;
	}


	private Component addSelection(String name) {
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeFull();
		Label label = new Label(name);
		label.setWidth("50%");
		hl.addComponent(label);
		ComboBox value = new ComboBox();
		String one = new String("1");
		value.setItems(one, new String("2"), new String("3"), new String("4"), new String("5"));
		value.setEmptySelectionAllowed(false);
		value.setValue(one);
		value.setWidth("80px");
		hl.addComponent(value);
		hl.setExpandRatio(label, 8);
		hl.setExpandRatio(value, 2);
		return hl;
	}
	
	

}
