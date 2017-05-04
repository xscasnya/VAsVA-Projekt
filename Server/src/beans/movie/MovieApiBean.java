package beans.movie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.DatabaseConfig;
import model.Response;
import model.SearchApiResponse;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;


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
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        String jsonString = "";

        if (movie.equals("")) {
            resp.setCode(Response.error);
            resp.setDescription("Please enter a movie title");
            return resp;
        }

        StringBuilder reqUrl = getReqUrl(movie, year, type);

        try {
            jsonString = sendGet(reqUrl.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonString);

        JsonObject data = parser.parse(jsonString).getAsJsonObject();
        SearchApiResponse apiResp = gson.fromJson(data,SearchApiResponse.class);
        System.out.println(apiResp.getResponse());
        if(!apiResp.getResponse()) {
            resp.setDescription("Error");
            resp.setCode(Response.error);
        }

        resp.setData(apiResp.getSearch());

        return resp;
    }

    // HTTP GET request
    private String sendGet(String urlAddress) throws Exception {

        // Encode URL to valid URL (e.g replace spaces with %20 and so on)
        URL url= new URL(urlAddress);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        String urlStr=uri.toASCIIString();

        System.out.println(urlStr);

        HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(),"UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        return response.toString();
    }

    public StringBuilder getReqUrl(String movie, int year, String type) {
        DatabaseConfig cfg = DatabaseConfig.getInstance();
        StringBuilder url = new StringBuilder(DatabaseConfig.getInstance().getApiUrl().toString());
        url.append(cfg.getApiSearch() + (movie == null ? "" : movie));
        if (year != 0)
            url.append("&" + cfg.getApiYearPrefix() + year);
        if (!type.equals(""))
            url.append("&" + cfg.getApiTypePrefix() + type);
        return url;
    }


}
