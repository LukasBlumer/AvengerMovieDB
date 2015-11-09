package amdb;

import static org.junit.Assert.*;

import org.junit.Test;

import amdb.shared.Movie;

public class MovieTest {
	Movie m1,m2,m3;
	@Test
	public void testConstructor() {
		// simple movie
		String name1 = "Retrospection";
		int length1 = 170;
		int releaseDate1 = 1968;
		String[] genres1 = new String[]{"Science Fiction"};
		String[] languages1 = new String[]{"English"};
		String[] countries1 = new String[]{"United States"};
		
		m1 = new Movie(name1,length1, releaseDate1, genres1, languages1, countries1);
		// more than one elements in countries, genres, languages
		
		String name2 ="The doctor";
		int length2 = 210;
		int releaseDate2 = 2011;
		String[] genres2 = new String[]{"Documentary", "Family"};
		String[] languages2 = new String[]{"English", "Spanish"};
		String[] countries2 = new String[]{"United States", "Mexico", "Italy"};
			
		m2 = new Movie(name2, length2, releaseDate2, genres2, languages2, countries2);
		
		assertNotEquals(null, m1);
		assertNotEquals(null, m2);
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
		float testLength = 210;
		m2 = new Movie("The doctor",210, 2011, new String[]{"Documentary", "Family"}, new String[]{"English", "Spanish"}, new String[]{"United States", "Mexico", "Italy"});
		assertEquals(testLength, m2.getLength(), 0);
	}
}
