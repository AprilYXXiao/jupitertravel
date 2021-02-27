package com.laioffer.jupiter.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.jupiter.entity.Location;
import com.laioffer.jupiter.entity.Place;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

//根據Twitch API要求，提出請求

public class GoogleClient {
    private static final String GOOGLE_API_KEY = "AIzaSyAtiuQLx-WDx9BHkunZ6Ltvh7EEeKzm-P0";
    private static final String LOCATION_SEARCH_URL_TEMPLATE = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=tourist_attraction+in+%s&key=%s";

//R01:Get Twitch Game search URL => 發送請求前，確定目的地網址 (原網址格式是由上面的search template為基礎，透過 String.format,取代%s)。

    private String buildSearchURL(String url, String cityName) {
        if (cityName.equals("")) {
            return String.format(url, "SanFrancisco", GOOGLE_API_KEY);
        } else {
            try {
                cityName = URLEncoder.encode(cityName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return String.format(url, cityName, GOOGLE_API_KEY);
        }
    }

    //R02:Get Twitch Stream/Video/Clip search URL => 與R01一樣的作用，只是抓不同類別的數據
//R03: Based on URL, send request to Twitch, return response body as json string
    private String searchGoogle(String url) throws GoogleException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        ResponseHandler<String> responseHandler = response -> {
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != 200) {
                System.out.println("Response status: " + response.getStatusLine().getReasonPhrase());
                throw new GoogleException("Failed to get result from Google API");
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new GoogleException("Failed to get result from Google API");
            }
            JSONObject obj = new JSONObject(EntityUtils.toString(entity));
            return obj.getJSONArray("results").toString();
        };

        try {
            HttpGet request = new HttpGet(url);
//            request.setHeader("Authorization", TOKEN);
//            request.setHeader("Client-Id", CLIENT_ID);
            return httpclient.execute(request, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GoogleException("Failed to get result from Google API");
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //convert Google API returned data to a list of location objects
    private List<Place> getPlaceList(String googleData) throws GoogleException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(googleData, Place[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new GoogleException("Failed to parse game data from Google API");
        }
    }

    public List<Place> searchPlace(String cityName) throws GoogleException{
        List<Place> places = getPlaceList(searchGoogle(buildSearchURL(LOCATION_SEARCH_URL_TEMPLATE, cityName)));
        return places;
    }

}
