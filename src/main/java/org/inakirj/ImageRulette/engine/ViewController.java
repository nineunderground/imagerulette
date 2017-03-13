/**
 * 
 */
package org.inakirj.ImageRulette.engine;

import java.util.List;

import org.inakirj.ImageRulette.screens.DiceGalleryView;
import org.inakirj.ImageRulette.screens.DicePlayView;
import org.inakirj.ImageRulette.screens.DiceSetupView;
import org.inakirj.ImageRulette.screens.MainMenu;
import org.inakirj.ImageRulette.screens.Settings;

import com.vaadin.ui.UI;

/**
 * @author inaki
 *
 */
public class ViewController {

    public static final int VIEW_MAIN_MENU = 1;
    public static final int VIEW_SETUP_DICE = 11;
    public static final int VIEW_SETTINGS = 12;
    public static final int VIEW_DICE_PLAY = 111;
    public static final int VIEW_GALLERY = 121;
    private int currentView = VIEW_MAIN_MENU;

    public MainMenu mainMenuContent = new MainMenu();
    public DiceSetupView setupDiceContent = null;
    public Settings settingsContent = new Settings();
    public DicePlayView dicePlayContent = new DicePlayView();
    public DiceGalleryView galleryContent = new DiceGalleryView();

    /**
     * Main constructor
     */
    public ViewController() {

    }

    public void goTo(int viewToGo, Object... item) {
	currentView = viewToGo;
	switch (viewToGo) {
	case VIEW_MAIN_MENU:
	    UI.getCurrent().setContent(mainMenuContent);
	    break;
	case VIEW_SETUP_DICE:
	    UI.getCurrent().setContent(setupDiceContent);
	    break;
	case VIEW_SETTINGS:
	    UI.getCurrent().setContent(settingsContent);
	    break;
	case VIEW_DICE_PLAY:
	    if (item[0] instanceof List) {
		dicePlayContent.setupLottery((List<Object>) item[0]);
	    }
	    UI.getCurrent().setContent(dicePlayContent);
	    break;
	case VIEW_GALLERY:
	    UI.getCurrent().setContent(galleryContent);
	    break;
	}
    }

    /**
     * @param layoutId
     * @param layout
     */
    // public void setLayoutContent(int layoutId, CssLayout layout){
    // switch (layoutId){
    // case VIEW_MAIN_MENU:
    // mainMenuContent.setLayout(layout);
    // break;
    // case VIEW_SETUP_DICE:
    // setupDiceContent = layout;
    // break;
    // case VIEW_SETTINGS:
    // settingsContent = layout;
    // break;
    // case VIEW_DICE_PLAY:
    // dicePlayContent = layout;
    // break;
    // case VIEW_GALLERY:
    // galleryContent = layout;
    // break;
    // }
    // }

}
