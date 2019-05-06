package uk.ac.livjm.cms.cmpjwong.routefinder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Fragment fragment;
    public static DBHelper favouritesHelper;
    private GeofencingClient geofencingClient;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_favourites:
                    fragment = new favouritesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_find:
                    fragment = new routesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    fragment = new NotificationsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_settings:
                    fragment = new settingsFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragment = new favouritesFragment();
        loadFragment(fragment);

        favouritesHelper = new DBHelper(this);

        favouritesHelper.open();

        geofencingClient = LocationServices.getGeofencingClient(this);


    }




    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
