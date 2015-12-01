package amdb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Frame;

/**
 * This class contains the method to display the about page.
 * @author Lukas Blumer
 * @history 2015-11-30 LB first version committed
 * @version 2015-11-01 LB 1.0
 * @responsibilities This class contains a static method used to display the about page.
 */
public class AboutPage {
	
	/**
	 * This displays the about page with information about the creators, the sources and how to use the application.
	 * @param frame
	 */
	public static void drawAboutPage(Frame frame) {
		frame.setUrl(GWT.getModuleBaseURL() + "files/AboutPage.html");
	}

}
