package amdb.client;

import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

import com.google.gwt.core.shared.GWT;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.table.Table;
import com.googlecode.gwt.charts.client.table.TableOptions;
import com.googlecode.gwt.charts.client.table.TablePage;

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

		// enable paging and display max. 25 rows per page
		TablePage page = TablePage.ENABLE;
		options.setPage(page);	
		options.setPageSize(25);

		// draw the table
		table.draw(dataTable, options);

		GWT.log("drawing table component finished.");

		
	}
}