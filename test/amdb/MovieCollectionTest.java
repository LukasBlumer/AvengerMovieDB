package amdb;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

import amdb.shared.Movie;
import amdb.shared.MovieCollection;

public class MovieCollectionTest {
	static Movie m1 = new Movie("The Happening",94, 1990,new String[] {"Horror", "Romance", "Adventure"}, new String []{"Urdu", "Polish"}, new String[]{"Pakistan", "Italy"});
	static Movie m2 = new Movie("That new movie",102, 2010, new String[] {"Vampire fanfiction"}, new String[] {"English", "Gibberish"}, new String[]{"Monte Carlo", "Spain", "Sweden"});
	static Movie m3 = new Movie("Fun with knives",47, 1988, new String[] {"Family", "Educational"}, new String[] {"Spanish"}, new String[]{"United States of America"});
	static Movie m4 = new Movie("Watching grass",304, 1973, new String[] {"Documentary"}, new String[] {"English"}, new String[]{"Spain", "Sweden"});
	static Movie m5 = new Movie("Watching grass grow",370, 1974, new String[] {"Documentary"}, new String[] {"English"}, new String[]{"Spain", "Sweden"});

	static ArrayList<Movie> movieArrayList = new ArrayList<Movie>(Arrays.asList(m1,m2));


	@Test
	public void testDefaultConstructor(){
		MovieCollection testCollection = new MovieCollection();
		assertNotEquals(null, testCollection);
	}

	@Test
	public void testArrayListConstructor(){
		MovieCollection testCollection = new MovieCollection(movieArrayList);
		assertEquals(2, testCollection.getMovies().size());
		assertEquals(m1, testCollection.getMovies().get(0));
		assertEquals(m2, testCollection.getMovies().get(1));
	}

	@Test
	public void testAddMovie(){
		MovieCollection testCollection = new MovieCollection();
		assertEquals(0, testCollection.getMovies().size());
		testCollection.addMovie(m1);
		assertEquals(1, testCollection.getMovies().size());
		assertEquals(m1, testCollection.getMovies().get(0));

		testCollection.addMovie(m2);
		assertEquals(2, testCollection.getMovies().size());
		assertEquals(m2, testCollection.getMovies().get(1));
	}

	@Test
	public void testFilterByCountry() {
		MovieCollection testCollection = new MovieCollection();
		testCollection.addMovie(m1);
		testCollection.addMovie(m2);
		testCollection.addMovie(m4);
		testCollection.addMovie(m5);

		MovieCollection filteredByCountry = testCollection.filterByCountry("Sweden");
		assertEquals(3, filteredByCountry.getMovies().size());
		assertEquals(m2, filteredByCountry.getMovies().get(0));
		assertEquals(m4, filteredByCountry.getMovies().get(1));
		assertEquals(m5, filteredByCountry.getMovies().get(2));
	}

	@Test
	public void testFilterByGenre() {
		MovieCollection testCollection = new MovieCollection();
		testCollection.addMovie(m1);
		testCollection.addMovie(m2);
		testCollection.addMovie(m4);
		testCollection.addMovie(m5);

		MovieCollection filteredByGenre = testCollection.filterByGenre("Documentary");

		assertEquals(2, filteredByGenre.getMovies().size());
		assertEquals(m4, filteredByGenre.getMovies().get(0));
		assertEquals(m5, filteredByGenre.getMovies().get(1));

	}

	@Test
	public void testFilterByLanguage() {
		MovieCollection testCollection = new MovieCollection();
		testCollection.addMovie(m1);
		testCollection.addMovie(m2);
		testCollection.addMovie(m3);
		testCollection.addMovie(m4);
		testCollection.addMovie(m5);

		MovieCollection filteredByLanguage = testCollection.filterByLanguage("English");

		assertEquals(3, filteredByLanguage.getMovies().size());
		assertTrue(filteredByLanguage.getMovies().contains(m2));
		assertTrue(filteredByLanguage.getMovies().contains(m4));
		assertTrue(filteredByLanguage.getMovies().contains(m5));
	}

	@Test
	public void testFilterByLengthRange(){
		MovieCollection testCollection = new MovieCollection();
		testCollection.addMovie(m1);
		testCollection.addMovie(m2);
		testCollection.addMovie(m3);
		testCollection.addMovie(m4);
		testCollection.addMovie(m5);


		// regular filtering, no corner case
		MovieCollection filteredByLenghtRange1 = testCollection.filterByLengthRange(101, 103);

		assertEquals(1, filteredByLenghtRange1.getMovies().size());
		assertEquals(m2, filteredByLenghtRange1.getMovies().get(0));

		// min length and max length are equal to the movies length's
		MovieCollection filteredByLengthRange2 = testCollection.filterByLengthRange(47, 94);

		assertEquals(2, filteredByLengthRange2.getMovies().size());
		assertEquals(m1, filteredByLengthRange2.getMovies().get(0));
		assertEquals(m3, filteredByLengthRange2.getMovies().get(1));
	}

	@Test
	public void testFilterByMinLength(){
		MovieCollection testCollection = new MovieCollection();
		testCollection.addMovie(m1);
		testCollection.addMovie(m2);
		testCollection.addMovie(m3);
		testCollection.addMovie(m4);
		testCollection.addMovie(m5);

		// non cornercase
		MovieCollection filteredByMinLenght1 = testCollection.filterByMinLength(100);

		assertEquals(3, filteredByMinLenght1.getMovies().size());
		assertEquals(m2, filteredByMinLenght1.getMovies().get(0));
		assertEquals(m4, filteredByMinLenght1.getMovies().get(1));
		assertEquals(m5, filteredByMinLenght1.getMovies().get(2));

		// only one movie, where minLength is the length of the movie
		MovieCollection filteredByMinLenght2 = testCollection.filterByMinLength(370);

		assertEquals(1, filteredByMinLenght2.getMovies().size());
		assertEquals(m5, filteredByMinLenght2.getMovies().get(0));

		// no movie longer than minLenght
		MovieCollection filteredByMinLenght3 = testCollection.filterByMinLength(371);

		assertEquals(0, filteredByMinLenght3.getMovies().size());

	}

	@Test
	public void testFilterByMaxLength(){
		MovieCollection testCollection = new MovieCollection();
		testCollection.addMovie(m1);
		testCollection.addMovie(m2);
		testCollection.addMovie(m3);
		testCollection.addMovie(m4);
		testCollection.addMovie(m5);

		// non cornercase
		MovieCollection filteredByMaxLenght1 = testCollection.filterByMaxLength(100);

		assertEquals(2, filteredByMaxLenght1.getMovies().size());
		assertEquals(m1, filteredByMaxLenght1.getMovies().get(0));
		assertEquals(m3, filteredByMaxLenght1.getMovies().get(1));

		// only one movie, where maxLength is the length of the movie
		MovieCollection filteredByMaxLenght2 = testCollection.filterByMaxLength(47);

		assertEquals(1, filteredByMaxLenght2.getMovies().size());
		assertEquals(m3, filteredByMaxLenght2.getMovies().get(0));

		// no movie shorter than maxLength
		MovieCollection filteredByMaxLenght3 = testCollection.filterByMaxLength(46);

		assertEquals(0, filteredByMaxLenght3.getMovies().size());

	}

	@Test
	public void testFilterByYear() {

		MovieCollection testCollection = new MovieCollection();
		testCollection.addMovie(m1);
		testCollection.addMovie(m2);
		testCollection.addMovie(m3);
		testCollection.addMovie(m4);
		testCollection.addMovie(m5);

		MovieCollection filteredByYear = testCollection.filterByYear(1987, 1990);
		assertEquals(2, filteredByYear.getMovies().size());
		assertTrue(filteredByYear.getMovies().contains(m1));
		assertTrue(filteredByYear.getMovies().contains(m3));
	}
	
	@Test
	public void testGetMinYear(){
		MovieCollection testCollection = new MovieCollection();
		testCollection.addMovie(m1);
		testCollection.addMovie(m2);
		
		assertEquals(1990, testCollection.getMinYear());
	}
	
	@Test
	public void testGetMaxYear(){
		MovieCollection testCollection = new MovieCollection();
		testCollection.addMovie(m1);
		testCollection.addMovie(m2);
		
		assertEquals(2010, testCollection.getMaxYear());
		
	}
	
	@Test
	public void testGetAllLanguages(){
		MovieCollection testCollection = new MovieCollection(movieArrayList);
		String[] result = testCollection.getAllLanguages();
		assertNotEquals(result, null);
		assertEquals(4, result.length);
		assertArrayEquals(new String[]{"English", "Gibberish", "Polish", "Urdu"}, result);
	}
	
	@Test
	public void testGetAllGenres(){
		MovieCollection testCollection = new MovieCollection(movieArrayList);
		String[] result = testCollection.getAllGenres();
		assertNotEquals(result, null);
		assertEquals(4, result.length);
		assertArrayEquals(new String[]{"Adventure", "Horror", "Romance", "Vampire fanfiction"}, result);
	}
	
	@Test
	public void testgetAllCountries(){
		MovieCollection testCollection = new MovieCollection(movieArrayList);
		String[] result = testCollection.getAllCountries();
		assertNotEquals(result, null);
		assertEquals(5, result.length);
		assertArrayEquals(new String[]{"Italy", "Monte Carlo", "Pakistan", "Spain", "Sweden"}, result);
		
	}

}
