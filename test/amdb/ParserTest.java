package amdb;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import amdb.server.Parser;
import amdb.shared.Movie;
import amdb.shared.MovieCollection;

public class ParserTest {
	
	static Movie m1 = new Movie("Saino", 1987,-1,new String[] {}, new String []{"Nepali Language"}, new String[]{"Nepal", "India"});
	static Movie m2 = new Movie("39 East", -1, -1,new String[] {"Silent film", "Comedy"}, new String []{}, new String[]{});
	static Movie m3 = new Movie("Getting Away with Murder: The JonBenét Ramsey Mystery", 2000, 95 ,new String[] {"Mystery", "Biographical film", "Drama", "Crime Drama"}, new String []{"English Language"}, new String[]{"United States of America"});
	
		
	@Test
	public void testParse() throws IOException{
		String path = "war/WEB-INF/test_file.txt";
		try{
			InputStream stream = new FileInputStream(new File(path));
			MovieCollection test = Parser.parse(stream); 
			
			assertEquals("Saino", test.getMovies().get(0).getName());
			assertEquals(1987, test.getMovies().get(0).getReleaseDate());
			assertEquals(-1, test.getMovies().get(0).getLength(),0);
			assertArrayEquals(m1.getGenres(), test.getMovies().get(0).getGenres());
			assertArrayEquals(m1.getLanguages(), test.getMovies().get(0).getLanguages());
			assertArrayEquals(m1.getCountries(), test.getMovies().get(0).getCountries());
			
			assertEquals("39 East", test.getMovies().get(1).getName());
			assertEquals(-1, test.getMovies().get(1).getReleaseDate());
			assertEquals(-1.0, test.getMovies().get(1).getLength(),0);
			assertArrayEquals(m2.getGenres(), test.getMovies().get(1).getGenres());
			assertArrayEquals(m2.getLanguages(), test.getMovies().get(1).getLanguages());
			assertArrayEquals(m2.getCountries(), test.getMovies().get(1).getCountries());
			
			assertEquals(m3.getName(),test.getMovies().get(2).getName());
			assertEquals(2000,test.getMovies().get(2).getReleaseDate());
			assertEquals(95, test.getMovies().get(2).getLength(),0);
			assertArrayEquals(m3.getGenres(), test.getMovies().get(2).getGenres());
			assertArrayEquals(m3.getLanguages(), test.getMovies().get(2).getLanguages());
			assertArrayEquals(m3.getCountries(), test.getMovies().get(2).getCountries());
			
		}
		catch (IOException e){
			e.printStackTrace();
			
		}
			
			}
//	@Test (expected=IOException.class)
//	public void testException(){
//		try{
//			String exampleString = "failInput";
//			InputStream stream2 = new ByteArrayInputStream(exampleString.getBytes("UTF_8"));
//			MovieCollection test2 = Parser.parse(stream2);
//		}
//		catch (IOException e){
//			e.printStackTrace();
//		}
//	}
}