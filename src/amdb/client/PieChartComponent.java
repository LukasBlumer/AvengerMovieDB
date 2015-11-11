package amdb.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;
import com.googlecode.gwt.charts.client.event.SelectEvent;
import com.googlecode.gwt.charts.client.event.SelectHandler;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

public class PieChartComponent {
	
	/*
	 * The pie chart displays the total movie count on a per-country basis.
	 * This is limited by time frame selection in the timeline.
	 * It displays the x countries with the most movies in that timeframe and summarises all remaining into a "others" slice.
	 * Clicking on a slice applies the filter for the selected country to the collection and switches to table view.
	 * What happens if the user clicks on the "others" slice?
	 */
	public static void drawPieChart(PieChart pieChart, MovieCollection collection) {
		
		// Conversion of MovieCollection to DataTable
		GWT.log("creating dataTable for piechart.");
		DataTable dataTable = MovieCollectionConverter.toDataTableCountryAmount(collection);
		GWT.log("creating dataTable for piechart finished.");
		
		// onClick event listener (might go into another file)
		pieChart.addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent select) {
				// addFilter();
				GWT.log(select.NAME);
				GWT.log(select.getProperties().toString());
				Window.alert("You clicked on a slice");
			}
		});
		
		GWT.log("drawing piechart.");

		// Customising piechart appearance
		PieChartOptions options = PieChartOptions.create();
		options.setBackgroundColor("#f0f0f0");
		options.setPieResidueSliceColor("#000000");
		options.setPieResidueSliceLabel("Others");
		options.setSliceVisibilityThreshold(0.1);
		options.setTitle("This is the breakdown");

		// Actually draw the chart.
		pieChart.draw(dataTable, options);
		GWT.log("drawing piechart finished.");
	}

}
