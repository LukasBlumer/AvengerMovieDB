package amdb.client;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.event.PageEvent;
import com.googlecode.gwt.charts.client.event.PageHandler;
import com.googlecode.gwt.charts.client.table.Table;
import com.googlecode.gwt.charts.client.table.TableOptions;

/**
 * This class specifies a static method to draw a <tt>Table</tt> containing a <tt>MovieCollection</tt>.  
 * @author selinfabel
 * @history 2015-11-23
 * @version 2015-11-23
 * @responsibilities Provides a method to draw a {@link Table} containing a {@link MovieCollection}
 * @see MovieCollection
 * @see DataTable
 * 
 */

// https://github.com/google/gwt-charts/blob/master/gwt-charts-showcase/src/main/java/com/googlecode/gwt/charts/showcase/client/others/TableExample.java
public class TableComponent {
	/**
	 * Draws a <tt>Table</tt> with certain options on the mainpanel with the <tt>MovieCollection</tt> data.
	 * @param table The <tt>Table</tt> on the mainpanel of the website.
	 * @param collection the <tt>MovieCollection</tt> which should be visualized in the table
	 */
	public static void draw(final Table table, MovieCollection collection){
		
		GWT.log("creating dataTable for table component.");
		
		// Conversion of MovieCollection to DataTable, creating a dataTable
		final DataTable dataTable = MovieCollectionConverter.toDataTableForTableComponent(collection);
		
		GWT.log("creating dataTable for table component finished.");
		GWT.log("drawing table component.");
		
		// set options
		final TableOptions options = TableOptions.create();
		
		// odd rows are set to white, even to grey
		options.setAlternatingRowStyle(true);
		options.setShowRowNumber(true);
		options.setPage(1);	// This enables pageing in general
		
		// enables to let table be sorted for just 10000 entries 
		if (dataTable.getNumberOfRows() > 10000) {
			options.setSort("disable");
		}
		else {
			options.setSort("enable");
		}
		options.setPageSize(20);		
		table.addPageHandler(new PageHandler() {
			@Override
			public void onPage(PageEvent event) {
				// set the page as specified in the event
				options.setStartPage(event.getPage());
				// make changes visible
				table.redraw();
			}
		});
		
		// draw the table
		table.draw(dataTable, options);

		GWT.log("drawing table component finished.");

		
	}
}