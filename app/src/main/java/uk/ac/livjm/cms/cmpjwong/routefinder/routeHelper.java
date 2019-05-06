package uk.ac.livjm.cms.cmpjwong.routefinder;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * The routeHelper class is used to get routes based on a search from the Huxley API.
 */

public class routeHelper {

    public static String areServicesAvailable;
    public static String operator;
    public static String originName;
    public static String destinationName;
    public static String nrccMessage;
    public static String sta;
    public static String eta;
    public static String std;
    public static String etd;
    public static String platform;
    public static String serviceID;


    //This method returns a route based on an origin station and a destination station CRS code.
    public static void getRouteDetails(String from, String to, Context mContext) {


        RequestQueue requestQueue = Volley.newRequestQueue(mContext); //A volley request queue is declared to handle the HTTP request

        //The HTTP request is defined as a string, including the origin and destination CRS codes.
        String url = "https://huxley.apphb.com/all/" + from + "/to/" + to + "/1?accessToken=DA1C7740-9DA0-11E4-80E6-A920340000B1";

        //A JSON Object is requested based on the HTTP GET request
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override


                            public void onResponse(JSONObject response) {

                                try {

                                    areServicesAvailable = response.getString("areServicesAvailable");//The object is checked to see if there are any services available.


                                    JSONArray trainServices = response.getJSONArray("trainServices");//A JSON array contained all services is fetched from the response.

                                    //The array is looped through to get each value.
                                    for (int i = 0; i < trainServices.length(); i++) {

                                        //Each object is requested to extract route information from it.
                                        JSONObject trainServicesProperty = trainServices.getJSONObject(i);

                                        //The route information is set within the class.
                                        sta = trainServicesProperty.getString("sta");
                                        std = trainServicesProperty.getString("std");
                                        eta = trainServicesProperty.getString("eta");
                                        etd = trainServicesProperty.getString("etd");
                                        platform = trainServicesProperty.getString("platform");
                                        operator = trainServicesProperty.getString("operator");
                                        serviceID = trainServicesProperty.getString("serviceIdUrlSafe");


                                        //The origin station name is stored in an array, which is retrieved here.
                                        JSONArray origin = trainServicesProperty.getJSONArray("origin");

                                        //The array is looped through, setting the station name.
                                        for (int j = 0; j < origin.length(); j++) {

                                            JSONObject originProperty = origin.getJSONObject(i);
                                            originName = originProperty.getString("locationName");


                                        }

                                        //The destination station name is stored in an array, which is retrieved here.
                                        JSONArray destination = trainServicesProperty.getJSONArray("destination");

                                        //The array is looped through, setting the station name.
                                        for (int j = 0; j < destination.length(); j++) {

                                            JSONObject destinationProperty = destination.getJSONObject(i);
                                            destinationName = destinationProperty.getString("locationName");


                                        }

                                        //Any announcements or messages surrounding the journey are retrieved.
                                        JSONArray nrccMessages = trainServicesProperty.getJSONArray("nrccMessages");

                                        for (int j = 0; j < nrccMessages.length(); j++) {

                                            JSONObject nrccProperty = origin.getJSONObject(i);
                                            nrccMessage = nrccProperty.getString("value");


                                        }

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


    }

}




