/**
 * 
 */
package org.inakirj.ImageRulette.engine;

import org.inakirj.ImageRulette.screens.DicePlay;
import org.inakirj.ImageRulette.screens.DiceSetup;
import org.inakirj.ImageRulette.screens.Gallery;
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
	
	private MainMenu mainMenuContent = new MainMenu();
	private DiceSetup setupDiceContent = new DiceSetup();
	private Settings settingsContent = new Settings();
	private DicePlay dicePlayContent = new DicePlay();
	private Gallery galleryContent = new Gallery();

	/**
	 * Main constructor
	 */
	public ViewController() {
		
	}
	
	public void goTo(int viewToGo, Object... item){
		currentView = viewToGo;
		switch (viewToGo){
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
			dicePlayContent.setupLottery(item[0]);
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
//	public void setLayoutContent(int layoutId, CssLayout layout){
//		switch (layoutId){
//		case VIEW_MAIN_MENU:
//			mainMenuContent.setLayout(layout);
//			break;
//		case VIEW_SETUP_DICE:
//			setupDiceContent = layout;
//			break;
//		case VIEW_SETTINGS:
//			settingsContent = layout;
//			break;
//		case VIEW_DICE_PLAY:
//			dicePlayContent = layout;
//			break;
//		case VIEW_GALLERY:
//			galleryContent = layout;
//			break;
//		}
//	}

}
