package uk.scot.alexmalcolm.rocketflyer;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex on 16/11/2015.
 * this screen will contain the map, final score and custom map markers for when the player has flown their rocket
 */

public class PlayScreen_Main extends AppCompatActivity implements View.OnClickListener
{
    FragmentManager fmAboutDialog;
    AnimationDrawable rocketAnimation;
    ImageView rocketImage;
    RocketDBMgr dbMgr;
    mcSavePrefs mcSDPrefs;
    SharedPreferences mcSharedPrefs;
    mcUserData theUser;
    int currentUser;
    mcCalculateScore calcScore;
    TextView tvWeatherStatus, tvCashEarned, tvScoreGained,tvDistanceFlown;
    Button btnPlayReturn;
    private GoogleMap googleMap;
    ViewSwitcher vsPlaySwitcher;
    Location loc;
    LocationManager locationManager;
    xmlPullParser getXml;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen_main);

        fmAboutDialog = this.getFragmentManager();

        mcSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mcSDPrefs = new mcSavePrefs(mcSharedPrefs);

        dbMgr = new RocketDBMgr(this, "RocketUpgradesDB3.s3db", null, 1);
        try
        {
            dbMgr.dbCreate();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        currentUser = mcSDPrefs.loadIntPreferences("UsersSaveFile");
        theUser = dbMgr.getSaveFile(currentUser);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        loc = GetLastKnownLocation(locationManager);

        // Getting latitude of the current location
        double latitude = loc.getLatitude();

        // Getting longitude of the current location
        double longitude = loc.getLongitude();

        rocketImage = (ImageView) findViewById(R.id.IVRocketViewer);
        rocketImage.setBackgroundResource(R.drawable.rocketanimationinstructions);
        rocketImage.setScaleType(ImageView.ScaleType.FIT_XY);
        rocketAnimation = (AnimationDrawable) rocketImage.getBackground();

        btnPlayReturn = (Button) findViewById(R.id.btnPlayReturn);
        btnPlayReturn.setOnClickListener(this);

        tvCashEarned = (TextView) findViewById(R.id.tvCashEarned);
        tvDistanceFlown = (TextView) findViewById(R.id.tvDistanceFlown);
        tvScoreGained = (TextView) findViewById(R.id.tvScoreGained);
        tvWeatherStatus = (TextView) findViewById(R.id.tvWeatherStatus);

        vsPlaySwitcher = (ViewSwitcher) findViewById(R.id.vsPlaySwitcher);
        vsPlaySwitcher.setDisplayedChild(0);

        rocketAnimation.start();

        getXml = new xmlPullParser(latitude, longitude);

        checkIfAnimationDone(rocketAnimation);
    }

    private void checkIfAnimationDone(AnimationDrawable anim)
    {
        final AnimationDrawable a = anim;
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            public void run()
            {
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1))
                {
                    checkIfAnimationDone(a);
                } else
                {
                    //this is where to do stuff once the animation is done
                    //display the map, display the results
                    //the score is 50% of the distance flown
                    //the money is 2 for every metre flown

                    String geoName = getXml.geoName;
                    String weather = getXml.currentWeather;

                    Log.e("THE GEONAME", geoName);
                    Log.e("THE WEATHER", weather);

                    calcScore = new mcCalculateScore(theUser, dbMgr);

                    int temp = calcScore.CalculateDistance();
                    int tempDistance = Math.round(temp);
                    int tempScore = Math.round(temp / 2);
                    int tempCash = Math.round(temp * 2);
                    tvScoreGained.setText(Integer.toString(tempScore));
                    tvDistanceFlown.setText(Integer.toString(tempDistance) + "  Mtrs");
                    tvCashEarned.setText("£ " + Integer.toString(tempCash) + " ");
                    tvWeatherStatus.setText(weather);

                    CreateMap(temp);
                    PushResults(theUser, tempDistance, tempCash, tempScore);
                    vsPlaySwitcher.setDisplayedChild(1);
                }
            }
        }, timeBetweenChecks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mc_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.mQuit:
                Log.e("Main Play Screen", "quit pressed in top menu on options screen");
                finish();
                return true;

            case R.id.mAbout:
                Log.e("Main Play Screen", "about button pressed in top menu on options screen");
                DialogFragment mcAboutDlg = new AboutDialog();
                mcAboutDlg.show(fmAboutDialog, "mc_About_Dlg");
                return true;

            case R.id.mDrawing:
                Intent mcDrawing = new Intent (this, mcSnowyDrawingActivity.class);
                this.startActivity(mcDrawing);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnPlayReturn:
                //return to the main menu
                Log.e("Main Play Screen", "Return pressed in play screen");
                setResult(Activity.RESULT_OK);
                finish();
                break;

            default:
                break;
        }
    }

    //this method creates the map and places the custom markers and draws the landing radius
    private void CreateMap(int input)
    {
        try
        {
            if (googleMap == null)
            {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.mapFragment)).getMap();
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);

            // Getting latitude of the current location
            double latitude = loc.getLatitude();

            // Getting longitude of the current location
            double longitude = loc.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            //get a random compass orientation -- could be changed to REAL direction
            int bearing = GetRandomCompassDirection();

            //now calculate the final position of the rocket now that we have the starting point distance and bearing
            double finalLat = Math.asin(Math.sin(latitude) * Math.cos(input/6372000) + Math.cos(latitude) * Math.sin(input/6372000) * Math.cos(bearing));
            double finalLong = longitude + Math.atan2(Math.sin(bearing) * Math.sin(input/6372000) * Math.cos(latitude),
                    Math.cos(input/6372000) - Math.sin(latitude) * Math.sin(finalLat));

            Log.e("debug", "Landing Lat: " + Double.toString(finalLat) + " Landing Long: " + Double.toString(finalLong));

            LatLng finalLatLong = new LatLng(finalLat, finalLong);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(16).bearing(bearing).tilt(15).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            // Showing the current location in Google Map
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            // Zoom in the Google Map
            //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            //stop the user moving map
            googleMap.setMyLocationEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.setBuildingsEnabled(true);

            // Instantiates a new CircleOptions object and defines the center and radius
            CircleOptions circleOptions = new CircleOptions().center(latLng).radius(input);

            // Get back the mutable Circle
            Circle circle = googleMap.addCircle(circleOptions);
            circle.setFillColor(Color.argb(75, 68, 236, 141));
            circle.setStrokeWidth(2);
            circle.setStrokeColor(Color.RED);

            googleMap.addMarker(new MarkerOptions().position(latLng).title("Take Off Location")
                    .alpha(0.9f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.takeoffmarker))
                    .snippet("The Rocket has landed within the Green radius"));

            googleMap.addMarker(new MarkerOptions().position(finalLatLong).title("Possible Landing Site")
                    .alpha(0.9f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rocketmarker))
                    .snippet("The Possible crash site of the Rocket"));



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //this method will push the results to the users save file as well as calling the method
    //to update the main menu leaderboards if neccesary.
    private void PushResults(mcUserData theUser, int distanceIn, int cashIn, int scoreIn)
    {
        //the only thing to be directly updated will be the cash value,
        //the previous scores will then be moved down one, and these results pushed to no. 1
        theUser.setCurrentCash(theUser.getCurrentCash()+cashIn);

        //previous flight 4-5
        theUser.setPreviousFlight5Cash(theUser.getPreviousFlight4Cash());
        theUser.setPreviousFlight5Score(theUser.getPreviousFlight4Score());

        //previous flight 3-4
        theUser.setPreviousFlight4Cash(theUser.getPreviousFlight3Cash());
        theUser.setPreviousFlight4Score(theUser.getPreviousFlight3Score());


        //previous flight 2-3
        theUser.setPreviousFlight3Cash(theUser.getPreviousFlight2Cash());
        theUser.setPreviousFlight3Score(theUser.getPreviousFlight2Score());

        //previous flight 1-2
        theUser.setPreviousFlight2Cash(theUser.getPreviousFlight1Cash());
        theUser.setPreviousFlight2Score(theUser.getPreviousFlight1Score());


        //set latest previous flight
        theUser.setPreviousFlight1Cash(cashIn);
        theUser.setPreviousFlight1Score(scoreIn);

        //push changes to the user file
        dbMgr.OverwriteSavedData(theUser, currentUser);
        UpdateMainLeaderBoardData(cashIn, scoreIn, theUser.getUserName());
    }

    //this method will push changes to the main leaderboard if they are needed,
    //saving the cash score and name of the player.
    private void UpdateMainLeaderBoardData(int cashIn, int scoreIn, String userName)
    {

        //check if the current beats the top score
        if (scoreIn >= mcSDPrefs.loadIntPreferences("1stFlightScore"))
        {
            //move the first to second and so on.
            mcSDPrefs.savePreferences("3rdFlightName", mcSDPrefs.loadStringPreferences("2ndFlightName"));
            mcSDPrefs.savePreferences("3rdFlightScore", mcSDPrefs.loadIntPreferences("2ndFlightScore"));
            mcSDPrefs.savePreferences("3rdFlightCash", mcSDPrefs.loadIntPreferences("2ndFlightCash"));

            mcSDPrefs.savePreferences("2ndFlightName", mcSDPrefs.loadStringPreferences("1stFlightName"));
            mcSDPrefs.savePreferences("2ndFlightScore", mcSDPrefs.loadIntPreferences("1stFlightScore"));
            mcSDPrefs.savePreferences("2ndFlightCash", mcSDPrefs.loadIntPreferences("1stFlightCash"));

            mcSDPrefs.savePreferences("1stFlightName", userName);
            mcSDPrefs.savePreferences("1stFlightScore", scoreIn);
            mcSDPrefs.savePreferences("1stFlightCash", cashIn);
        }
        else if (scoreIn >= mcSDPrefs.loadIntPreferences("2ndFlightScore"))
        {
            //move the second to third, leave first as is and put current in second
            mcSDPrefs.savePreferences("3rdFlightName", mcSDPrefs.loadStringPreferences("2ndFlightName"));
            mcSDPrefs.savePreferences("3rdFlightScore", mcSDPrefs.loadIntPreferences("2ndFlightScore"));
            mcSDPrefs.savePreferences("3rdFlightCash", mcSDPrefs.loadIntPreferences("2ndFlightCash"));

            mcSDPrefs.savePreferences("2ndFlightName", userName);
            mcSDPrefs.savePreferences("2ndFlightScore", scoreIn);
            mcSDPrefs.savePreferences("2ndFlightCash", cashIn);
        }
        else if (scoreIn >= mcSDPrefs.loadIntPreferences("3rdFlightScore"))
        {
            //move the current into 3rd and forget third
            mcSDPrefs.savePreferences("3rdFlightName", userName);
            mcSDPrefs.savePreferences("3rdFlightScore", scoreIn);
            mcSDPrefs.savePreferences("3rdFlightCash", cashIn);
        }
    }

    private Location GetLastKnownLocation(LocationManager mLocationManager)
    {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers)
        {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null)
            {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy())
            {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    private int GetRandomCompassDirection()
    {

        int min = 0;
        int max = 359;

        Random r = new Random();
        int randomNumber = r.nextInt(max - min + 1) + min;
        return randomNumber;
    }
}