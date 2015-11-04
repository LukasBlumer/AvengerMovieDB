package amdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import amdb.server.Parser;
import amdb.shared.Movie;
import amdb.shared.MovieCollection;

public class ParserTest {
	public static void main(String[] args) throws IOException {
		System.out.println(new File(".").getAbsolutePath());
		InputStream fio = new FileInputStream(new File("./war/movies_80000.tsv"));
		MovieCollection mc = Parser.parse(fio);
		System.out.println(mc.getMovies().size());
		for(Movie m : mc.getMovies()) {
			System.out.println(m.getName() + " "+Arrays.toString(m.getGenres()));
		}
	}
}