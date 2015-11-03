package amdb.client;

import amdb.shared.MovieCollection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("movies")
public interface MovieCollectionServiceInterface extends RemoteService {
	public MovieCollection getMovieCollection();

}
