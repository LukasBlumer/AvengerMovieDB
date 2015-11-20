package amdb.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.event.SelectEvent;
import com.googlecode.gwt.charts.client.event.SelectHandler;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

public class ColumnChartComponent {
	
	/*
	 * The way this is supposed to work is that it displays movies on the y axis and years on the x axis.
	 * Countries are displayed as the columns.
	 * The user can select a number of countries to display (to be implemented) - there should probably also be a max number of selectable countries.
	 * Default should probably be the x countries with the most movies registered.
	 * When the user clicks on a column, ideally, it should add a filter for only that country and show the table.
	 */
	public static void drawColumnChart(ColumnChart columnChart, MovieCollection collection) {
		
		// Conversion of MovieCollection to DataTable
		GWT.log("creating dataTable for piechart.");
		DataTable dataTable = MovieCollectionConverter.toDataTableYearCountryAmount(collection);
		GWT.log("creating dataTable for piechart finished.");

		// onClick event listener (might go into another file)
		columnChart.addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent select) {
				// addFilter();
				GWT.log(select.NAME);
				GWT.log(select.getProperties().toString());
				Window.alert("You clicked on a column");
			}
		});
		
		GWT.log("drawing columnchart.");

		// Customising column chart appearance
		ColumnChartOptions options = ColumnChartOptions.create();
		options.setTitle("Here's the stuff");
		options.setHAxis(HAxis.create("Year"));
		options.setVAxis(VAxis.create("Movies"));
		
		
		// Actually draw the chart.
		columnChart.draw(dataTable, options);
		GWT.log("drawing columnchart finished.");
	}

}