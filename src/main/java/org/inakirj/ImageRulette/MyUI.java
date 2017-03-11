package org.inakirj.ImageRulette;

import javax.servlet.annotation.WebServlet;

import org.inakirj.ImageRulette.engine.ViewController;
import org.vaadin.leif.splashscreen.SplashScreen;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
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
@SplashScreen(value = "VAADIN/splash.png", width = 400, height = 400)
public class MyUI extends UI {

    private static final long serialVersionUID = 7664729118286363293L;
    private ViewController mainController = new ViewController();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
	// MainMenu menuLayout = new MainMenu();
	// setContent(menuLayout);
	MyUI ui = (MyUI) UI.getCurrent();
	ui.getController().goTo(ViewController.VIEW_MAIN_MENU);
	setResponsive(true);
	setSizeFull();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
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

}
