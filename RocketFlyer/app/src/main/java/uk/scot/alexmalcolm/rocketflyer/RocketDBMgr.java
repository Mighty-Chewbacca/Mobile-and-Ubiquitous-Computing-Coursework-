package uk.scot.alexmalcolm.rocketflyer;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.sql.SQLException;

/**
 * Created by rla on 10/10/2014 and edited to work with my databases by Alex Malcolm.
 */
public class RocketDBMgr extends SQLiteOpenHelper
{

    private static final int DB_VER = 1;
    private static final String DB_PATH ="/data/data/uk.scot.alexmalcolm.rocketflyer/databases/";
    private static final String DB_NAME = "RocketUpgradesDB3.s3db";

    //all of my tables -- 3 for the upgrades and 3 for the users saved data
    private static final String TBL_EngineUpgrades = "EngineUpgrades";
    private static final String TBL_FuelTankUpgrades = "FuelTankUpgrades";
    private static final String TBL_HullUpgrades = "HullUpgrades";
    private static final String TBL_UserSavedData = "UserSavedData";

    //my columns -- different tables may have different columns

    //columns for the upgrade tables
    public static final String COL_UpgradeLevel = "UpgradeLevel";
    public static final String COL_UpgradeName = "UpgradeName";
    public static final String COL_UpgradeDescription = "UpgradeDescription";
    public static final String COL_UpgradeChanges = "UpgradeChanges";
    public static final String COL_UpgradeModification = "UpgradeModification";
    public static final String COL_UpgradeCost = "UpgradeCost";

    //columns for the user saved data tables
    public static final String COL_UserName = "UserName";
    public static final String COL_PKColumn = "PKColumn";

    public static final String COL_CurrentCash = "CurrentCash";

    public static final String COL_EngineUpgradeLevel = "EngineUpgradeLevel";
    public static final String COL_FuelTankUpgradeLevel = "FuelTankUpgradeLevel";
    public static final String COL_HullUpgradeLevel = "HullUpgradeLevel";

    public static final String COL_PreviousFlight1Score = "PreviousFlight1Score";
    public static final String COL_PreviousFlight1Cash = "PreviousFlight1Cash";

    public static final String COL_PreviousFlight2Score = "PreviousFlight2Score";
    public static final String COL_PreviousFlight2Cash = "PreviousFlight2Cash";

    public static final String COL_PreviousFlight3Score = "PreviousFlight3Score";
    public static final String COL_PreviousFlight3Cash = "PreviousFlight3Cash";

    public static final String COL_PreviousFlight4Score = "PreviousFlight4Score";
    public static final String COL_PreviousFlight4Cash = "PreviousFlight4Cash";

    public static final String COL_PreviousFlight5Score = "PreviousFlight5Score";
    public static final String COL_PreviousFlight5Cash = "PreviousFlight5Cash";

    private final Context appContext;

    public RocketDBMgr(Context context, String name,
                                SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.appContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String CREATE_EngineUpgrades_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_EngineUpgrades + "("
                + COL_UpgradeLevel + " INTEGER PRIMARY KEY,"
                + COL_UpgradeName + " TEXT,"
                + COL_UpgradeDescription + " TEXT,"
                + COL_UpgradeChanges + " TEXT,"
                + COL_UpgradeModification + " DOUBLE,"
                + COL_UpgradeCost + " INTEGER"
                + ")";
        db.execSQL(CREATE_EngineUpgrades_TABLE);

        String CREATE_FuelTankUpgrades_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_FuelTankUpgrades + "("
                + COL_UpgradeLevel + " INTEGER PRIMARY KEY,"
                + COL_UpgradeName + " TEXT,"
                + COL_UpgradeDescription + " TEXT,"
                + COL_UpgradeChanges + " TEXT,"
                + COL_UpgradeModification + " DOUBLE,"
                + COL_UpgradeCost + " INTEGER"
                + ")";
        db.execSQL(CREATE_FuelTankUpgrades_TABLE);

        String CREATE_HullUpgrades_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_HullUpgrades + "("
                + COL_UpgradeLevel + " INTEGER PRIMARY KEY,"
                + COL_UpgradeName + " TEXT,"
                + COL_UpgradeDescription + " TEXT,"
                + COL_UpgradeChanges + " TEXT,"
                + COL_UpgradeModification + " DOUBLE,"
                + COL_UpgradeCost + " INTEGER"
                + ")";
        db.execSQL(CREATE_HullUpgrades_TABLE);

        String CREATE_UserSavedData_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_UserSavedData + "("
                + COL_PKColumn + " INTEGER PRIMARY KEY,"
                + COL_UserName + " TEXT,"
                + COL_CurrentCash + " INTEGER,"
                + COL_EngineUpgradeLevel + " INTEGER,"
                + COL_FuelTankUpgradeLevel + " INTEGER,"
                + COL_HullUpgradeLevel + " INTEGER,"
                + COL_PreviousFlight1Score + " INTEGER," + COL_PreviousFlight1Cash + " INTEGER,"
                + COL_PreviousFlight2Score + " INTEGER," + COL_PreviousFlight2Cash + " INTEGER,"
                + COL_PreviousFlight3Score + " INTEGER," + COL_PreviousFlight3Cash + " INTEGER,"
                + COL_PreviousFlight4Score + " INTEGER," + COL_PreviousFlight4Cash + " INTEGER,"
                + COL_PreviousFlight5Score + " INTEGER," + COL_PreviousFlight5Cash + " INTEGER"
                + ")";

        db.execSQL(CREATE_UserSavedData_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(newVersion > oldVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_EngineUpgrades);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_FuelTankUpgrades);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_HullUpgrades);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_UserSavedData);
            onCreate(db);
        }
    }

    // ================================================================================
    // Creates a empty database on the system and rewrites it with your own database.
    // ================================================================================
    public void dbCreate() throws IOException
    {

        boolean dbExist = dbCheck();

        if(!dbExist)
        {
            //By calling this method an empty database will be created into the default system path
            //of your application so we can overwrite that database with our database.
            this.getReadableDatabase();

            try
            {

                copyDBFromAssets();

            } catch (IOException e)
            {

                throw new Error("Error copying database");

            }
        }

    }

    // ============================================================================================
    // Check if the database already exist to avoid re-copying the file each time you open the application.
    // @return true if it exists, false if it doesn't
    // ============================================================================================
    private boolean dbCheck()
    {

        SQLiteDatabase db = null;

        try
        {
            String dbPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);

        }catch(SQLiteException e)
        {

            Log.e("SQLHelper", "Database not Found!");

        }

        if(db != null)
        {

            db.close();

        }

        return db != null ? true : false;
    }

    // ============================================================================================
    // Copies your database from your local assets-folder to the just created empty database in the
    // system folder, from where it can be accessed and handled.
    // This is done by transfering bytestream.
    // ============================================================================================
    private void copyDBFromAssets() throws IOException
    {

        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;

        String string [] = appContext.getAssets().list("");
        Log.e("RocketDBMgr", "the assets log" + string);

        try
        {

            dbInput = appContext.getAssets().open(DB_NAME);
            dbOutput = new FileOutputStream(dbFileName);
            //transfer bytes from the dbInput to the dbOutput
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0)
            {
                dbOutput.write(buffer, 0, length);
            }

            //Close the streams
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
        }
        catch (IOException e)
        {
            throw new Error("Problems copying DB!");
        }
    }

    public void OverwriteSavedData(mcUserData usersData, int rowID)
    {

        ContentValues values = new ContentValues();
        values.put(COL_UserName, usersData.getUserName());
        values.put(COL_CurrentCash, usersData.getCurrentCash());
        values.put(COL_EngineUpgradeLevel, usersData.getEngineUpgradeLevel());
        values.put(COL_FuelTankUpgradeLevel, usersData.getFuelTankUpgradeLevel());
        values.put(COL_HullUpgradeLevel, usersData.getHullUpgradeLevel());
        values.put(COL_PreviousFlight1Score, usersData.getPreviousFlight1Score());
        values.put(COL_PreviousFlight1Cash, usersData.getPreviousFlight1Cash());
        values.put(COL_PreviousFlight2Score, usersData.getPreviousFlight2Score());
        values.put(COL_PreviousFlight2Cash, usersData.getPreviousFlight2Cash());
        values.put(COL_PreviousFlight3Score, usersData.getPreviousFlight3Score());
        values.put(COL_PreviousFlight3Cash, usersData.getPreviousFlight3Cash());
        values.put(COL_PreviousFlight4Score, usersData.getPreviousFlight4Score());
        values.put(COL_PreviousFlight4Cash, usersData.getPreviousFlight4Cash());
        values.put(COL_PreviousFlight5Score, usersData.getPreviousFlight5Score());
        values.put(COL_PreviousFlight5Cash, usersData.getPreviousFlight5Cash());

        SQLiteDatabase db = this.getWritableDatabase();

        db.update(TBL_UserSavedData, values, COL_PKColumn + " = " + rowID , null);
        db.close();
    }

    //this method will take in a rowID which will be the number of the save file
    //it then wipes all of the data and updates the database accordingly
    public void ResetUserData(int rowID)
    {
        ContentValues values = new ContentValues();
        values.put(COL_UserName, "No Saved Data");
        values.put(COL_CurrentCash, 0);
        values.put(COL_EngineUpgradeLevel, 0);
        values.put(COL_FuelTankUpgradeLevel, 0);
        values.put(COL_HullUpgradeLevel, 0);
        values.put(COL_PreviousFlight1Score, 0);
        values.put(COL_PreviousFlight1Cash, 0);
        values.put(COL_PreviousFlight2Score, 0);
        values.put(COL_PreviousFlight2Cash, 0);
        values.put(COL_PreviousFlight3Score, 0);
        values.put(COL_PreviousFlight3Cash, 0);
        values.put(COL_PreviousFlight4Score, 0);
        values.put(COL_PreviousFlight4Cash, 0);
        values.put(COL_PreviousFlight5Score, 0);
        values.put(COL_PreviousFlight5Cash, 0);

        SQLiteDatabase db = this.getWritableDatabase();

        db.update(TBL_UserSavedData, values, COL_PKColumn + " = " + rowID , null);
        db.close();
    }

    public String findUserName(int saveFile)
{
    String query = "Select * FROM " + TBL_UserSavedData + " WHERE " + COL_PKColumn + " =  \"" + saveFile + "\"";

    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.rawQuery(query, null);

    String name;

    if (cursor.moveToFirst())
    {
        cursor.moveToFirst();
        name = cursor.getString(1);
        Log.e("RocketDBMgr", name);
        cursor.close();
    }
    else
    {
        Log.e("RocketDBMgr", "not moved to first - no name found");
        name = "No Saved Data";
    }
    db.close();
    return name;
}
    public mcUpgradesData GetEngineVariables(int level)
    {
        String query = "Select * FROM " + TBL_EngineUpgrades + " WHERE " + COL_UpgradeLevel + " =  \"" + level + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        mcUpgradesData engineUpgrade = new mcUpgradesData();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            engineUpgrade.setUpgradeLevel(cursor.getInt(0));
            engineUpgrade.setUpgradeName(cursor.getString(1));
            engineUpgrade.setUpgradeDescription(cursor.getString(2));
            engineUpgrade.setUpgradeChanges(cursor.getString(3));
            engineUpgrade.setUpgradeModifications(cursor.getFloat(4));
            engineUpgrade.setUpgradeCost(cursor.getInt(5));
            Log.e("RocketDBMgr", "engine stuff  " + engineUpgrade.UpgradeToString());
            cursor.close();
        }
        else
        {
            Log.e("RocketDBMgr", "not moved to first - no engine data found");
        }
        db.close();
        return engineUpgrade;
    }
    public mcUpgradesData GetFuelTankVariables(int level)
    {
        String query = "Select * FROM " + TBL_FuelTankUpgrades + " WHERE " + COL_UpgradeLevel + " =  \"" + level + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        mcUpgradesData fuelTankUpgrade = new mcUpgradesData();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            fuelTankUpgrade.setUpgradeLevel(cursor.getInt(0));
            fuelTankUpgrade.setUpgradeName(cursor.getString(1));
            fuelTankUpgrade.setUpgradeDescription(cursor.getString(2));
            fuelTankUpgrade.setUpgradeChanges(cursor.getString(3));
            fuelTankUpgrade.setUpgradeModifications(cursor.getFloat(4));
            fuelTankUpgrade.setUpgradeCost(cursor.getInt(5));
            Log.e("RocketDBMgr", "fuel tank stuff  "+  fuelTankUpgrade.UpgradeToString());
            cursor.close();
        }
        else
        {
            Log.e("RocketDBMgr", "not moved to first - fuel tank data found");
        }
        db.close();
        return fuelTankUpgrade;
    }
    public mcUpgradesData GetHullVariables(int level)
    {
        String query = "Select * FROM " + TBL_HullUpgrades + " WHERE " + COL_UpgradeLevel + " =  \"" + level + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        mcUpgradesData hullUpgrade = new mcUpgradesData();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            hullUpgrade.setUpgradeLevel(cursor.getInt(0));
            hullUpgrade.setUpgradeName(cursor.getString(1));
            hullUpgrade.setUpgradeDescription(cursor.getString(2));
            hullUpgrade.setUpgradeChanges(cursor.getString(3));
            hullUpgrade.setUpgradeModifications(cursor.getFloat(4));
            hullUpgrade.setUpgradeCost(cursor.getInt(5));
            Log.e("RocketDBMgr",  "hull stuff  "+  hullUpgrade.UpgradeToString());
            cursor.close();
        }
        else
        {
            Log.e("RocketDBMgr", "not moved to first - no hull data found");
        }
        db.close();
        return hullUpgrade;
    }

    public mcUserData getSaveFile(int saveFile)
    {
        String query = "Select * FROM " + TBL_UserSavedData + " WHERE " + COL_PKColumn + " =  \"" + saveFile + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        mcUserData usersData = new mcUserData();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            usersData.setUserName(cursor.getString(1));
            usersData.setCurrentCash(cursor.getInt(2));
            usersData.setEngineUpgradeLevel(cursor.getInt(3));
            usersData.setFuelTankUpgradeLevel(cursor.getInt(4));
            usersData.setHullUpgradeLevel(cursor.getInt(5));
            usersData.setPreviousFlight1Score(cursor.getInt(6));
            usersData.setPreviousFlight1Cash(cursor.getInt(7));
            usersData.setPreviousFlight2Score(cursor.getInt(8));
            usersData.setPreviousFlight2Cash(cursor.getInt(9));
            usersData.setPreviousFlight3Score(cursor.getInt(10));
            usersData.setPreviousFlight3Cash(cursor.getInt(11));
            usersData.setPreviousFlight4Score(cursor.getInt(12));
            usersData.setPreviousFlight4Cash(cursor.getInt(13));
            usersData.setPreviousFlight5Score(cursor.getInt(14));
            usersData.setPreviousFlight5Cash(cursor.getInt(15));
            Log.e("RocketDBMgr", usersData.userDataToString());
            cursor.close();
        }
        else
        {
            Log.e("RocketDBMgr", "not moved to first - no save file stuff found");
        }
        db.close();
        return usersData;
    }
}


