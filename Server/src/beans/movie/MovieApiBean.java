package beans.movie;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.DatabaseConfig;
import model.Response;
import model.api.ApiMovie;
import model.api.SearchApiResponse;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


@Stateless
@Remote(MovieApiBeanRemote.class)
public class MovieApiBean implements MovieApiBeanRemote {

    private static Logger LOG = Logger.getLogger("beans.movie");

    private static final int DO_SEARCH = 1;
    private static final int DO_GET_ID = 0;

    @Override
    public Response searchMovie(String movie, int year, String type) {
        LOG.log(Level.INFO,"Spustam vyhladanie filmu");
        Response resp = new Response();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        String jsonString = "";
        StringBuilder reqUrl;

        if (movie.equals("")) {
            resp.setCode(Response.error);
            resp.setDescription("Please enter a movie title");
            return resp;
        }

        try {
             reqUrl = getReqUrl(DO_SEARCH,movie, year, type,"");

        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani API URL -> ",e);
            resp.setCode(Response.error);
            resp.setDescription("Error please try again.");
            return resp;
        }

        try {
            jsonString = sendGet(reqUrl.toString());
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri spracovani GET requestu -> ",e);
            e.printStackTrace();
        }

        JsonObject data = parser.parse(jsonString).getAsJsonObject();
        SearchApiResponse apiResp = gson.fromJson(data,SearchApiResponse.class);

        if(!apiResp.getResponse()) {
            resp.setDescription("Error");
            resp.setCode(Response.error);
        }

        resp.setData(apiResp.getSearch());

        return resp;
    }

    public Response getMovie(String imdb_ID){
        Response resp = new Response();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        String jsonString = "";
        StringBuilder reqUrl;

        if("".equals(imdb_ID)) {
            resp.setCode(Response.error);
            resp.setDescription("Error. IMDB id not found!");
            return resp;
        }

        try {
            reqUrl = getReqUrl(DO_GET_ID,"",0,"",imdb_ID);

        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani API URL -> ",e);
            resp.setCode(Response.error);
            resp.setDescription("Error please try again.");
            return resp;
        }

        try {
            jsonString = sendGet(reqUrl.toString());
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri spracovani GET requestu -> ",e);
            e.printStackTrace();
        }

        JsonObject data = parser.parse(jsonString).getAsJsonObject();
        ApiMovie apiResp = gson.fromJson(data,ApiMovie.class);
        if(!apiResp.getResponse()) {
            resp.setDescription("Error");
            resp.setCode(Response.error);
        }

        resp.setData(apiResp);


        return resp;
    }

    // HTTP GET request
    private String sendGet(String urlAddress) throws Exception {

        // Encode URL to valid URL (e.g replace spaces with %20 and so on)
        URL url= new URL(urlAddress);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        String urlStr=uri.toASCIIString();

        //System.out.println(urlStr);

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


    // 0 -> search , 1 -> get
    public StringBuilder getReqUrl(int i,String movie, int year, String type, String imdb_ID) {
        DatabaseConfig cfg = DatabaseConfig.getInstance();
        StringBuilder url = new StringBuilder(DatabaseConfig.getInstance().getApiUrl().toString());
        if(i == 1) {
            url.append(cfg.getApiSearch() + (movie == null ? "" : movie));
            if (year != 0)
                url.append("&" + cfg.getApiYearPrefix() + year);
            if (!type.equals(""))
                url.append("&" + cfg.getApiTypePrefix() + type);
        }

        else {
            url.append(cfg.getApiGetByID()).append(imdb_ID);
        }

        return url;
    }


}
