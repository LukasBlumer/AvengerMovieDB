package amdb.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.core.shared.GWT;
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
	public static DataTable toDataTablePerCountry(MovieCollection movies){

		if(movies == null){
			GWT.log("created empty DataTable for map component.");
			DataTable tableCountryAmount = DataTable.create();
			// add row for the names of the countries
			tableCountryAmount.addColumn(ColumnType.STRING, "Country");
			// add row for the number of movies the country has
			tableCountryAmount.addColumn(ColumnType.NUMBER, "Number of movies");
			return tableCountryAmount;
		}

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

		HashMap<String, ArrayList<Integer>> tally = numberOfMoviesPerCountryPerYear(movies);
		String nameOfCountry; // holds movie.getName() for a single movie

		DataTable tableYearCountryAmount = DataTable.create();

		// Format tableCountryAmount
		// add row for the years
		tableYearCountryAmount.addColumn(ColumnType.STRING, "Years");

		tableYearCountryAmount.addRows(timeframeInYears+1);
		for (int i = 0; i < timeframeInYears+1; i++) {
			tableYearCountryAmount.setValue(i, 0, Integer.toString(movies.getMinYear() + i));
		}

		// iterate over all keys in tally
		int columnCounter = 1; // because column 0 is the year (TEST THIS)
		Iterator<String> tallyIterator = tally.keySet().iterator();
		while (tallyIterator.hasNext()) { // while there are more countries
			nameOfCountry = tallyIterator.next();
			// add the column for the country
			tableYearCountryAmount.addColumn(ColumnType.NUMBER, nameOfCountry);

			// add the movies released in every year in the timeframe as values	
			for (int rowCounter = 0; rowCounter < timeframeInYears+1; rowCounter++) {
				tableYearCountryAmount.setValue(rowCounter, columnCounter, tally.get(nameOfCountry).get(rowCounter));
			}
			columnCounter++;
		}

		return tableYearCountryAmount;
	}

	/**
	 * Convert <tt>MovieCollection</tt> collection to a <tt>DataTable</tt> with the columns "Name", "Release Date",
	 * "Length", "Genre", "Language" and "Country".
	 * For each entry of the collection such a row will be created. 
	 * 
	 * @param collection The <tt>MovieCollection</tt> that will be converted.
	 * @return collection as a <tt>DataTable</tt>
	 */

	public static DataTable toDataTableForTableComponent(MovieCollection collection){

		DataTable dataTable = DataTable.create();

		int size = collection.getMovies().size();
		dataTable.addColumn(ColumnType.STRING, "Name");
		dataTable.addColumn(ColumnType.NUMBER, "Release Date");
		dataTable.addColumn(ColumnType.NUMBER, "Length");
		dataTable.addColumn(ColumnType.STRING, "Genre");
		dataTable.addColumn(ColumnType.STRING, "Language");
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addRows(size);

		// fill in the data in each row
		for(int i=0; i < size; i++){
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
	 * <p>
	 * Creates a hash map where the key is the name of a country occurring in movies
	 * and the corresponding value is the number of Movie in movies that contain the country in their list of countries.
	 * <p>
	 * All countries according to ALL_COUNTRIES are inserted in the hash.
	 * If a country has no movies, the value will be 0.
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

		for (int i = 0; i < ALL_COUNTRIES.length; i++) {
			tally.put(ALL_COUNTRIES[i], 0);
		}

		for (int i = 0; i < movieList.size(); i++) { // for all movies
			countries = movieList.get(i).getCountries();
			for (int j = 0; j < countries.length; j++) {
				nameOfCountry = countries[j];

				if(tally.containsKey(nameOfCountry)){ // if the tally already has the country
					// add 1 to the value of the pair <nameOfCountry, value> in the hash
					tally.put(nameOfCountry, tally.get(nameOfCountry)+1);
				} else{ // if the tally does not already contain the country
					tally.put(nameOfCountry, 1);
					GWT.log("Could not find "+ nameOfCountry);
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
				if (movieList.get(i).getReleaseDate() != -1) {
					int index = movieList.get(i).getReleaseDate() - movies.getMinYear();

					if(tally.containsKey(nameOfCountry)){
						// add 1 to the value of the movie release year of the country.
						// need to get the release year of a movie somehow
						tally.get(nameOfCountry).set(index, (int)tally.get(nameOfCountry).get(index) + 1);
					} else{
						//  fill the arraylist with zeroes and add one for the release year
						ArrayList<Integer> list = new ArrayList<Integer>(); // doesn't that save multiple arrayLists to the same variable?
						for (int k = 0; k < timeframeInYears+1; k++) {
							list.add(k, 0);
						}
						list.set(index, (int)list.get(index) + 1); // increment list[index] by 1
						tally.put(nameOfCountry, list);
					}
				}
			}
		}

		return tally;
	}


	/**
	 * List of all countries according to ISO 3166-2 standard. Slightly changed to accomodate the source file.
	 */
	public static final String[] ALL_COUNTRIES = new String[] {
		"Afghanistan",
		"Albania",
		"Algeria",
		"Andorra",
		"Angola",
		"Antigua and Barbuda",
		"Argentina",
		"Armenia",
		"Australia",
		"Austria",
		"Azerbaijan",
		"Bahamas",
		"Bahrain",
		"Bangladesh",
		"Barbados",
		"Belarus",
		"Belgium",
		"Belize",
		"Benin",
		"Bhutan",
		"Bolivia",
		"Bosnia and Herzegovina",
		"Botswana",
		"Brazil",
		"Brunei",
		"Bulgaria",
		"Burkina Faso",
		"Burundi",
		"Cambodia",
		"Cameroon",
		"Canada",
		"Cape Verde",
		"Central African Republic",
		"Chad",
		"Chile",
		"China",
		"Colombia",
		"Congo",
		"Comoros",
		"Costa Rica",
		"Cote d'Ivoire",
		"Croatia",
		"Cuba",
		"Cyprus",
		"Czech Republic",
		"Denmark",
		"Democratic Republic of the Congo",
		"Djibouti",
		"Dominica",
		"Dominican Republic",
		"Ecuador",
		"Egypt",
		"El Salvador",
		"Equatorial Guinea",
		"Eritrea",
		"Estonia",
		"Ethiopia",
		"Fiji",
		"Finland",
		"France",
		"Gabon",
		"Gambia",
		"Georgia",
		"Germany",
		"Ghana",
		"Greece",
		"Grenada",
		"Guatemala",
		"Guinea",
		"Guinea-Bissau",
		"Guyana",
		"Haiti",
		"Honduras",
		"Hungary",
		"Iceland",
		"India",
		"Indonesia",
		"Iran",
		"Iraq",
		"Ireland",
		"Israel",
		"Italy",
		"Jamaica",
		"Japan",
		"Jordan",
		"Kazakhstan",
		"Kenya",
		"Kiribati",
		"North Korea",
		"South Korea",
		"Kuwait",
		"Kyrgyzstan",
		"Laos",
		"Latvia",
		"Lebanon",
		"Lesotho",
		"Liberia",
		"Libya",
		"Liechtenstein",
		"Lithuania",
		"Luxembourg",
		"Macedonia",
		"Madagascar",
		"Malawi",
		"Malaysia",
		"Maldives",
		"Mali",
		"Malta",
		"Marshall Islands",
		"Mauritania",
		"Mauritius",
		"Mexico",
		"Micronesia",
		"Moldova",
		"Monaco",
		"Mongolia",
		"Montenegro",
		"Morocco",
		"Mozambique",
		"Myanmar (Burma)",
		"Namibia",
		"Nauru",
		"Nepal",
		"Netherlands",
		"New Zealand",
		"Nicaragua",
		"Niger",
		"Nigeria",
		"Norway",
		"Oman",
		"Pakistan",
		"Palau",
		"Panama",
		"Papua New Guinea",
		"Paraguay",
		"Peru",
		"Philippines",
		"Poland",
		"Portugal",
		"Qatar",
		"Romania",
		"Russia",
		"Rwanda",
		"Saint Kitts and Nevis",
		"Saint Lucia",
		"Saint Vincent and the Grenadines",
		"Samoa",
		"San Marino",
		"Sao Tome and Principe",
		"Saudi Arabia",
		"Senegal",
		"Serbia",
		"Seychelles",
		"Sierra Leone",
		"Singapore",
		"Slovakia",
		"Slovenia",
		"Solomon Islands",
		"Somalia",
		"South Africa",
		"Spain",
		"Sri Lanka",
		"Sudan",
		"South Sudan",
		"Suriname",
		"Swaziland",
		"Sweden",
		"Switzerland",
		"Syria",
		"Tajikistan",
		"Tanzania",
		"Thailand",
		"Timor-Leste (East Timor)",
		"Togo",
		"Tonga",
		"Trinidad and Tobago",
		"Tunisia",
		"Turkey",
		"Turkmenistan",
		"Tuvalu",
		"Uganda",
		"Ukraine",
		"United Arab Emirates",
		"United Kingdom",
		"United States of America",
		"Uruguay",
		"Uzbekistan",
		"Vanuatu",
		"Vatican City",
		"Venezuela",
		"Vietnam",
		"Yemen",
		"Zambia",
		"Zimbabwe",
		"Abkhazia",
		"Taiwan",
		"Nagorno-Karabakh",
		"Northern Cyprus",
		"Pridnestrovie (Transnistria)",
		"Somaliland",
		"South Ossetia",
		"Ashmore and Cartier Islands",
		"Christmas Island",
		"Cocos (Keeling) Islands",
		"Coral Sea Islands",
		"Heard Island and McDonald Islands",
		"Norfolk Island",
		"New Caledonia",
		"French Polynesia",
		"Mayotte",
		"Saint Barthelemy",
		"Saint Martin",
		"Saint Pierre and Miquelon",
		"Wallis and Futuna",
		"French Southern and Antarctic Lands",
		"Clipperton Island",
		"Bouvet Island",
		"Cook Islands",
		"Niue",
		"Tokelau",
		"Guernsey",
		"Isle of Man",
		"Jersey",
		"Anguilla",
		"Bermuda",
		"British Indian Ocean Territory",
		"British Sovereign Base Areas",
		"British Virgin Islands",
		"Cayman Islands",
		"Falkland Islands (Islas Malvinas)",
		"Gibraltar",
		"Montserrat",
		"Pitcairn Islands",
		"Saint Helena",
		"South Georgia & South Sandwich Islands",
		"Turks and Caicos Islands",
		"Northern Mariana Islands",
		"Puerto Rico",
		"American Samoa",
		"Baker Island",
		"Guam",
		"Howland Island",
		"Jarvis Island",
		"Johnston Atoll",
		"Kingman Reef",
		"Midway Islands",
		"Navassa Island",
		"Palmyra Atoll",
		"U.S. Virgin Islands",
		"Wake Island",
		"Hong Kong",
		"Macau",
		"Faroe Islands",
		"Greenland",
		"French Guiana",
		"Guadeloupe",
		"Martinique",
		"Reunion",
		"Aland",
		"Aruba",
		"Netherlands Antilles",
		"Svalbard",
		"Ascension",
		"Tristan da Cunha",
		"Australian Antarctic Territory",
		"Ross Dependency",
		"Peter I Island",
		"Queen Maud Land",
		"British Antarctic Territory"
	};


}
