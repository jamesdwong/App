package uk.ac.livjm.cms.cmpjwong.routefinder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 *The locationHelper class is used to assist with any location based functionality in the app.
 */

public class locationHelper {


    //This method returns the coordinates of a station based on a String using the Google Places API
    public static Location getCoordinates(String stationName, Context mContext) {


        RequestQueue requestQueue = Volley.newRequestQueue(mContext); //A Volley request queue is declared to handle the HTTP request

        //The HTTP request is defined as a string, including the station name.
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + stationName + "&type=train_station&key=AIzaSyC-S0QVckCVQ8aqRnVvgtSBN2VzaYfjcuM";

        final Location coordinates = new Location(LocationManager.GPS_PROVIDER);//A location is declared to handle the coordinates.

        //A JSON Object is requested based on the HTTP GET request
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override

                            //When the server responds, the JSON is parsed
                            public void onResponse(JSONObject response) {

                                try {

                                    JSONArray results = response.getJSONArray("results"); //JSON array is fetched from the response

                                    //The array is looped through to get each value.
                                    for (int i = 0; i < results.length(); i++) {

                                        //The JSON response is narrowed down to access only location data.
                                        JSONObject trainServicesProperty = results.getJSONObject(i);
                                        JSONObject geometry = trainServicesProperty.getJSONObject("geometry");
                                        JSONObject location = geometry.getJSONObject("location");

                                        //The latitude and longitude are retrieved as strings
                                        String latString = location.getString("lat");
                                        String lngString = location.getString("lng");

                                        //The latitude and longitude are converted in to doubles to make them usable with the Location data type
                                        double lat = Double.parseDouble(latString);
                                        double lng = Double.parseDouble(lngString);

                                        //The location is set based on the JSON response
                                        coordinates.setLatitude(lat);
                                        coordinates.setLatitude(lng);


                                    }



                                } catch (JSONException e) {


                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }

                });


        requestQueue.add(jsonObjectRequest); //The JSON Object request is added to the Volley queue.

        return coordinates; //Method completes and coordinates are returned



    }




}

