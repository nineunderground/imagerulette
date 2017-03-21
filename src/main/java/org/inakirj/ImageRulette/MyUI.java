package org.inakirj.ImageRulette;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.inakirj.ImageRulette.screens.DicePlayView;
import org.inakirj.ImageRulette.screens.DiceSetupView;

import com.vaadin.addon.touchkit.annotations.OfflineModeEnabled;
import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.settings.TouchKitSettings;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.addon.touchkit.ui.TabBarView.SelectedTabChangeEvent;
import com.vaadin.addon.touchkit.ui.TabBarView.SelectedTabChangeListener;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet.Tab;
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
@Theme("mytheme")
@Widgetset("org.vaadin.touchkit.gwt.ImageRuletteWidgetSet")
@Title("Dice Rulette")
@OfflineModeEnabled
public class MyUI extends UI {

    private static final long serialVersionUID = 7664729118286363293L;
    private List<Object> generateLotteryList;
    private NavigationView tabContent2;
    private NavigationView tabContent3;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
	TabBarView tabManager = new TabBarView();
	tabManager.addStyleName("tab-style");

	// NavigationView tabContent1 = new NavigationView();
	tabContent2 = new NavigationView();
	tabContent2.setCaption("Create your dice pool");
	tabContent2.addStyleName("view-background");
	Button resetSliders = new Button("RESET");
	resetSliders.addClickListener(e -> onResetSliders());
	resetSliders.addStyleName("reset-button");
	tabContent2.setRightComponent(resetSliders);

	tabContent3 = new NavigationView();
	tabContent3.setCaption("Roll the dice");

	// tabContent1.setContent(new DiceGalleryView());
	// tabContent1.setData("1");
	// tabManager.addTab(tabContent1, "GALLERY");
	tabContent2.setContent(new DiceSetupView(tabContent3));
	tabContent2.setData("2");
	Tab tabSetup = tabManager.addTab(tabContent2, "SETUP");
	tabSetup.setIcon(FontAwesome.PICTURE_O);
	tabContent3.setContent(new DicePlayView());
	tabContent3.setData("3");
	Tab tabPlay = tabManager.addTab(tabContent3, "DICE");
	tabPlay.setIcon(FontAwesome.CUBES);
	tabContent3.setEnabled(false);
	tabManager.setSelectedTab(tabContent2);
	tabManager.addListener(new SelectedTabChangeListener() {

	    /** The Constant serialVersionUID. */
	    private static final long serialVersionUID = -5418597313724716790L;

	    @Override
	    public void selectedTabChange(SelectedTabChangeEvent event) {
		NavigationView tab = (NavigationView) (event.getTabSheet().getSelelectedTab()).getComponent();
		if (tab.getData().equals("3") && tab.isEnabled()) {
		    ((DicePlayView) tab.getContent()).setupLottery(generateLotteryList);
		}
	    }
	});
	setContent(tabManager);
    }

    /**
     * On reset sliders.
     *
     * @param e
     *            the e
     */
    private void onResetSliders() {
	((DicePlayView) tabContent3.getContent()).statsLayout.removeAllItems();
	tabContent3.setEnabled(false);
	tabContent2.setContent(new DiceSetupView(tabContent3));
    }

    /**
     * The Class MyUIServlet.
     */
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends TouchKitServlet {
	private static final long serialVersionUID = 1259803207649501173L;

	@Override
	protected void servletInitialized() throws ServletException {
	    super.servletInitialized();
	    TouchKitSettings s = getTouchKitSettings();
	    String contextPath = getServletConfig().getServletContext().getContextPath();
	    // App icon
	    s.getApplicationIcons().addApplicationIcon(contextPath + "VAADIN/themes/mytheme/dicerulette.png");
	    // Splash screen
	    // s.getWebAppSettings().setStartupImage(contextPath +
	    // "VAADIN/themes/splash.png");
	    // ViewPortSettings vp = s.getViewPortSettings();
	    // vp.setViewPortUserScalable(true);
	}
    }

    /**
     * Sets the lottery.
     *
     * @param generateLotteryList
     *            the new lottery
     */
    public void setLottery(List<Object> generateLotteryList) {
	this.generateLotteryList = generateLotteryList;
    }

}
