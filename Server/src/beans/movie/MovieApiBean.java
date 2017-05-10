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


/**
 * Stateless Beana ktorá spracováva filmy, podrobnosti o filmoch z IMDB API
 * @author Andrej Ščasný
 */
@Stateless
@Remote(MovieApiBeanRemote.class)
public class MovieApiBean implements MovieApiBeanRemote {

    private static Logger LOG = Logger.getLogger("beans.movie");

    private static final int DO_SEARCH = 1;
    private static final int DO_GET_ID = 0;

    /**
     * Metóda ktorá vyhľadá filmy na základe parametrov
     * @param movie Názov titulku filmu
     * @param year Rok vydania filmu
     * @param type Typ filmu , epizoda, seriál, film
     * @return Vráti Response v ktorom je zabalený kód, description a samotné dáta
     */
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

    /**
     * Metóda ktorá vyhľadá film na základe imdb id
     * @param imdb_ID IMDB ID filmu
     * @return
     */
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

    /**
     * Pomocná metóda na poslanie GET requestu na danú URL, vracia String ktorý sa potom mení do JSON
     * @param urlAddress URL adresa na ktorú sa posiela GET request
     * @return
     * @throws Exception
     */
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

    /**
     * Metóda ktorá skladá URL adresu ktorá sa použije na API request
     * @param i Parameter ktorý označuje typ requestu, 0 je search , 1 je get
     * @param movie Názov filmu
     * @param year Rok vydania
     * @param type Typ filmu
     * @param imdb_ID IMDB id filmu
     * @return
     */
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
