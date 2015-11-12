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
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Name");
		dataTable.addColumn(ColumnType.NUMBER, "Erscheinungsjahr");
		dataTable.addColumn(ColumnType.NUMBER, "Filmdauer");
		dataTable.addColumn(ColumnType.STRING, "Genre");
		dataTable.addColumn(ColumnType.STRING, "Sprache");
		dataTable.addColumn(ColumnType.STRING, "Land");
		dataTable.addRows(100);
		
		// fill in the data in each row
		for(int i=0; i<100; i++){
			dataTable.setCell(i, 0, collection.getMovies().get(i).getName());
			
			if(collection.getMovies().get(i).getReleaseDate() == -1){
				dataTable.setCell(i, 1, -1, "unknown");		}
			else {
			dataTable.setCell(i, 1, collection.getMovies().get(i).getReleaseDate());	}
			
			if(collection.getMovies().get(i).getLength() == -1){
				dataTable.setCell(i, 2, -1, "unknown"); }
			else {
			dataTable.setCell(i, 2, collection.getMovies().get(i).getLength());	}

			String[] gen = collection.getMovies().get(i).getGenres();
			String g = gen[0];
			for(int j=1; j< gen.length; j++){
					g = g + ", " + gen[j];
				}

			dataTable.setCell(i, 3, g);
			
			String[] lan = collection.getMovies().get(i).getLanguages();
			String l = lan[0];
			for(int j=1; j< lan.length; j++){
					l = l + ", " + lan[j];
				}
			dataTable.setCell(i, 4, l);
			
			String[] arr = collection.getMovies().get(i).getCountries();
			String c = arr[0];
			for(int j=1; j< arr.length; j++){
					c = c + ", " + arr[j];
				}
				dataTable.setCell(i, 5, c); 
		}
		
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