package amdb;

import static org.junit.Assert.*;

import org.junit.Test;

import amdb.shared.Movie;

public class MovieTest {
	Movie m1,m2,m3;
	@Test
	public void testConstructor() {
		// simple movie
		m1 = new Movie("Retrospection",170, 1968, new String[]{"Science Fiction"}, new String[]{"English"}, new String[]{"United States"});
		// more than one elements in countries, genres, languages
		m2 = new Movie("The doctor",210, 2011, new String[]{"Documentary", "Family"}, new String[]{"English", "Spanish"}, new String[]{"United States", "Mexico", "Italy"});
		// invalid release date
		m3 = new Movie("The time machinist",87, 1399, new String[]{"Period"}, new String[]{"Osmanic empire"}, new String[]{"Turkish"});
		
		assertNotEquals(null, m1);
		assertNotEquals(null, m2);
		assertNotEquals(null, m3);
	}

	@Test
	public void testGetName(){
		String movieName = "The doctor";
		m2 = new Movie("The doctor",210, 2011, new String[]{"Documentary", "Family"}, new String[]{"English", "Spanish"}, new String[]{"United States", "Mexico", "Italy"});
		assertEquals(movieName, m2.getName());
	}
	
	@Test
	public void testGetReleaseDate(){
		int movieReleaseDate = 2011;
		m2 = new Movie("The doctor",210, 2011, new String[]{"Documentary", "Family"}, new String[]{"English", "Spanish"}, new String[]{"United States", "Mexico", "Italy"});
		assertEquals(movieReleaseDate, m2.getReleaseDate());
	}
	
	@Test
	public void testGetGenres(){
		String[] movieGenres = {"Documentary", "Family"};
		m2 = new Movie("The doctor",210, 2011, new String[]{"Documentary", "Family"}, new String[]{"English", "Spanish"}, new String[]{"United States", "Mexico", "Italy"});
		assertArrayEquals(movieGenres, m2.getGenres());
	}
	
	@Test
	public void testGetLanguages(){
		String[] movieLanguages = {"English", "Spanish"};
		m2 = new Movie("The doctor",210, 2011, new String[]{"Documentary", "Family"}, new String[]{"English", "Spanish"}, new String[]{"United States", "Mexico", "Italy"});
		assertArrayEquals(movieLanguages, m2.getLanguages());
	}
	
	@Test
	public void testGetCountries(){
		String[] movieContries = {"United States", "Mexico", "Italy"};
		m2 = new Movie("The doctor",210, 2011, new String[]{"Documentary", "Family"}, new String[]{"English", "Spanish"}, new String[]{"United States", "Mexico", "Italy"});
		assertArrayEquals(movieContries, m2.getCountries());
	}
	
	@Test
	public void testGetLength(){
		int testLength = 210;
		m2 = new Movie("The doctor",210, 2011, new String[]{"Documentary", "Family"}, new String[]{"English", "Spanish"}, new String[]{"United States", "Mexico", "Italy"});
		assertEquals(testLength, m2.getLength());
	}
}
