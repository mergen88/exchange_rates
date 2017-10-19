package kz.mergen.exchagerates.exchangerates.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import kz.mergen.exchagerates.exchangerates.Constants;

/**
 * Created by arman on 14.09.17.
 */

public class SharedController {


    private static SharedPreferences prefs = null;

    public Boolean isFirstRun(Context context){
        return getPrefs(context).getBoolean(Constants.FIRST_RUN, true);
    }
    public void setRunned(Context context){
        getPrefs(context).edit().putBoolean(Constants.FIRST_RUN,false).commit();
    }
    private SharedPreferences getPrefs(Context context){
        prefs = context.getSharedPreferences(Constants.SHARED_SETTINGS, Context.MODE_PRIVATE);
        return prefs;
    }
}
