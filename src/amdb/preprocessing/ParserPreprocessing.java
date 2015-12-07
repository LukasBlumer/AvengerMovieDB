package amdb.preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import amdb.shared.Movie;
import amdb.shared.MovieCollection;

/**
 * <p>
 * Contains methods that allow for a source file of the same format as the original source file movies_80000.tsv
 * to be converted to a new format and stored in a file.
 * 
 * <p>
 * Each Movie is stored in a single line in the produced file.
 * The format for a single line is as follows:
 * <blockquote><pre>
 *name	releaseDate	length	lang1|...|langN	country1|...|countryN	genre1|...|genreN
 * </pre></blockquote>
 * 
 * <p>
 * If a language, country or genre block is empty a space character is inserted in it's place.
 * 
 * @author petrawittwer
 * @history 2015-11-16 PW first version
 * @version 2015-11-16 PW 1.0
 * @responsibilities Allow for a source file of the same format as the original source file movies_80000.tsv
 * to be converted to a new format and stored in a file.
 */
public class ParserPreprocessing {

	/**
	 * Takes an input Stream in the same format as the original source file and turns it into a MovieCollection.
	 * 
	 * @param in The movie data as an {@link InputStream}
	 * @return A movieBase containing Movie elements whose fields are specified in the input stream.
	 * @throws IOException
	 * @see {@link MovieCollection}
	 */
	private static MovieCollection parse(InputStream in) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

		/*
		 * memo is used to only have one instance of a string in the memory as opposed to
		 * for example several instances of String with the value "United States of America".
		 * All repeated occurences of a String-value point to the first occurence of that value. 
		 * This saves quite a lot of memory space.
		 */
		HashMap<String, String> memo = new HashMap<String,String>();
		ArrayList<Movie> resultArray = new ArrayList<Movie>();

		String[] lineArray; // holds the splitted lines

		// variable for each value of the Movie class
		String name;
		
		// can hold intermediate String arrays for languages, countries and genres.
		ArrayList<String> intermediateResult = new ArrayList<String>();
		String[] genres;
		String[] languages;
		String[] countries;
		int releaseDate = -1;
		int length = -1;

		// variables for hash lookup
		String stringReference;

		// Pattern to match all the expressions of the form 
		//{"someTag": "actualValue", "someTag": "actualValue" ...}
		// the actualValue is always at the index [1] of the returned array
		Pattern keyValuePattern = Pattern.compile("(?:\".*?\": \"(.*?)\")+");
		// Pattern to match the actualValue in the line
		Matcher keyValueMatcher;

		String line;
		while ((line = br.readLine()) != null) {
			releaseDate = -1;
			length = -1;

			// split the line by the tabs.
			lineArray = line.split("\\t", 9);
			if (lineArray.length != 9) {
				throw new RuntimeException("invalid line, incorrect amount of values: "
						+ line);
			}

			// set name
			name = lineArray[2];

			// set length
			if(!lineArray[5].isEmpty()){
				length = (int) Float.parseFloat(lineArray[5]);
			}

			// set releaseDate
			if (lineArray[3] != null && lineArray[3].length() >= 4) {
				releaseDate = Integer.parseInt(lineArray[3].substring(0, 4));
				if( releaseDate < 1880){ // if release date is earlier than earliest movies
					releaseDate = -1;
				}
			}

			// set genres
			intermediateResult.clear();
			keyValueMatcher = keyValuePattern.matcher(lineArray[8]);
			while (keyValueMatcher.find()) {
				if(!memo.containsKey(keyValueMatcher.group(1))){ // if country name is not yet in hash
					memo.put(keyValueMatcher.group(1), keyValueMatcher.group(1));
				}
				// use a pointer to the value in memo as the value in the Movie
				stringReference = memo.get(keyValueMatcher.group(1));

				intermediateResult.add(stringReference);
			}
			genres = intermediateResult.toArray(new String[0]);

			// set languages
			intermediateResult.clear();
			keyValueMatcher = keyValuePattern.matcher(lineArray[6]);
			while (keyValueMatcher.find()) {
				if(!memo.containsKey(keyValueMatcher.group(1))){ // if country name is not yet in hash
					memo.put(keyValueMatcher.group(1), keyValueMatcher.group(1));
				}
				stringReference = memo.get(keyValueMatcher.group(1));

				intermediateResult.add(stringReference);
			}
			languages = intermediateResult.toArray(new String[0]);

			// set countries
			intermediateResult.clear();
			keyValueMatcher = keyValuePattern.matcher(lineArray[7]);
			while (keyValueMatcher.find()) {

				if(!memo.containsKey(keyValueMatcher.group(1))){ // if country name is not yet in hash
					memo.put(keyValueMatcher.group(1), keyValueMatcher.group(1));
				}
				stringReference = memo.get(keyValueMatcher.group(1));

				intermediateResult.add(stringReference);
			}
			countries = intermediateResult.toArray(new String[0]);

			resultArray.add(new Movie(name, length, releaseDate, genres, languages,
					countries));

		}

		br.close();
		return new MovieCollection(resultArray);
	}

	/**
	 * <p>
	 * Converts a <code>Movie</code> to a String. 
	 * Fields of <code>Movie</code> are separated by tabs, single values in arrays by |
	 * 
	 * <p>
	 * The format for a single Movie is as follows:
	 * <blockquote><pre>
	 *name	releaseDate	length	lang1|...|langN	country1|...|countryN	genre1|...|genreN
	 * </pre></blockquote>
	 * 
	 * @param m The Movie to be converted
	 * @return m The Movie as a String
	 */
	private static String movieToString(Movie m){
		StringBuilder builder = new StringBuilder(); 
		String[] intermediateArray;

		// add name
		builder.append(m.getName());
		builder.append('\t');

		// add releaseDate

		builder.append(m.getReleaseDate());
		builder.append('\t');

		// add length
		builder.append(m.getLength());
		builder.append('\t');

		// add languages
		intermediateArray = m.getLanguages();
		if(intermediateArray.length > 0) {
			for (int i = 0; i < intermediateArray.length-1; i++) {
				builder.append(intermediateArray[i]);
				builder.append('|');
			}
			builder.append(intermediateArray[intermediateArray.length-1]);
			builder.append('\t');
		} else {
			builder.append(' ');
			builder.append('\t');			
		}

		// add countries

		intermediateArray = m.getCountries();
		if(intermediateArray.length > 0) {
			for (int i = 0; i < intermediateArray.length-1; i++) {
				builder.append(intermediateArray[i]);
				builder.append('|');
			}
			builder.append(intermediateArray[intermediateArray.length-1]);
			builder.append('\t');
		} else {
			builder.append(' ');

			builder.append('\t');			
		}

		// add genres

		// It is crucial to append a space here because otherwise the split during reading doesn't work.
		intermediateArray = m.getGenres();
		if(intermediateArray.length > 0) {
			for (int i = 0; i < intermediateArray.length-1; i++) {
				builder.append(intermediateArray[i]);
				builder.append('|');
			}
			builder.append(intermediateArray[intermediateArray.length-1]);
		} else {
			builder.append(' ');
		}
		return builder.toString();
	}

	/**
	 * Converts the file specified by <tt>sourcePath</tt> to a template used to build a MovieCollection.
	 * Saves the resulting template to a file. 
	 * 
	 * @param sourcePath The source of the File that is supposed to be converted.
	 * @param targetPath The path where the new File is supposed to be saved.
	 */
	public static void preprocessFile(String sourcePath, String targetPath){
		File[] files = new File(sourcePath).listFiles(); 

		try {
			PrintWriter printWriter = new PrintWriter (new File(targetPath), "UTF-8");
			for(File sourceFile : files){
				MovieCollection movieCollection = parse(new FileInputStream(sourceFile));
				
				ArrayList<Movie> movies = movieCollection.getMovies();
		
				// for all movies add the movie as a string to the file
				for (int i = 0; i < movies.size()-1; i++) {
					printWriter.println(movieToString(movies.get(i)));
				}
				printWriter.print(movieToString(movies.get(movies.size()-1)));
				printWriter.print("\n");
			}
				printWriter.close();

		} catch (FileNotFoundException e) {
			System.err.println("Sourcefile not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("File could not be parsed due to IOException.");
			e.printStackTrace();
		}

	}

	/**
	 * This class envokes the preprocessing process and stores the resulting output in a file.
	 * 
	 * @param args If you don't know what this does you have bigger problems that will not be solved by a single comment.
	 */
	public static void main(String[] args) {
		preprocessFile("war/WEB-INF/files", "war/PreprocessedData/movies_preprocessed_dir.tsv");
//		preprocessFile("war/WEB-INF/systemtest_files", "war/PreprocessedData/systemtest_file.tsv");
		System.out.println("Done");
	}

}
