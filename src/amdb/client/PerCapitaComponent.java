package amdb.client;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.gwt.core.shared.GWT;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.geochart.GeoChartColorAxis;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;
import com.googlecode.gwt.charts.client.geochart.MagnifyingGlass;

/**
 * This class contains the method to draw the per capita view.
 * @author Lukas Blumer
 * @history 2015-12-03 LB first version committed
 * @version 2015-12-03 LB 1.0
 * @responsibilities This class contains a static method used to draw the per capita view.
 */
public class PerCapitaComponent {
	/*
	 * Colors for the map as Hex or html color names.
	 * More than two axis colors can be defined, but have to be added in the definition of colorAxisHelper
	 * Found this page helpful:
	 * http://www.w3schools.com/tags/ref_colorpicker.asp
	 */
	private static final String datalessRegionColor = "white";

	private static final String backgroundColor = "#E5F7FF";
			
	/**
	 * This draws the per capita view with the specified <tt>MovieCollection</tt> into the specified <tt>GeoChart</tt>.
	 * @param geoChart
	 * @param collection
	 */
	public static void drawPerCapita(GeoChart geoChart, MovieCollection collection) {
		
		// Conversion of MovieCollection to DataTable
		DataTable dataTable = MovieCollectionConverter.toDataTablePerCountry(collection);
		
		dataTable.setColumnLabel(1, "Movies per capita * 10^5");
		
		String[] countriesToLookAt = {"United States of America", "Germany", "Japan", "Switzerland", "United Kingdom", "India", "France"};
		// source: http://www.worldometers.info/world-population/population-by-country/
		int[] countryPopulation = {322583006, 82652256, 126999808, 8157896, 63489234, 1267401849, 64641279};
		boolean wipe = true;
		
		for (int i = 0; i < dataTable.getNumberOfRows(); i++) {
			
			for (int j = 0; j < countriesToLookAt.length; j++) {
				
				// if the country is in our reference array, consult the population array and calculate per capita. also mark as not to be wiped.
				if (dataTable.getValueString(i, 0) == countriesToLookAt[j]) {
					double temp = dataTable.getValueNumber(i, 1) / countryPopulation[j] * 100000;
					dataTable.setValue(i, 1, round(temp,2));
					wipe = false;
				}
			}
			
			// wipe = true means that the country is not one of the ones we're interested in, hence set to 0.
			if (wipe) {
				dataTable.setValue(i, 1, 0);
			}
			
			wipe = true;
		}
		
		// Color countries according to movies per capita
		GeoChartOptions options = GeoChartOptions.create();
		GeoChartColorAxis geoChartColorAxis = GeoChartColorAxis.create();

		double dataTableMax = dataTable.getColumnRange(1).getMaxNumber(); // this gets me the entry in the dataTable with the highest total movie count

		geoChartColorAxis.setColors("white", "0000FF",  "0033FF",  "0065FF",  "0099FF",  "00CBFF",  "00FFFF",  "00FFCB",  "00FF99",  "00FF65",  "00FF33",  "00FF00",  "32FF00",  "65FF00",  "99FF00",  "CCFF00",  "FFFF00",  "FFCC00",  "FF9900",  "FF6600",  "FF3200",  "FF0000");
		geoChartColorAxis.setValues(0.0, 1.0, 1+0.05*dataTableMax, 1+0.1*dataTableMax, 1+0.15*dataTableMax, 1+0.2*dataTableMax, 1+0.25*dataTableMax, 1+0.3*dataTableMax, 1+0.35*dataTableMax, 1+0.4*dataTableMax, 1+0.45*dataTableMax, 1+0.5*dataTableMax, 1+0.55*dataTableMax, 1+0.6*dataTableMax, 1+0.65*dataTableMax, 1+0.7*dataTableMax, 1+0.75*dataTableMax, 1+0.8*dataTableMax, 1+0.85*dataTableMax, 1+0.9*dataTableMax, 1+0.95*dataTableMax, 1+dataTableMax);
		
		options.setColorAxis(geoChartColorAxis);
		options.setDatalessRegionColor(datalessRegionColor);
		options.setBackgroundColor(backgroundColor);
	
		// actually draw the map
		geoChart.draw(dataTable, options);

	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	
}