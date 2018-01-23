package olga.maslova.outerspacemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.UUID;

/**
 * Created by omaslova on 23/01/2018.
 */

public class UserDataSource {
    // Database fields
    private SQLiteDatabase database;
    private UserDB dbHelper;
    private String[] allColumns = { UserDB.KEY_UUID, UserDB.KEY_USERNAME,UserDB.KEY_POINTS, UserDB.KEY_GAS, UserDB.KEY_MINERALS};
    public UserDataSource(Context context) {
        dbHelper = new UserDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public void insertOrRefreshUser(getUserResponse data) {
        ContentValues values = new ContentValues();
        values.put(UserDB.KEY_USERNAME, data.getUsername());
        values.put(UserDB.KEY_POINTS, data.getPoints());
        values.put(UserDB.KEY_GAS, data.getGas());
        values.put(UserDB.KEY_MINERALS, data.getMinerals());
        //check if username exists already
        UUID currentUUID = getUser(data.getUsername()).getUuid();
        if (currentUUID != null) {
            // user exists => update
            database.update(UserDB.USER_TABLE_NAME, values,  UserDB.KEY_USERNAME + " = ?", new String[]{data.getUsername()});
        } else {
            UUID newID = UUID.randomUUID();
            values.put(UserDB.KEY_UUID, newID.toString());
            //username does not exists => create new user => insert
            database.insert(UserDB.USER_TABLE_NAME, null, values);
        }
    }

    public User getUser(String username) {
        Cursor cursor = database.query(UserDB.USER_TABLE_NAME,
                allColumns, UserDB.KEY_USERNAME + " = \"" + username+"\"", null,
                null, null, null);
        if (cursor.getCount() > 0) {
            //user found
            cursor.moveToFirst();
            User user = cursorToUser(cursor);
            cursor.close();
            return user;
        } else {
            //returns empty user
            return new User();
        }
    }

    private User cursorToUser(Cursor cursor) {
        User comment = new User();
        String result = cursor.getString(0);
        comment.setUuid(UUID.fromString(result));
        comment.setUsername(cursor.getString(1));
        comment.setPoints(cursor.getString(2));
        comment.setGas(cursor.getString(3));
        comment.setMinerals(cursor.getString(4));
        return comment;
    }
}
