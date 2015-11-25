package amdb.client;

/**
 * This class contains static methods used to export elements from the website.
 * @author petrawittwer
 * @history 2015-11-25 PW first version
 * @version 2015-11-25 PW 1.0
 * @responsibilities This class contains static methods used to export elements from the website.
 */
public class Export {

	/**
	 * This extracts the SVG from the currently displayed GWT chart.
	 * Do not call if the currently displayed chart is not an SVG (e.g. the table)
	 * @pre Currently displayed chart contains a svg
	 * @post true
	 */
	public static native void exportAsSVG() /*-{
		// $doc refers to the current HTML tree
		// $wnd refers to the currently displayed window
		
		// this function is used for UTF-8 encoding
		function utf8_to_b64(str) {
    		return $wnd.btoa(unescape(encodeURIComponent(str)));
		}
		
		// get the currently displayed svg
		var svg  = $doc.querySelector("svg"),
		
		// pack it into a URL, call that URL and open the download window
	    xml  = new XMLSerializer().serializeToString(svg),
	    data = "data:image/svg+xml;base64," + utf8_to_b64(xml);
		var link = $doc.createElement("a");
            link.setAttribute("href", data);
            link.setAttribute("download", "export.svg");
            link.style.visibility = 'hidden';
            $doc.body.appendChild(link);
            link.click();
            $doc.body.removeChild(link);
	}-*/;
	


}
