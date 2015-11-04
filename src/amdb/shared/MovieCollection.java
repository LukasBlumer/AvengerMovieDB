package amdb.shared;

import java.io.Serializable;
import java.util.ArrayList;


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


	/**
	 * Creates a new MovieCollection instance.
	 * 
	 * @pre true
	 * @post movies != null
	 */
	public MovieCollection(){
		// initialize the collection that holds the elements of type Movie
		movies = new ArrayList<Movie>();
		movies.add(new Movie("The Happening", 1990,new String[] {"Horror", "Romance", "Adventure"}, new String []{"Urdu", "Polish"}, new String[]{"Pakistan", "Italy"}));
	}

	/**
	 * Creates a MovieCollection from an existing ArrayList<Movie>.
	 * 
	 * @param movies which contains the movies a new MovieCollection should be created from.
	 * @pre true
	 * @post this.movies == movies
	 */
	public MovieCollection(ArrayList<Movie> movies){
		this.movies = movies;
	}
	
	/**
	 * Adds a movie to the collection of Movies.
	 * 
	 * @param movie
	 * @pre this.movies != null
	 * @post this.movies.contains(movie)
	 */
	public void addMovie(Movie movie){
		movies.add(movie);
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
	 *  
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
	 * @deprecated
	 * @return
	 */
	public int getMinYear(){
		return 0;
	}
	/**
	 * @deprecated
	 * @return
	 */
	public int getMaxYear(){
		return 10000;
	}

}

