package olga.maslova.outerspacemanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

/**
 * Created by omaslova on 16/01/2018.
 */

public class Tools {
    static public void showToast(Context context, CharSequence text) {
        //Show Toast
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    static public String getToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences("MyPrefsFile", 0);
        String tokenFromShared = settings.getString("token", null);
        return tokenFromShared;
    }

    static public Set<String> getPlayers(Context context) {
        SharedPreferences settings = context.getSharedPreferences("MyPrefsFile", 0);
        Set<String> players = settings.getStringSet("players", null);
        return players;
    }


}
