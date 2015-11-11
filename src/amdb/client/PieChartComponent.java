package amdb.client;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.Selection;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;
import com.googlecode.gwt.charts.client.event.ReadyEvent;
import com.googlecode.gwt.charts.client.event.ReadyHandler;
import com.googlecode.gwt.charts.client.event.SelectEvent;
import com.googlecode.gwt.charts.client.event.SelectHandler;

public class PieChartComponent {
	
	public static void drawPieChart(PieChart pieChart, MovieCollection collection) {
		
		// Log when the creation of the dataTable starts and ends
		GWT.log("creating dataTable for piechart.");
		// Conversion of MovieCollection to DataTable
		DataTable dataTable = MovieCollectionConverter.toDataTableCountryAmount(collection);
		GWT.log("creating dataTable for piechart finished.");

		// Log how when the creation of the piechart starts and finishes
		GWT.log("drawing piechart.");

		// customizing piechart appearance
		PieChartOptions options = PieChartOptions.create();
		options.setBackgroundColor("#f0f0f0");
		options.setPieResidueSliceColor("#000000");
		options.setPieResidueSliceLabel("Others");
		options.setSliceVisibilityThreshold(0.1);
		options.setTitle("This is the breakdown");
		
		// onClick event listener (might go into another file)
		pieChart.addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent select) {
				// addFilter();
				GWT.log(select.NAME);
				GWT.log(select.getProperties().toString());
				Window.alert("You clicked on a slice");
			}
		});
		
		pieChart.draw(dataTable, options);
		
		GWT.log("drawing piechart finished.");
	}

}
