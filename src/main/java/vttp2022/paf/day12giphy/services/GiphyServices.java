package vttp2022.paf.day12giphy.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class GiphyServices {
    // GIPHY_API_KEY
    @Value("${giphy.api.key}")
    private String giphykey;

    private static final String BASE_URL = "https://api.giphy.com/v1/gifs/search";

    public List<String> getGiphs(String q){
        return getGiphs(q,"pg",10);
    }

    public List<String> getGiphs(String q, String rating){
        return getGiphs(q,rating,10);
    }

    public List<String> getGiphs(String q, Integer limit){
        return getGiphs(q,"pg",limit);
    }

    public List<String> getGiphs(String q, String rating, Integer limit){
        
        List<String> result = new LinkedList<>();
        //Fixed width URL
        String url = UriComponentsBuilder.fromUriString(BASE_URL)
            .queryParam("api_key", giphykey)
            .queryParam("q", q)
            .queryParam("rating", rating)
            .queryParam("limit", limit)
            .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        
        try{
            resp = template.exchange(req, String.class);
        } catch (Exception ex){
            ex.printStackTrace();
            return result;
        }

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject res = r.readObject();

            JsonArray dataArray = res.getJsonArray("data");
            
            if (dataArray != null) {      
  
                for (int i=0;i<dataArray.size();i++){               
                    //Adding each element of JSON array into ArrayList 
                    String img = dataArray.getJsonObject(i).getJsonObject("images")
                            .getJsonObject("fixed_width").getString("url");
                    result.add(img);            
                }   
            } 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        return result;

    }
}
