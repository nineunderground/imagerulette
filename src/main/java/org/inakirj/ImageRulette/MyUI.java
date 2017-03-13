package org.inakirj.ImageRulette;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.inakirj.ImageRulette.engine.ViewController;
import org.inakirj.ImageRulette.screens.DiceGalleryView;
import org.inakirj.ImageRulette.screens.DicePlayView;
import org.inakirj.ImageRulette.screens.DiceSetupView;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.addon.touchkit.ui.TabBarView.SelectedTabChangeEvent;
import com.vaadin.addon.touchkit.ui.TabBarView.SelectedTabChangeListener;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * 
 * @author inaki
 *
 *         This UI is the application entry point. A UI may either represent a
 *         browser window (or tab) or some part of a html page where a Vaadin
 *         application is embedded.
 *         <p>
 *         The UI is initialized using {@link #init(VaadinRequest)}. This method
 *         is intended to be overridden to add component to the user interface
 *         and initialize non-component functionality.
 */
// @Theme("mytheme")
@Theme("mymobiletheme")
// @SplashScreen(value = "VAADIN/splash.png", width = 400, height = 400)
@Widgetset("org.vaadin.touchkit.gwt.ImageRuletteWidgetSet")
@Title("My Simple App")
public class MyUI extends UI {

    private static final long serialVersionUID = 7664729118286363293L;
    private ViewController mainController = new ViewController();
    private List<Object> generateLotteryList;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
	TabBarView tabManager = new TabBarView();
	NavigationView tabContent1 = new NavigationView();
	NavigationView tabContent2 = new NavigationView();
	NavigationView tabContent3 = new NavigationView();

	tabContent1.setContent(new DiceGalleryView());
	tabContent1.setData("1");
	tabManager.addTab(tabContent1, "GALLERY");
	tabContent2.setContent(new DiceSetupView(tabContent3));
	tabContent2.setData("2");
	tabManager.addTab(tabContent2, "SETUP");
	tabContent3.setContent(new DicePlayView());
	tabContent3.setData("3");
	tabManager.addTab(tabContent3, "DICE");
	tabContent3.setEnabled(false);
	tabManager.setSelectedTab(tabContent2);
	tabManager.addListener(new SelectedTabChangeListener() {

	    /** The Constant serialVersionUID. */
	    private static final long serialVersionUID = -5418597313724716790L;

	    @Override
	    public void selectedTabChange(SelectedTabChangeEvent event) {
		NavigationView tab = (NavigationView) (event.getTabSheet().getSelelectedTab()).getComponent();
		if (tab.getData().equals("1")) {

		} else if (tab.getData().equals("2")) {

		} else if (tab.getData().equals("3") && tab.isEnabled()) {
		    ((DicePlayView) tab.getContent()).setupLottery(generateLotteryList);
		}
	    }
	});
	setContent(tabManager);
	// manager.addNavigationListener(listener);
	// MyUI ui = (MyUI) UI.getCurrent();
	// ui.getController().goTo(ViewController.VIEW_MAIN_MENU);
	// setResponsive(true);
	// setSizeFull();
	// test();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends TouchKitServlet {
	private static final long serialVersionUID = 1259803207649501173L;
	// @Override
	// public void init(ServletConfig servletConfig) throws ServletException
	// {
	// super.init(servletConfig);
	//
	// // Hook up with the framework's host page generation
	// SplashScreenHandler.init(getService());
	// }
    }

    public ViewController getController() {
	return mainController;
    }

    public void setLottery(List<Object> generateLotteryList) {
	this.generateLotteryList = generateLotteryList;
    }

}
