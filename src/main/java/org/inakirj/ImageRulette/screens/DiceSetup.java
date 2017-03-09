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
import org.inakirj.ImageRulette.engine.ViewController;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
public class DiceSetup extends CssLayout {

	private List<HorizontalLayout> iconItemLayoutList;

	/**
	 * Basic constructor
	 */
	public DiceSetup() {
		iconItemLayoutList = new ArrayList<>();
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
        title.setSizeFull();
        
        VerticalLayout imagesLayout = new VerticalLayout();
        imagesLayout.setSizeFull();
        IntStream.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16).forEach(i -> imagesLayout.addComponent(addDiceImage(i)));
        
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSizeFull();
        Button backBtn = new Button("BACK");
        backBtn.addClickListener(this::onBackClick);
        buttons.addComponent(backBtn);        
        Button goBtn = new Button("GO");
        goBtn.addClickListener(this::onGoClick);
        buttons.addComponent(goBtn);
        layout.addComponents(title, imagesLayout, buttons);
		
        layout.setExpandRatio(title, 0.2f);
        layout.setExpandRatio(imagesLayout, 8.8f);
        layout.setExpandRatio(buttons, 1);
        
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
		for(HorizontalLayout hl : iconItemLayoutList){
			Image img = (Image)hl.getComponent(0);
			ComboBox cmb = (ComboBox)hl.getComponent(1);
			int rep = Integer.parseInt((String)cmb.getValue());
			while(rep > 0){
				randomList.add(img);
				rep--;
			}
		}
		return randomList;
	}

	private Component addDiceImage(int value) {		
		FileResource resource = new FileResource(new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/image/" + value + ".png"));
		Image image = new Image("", resource);
		image.setSizeFull();
		
		ComboBox cmb = new ComboBox();
		List<String> cmbValues = IntStream.of(0,1,2,3,4,5).mapToObj(i -> String.valueOf(i)).collect(Collectors.toList());
		cmb.setItems(cmbValues);
		cmb.setEmptySelectionAllowed(false);
		cmb.setTextInputAllowed(false);
		cmb.setValue(cmbValues.get(0));
		cmb.setWidth("70px");
		cmb.setHeight(100, Unit.PERCENTAGE);
		
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
	
}
