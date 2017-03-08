package org.inakirj.ImageRulette.screens;

import org.inakirj.ImageRulette.MyUI;
import org.inakirj.ImageRulette.engine.ViewController;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The Class MainMenu.
 *
 * @author inaki
 */
public class MainMenu extends CssLayout {

	private static final long serialVersionUID = -5062613950325511977L;

	/**
	 * Instantiates a new main menu.
	 * @param vc 
	 */
	public MainMenu() {
		setLayout();
	}

	/**
	 * Instantiates a new main menu.
	 *
	 * @param children the children
	 */
	public MainMenu(Component... children) {
		super(children);
	}
	
	/**
	 * Set content
	 */
	private void setLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
        
		Button newBtn = new Button("NEW");
        newBtn.addClickListener(this::onNewDiceClick);
        
        Button settingsBtn = new Button("SETTINGS");
        settingsBtn.addClickListener(this::onSettingsClick);
        
        Button aboutBtn = new Button("ABOUT");
        aboutBtn.addClickListener(this::onAboutClick);
        
        layout.addComponents(newBtn, settingsBtn, aboutBtn);
        addComponent(layout);
	}

	/**
	 * 
	 * @param o
	 */
	private void onSettingsClick(ClickEvent o) {
		MyUI ui = (MyUI)UI.getCurrent();
		ui.getController().goTo(ViewController.VIEW_SETTINGS);
	}

	/**
	 * 
	 * @param e
	 */
	private void onNewDiceClick(ClickEvent e) {
		MyUI ui = (MyUI)UI.getCurrent();
        ui.getController().goTo(ViewController.VIEW_SETUP_DICE);
	}

	/**
	 * On about click event
	 * @param e Click event
	 */
	private void onAboutClick(ClickEvent e) {
		Window popup = new Window("About me");
		popup.setModal(true);
		popup.setResizable(false);
		popup.setDraggable(false);
		// popup.setWindowMode(WindowMode.NORMAL);
		VerticalLayout popupContent = new VerticalLayout();
		String infoValue = new String("Thanks for using Dice Rulette");
		Label info = new Label(infoValue);
		popupContent.addComponent(info);
		popup.setContent(popupContent);
		UI.getCurrent().addWindow(popup);
	}

}
