package uk.ac.livjm.cms.cmpjwong.routefinder;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class favouritesFragment extends Fragment implements View.OnClickListener{

    List <String> favouritesList = new ArrayList<>();


    public static favouritesFragment newInstance() {
        return new favouritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.favourites_fragment, container, false);


        Button b = (Button) v.findViewById(R.id.update);
        b.setOnClickListener(this);

        return v;



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:



                //A cursor is declared to access each entry in the database table.
                Cursor scroller = MainActivity.favouritesHelper.getFavourites();

                //The cursor moves to the first entry in the table.
                scroller.moveToFirst();

                //Each entry from the database table is iterated over to get each location in order for it to be manipulated into a marker.
                for (int i = 0; i < scroller.getCount(); i++) {

                    //Database is opened for each entry in case it is not the final entry.

                  //  MainActivity.favouritesHelper.open();

                    //A new location is obtained from the current entry from the cursor.
                    String id = scroller.getString(scroller.getColumnIndex("id"));
                    //favouritesList.add(id);


                    TextView serviceID = (TextView) getView().findViewById(R.id.id);
                    serviceID.setText("ID: "+ id);
                    //The cursor moves to the next entry
                    scroller.moveToNext();


                    //The database is closed, it will reopen if there is another iteration of this loop.
                   // MainActivity.favouritesHelper.close();




                }


        }
    }
}