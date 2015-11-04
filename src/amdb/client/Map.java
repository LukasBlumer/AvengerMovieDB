package amdb.client;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

import com.google.gwt.core.client.JsArrayString;
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
	private static final String lightestAxisColor = "5CD65C";
	private static final String darkestAxisColor = "0A290A";
	private static final String datalessRegionColor = "white";
	private static final String backgroundColor = "005CB8";
			
			
	// create constructor
	
	public static void drawMap(GeoChart geoChart, MovieCollection collection) {
		
		// Conversion of MovieCollection to DataTable
		DataTable dataTable = MovieCollectionConverter.toDataTableCountryAmount(collection);
		
		// Color countries according to number of movies released
		GeoChartOptions options = GeoChartOptions.create();
		GeoChartColorAxis geoChartColorAxis = GeoChartColorAxis.create();
		// required to make a JsArrayString
		@SuppressWarnings("deprecation")
		JsArrayString colorAxisHelper = ArrayHelper.createJsArray(new String[]{lightestAxisColor,darkestAxisColor }); 
		geoChartColorAxis.setColors(colorAxisHelper);
		// values higher than MaxValue are displayed in the darkest color. The gradient stops at this value
		geoChartColorAxis.setMaxValue(5000);
		
		options.setColorAxis(geoChartColorAxis);
		options.setDatalessRegionColor(datalessRegionColor);
		options.setBackgroundColor(backgroundColor);
		
//		onClick event listener (cannot figure out how to do this)
		geoChart.addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent select) {
				// addFilter();
				

				Window.alert("You clicked on a country");
			}
		});
		

		// zoom (no idea if this works) - probably not
		MagnifyingGlass zoom = MagnifyingGlass.create();
		zoom.setEnable(true);
		zoom.setZoomFactor(1000.0);
		options.setMagnifyingGlass(zoom);
	
		// actually draw the map
		geoChart.draw(dataTable, options);
	}

}