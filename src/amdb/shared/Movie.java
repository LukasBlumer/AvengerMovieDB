package amdb.shared;

/**
 * Elements of this class represent metadata of movies.
 * 
 * @author petrawittwer
 * @history 2015-10-31 PW first version
 * @version 2015-10-31 PW 0.1
 * @responsibilities Manages the metadata of movies.
 *
 */
public class Movie {

	private String name;
	private int releaseDate;
	private String[] genres;
	private String[] languages;
	private String[] countries;

	/**
	 * Creates a new instance of Movie with data name, releaseDate, genres, languages and countries.
	 * @param name The name of the movie.
	 * @param releaseDate The release date as year. Must be larger than 1900 and smaller than 2100
	 * @param genres The genres of the movie or void.
	 * @param languages The languages of the movie or void.
	 * @pre releaseDate > 1900 && releaseDate < 2100
	 * @post Created object has values specified by the parameters
	 */
	public Movie(String name, int releaseDate, String[] genres, String[] languages, String[] countries){
		this.name = name;
		
		// only allow movies made after 1900 and before 2100
		if(releaseDate < 2100 && releaseDate> 1900){
			this.releaseDate = releaseDate;
		} else{
			return;
		}
		this.genres = genres;
		this.languages = languages;
		this.countries = countries;
	}

	/**
	 * Returns the name of the Movie as a String.
	 * @pre true
	 * @post return value == this.name
	 * @return The value name of this Movie.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the Release Date of the Movie as an int.
	 * 
	 * @pre true
	 * @post return value == this.releaseDate
	 * @return The value releaseDate of this Movie.
	 */
	public int getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Returns the genres of the Movie as a String[].
	 * 
	 * @pre true
	 * @post return value == this.genres
	 * @return The value genres of this Movie.
	 */
	public String[] getGenres() {
		return genres;
	}

	/**
	 * Returns the languages of the Movie as a String[].
	 * 
	 * @pre true
	 * @post return value == this.languages
	 * @return The value languages of this Movie.
	 */
	public String[] getLanguages() {
		return languages;
	}

	/**
	 * Returns the countries of the Movie as a String[].
	 * 
	 * @pre true
	 * @post return value == this.countries
	 * @return The value countries of this Movie.
	 */
	public String[] getCountries() {
		return countries;
	}



}
