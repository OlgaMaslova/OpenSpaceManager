package olga.maslova.outerspacemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by omaslova on 23/01/2018.
 */

public class UserDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDB.db";
    public static final String USER_TABLE_NAME = "User";
    public static final String KEY_UUID = "uuid";
    public static final String KEY_GAS = "gas";
    public static final String KEY_MINERALS = "minerals";
    public static final String KEY_POINTS = "points";
    public static final String KEY_USERNAME = "name";
    private static final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE_NAME + " (" + KEY_UUID + " TEXT, "  + KEY_USERNAME + " TEXT, " +
            KEY_POINTS + " TEXT, " + KEY_GAS + " TEXT, " + KEY_MINERALS + " TEXT);";
    public UserDB(Context context) {
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
}
