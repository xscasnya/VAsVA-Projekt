package beans.movie;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import config.DatabaseConfig;
import model.Response;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static javafx.scene.input.KeyCode.J;


/**
 * Author : Andrej Ščasný
 * Date : 03.05.2017
 */

@Stateless
@Remote(MovieApiBeanRemote.class)
public class MovieApiBean implements MovieApiBeanRemote {

    @Override
    public Response searchMovie(String movie, int year, String type) {
        Response resp = new Response();

        if(movie.equals("")) {
            resp.setCode(Response.error);
            resp.setDescription("Please enter a movie title");
            return resp;
        }

        StringBuilder reqUrl = getReqUrl(movie,year,type);

        System.out.println(reqUrl);

        try {
            sendGet(reqUrl.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    // HTTP GET request
    private void sendGet(String urlAddress) throws Exception {
        String url = urlAddress;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append("\n");
        }
        in.close();

        System.out.println(response.toString());

        

    }

    public StringBuilder getReqUrl(String movie, int year, String type) {
        DatabaseConfig cfg = DatabaseConfig.getInstance();
        StringBuilder url = new StringBuilder(DatabaseConfig.getInstance().getApiUrl().toString());
        url.append(cfg.getApiSearch() + (movie == null ? "" : movie));
        if(year != 0)
            url.append("&" + cfg.getApiYearPrefix() + year);
        if(!type.equals(""))
            url.append("&" + cfg.getApiTypePrefix() + type);
        return url;
    }


}
