package uk.ac.livjm.cms.cmpjwong.routefinder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * The DBHelper class contains methods that can be used to query on the database.
 */

public class DBHelper extends Activity {

    // The Keys for the able are declared as Strings
    public static final String KEY_ID = "id"; //Primary Key


    //The DatabaseHelper and Database are declared.
    private DatabaseHelper favouritesHelper; //Used to interact with the DB
    private SQLiteDatabase favouritesDB; //Used to construct the DB itself

    // The statement for creating the database is listed here. (The 'favourites' table and 'id' column are created).
    private static final String DATABASE_CREATE =
            "create table favourites (id text primary key not null);";

    //Database attributes are declared.
    private static final String DATABASE_NAME = "favouritesDB";
    private static final String DATABASE_TABLE = "favourites";
    private static final int DATABASE_VERSION = 1;

    //A new context in which the helper can work in is declared.
    private final Context mContext;


    //The Database helper method is initiated within the class
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        //When the helper is created, a new database is created if one does not already exist
        @Override
        public void onCreate(SQLiteDatabase favouritesDB) {

            favouritesDB.execSQL(DATABASE_CREATE);


        }

        @Override
        public void onUpgrade(SQLiteDatabase locationHistoryDB, int oldVersion, int newVersion) {

        }
    }

    //Constructor for DBHelper.
    public DBHelper(Context ctx) {
        this.mContext = ctx;
    }

    //Opens the database in the current context and gets a writable version of the database when called.
    public DBHelper open() throws SQLException {
        favouritesHelper = new DatabaseHelper(mContext);
        favouritesDB = favouritesHelper.getWritableDatabase();
        return this;
    }

    //Closes the database when called.
    public void close() {
        favouritesHelper.close();
    }

    //This method adds a value to the database.
    public void addFavourite(String id) {

        //A set of values are declared to allow them to be added to the database more easily.
        ContentValues initialValues = new ContentValues();

        //The values are set.
        initialValues.put(KEY_ID, id);


        //The entry is inserted into the table.
        favouritesDB.insert(DATABASE_TABLE, null, initialValues);
    }


    // Removes a specific entry from the DB
    public void removeFavourite(String id) {

        favouritesDB.delete(DATABASE_TABLE, null, null);

    }

    // Removes all entries from the table
    public void resetFavourites() {

       favouritesDB.delete(DATABASE_TABLE, null, null);


    }


    //Creates a cursor which scans over and returns all entries in the table.
    public Cursor getFavourites() {

        return favouritesDB.query(DATABASE_TABLE, new String[]{KEY_ID}, null, null, null, null, null);

    }


}