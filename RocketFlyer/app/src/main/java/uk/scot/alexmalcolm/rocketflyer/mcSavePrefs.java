package uk.scot.alexmalcolm.rocketflyer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Alex on 04/12/2015.
 */
public class mcSavePrefs extends Activity
{
    SharedPreferences mcSharedPrefs;


    public mcSavePrefs(SharedPreferences mcSharedPrefs)
    {
        try
        {
            this.mcSharedPrefs = mcSharedPrefs;
            //if it succeeded properly it should print all values correctly
            Log.e("mcSavePrefs", "UsersSaveFileNo: " + Integer.toString(mcSharedPrefs.getInt("UsersSaveFile", 0)));
            Log.e("mcSavePrefs", "SoundState: " + Boolean.toString(mcSharedPrefs.getBoolean("soundState", false)));

            Log.e("mcSavePrefs", "1stName: " + mcSharedPrefs.getString("1stFlightName", "NULL"));
            Log.e("mcSavePrefs", "1stScore: " + Integer.toString(mcSharedPrefs.getInt("1stFlightScore", 0)));
            Log.e("mcSavePrefs", "1stCash: " + Integer.toString(mcSharedPrefs.getInt("1stFlightCash", 0)));

            Log.e("mcSavePrefs", "2ndName: " + mcSharedPrefs.getString("2ndFlightName", "NULL"));
            Log.e("mcSavePrefs", "2ndScore: " + Integer.toString(mcSharedPrefs.getInt("2ndFlightScore", 0)));
            Log.e("mcSavePrefs", "2ndCash: " + Integer.toString(mcSharedPrefs.getInt("2ndFlightCash", 0)));

            Log.e("mcSavePrefs", "3rdName: " + mcSharedPrefs.getString("3rdFlightName", "NULL"));
            Log.e("mcSavePrefs", "3rdScore: " + Integer.toString(mcSharedPrefs.getInt("3rdFlightScore", 0)));
            Log.e("mcSavePrefs", "3rdCash: " + Integer.toString(mcSharedPrefs.getInt("3rdFlightCash", 0)));
        }
        catch (Exception e)
        {
            Log.e("n", "Pref Manager is null");
        }
        //setDefaultPrefs();
    }

    public void savePreferences(String key, boolean value)
    {
        SharedPreferences.Editor editor = mcSharedPrefs.edit();
        editor.putBoolean(key, value);
        editor.commit();

        Log.e("mcSaveDataPrefs", key + " " + value);
    }

    public void savePreferences(String key, String value)
    {
        SharedPreferences.Editor editor = mcSharedPrefs.edit();
        editor.putString(key, value);
        editor.commit();

        Log.e("mcSaveDataPrefs", "Saved String:" +  key + " " + value);
    }

    public void savePreferences(String key, int value)
    {
        SharedPreferences.Editor editor = mcSharedPrefs.edit();
        editor.putInt(key, value);
        editor.commit();

        Log.e("mcSaveDataPrefs", "Saved Int:" + key + " " + value);
    }

    public int loadIntPreferences (String key)
    {
        int temp = mcSharedPrefs.getInt(key, 0);
        return temp;
    }

    public String loadStringPreferences (String key)
    {
        String temp = mcSharedPrefs.getString(key, "No Saved Data");
        return temp;
    }

    public Boolean loadBooleanPreferences (String key)
    {
        boolean temp = mcSharedPrefs.getBoolean(key, false);
        return temp;
    }

    public void setDefaultPrefs()
    {
        savePreferences("UsersSaveFile", 1);
        savePreferences("soundState", true);

        savePreferences("1stFlightName", "No Data");
        savePreferences("1stFlightScore", 0);
        savePreferences("1stFlightCash", 0);

        savePreferences("2ndFlightName", "No Data");
        savePreferences("2ndFlightScore", 0);
        savePreferences("2ndFlightCash", 0);

        savePreferences("3rdFlightName", "No Data");
        savePreferences("3rdFlightScore", 0);
        savePreferences("3rdFlightCash", 0);
    }

    public void ResetLeaderboardPrefs()
    {
        savePreferences("1stFlightName", "No Data");
        savePreferences("1stFlightScore", 0);
        savePreferences("1stFlightCash", 0);

        savePreferences("2ndFlightName", "No Data");
        savePreferences("2ndFlightScore", 0);
        savePreferences("2ndFlightCash", 0);

        savePreferences("3rdFlightName", "No Data");
        savePreferences("3rdFlightScore", 0);
        savePreferences("3rdFlightCash", 0);
    }
}
