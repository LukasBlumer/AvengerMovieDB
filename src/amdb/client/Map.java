package amdb.client;

<<<<<<< HEAD
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
=======
>>>>>>> 54ffb8c30105c5f6db034d0a4c1404f5a35f3f25
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.event.SelectEvent;
import com.googlecode.gwt.charts.client.event.SelectHandler;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.geochart.GeoChartColorAxis;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;
import com.googlecode.gwt.charts.client.geochart.MagnifyingGlass;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

public class Map {
	// create constructor
	
	public static void drawMap(GeoChart geoChart, MovieCollection collection) {
		
		// Conversion of MovieCollection to DataTable
		DataTable dataTable = MovieCollectionConverter.toDataTableCountryAmount(collection);
		
		// Color countries according to number of movies released
		GeoChartOptions options = GeoChartOptions.create();
		GeoChartColorAxis geoChartColorAxis = GeoChartColorAxis.create();
		
		options.setColorAxis(geoChartColorAxis);
		options.setDatalessRegionColor("white");
		options.setBackgroundColor("MediumLightBlue");
		
		// onClick event listener (cannot figure out how to do this)
		geoChart.addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent select) {
				// addFilter();
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