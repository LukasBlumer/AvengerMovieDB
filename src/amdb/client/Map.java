package amdb.client;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.ajaxloader.ArrayHelper;
import com.googlecode.gwt.charts.client.event.RegionClickEvent;
import com.googlecode.gwt.charts.client.event.RegionClickHandler;
import com.googlecode.gwt.charts.client.event.SelectEvent;
import com.googlecode.gwt.charts.client.event.SelectHandler;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.geochart.GeoChartColorAxis;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;
import com.googlecode.gwt.charts.client.geochart.MagnifyingGlass;

public class Map {
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
		DataTable dataTable = MovieCollectionConverter.toDataTableCountryAmount(collection);
		GWT.log("creating dataTable for worldmap finished.");

		// Log how when the creation of the worldmap starts and finishes
		GWT.log("drawing worldmap.");
		
		// Color countries according to number of movies released
		GeoChartOptions options = GeoChartOptions.create();
		GeoChartColorAxis geoChartColorAxis = GeoChartColorAxis.create();
		// required to make a JsArrayString
		@SuppressWarnings("deprecation")
		JsArrayString colorAxisHelper = ArrayHelper.createJsArray(new String[]{"0000FF","0071FF","00E2FF","00FFA9","00FF38","38FF00","AAFF00","FFE200","FF7100","FF0000"});

		geoChartColorAxis.setColors(colorAxisHelper);
		// values higher than MaxValue are displayed in the darkest color. The gradient stops at this value
//		geoChartColorAxis.setMaxValue(33500);
		// options.hideLegend();
		
		options.setColorAxis(geoChartColorAxis);
		options.setDatalessRegionColor(datalessRegionColor);
		options.setBackgroundColor(backgroundColor);
		
//		onClick event listener (might go into another file)
		geoChart.addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent select) {
				// addFilter();
				GWT.log(select.NAME);
				GWT.log(select.getProperties().toString());
				Window.alert("You clicked on a country");
			}
		});
		

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