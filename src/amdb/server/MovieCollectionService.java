/*package com.google.gwt.sample.moviebase.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.sample.moviebase.client.ParserInterface;*/

package amdb.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import amdb.client.MovieCollectionServiceInterface;
import amdb.shared.MovieCollection;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This class allows the user to receive a movieCollection created from the source file on the server.
 * 
 * @author petrawittwer
 * @author selinfabel
 * @history 2015-11-03 first complete version
 * @version 2015-11-03 1.0
 *
 */
@SuppressWarnings("serial")
public class MovieCollectionService extends RemoteServiceServlet implements MovieCollectionServiceInterface {	// Servlet to apply server-side processing, RemoteServiceServlet automatically handles serialization
	/*
	 * Holds the parsed MovieCollection if getMovieCollection has already been called once.
	 */
	private static MovieCollection movieCollection;

	/**
	 * If movieCollection has not yet been set, this method parses the source file for the database and saves the result in movieCollection.
	 * 
	 * @throws IOException The source file could not be found.
	 * @return the parsed {@link MovieCollection}.
	 */
	public MovieCollection getMovieCollection() {

		if(movieCollection == null) {
			InputStream fileStream;
			try {
				fileStream = new FileInputStream(new File("WEB-INF/movies.tsv"));

				try {
					movieCollection = Parser.parse(fileStream);
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Failed to parse file");
					throw new RuntimeException(e);
				}
			}catch (FileNotFoundException e1) {
				Window.alert("Loading failed");
			}
		}
		return movieCollection;
	}

}