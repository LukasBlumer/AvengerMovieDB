package amdb.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable; 

/**
 * This class contains static methods that convert a <tt>MovieCollection</tt> to a <tt>DataTable</tt>.
 * The format of the resulting <tt>DataTable</tt> is specified in the comment of each method.
 * @author petrawittwer
 * @history 2015-11-1 First Version
 * @version 2015-11-1 0.1
 * @responsibilities Provide methods that convert a {@link MovieCollection} to a {@link DataTable}.
 * @see MovieCollection
 * @see DataTable
 */
public class MovieCollectionConverter {
	
	/**
	 * Convert <tt>MovieCollection</tt> movies to a <tt>DataTable</tt> with the columns "Name" and "Number of movies".
	 * For each unique country in the <tt>MovieCollection</tt> the number of occurrences in movies will be calculated. 
	 * @param movies The <tt>MovieCollection</tt> that will be converted.
	 * @return movies as a <tt>DataTable</tt>
	 */
	public static DataTable toDataTableCountryAmount(MovieCollection movies){
		HashMap<String, Integer> tally = new HashMap<>(); // key: Country name, value: Movies per country
		ArrayList<Movie> movieList = movies.getMovies();
		String[] countries; // holds movie.getCountries() for a single movie
		String nameOfCountry; // holds movie.getName() for a single movie
		for (int i = 0; i < movieList.size(); i++) { // for all movies
			countries = movieList.get(i).getCountries();
			for (int j = 0; j < countries.length; j++) {
				nameOfCountry = countries[j];

				if(tally.containsKey(nameOfCountry)){ // if the tally already has the country
					// add 1 to the value of the pair <nameOfCountry, value> in the hash
					tally.put(nameOfCountry, tally.get(nameOfCountry)+1);
				} else{ // if the tally does not already contain the country
					tally.put(nameOfCountry, 1);
				}
			}
		}


		DataTable tableCountryAmount = DataTable.create();

		// add row for the names of the countries
		tableCountryAmount.addColumn(ColumnType.STRING, "Country");
		// add row for the number of movies the country has
		tableCountryAmount.addColumn(ColumnType.NUMBER, "Number of movies");
		// add as many rows as there are unique countries
		tableCountryAmount.addRows(tally.size());
		
		// iterate over all keys in tally
		int rowCounter = 0;
		Iterator<String> tallyIterator = tally.keySet().iterator();
		while (tallyIterator.hasNext()) { // while there are more countries
			nameOfCountry = tallyIterator.next();
			// add country to the row
			tableCountryAmount.setValue(rowCounter, 0, nameOfCountry);
			tableCountryAmount.setValue(rowCounter, 1, tally.get(nameOfCountry));
			rowCounter++;
		}

		return tableCountryAmount;
	}

}
