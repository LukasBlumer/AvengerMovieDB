package amdb.client;

import com.googlecode.gwt.charts.client.controls.filter.DateRangeFilterOptions;
import com.googlecode.gwt.charts.client.controls.filter.DateRangeFilterUi;
import com.googlecode.gwt.charts.client.format.DateFormatOptions;
import com.googlecode.gwt.charts.client.table.Table;


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
public class DateRangeFilter {

	/**
	 * Draws a <tt>DateRangeFilter</tt> with certain options containing a <tt>MovieCollection</tt>.
	 * @param dateRangeFilter
	 * @param collection
	 */
	public static void drawDateRangeFinder(DateRangeFilter dateRangeFilter, MovieCollection collection){
		
		//setup daterange slider thingy
		DateRangeFilterOptions dateRangeFilterOptions = DateRangeFilterOptions.create();
		dateRangeFilterOptions.setFilterColumnLabel("Year");
		DateRangeFilterUi dateRangeFilterUi = DateRangeFilterUi.create();
		dateRangeFilterUi.setFormat(DateFormatOptions.create("yyyy"));
		dateRangeFilterOptions.setUi(dateRangeFilterUi);
		// dateRangeFilter.setOptions(dateRangeFilterOptions); this doesn't quite work for whatever reason
		
		// create fake dataTable with getMinYear() and getMaxYear() from moviecollection (no converter needed)
		// make an entry for every year inbetween as well
		
		// create a fake table chart with the dataTable
		
		// connect the daterange slider to the fake table chart
		
		// read out the current positions of the sliders via
		// http://gwt-charts.googlecode.com/svn/site/0.9.10/apidocs/com/googlecode/gwt/charts/client/controls/filter/DateRangeFilterState.html
		
		// is there an event for the sliders being moved? Then use that. 
		// Else just an update chart button to apply those values as filters to the moviecollection
		
		// make moviebase redraw current center panel

	}
}
