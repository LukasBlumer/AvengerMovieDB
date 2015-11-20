package amdb.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


/**
 * Represents a collection of objects of the type <tt>Movie</tt>. The collection can be filtered by the attributes genre, country, language or a range of years.
 * 
 * @author petrawittwer
 * @history 2015-10-31 PW first version
 * @version 2015-10-31 PW 0.1
 * @responsibilities This class executes all instructions that directly influence the collection of movies	
 * 
 * @see Movie
 */
@SuppressWarnings("serial")
public class MovieCollection implements Serializable {
	/**
	 * Contains the collection of Movies.
	 * Content should never be changed.
	 * @see Movie
	 */
	private ArrayList<Movie> movies; 
	private int minYear= Integer.MAX_VALUE, maxYear = Integer.MIN_VALUE;
	private TreeMap<String, String> countries, genres, languages;


	/**
	 * Creates a new MovieCollection instance.
	 * 
	 * @pre true
	 * @post movies != null
	 */
	public MovieCollection(){
		// initialize the collection that holds the elements of type Movie
		movies = new ArrayList<Movie>();

		countries = new TreeMap<String, String>();
		genres = new TreeMap<String, String>();
		languages = new TreeMap<>();
	}

	/**
	 * Creates a MovieCollection from an existing ArrayList<Movie>.
	 * Calculates minYear and maxYear.
	 * 
	 * @param movies which contains the movies a new MovieCollection should be created from.
	 * @pre true
	 * @post this.movies == movies && minYear = smallest value for releaseDate larger than -1 && maxYear = largest value for releaseDate
	 */
	public MovieCollection(ArrayList<Movie> movies){
		this.movies = movies;
		setMinMaxYear();
		setCountriesGenresLanguages();
	}

	/**
	 * Adds a movie to the collection of Movies.
	 * Updates minYear and maxYear.
	 * 
	 * @param movie
	 * @pre this.movies != null
	 * @post this.movies.contains(movie)
	 */
	public void addMovie(Movie movie){
		movies.add(movie);
		
		if(isNewMaxYear(movie.getReleaseDate())){
			maxYear = movie.getReleaseDate();
		}
		if(isNewMinYear(movie.getReleaseDate())){
			minYear = movie.getReleaseDate();
		}
		
		for (String country : movie.getCountries()) {
			countries.put(country, country);
		}
		for (String genre : movie.getGenres()) {
			genres.put(genre, genre);
		}
		for (String language : movie.getLanguages()) {
			languages.put(language, language);
		}
	}

	/**
	 * Returns the collection of movies as an ArrayList<Movie>.
	 * 
	 * @pre this.movies != NULL
	 * @post true
	 * @return The collection of movies as an ArrayList<Movie>
	 */
	public ArrayList<Movie> getMovies(){
		return movies;
	}	

	/**
	 * Returns a new MovieCollection created from elements in movies that have a releaseDate larger or equal to start and smaller or equal to end.
	 * 
	 *  @param start The lower bound for the range of years. 
	 *  @param end The upper bound for the range of years.
	 *  @pre this.movies != NULL 
	 *  @post currentMovies only holds Movies with start <= releaseDate <= end.
	 *  @return A new movieCollection that only contains movies with start <= releaseDate <= end
	 */
	public MovieCollection filterByYear(int start, int end){
		ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
		Movie currentMovie;

		// for all movies in movies check if it's release date is within the range
		for (int i = 0; i < movies.size(); i++) {
			currentMovie = movies.get(i);
			if(currentMovie.getReleaseDate() >= start && currentMovie.getReleaseDate() <= end){
				filteredMovies.add(currentMovie);
			}
		}

		return new MovieCollection(filteredMovies);
	}


	/**
	 * Returns a new MovieCollection created from elements in movies that contain genre in their attribute genres.
	 * 
	 * @param genre The genre that should be contained in all elements of the returned MovieCollection's Movies' array of genre
	 * @pre this.movies != NULL
	 * @post foreach movie m in movies: genre is in m.getGenres()
	 * @return A new MovieCollection that only contains elements that contain genre in their attribute genre
	 */
	public MovieCollection filterByGenre(String genre){
		ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
		Movie currentMovie;
		String[] genres;

		for (int i = 0; i < movies.size(); i++) {
			currentMovie = movies.get(i);
			genres = currentMovie.getGenres();
			for (int j = 0; j < genres.length; j++) {
				if(genres[j] == genre){
					filteredMovies.add(currentMovie);
					j = genres.length;  // continue with another movie
				}
			}
		}

		return new MovieCollection(filteredMovies);		
	}

	/**
	 * Returns a new MovieCollection created from elements in movies that contain language in their attribute language
	 * 
	 * @param language The language that should be contained in all elements of the returned MovieCollection's Movies' array of language
	 * @pre this.movies != NULL
	 * @post foreach movie m in movies: language is in m.getLanguages()
	 * @return A new MovieCollection that only contains elements that contain language in their attribute languages
	 */
	public MovieCollection filterByLanguage(String language){
		ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

		Movie currentMovie;
		String[] languages;

		for (int i = 0; i < movies.size(); i++) {
			currentMovie = movies.get(i);
			languages = currentMovie.getLanguages();
			for (int j = 0; j < languages.length; j++) {
				if(languages[j] == language){
					filteredMovies.add(currentMovie);
					j = languages.length;  // continue with another movie
				}
			}
		}		

		return new MovieCollection(filteredMovies);
	}

	/**
	 * Returns a new MovieCollection created from elements in movies that contain country in their ArrayList of countries.
	 * 
	 * @param country The country that should be contained in all elements of the returned MovieCollection's Movies' array of countries
	 * @pre this.movies != NULL
	 * @post foreach movie m in movies: country is in m.getCountries()
	 * @return A new MovieCollection that only contains elements that contain country in their attribute countries
	 * 
	 */
	public MovieCollection filterByCountry(String country){

		ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

		Movie currentMovie;
		String[] countries;

		for (int i = 0; i < movies.size(); i++) {
			currentMovie = movies.get(i);
			countries = currentMovie.getCountries();
			for (int j = 0; j < countries.length; j++) {
				if(countries[j] == country){
					filteredMovies.add(currentMovie);
					j = countries.length;  // continue with another movie
				}
			}
		}		

		return new MovieCollection(filteredMovies);
	}

	/**
	 * Returns a new MovieCollection created from movies in movies that
	 * have a length larger than or equal than minLength and smaller than or equal to the maxLength.
	 * 
	 * @param minLength The lower bound for the range of length
	 * @param maxLength The upper bound for the range of length
	 * @return A new movieCollection that only contains movies with minLength <= length <= maxLength
	 */
	public MovieCollection filterByLengthRange(int minLength, int maxLength){
		ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

		Movie currentMovie;
		float length;

		for (int i = 0; i < movies.size(); i++) {
			currentMovie = movies.get(i);
			length = currentMovie.getLength();

			if(length >= minLength && length <= maxLength){
				filteredMovies.add(currentMovie);
			}

		}

		return new MovieCollection(filteredMovies);
	}

	/**
	 * Returns a new MovieCollection created from movies in movies that
	 * have a length smaller than or equal to maxLength.
	 * 
	 * @param maxLength The upper bound for the range of length
	 * @pre this.movies != null
	 * @post there are no movies in the returned collection with length larger than maxLength
	 * @return A new movieCollection that only contains movies with length <= maxLength
	 */
	public MovieCollection filterByMaxLength(int maxLength){
		ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

		Movie currentMovie;
		float length;

		for (int i = 0; i < movies.size(); i++) {
			currentMovie = movies.get(i);
			length = currentMovie.getLength();

			if(length <= maxLength){
				filteredMovies.add(currentMovie);
			}	
		}
		return new MovieCollection(filteredMovies);
	}

	/**
	 * Returns a new MovieCollection created from movies in movies that
	 * have a length larger than or equal to minLength.
	 * 
	 * @param minLength The lower bound for the range of length
	 * @pre this.movies != null
	 * @post there are no movies in the returned collection with length smaller than minLength
	 * @return A new movieCollection that only contains movies with length >= minLength
	 */
	public MovieCollection filterByMinLength(int minLength){
		ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

		Movie currentMovie;
		float length;

		for (int i = 0; i < movies.size(); i++) {
			currentMovie = movies.get(i);
			length = currentMovie.getLength();

			if(length >= minLength){
				filteredMovies.add(currentMovie);
			}	
		}
		return new MovieCollection(filteredMovies);
	}

	/**
	 * Traverses movies and finds the smallest value for year larger than -1 and sets it as minYear,
	 * and finds the largest value for year and sets it as maxYear.
	 */
	private void setMinMaxYear(){
		int year;
		for (int i = 0; i < movies.size(); i++) {
			year = movies.get(i).getReleaseDate();
			if(isNewMaxYear(year)){
				maxYear = year;
			}
			if(isNewMinYear(year)){
				minYear = year;
			}
		}
	}

	/**
	 * Computes whether year is smaller than minYear but larger than -1.
	 * 
	 * @param year The value to be compared
	 * @return True if year < minYear && year > -1 False otherwise
	 */
	private boolean isNewMinYear(int year){
		if(year < minYear && year > -1){
			return true;
		}
		return false;
	}

	/**
	 * Computes whether year is smaller than minYear but larger than -1.
	 * 
	 * @param year The value to be compared
	 * @return True if year < minYear && year > -1 False otherwise
	 */
	private boolean isNewMaxYear(int year){
		if(year > maxYear){
			return true;
		}
		return false;
	}

	/**
	 * Return minYear.
	 * 
	 * @pre movies != null
	 * @post returned value == minYear
	 * @return The value minYear.
	 */
	public int getMinYear(){
		return minYear;
	}
	/**
	 * Return maxYear.
	 * @pre 
	 * @post
	 * @return The value maxYear.
	 */
	public int getMaxYear(){
		return maxYear;
	}

	/**
	 * This method traverses <code>movies</code> and puts all 
	 * values for language, genre and country occurring in any
	 * <code>Movie</code> in <code>movies</code> in it's 
	 * respective <code>TreeMap</code>.
	 */
	private void setCountriesGenresLanguages(){
		countries = new TreeMap<String, String>();
		genres = new TreeMap<String, String>();
		languages = new TreeMap<>();

		for (Movie movie : movies) {
			for (String country : movie.getCountries()) {
				countries.put(country, country);
			}
			for (String genre : movie.getGenres()) {
				genres.put(genre, genre);
			}
			for (String language : movie.getLanguages()) {
				languages.put(language, language);
			}
		}
	}

	/**
	 * Returns all values that occur as country
	 * in <code>movies</code>.
	 * @return All values that occur as country
	 * in <code>movies</code>.
	 */
	public String[] getAllCountries(){
		return hashMapToStringArray(countries);
	}

	/**
	 * Returns all values that occur as genre
	 * in <code>movies</code>.
	 * @return All values that occur as genre
	 * in <code>movies</code>.
	 */
	public String[] getAllGenres(){
		return hashMapToStringArray(genres);

	}

	/**
	 * Returns all values that occur as language
	 * in <code>movies</code>.
	 * @return All values that occur as language
	 * in <code>movies</code>.
	 */
	public String[] getAllLanguages(){
		return hashMapToStringArray(languages);
	}

	/**
	 * Helper method that converts a <code>Map<String,String></code> or a class that
	 * implements <code>Map</code> to a String[].
	 * @param map The map whose values are supposed to be converted
	 * @return A String[] containing the values <code>map</code>.
	 */
	private String[] hashMapToStringArray(Map<String, String> map){
		String[] result = new String[map.size()];
		int index = 0;
		for (String value : map.values()) {
			result[index++] = value;
		}
		return result;

	}
}

