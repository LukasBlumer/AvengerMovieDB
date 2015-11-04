package amdb.client;

import amdb.shared.MovieCollection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author petrawittwer
 * @author selinfabel
 * @history 2015-11-03 first complete version
 * @version 2015-11-03 1.0
 *
 */
@RemoteServiceRelativePath("movies")
public interface MovieCollectionServiceInterface extends RemoteService {
	public MovieCollection getMovieCollection();

}
