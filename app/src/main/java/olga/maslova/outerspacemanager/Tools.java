package olga.maslova.outerspacemanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by omaslova on 16/01/2018.
 */

public class Tools {
    static void showToast(Context context, CharSequence text) {
        //Show Toast
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    };


}
