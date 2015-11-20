package amdb;

import static amdb.client.ParserClientside.stringToMovieCollection;
import static amdb.preprocessing.ParserPreprocessing.preprocessFile;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import amdb.shared.Movie;
import amdb.shared.MovieCollection;

public class ParserClientsidePreprocessingTest {
	String inPathName = "test/amdb/testData/test_file.txt";
	String outPathName = "test/amdb/testData/parsertestresult.tsv";
	
	static Movie m1 = new Movie("Saino", 1987,-1,new String[] {}, new String []{"Nepali Language"}, new String[]{"Nepal", "India"});
	static Movie m2 = new Movie("39 East", -1, -1,new String[] {"Silent film", "Comedy"}, new String []{}, new String[]{});
	static Movie m3 = new Movie("Getting Away with Murder: The JonBenét Ramsey Mystery", 2000, 95 ,new String[] {"Mystery", "Biographical film", "Drama", "Crime Drama"}, new String []{"English Language"}, new String[]{"United States of America"});


	@Test
	public void testAll() {
		preprocessFile(inPathName, outPathName);
		Scanner scanner;
		try {
			scanner = new Scanner(new File(outPathName));
			String preprocessedText = scanner.useDelimiter("\\Z").next();
			scanner.close();
			
			MovieCollection movieCollection = stringToMovieCollection(preprocessedText);
			
			assertEquals("Saino", movieCollection.getMovies().get(0).getName());
			assertEquals(1987, movieCollection.getMovies().get(0).getReleaseDate());
			assertEquals(-1, movieCollection.getMovies().get(0).getLength(),0);
			assertArrayEquals(m1.getGenres(), movieCollection.getMovies().get(0).getGenres());
			assertArrayEquals(m1.getLanguages(), movieCollection.getMovies().get(0).getLanguages());
			assertArrayEquals(m1.getCountries(), movieCollection.getMovies().get(0).getCountries());
			
			assertEquals("39 East", movieCollection.getMovies().get(1).getName());
			assertEquals(-1, movieCollection.getMovies().get(1).getReleaseDate());
			assertEquals(-1.0, movieCollection.getMovies().get(1).getLength(),0);
			assertArrayEquals(m2.getGenres(), movieCollection.getMovies().get(1).getGenres());
			assertArrayEquals(m2.getLanguages(), movieCollection.getMovies().get(1).getLanguages());
			assertArrayEquals(m2.getCountries(), movieCollection.getMovies().get(1).getCountries());
			
			assertEquals(m3.getName(),movieCollection.getMovies().get(2).getName());
			assertEquals(2000,movieCollection.getMovies().get(2).getReleaseDate());
			assertEquals(95, movieCollection.getMovies().get(2).getLength(),0);
			assertArrayEquals(m3.getGenres(), movieCollection.getMovies().get(2).getGenres());
			assertArrayEquals(m3.getLanguages(), movieCollection.getMovies().get(2).getLanguages());
			assertArrayEquals(m3.getCountries(), movieCollection.getMovies().get(2).getCountries());
			
		} catch (FileNotFoundException e) {
			fail("File "+outPathName+" could not be found.");
		}




	}

}
