package saykp.dev.actigagechallange.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by pc3 on 8/18/2017.
 */

public class DbAdapter {
    static final String TAG = "ChallangeDb";

    //Database details
    static final String DATABASE_NAME = "wd_app_db";
    static final int DATABASE_VERSION = 12;
    //Table names
    static final String TABLE_FAV = "table_fav";
//    public static final String TABLE_USERS = "table_users";


    //Common variables
    private static final String KEY_ID = "id";

    private static final String KEY_URL = "imageurl";
    private static final String KEY_TIME = "time";
    public Context context;
    public SQLiteDatabase actigageDatabase;
    private DatabaseHelper databaseHelper;


    //Campaign table cart create query
    public static final String CREATE_TABLE_IMAGES=
            "create table " + TABLE_FAV + "(" + KEY_ID + " integer primary key autoincrement," +
                    KEY_URL + " string not null," +
                    KEY_TIME + " string not null" +
                    ")";

   

    public long insertMsgData(String imageUrl, String time) {
        long status = 0;
        
        String Query;
        try {

            ContentValues itemDetails = new ContentValues();
            itemDetails.put(KEY_URL, imageUrl);
            itemDetails.put(KEY_TIME, time);

           
                Query = "Select * from " + TABLE_FAV + " where " + KEY_URL + "= '" + imageUrl + "'";
            
                Cursor cursor = actigageDatabase.rawQuery(Query, null);

                if (cursor.getCount() <= 0) {
                    status = actigageDatabase.insert(TABLE_FAV, null, itemDetails);


                    cursor.close();

                } else {

                    cursor.close();

                }




        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }finally {
//            databaseHelper.close();
        }
        return status;
    }

    public int deleteImageUrl(String name) {
        try {
            String deleteCartDetailsQuery = "delete from " + TABLE_FAV + " where " + KEY_URL + "= '"+name+"'";
            actigageDatabase.execSQL(deleteCartDetailsQuery);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public ArrayList<String> getData() {
        ArrayList<String> imageUrlArray=new ArrayList<>();
        try {

            String name=null;

            String getQuery = " SELECT * FROM " + TABLE_FAV  ;
            Cursor cursor = actigageDatabase.rawQuery(getQuery, null);
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {


                  String imageUrl=cursor.getString(cursor.getColumnIndex(KEY_URL));
                  imageUrlArray.add(imageUrl);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return imageUrlArray;
    }







        /**
         * Constructor
         *
         * @param context context from where it is accessed
         */
    public DbAdapter(Context context) {
            this.context = context;
            databaseHelper = new DatabaseHelper(context);
        }

        /**
         * Method to get database helper instance to perform db operations
         *
         * @return Database helper class instance(writable database object)
         */

    public DbAdapter open() {
        actigageDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    /**
     * To close database helper class instances
     */
    public void close() {
        databaseHelper.close();
    }


    /**
     * DatabaseHelper class to create & upgrade tables
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //Execute queries for database creation
//            sqLiteDatabase.execSQL(CREATE_TABLE_CART);
            sqLiteDatabase.execSQL(CREATE_TABLE_IMAGES);


        }


        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database version from " + oldVersion + " to " + newVersion + "!");

            //Destroy old database
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);




            //Recreate new database
            onCreate(sqLiteDatabase);
        }
    }


    /**
     * Created by mukesh on 19/5/15.
     */

}
