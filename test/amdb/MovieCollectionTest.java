package amdb;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

import amdb.shared.Movie;
import amdb.shared.MovieCollection;

public class MovieCollectionTest {
	static Movie m1 = new Movie("The Happening", 1990,new String[] {"Horror", "Romance", "Adventure"}, new String []{"Urdu", "Polish"}, new String[]{"Pakistan", "Italy"});
	static Movie m2 = new Movie("That new movie", 2010, new String[] {"Vampire fanfiction"}, new String[] {"English", "Gibberish"}, new String[]{"Monte Carlo", "Spain", "Sweden"});
	static Movie m3 = new Movie("Fun with knives", 1988, new String[] {"Family", "Educational"}, new String[] {"Spanish"}, new String[]{"United States of America"});
	static Movie m4 = new Movie("Watching grass", 1973, new String[] {"Documentary"}, new String[] {"English"}, new String[]{"Spain", "Sweden"});
	static Movie m5 = new Movie("Watching grass grow", 1974, new String[] {"Documentary"}, new String[] {"English"}, new String[]{"Spain", "Sweden"});

	static ArrayList<Movie> movieArrayList = new ArrayList<Movie>(Arrays.asList(m1,m2));


	@Test
	public void testDefaultConstructor(){
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
	public void testArrayListConstructor(){
		MovieCollection testCollection = new MovieCollection(movieArrayList);
		assertEquals(2, testCollection.getMovies().size());
		assertEquals(m1, testCollection.getMovies().get(0));
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
}
