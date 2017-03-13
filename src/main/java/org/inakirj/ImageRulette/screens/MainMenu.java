package org.inakirj.ImageRulette.screens;

import org.inakirj.ImageRulette.MyUI;
import org.inakirj.ImageRulette.engine.ViewController;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * The Class MainMenu.
 *
 * @author inaki
 */
public class MainMenu extends VerticalLayout {

    private static final long serialVersionUID = -5062613950325511977L;

    /**
     * Instantiates a new main menu.
     */
    public MainMenu() {
	setLayout();
    }

    /**
     * Set content
     */
    private void setLayout() {
	VerticalLayout layout = this;
	layout.setSizeFull();

	Button newBtn = new Button("START");
	newBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
	newBtn.addClickListener(this::onNewDiceClick);
	newBtn.setSizeFull();

	Button settingsBtn = new Button("SETTINGS");
	settingsBtn.addClickListener(this::onSettingsClick);
	settingsBtn.setSizeFull();
	settingsBtn.setEnabled(false);

	Button aboutBtn = new Button("ABOUT");
	aboutBtn.setVisible(true);
	aboutBtn.addClickListener(this::onAboutClick);
	aboutBtn.setSizeFull();

	layout.addComponents(newBtn, settingsBtn, aboutBtn);// , settingsBtn,
							    // aboutBtn

	// addComponent(layout);
    }

    /**
     * On settings click.
     *
     * @param o
     *            the o
     */
    private void onSettingsClick(ClickEvent o) {
	MyUI ui = (MyUI) UI.getCurrent();
	ui.getController().goTo(ViewController.VIEW_SETTINGS);
    }

    /**
     * 
     * @param e
     */
    private void onNewDiceClick(ClickEvent e) {
	MyUI ui = (MyUI) UI.getCurrent();
	ui.getController().goTo(ViewController.VIEW_SETUP_DICE);
    }

    /**
     * On about click event
     * 
     * @param e
     *            Click event
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
