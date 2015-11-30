package amdb.client;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.event.SelectEvent;
import com.googlecode.gwt.charts.client.event.SelectHandler;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

import amdb.shared.Movie;
import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

/**
 * This class contains the method to draw the column view.
 * @author Lukas Blumer
 * @history 2015-11-15 LB first version committed
 * @version 2015-11-21 LB 1.0
 * @responsibilities This class contains a static method used to draw the column view.
 */
public class ColumnChartComponent {
	
	/**
	 * This draws the column view with the specified MovieCollection into the specified ColumnChart.
	 * @param columnChart
	 * @param collection
	 */
	public static void drawColumnChart(ColumnChart columnChart, MovieCollection collection) {
		
		// Conversion of MovieCollection to DataTable
		GWT.log("creating dataTable for ColumnChart.");
		DataTable dataTable = MovieCollectionConverter.toDataTableYearCountryAmount(collection);
		GWT.log("creating dataTable for ColumnChart finished.");

		/* onClick event listener (might go into another file)
		columnChart.addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent select) {
				// addFilter();
				GWT.log(select.NAME);
				GWT.log(select.getProperties().toString());
				Window.alert("You clicked on a column");
			}
		});
		*/
		
		GWT.log("drawing ColumnChart.");

		// Customizing column chart appearance
		ColumnChartOptions options = ColumnChartOptions.create();
		options.setHAxis(HAxis.create("Year"));
		options.setVAxis(VAxis.create("Movies"));
		
		
		// Actually draw the chart.
		columnChart.draw(dataTable, options);
		GWT.log("drawing columnchart finished.");
	}

}
