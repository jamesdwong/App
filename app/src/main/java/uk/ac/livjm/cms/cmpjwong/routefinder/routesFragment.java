package uk.ac.livjm.cms.cmpjwong.routefinder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;


public class routesFragment extends Fragment implements View.OnClickListener {


    private String fromCode;
    private String toCode;

    private SuggestionAdapter fromAdapter;
    private SuggestionAdapter toAdapter;




    public static routesFragment newInstance() {
        return new routesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.routes_fragment, container, false);


        Button b = (Button) v.findViewById(R.id.find);
        b.setOnClickListener(this);


        AutoCompleteTextView from = (AutoCompleteTextView) v.findViewById(R.id.from);
        AutoCompleteTextView to = (AutoCompleteTextView) v.findViewById(R.id.to);

        fromAdapter = new SuggestionAdapter(getActivity(),from.getText().toString());
        toAdapter = new SuggestionAdapter(getActivity(),to.getText().toString());



        from.setAdapter(fromAdapter);
        from.setThreshold(3);

        to.setAdapter(toAdapter);
        to.setThreshold(3);


        from.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {


                fromCode = fromAdapter.getStation(position).getCode();


            }

        });

        to.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {



                toCode = toAdapter.getStation(position).getCode();


            }

        });




        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    public void updateTextView(String fromThis, String toThis) {
        TextView textView = (TextView) getView().findViewById(R.id.textView);
        textView.setText(toThis);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find:


                routeHelper.getRouteDetails(fromCode,toCode,getContext());

                locationHelper.getCoordinates("Liverpool Lime Street", getContext());


                TextView departureTime = (TextView) getView().findViewById(R.id.departureTime);
                departureTime.setText("Departs: "+routeHelper.std);

                TextView arrivalTime = (TextView) getView().findViewById(R.id.arrivalTime);
                arrivalTime.setText("Arrives: "+routeHelper.sta);

                TextView status = (TextView) getView().findViewById(R.id.status);
                status.setText("Status: "+routeHelper.eta);


                TextView origin = (TextView) getView().findViewById(R.id.origin);
                origin.setText("Origin: "+routeHelper.originName);


                TextView destination = (TextView) getView().findViewById(R.id.destination);
                destination.setText("Destination: "+routeHelper.destinationName);

                TextView operator = (TextView) getView().findViewById(R.id.operator);
                operator.setText("Operator: "+routeHelper.operator);

                TextView platform = (TextView) getView().findViewById(R.id.platform);
                platform.setText("Platform: "+routeHelper.platform);

                TextView serviceID = (TextView) getView().findViewById(R.id.serviceID);
                serviceID.setText("ID: "+routeHelper.serviceID);


                MainActivity.favouritesHelper.addFavourite(routeHelper.serviceID);



                break;
        }
    }

}
