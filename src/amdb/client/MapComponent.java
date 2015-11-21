package amdb.client;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.ajaxloader.ArrayHelper;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.geochart.GeoChartColorAxis;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;

public class MapComponent {
	/*
	 * Colors for the map as Hex or html color names.
	 * More than two axis colors can be defined, but have to be added in the definition of colorAxisHelper
	 * Found this page helpful:
	 * http://www.w3schools.com/tags/ref_colorpicker.asp
	 */
	private static final String datalessRegionColor = "white";

	private static final String backgroundColor = "#E5F7FF";
			
			
	// create constructor
	
	public static void drawMap(GeoChart geoChart, MovieCollection collection) {
		
		// Log when the creation of the dataTable starts and ends
		GWT.log("creating dataTable for worldmap.");
		// Conversion of MovieCollection to DataTable
		DataTable dataTable = MovieCollectionConverter.toDataTablePerCountry(collection);
		GWT.log("creating dataTable for worldmap finished.");

		// Log how when the creation of the worldmap starts and finishes
		GWT.log("drawing worldmap.");
		
		// Color countries according to number of movies released
		GeoChartOptions options = GeoChartOptions.create();
		GeoChartColorAxis geoChartColorAxis = GeoChartColorAxis.create();
		// required to make a JsArrayString
		@SuppressWarnings("deprecation")
		JsArrayString colorAxisHelper = ArrayHelper.createJsArray(new String[]{"white", "0000FF",  "0033FF",  "0065FF",  "0099FF",  "00CBFF",  "00FFFF",  "00FFCB",  "00FF99",  "00FF65",  "00FF33",  "00FF00",  "32FF00",  "65FF00",  "99FF00",  "CCFF00",  "FFFF00",  "FFCC00",  "FF9900",  "FF6600",  "FF3200",  "FF0000"});
		
		double dataTableMax = dataTable.getColumnRange(1).getMaxNumber(); // this gets me the entry in the dataTable with the highest total movie count
		
		// in order to make countries with 0 movies white, we associate values with color ranges
		@SuppressWarnings("deprecation")
		JsArrayNumber colorAxisValues = ArrayHelper.createJsArray(new double[]{0.0, 1.0, 0.05*dataTableMax, 0.1*dataTableMax, 0.15*dataTableMax, 0.2*dataTableMax, 0.25*dataTableMax, 0.3*dataTableMax, 0.35*dataTableMax, 0.4*dataTableMax, 0.45*dataTableMax, 0.5*dataTableMax, 0.55*dataTableMax, 0.6*dataTableMax, 0.65*dataTableMax, 0.7*dataTableMax, 0.75*dataTableMax, 0.8*dataTableMax, 0.85*dataTableMax, 0.9*dataTableMax, 0.95*dataTableMax, dataTableMax});

		geoChartColorAxis.setColors(colorAxisHelper);
		geoChartColorAxis.setValues(colorAxisValues);
		// options.hideLegend();
		
		options.setColorAxis(geoChartColorAxis);
		options.setDatalessRegionColor(datalessRegionColor);
		options.setBackgroundColor(backgroundColor);
				
		// onClick event listener (might go into another file)
//		geoChart.addSelectHandler(new SelectHandler() {
//			public void onSelect(SelectEvent select) {
//				// addFilter();
//				GWT.log(select.NAME);
//				GWT.log(select.getProperties().toString());
//				Window.alert("You clicked on a country");
//			}
//		});
		

		// zoom does not work
		/*
		MagnifyingGlass zoom = MagnifyingGlass.create();
		zoom.setEnable(true);
		zoom.setZoomFactor(1000.0);
		options.setMagnifyingGlass(zoom);
		*/
	
		// actually draw the map
		geoChart.draw(dataTable, options);
		
		GWT.log("drawing worldmap finished.");

	}
	
	
}