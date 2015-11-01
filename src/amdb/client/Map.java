package amdb.client;

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
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.geochart.GeoChartColorAxis;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;
import com.googlecode.gwt.charts.client.geochart.MagnifyingGlass;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Map implements EntryPoint {
	private Button addStockButton = new Button("Lukas");
	private SplitLayoutPanel sPanel = new SplitLayoutPanel(5);
	
	
	/**
	 * This is the entry point method. (this goes once it's tested and implemented properly)
	 */
	public void onModuleLoad() {
		
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		 chartLoader.loadApi(new Runnable() {
		        @Override
		        public void run() {
		        	DataTable table = DataTable.create();
					drawMap(sPanel, table);
					
		        }
		 });
		 sPanel.addWest(addStockButton, 10);
		 RootPanel.get("MainContent").add(sPanel);
	}
	
	public void drawMap(SplitLayoutPanel panel, MovieCollection collection) {
		GeoChart geoChart;
		geoChart = new GeoChart();
		panel.add(geoChart);
		
		// Convert array to dataTable
		
		// here goes conversion of database to dataTable
		
		
		// this is test stuff
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addColumn(ColumnType.NUMBER, "Movies");
		dataTable.addRows(7);
		dataTable.setValue(0, 0, "Switzerland");
		dataTable.setValue(0, 1, 300);
		dataTable.setValue(1, 0, "Austria");
		dataTable.setValue(1, 1, 400);
		dataTable.setValue(2, 0, "United States");
		dataTable.setValue(2, 1, 800);
		dataTable.setValue(3, 0, "Sweden");
		dataTable.setValue(3, 1, 200);
		dataTable.setValue(4, 0, "United Kingdom");
		dataTable.setValue(4, 1, 700);
		
		// Color countries according to number of movies released
		GeoChartOptions options = GeoChartOptions.create();
		GeoChartColorAxis geoChartColorAxis = GeoChartColorAxis.create();
		
		options.setColorAxis(geoChartColorAxis);
		options.setDatalessRegionColor("white");
		options.setBackgroundColor("MediumLightBlue");
		
		// onClick event listener (cannot figure out how to do this)
		geoChart.addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent select) {
				addFilter();
			}
		});
		
		// zoom (no idea if this works)
		MagnifyingGlass zoom = MagnifyingGlass.create();
		zoom.setEnable(true);
		zoom.setZoomFactor(100.0);
		options.setMagnifyingGlass(zoom);
	
		// actually draw the map
		geoChart.draw(dataTable, options);
	}

}
