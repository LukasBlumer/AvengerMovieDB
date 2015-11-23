package amdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

import amdb.shared.Movie;
import amdb.shared.MovieCollection;
import amdb.shared.MovieCollectionConverter;

public class MovieCollectionConverterTest {
	static Movie m1 = new Movie("The Happening",94, 1990,new String[] {"Horror", "Romance", "Adventure"}, new String []{"Urdu", "Polish"}, new String[]{"Pakistan", "Italy"});
	static Movie m2 = new Movie("That new movie",102, 2010, new String[] {"Vampire fanfiction"}, new String[] {"English", "Gibberish"}, new String[]{"Monte Carlo", "Spain", "Sweden"});
	static Movie m3 = new Movie("Fun with knives",47, 1988, new String[] {"Family", "Educational"}, new String[] {"Spanish"}, new String[]{"United States of America"});
	static Movie m4 = new Movie("Watching grass",304, 1973, new String[] {"Documentary"}, new String[] {"English"}, new String[]{"Spain", "Sweden"});
	static Movie m5 = new Movie("Watching grass grow",370, 1974, new String[] {"Documentary"}, new String[] {"English"}, new String[]{"Spain", "Sweden"});

	static ArrayList<Movie> movieArrayList = new ArrayList<Movie>(Arrays.asList(m1,m2,m3,m4,m5));
	static MovieCollection testMovies = new MovieCollection(movieArrayList);


	@Test
	public void numberOfMoviesPerCountryTest(){
		HashMap<String, Integer> testMap = MovieCollectionConverter.numberOfMoviesPerCountry(testMovies);
		
		// ALL_COUNTRIES.length = 269 but Monte Carlo is not one of them, hence +1
		assertEquals(MovieCollectionConverter.ALL_COUNTRIES.length+1, testMap.size());
		assertEquals(3, (int) testMap.get("Sweden"));
		assertEquals(1, (int) testMap.get("Italy"));
		assertEquals(0, (int) testMap.get("Germany"));
	}

}
