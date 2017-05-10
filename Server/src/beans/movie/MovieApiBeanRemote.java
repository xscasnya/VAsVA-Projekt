package beans.movie;

import model.Response;

/**
 * Remote interface pre MovieApiBean
 * @author Andrej Ščasný, Dominik Števlík
 */
public interface MovieApiBeanRemote {
    public Response searchMovie(String movie, int year, String type);
    public Response getMovie(String imdb_ID);
}
