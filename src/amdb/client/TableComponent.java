package amdb.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.table.Table;
import com.googlecode.gwt.charts.client.table.TableOptions;
import com.googlecode.gwt.charts.client.table.*;

import amdb.shared.Movie;
import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

/*
 * @author selinfabel
 * 
 * diese Seite hat mir sehr geholfen, gibt Beispiele für alle Elemente der gwt-charts-library:
 * https://github.com/google/gwt-charts/blob/master/gwt-charts-showcase/src/main/java/com/googlecode/gwt/charts/showcase/client/others/TableExample.java
 * 
 * 
 */

public class TableComponent {
	
	public static void draw(Table table, MovieCollection collection){
		// Create a data table
		DataTable dataTable = MovieCollectionConverter.toDataTableForTableComponent(collection);
		
		// set options
		TableOptions options = TableOptions.create();
		// odd rows are set to white, even to grey
		options.setAlternatingRowStyle(true);
		options.setShowRowNumber(true);
//		options.setPage(1);	// ??
		options.setSort("enable");
//		options.setPageSize(20);

		
		// draw the table
		table.draw(dataTable, options);
		
	}
}