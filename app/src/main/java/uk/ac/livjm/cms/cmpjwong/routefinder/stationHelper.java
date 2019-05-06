package uk.ac.livjm.cms.cmpjwong.routefinder;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/*
 *The stationHelper class is used to retrieve a list of stations for the Autocomplete text input functionality.
 */

public class stationHelper {

    String stationName;
    String crsCode;
    boolean success;



    //Constructor for stationHelper
    public stationHelper() {

    }

    //Parameters that are used within the class
    public stationHelper(String stationName, String crsCode) {
        this.stationName = stationName;
        this.crsCode = crsCode;
    }

        public List<SuggestGetSet> getParseJsonWCF(String search, Context mContext) {

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        final List<SuggestGetSet> ListData = new ArrayList<SuggestGetSet>();


        String url = "https://huxley.apphb.com/crs/" + search + "?accessToken=DA1C7740-9DA0-11E4-80E6-A920340000B1";

        while (ListData.isEmpty()) {


            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONArray>() {

                                @Override


                                public void onResponse(JSONArray response) {

                                    try {


                                        for (int i = 0; i < response.length(); i++) {

                                            JSONObject stationProperty = response.getJSONObject(i);
                                            stationName = stationProperty.getString("stationName");
                                            crsCode = stationProperty.getString("crsCode");

                                            ListData.add(new SuggestGetSet(crsCode, stationName));


                                        }
                                        success = true;

                                    } catch (JSONException e) {


                                        success = false;

                                    }


                                }
                            }, new Response.ErrorListener() {


                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                        }

                    });


            requestQueue.add(jsonArrayRequest);
        }
        return ListData;


    }
}










