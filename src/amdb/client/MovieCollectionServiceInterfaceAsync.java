package amdb.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import amdb.shared.MovieCollection;

/**
 * 
 * 
 * @author petrawittwer
 * @author selinfabel
 * @history 2015-11-03 first complete version
 * @version 2015-11-03 1.0
 *
 */
public interface MovieCollectionServiceInterfaceAsync {
	
	// the caller is to pass in a callback object which can be notified 
	public void getMovieCollection(AsyncCallback<MovieCollection> callback);	
	
}
