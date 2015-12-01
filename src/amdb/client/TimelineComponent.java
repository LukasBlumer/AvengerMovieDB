package amdb.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwt.charts.client.ChartType;
import com.googlecode.gwt.charts.client.ChartWrapper;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.controls.Dashboard;
import com.googlecode.gwt.charts.client.controls.filter.DateRangeFilter;
import com.googlecode.gwt.charts.client.controls.filter.DateRangeFilterOptions;
import com.googlecode.gwt.charts.client.controls.filter.DateRangeFilterState;
import com.googlecode.gwt.charts.client.controls.filter.DateRangeFilterUi;
import com.googlecode.gwt.charts.client.format.DateFormatOptions;
import com.googlecode.gwt.charts.client.table.Table;
import com.googlecode.gwt.charts.client.table.TableOptions;
import com.googlecode.gwt.charts.client.util.ChartHelper;

import amdb.shared.MovieCollection;

/**
 * This class defines a static method to draw a <tt>DateRangeFilter</tt> visualizing a <tt>MovieCollection</tt>.
 * @author selinfabel
 * @history 2015-12-06
 * @version 2015-12-06
 * @responsibilities Provides a method to draw a {@link DateRangeFilter} containing a {@link MovieCollection}
 * @see DateRangeFilter
 * @see MovieCollection
 *
 */
public class TimelineComponent {

	/**
	 * Draws a <tt>DateRangeFilter</tt> with certain options containing a <tt>MovieCollection</tt>.
	 * @param dateRangeFilter
	 * @param completeCollection
	 * @param currentCollection
	 */
	public static DateRangeFilterState drawDateRangeFilter(Dashboard dashboard, MovieCollection completeCollection, MovieCollection currentCollection){
		// setup of auxiliary stuff
		DateRangeFilter dateRangeFilter = new DateRangeFilter();
		ChartWrapper<TableOptions> tableWrapper = new ChartWrapper<TableOptions>();
		tableWrapper.setChartType(ChartType.TABLE);
		
		//setup daterange slider
		DateRangeFilterOptions dateRangeFilterOptions = DateRangeFilterOptions.create();
		dateRangeFilterOptions.setFilterColumnLabel("Year");
		DateRangeFilterUi dateRangeFilterUi = DateRangeFilterUi.create();
		dateRangeFilterUi.setFormat(DateFormatOptions.create("yyyy"));
		dateRangeFilterOptions.setUi(dateRangeFilterUi);
		
		/*
		// defining lowest and highest value
		Date minYear = new Date();
		minYear.setYear(completeCollection.getMinYear());
		Date maxYear = new Date();
		maxYear.setYear(completeCollection.getMaxYear());
		
		dateRangeFilterOptions.setMinValue(minYear);
		dateRangeFilterOptions.setMaxValue(maxYear);
		*/
		
		dateRangeFilter.setOptions(dateRangeFilterOptions);
		
		// create fake dataTable with getMinYear() and getMaxYear() from moviecollection (no converter needed)
		// make an entry for every year inbetween as well
		
		int timeframeInYears = completeCollection.getMaxYear() - completeCollection.getMinYear();
		DataTable fakeDataTable = DataTable.create();
		fakeDataTable.addColumn(ColumnType.STRING, "Years");
		
		TableOptions tableOptions = TableOptions.create();
		tableWrapper.setOptions(tableOptions);
		
		
		// setup DataTable here
		
		for (int i = 0; i < timeframeInYears; i++) {
			// add a new entry for each year
			fakeDataTable.setValue(i, 1, completeCollection.getMinYear() + i); // might have to be column 0 instead of 1?
		}
		
		// connect the daterange slider to the fake table chart
		dashboard.bind(dateRangeFilter, tableWrapper);
		dashboard.draw(fakeDataTable);
		
		// read out the current positions of the sliders via
		// http://gwt-charts.googlecode.com/svn/site/0.9.10/apidocs/com/googlecode/gwt/charts/client/controls/filter/DateRangeFilterState.html
		DateRangeFilterState dateRangeFilterState = DateRangeFilterState.create();
		dateRangeFilter.setState(dateRangeFilterState);
		
		GWT.log("got to the end of the timeline thing.");
		
		// I need to return the filter state and let the moviebase handle reading it out and updating the filters
		return dateRangeFilterState;
		
	}
}
