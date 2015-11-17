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
		HashMap<String, Integer> tally = numberOfMoviesPerCountry(movies);
		String nameOfCountry; // holds movie.getName() for a single movie

		DataTable tableCountryAmount = DataTable.create();

		// Format tableCountryAmount
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
	
	/**
	 * Convert <tt>MovieCollection</tt> movies to a <tt>DataTable</tt> with the columns "Years" and "Name" .
	 * For each unique country in the <tt>MovieCollection</tt> the number of occurrences in movies will be calculated. 
	 * @param movies The <tt>MovieCollection</tt> that will be converted.
	 * @return movies as a <tt>DataTable</tt>
	 */
	public static DataTable toDataTableYearCountryAmount(MovieCollection movies){
		
		/*
		 * Change so that: 
		 * year is set by filter menu - the moviecollection already only has movies from the selected timeframe
		 * to find min / max year, use the tally thing to go through the moviecollection once and then do it again afterwards
		 * need a way to get total movies per year specifically
		 */
		
		int timeframeInYears = movies.getMaxYear() - movies.getMinYear();
		
		HashMap<String, Integer> tally = numberOfMoviesPerCountry(movies);
		String nameOfCountry; // holds movie.getName() for a single movie

		DataTable tableYearCountryAmount = DataTable.create();

		// Format tableCountryAmount
		// add row for the years
		tableYearCountryAmount.addColumn(ColumnType.NUMBER, "Years");
		
		tableYearCountryAmount.addRows(timeframeInYears);
		for (int i = 0; i < timeframeInYears; i++) {
			tableYearCountryAmount.setValue(i, 0, movies.getMinYear() + i);
		}
				
		// iterate over all keys in tally
		int columnCounter = 1; // because column 0 is the year (TEST THIS)
		Iterator<String> tallyIterator = tally.keySet().iterator();
		while (tallyIterator.hasNext()) { // while there are more countries
			nameOfCountry = tallyIterator.next();
			// add the column for the country
			tableYearCountryAmount.addColumn(ColumnType.STRING, nameOfCountry);
			
			// add the movies released in every year in the timeframe as values
			for (int rowCounter = movies.getMinYear(); rowCounter <= movies.getMaxYear(); rowCounter++) {
				// tableYearCountryAmount.setValue(rowCounter, columnCounter, tally.get(nameOfCountry)[rowCounter]); // IMPORTANT: Need to get movies released in specific year here!
			}
			columnCounter++;
		}

		return tableYearCountryAmount;
	}
	
	/**
	 * Convert <tt>MovieCollection</tt> collection to a <tt>DataTable</tt> with the columns "Name", "Erscheinungsjahr",
	 * "Filmdauer", "Genre", "Sprache" and "Land".
	 * For each entry of the collection such a row will be created. 
	 * 
	 * @param collection The <tt>MovieCollection</tt> that will be converted.
	 * @return collection as a <tt>DataTable</tt>
	 */
	
	public static DataTable toDataTableForTableComponent(MovieCollection collection){
		
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
		return dataTable;
	}
	
	/**
	 * Creates a hash map where the key is the name of a country occurring in movies 
	 * and the corresponding value is the number of Movie in movies that contain the country in their list of countries.
	 * 
	 * @param movies The collection that is searched for occurrences of country names
	 * @return HashMap where key = name of a country, value = Movie in movies that contain this country.
	 * @see MovieCollection
	 * @see Movie
	 */
	public static HashMap<String, Integer> numberOfMoviesPerCountry(MovieCollection movies){
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
		
		return tally;
	}
	
	public static HashMap<String, ArrayList<Integer>> numberOfMoviesPerCountryPerYear(MovieCollection movies){
		int timeframeInYears = movies.getMaxYear() - movies.getMinYear();
		
		HashMap<String, ArrayList<Integer>> tally = new HashMap<>(); // key: Country name, value: ArrayList of total movie count per year
		
		ArrayList<Movie> movieList = movies.getMovies();
		
		String[] countries; // holds movie.getCountries() for a single movie
		String nameOfCountry; // holds movie.getName() for a single movie
		
		for (int i = 0; i < movieList.size(); i++) { // for all movies
			countries = movieList.get(i).getCountries();
			for (int j = 0; j < countries.length; j++) {
				nameOfCountry = countries[j];

				if(tally.containsKey(nameOfCountry)){
					// add 1 to the value of the movie release year of the country.
					// need to get the release year of a movie somehow
					// tally.put(nameOfCountry, tally.get(nameOfCountry).get(movieList.get(i).getReleaseDate() - movies.getMinYear()) + 1);
				} else{
					//  fill the arraylist with zeroes and add one for the release year
					ArrayList list = new ArrayList(timeframeInYears); // doesn't that save multiple arrayLists to the same variable?
					for (int k = 0; k < timeframeInYears; k++) {
						list.add(k, 0);
					}
					// tally.put(nameOfCountry, 1);
				}
			}
		}
		
		return tally;
	}

}
