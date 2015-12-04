package amdb.client;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

import com.google.gwt.core.shared.GWT;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;

/**
 * This class contains the method to draw the pie chart view.
 * @author Lukas Blumer
 * @history 2015-11-15 LB first version committed
 * @version 2015-11-21 LB 1.0
 * @responsibilities This class contains a static method used to draw the pie chart view.
 */
public class PieChartComponent {
	
	/**
	 * This draws the pie chart view with the specified <tt>MovieCollection</tt> into the specified <tt>PieChart</tt>.
	 * @param pieChart
	 * @param collection
	 */
	public static void drawPieChart(PieChart pieChart, MovieCollection collection) {
		
		// Conversion of MovieCollection to DataTable
		GWT.log("creating dataTable for piechart.");
		DataTable dataTable = MovieCollectionConverter.toDataTablePerCountry(collection);
		GWT.log("creating dataTable for piechart finished.");
		
		GWT.log("drawing piechart.");

		// Customising piechart appearance
		PieChartOptions options = PieChartOptions.create();
		options.setBackgroundColor("#ffffff");
		options.setPieResidueSliceColor("#000000");
		options.setPieResidueSliceLabel("Others");
		options.setSliceVisibilityThreshold(0.025);

		// Actually draw the chart.
		pieChart.draw(dataTable, options);
		GWT.log("drawing piechart finished.");
	}

}
