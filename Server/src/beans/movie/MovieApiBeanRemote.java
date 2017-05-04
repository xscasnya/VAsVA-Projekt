package beans.movie;

import model.Response;

/**
 * Author : Andrej Ščasný
 * Date : 03.05.2017
 */
public interface MovieApiBeanRemote {
    public Response searchMovie(String movie, int year, String type);
    public Response getMovie(String imdb_ID);
}
