package amdb.client;

import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;

/**
 * This class contains the method to display the about page.
 * @author Lukas Blumer
 * @history 2015-11-30 LB first version committed
 * @version 2015-11-30 LB 1.0
 * @responsibilities This class contains a static method used to display the about page.
 */
public class AboutPage {
	
	/**
	 * This displays the about page with information about the creators, the sources and how to use the application.
	 * @param panel
	 */
	public static void drawAboutPage(DockLayoutPanel panel) {
		Frame frame = new Frame("http://www.google.com/"); // test site. need to figure out how to link local html file.
		panel.add(frame);
	}

}
