package uk.scot.alexmalcolm.rocketflyer;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Alex
 * this screen will be on the main menu and will contain the leaderboard which is saved to the user prefs
 * it will only give return access to the main menu
 */
public class MainMenu_ScreenLeaderboard extends AppCompatActivity implements View.OnClickListener
{
    Button btnReturn;
    FragmentManager fmAboutDialog;
    TextView tv1stPlaceTitle, tv1stPlaceCash, tv1stPlaceScore, tv2ndPlaceTitle, tv2ndPlaceScore,
            tv2ndPlaceCash, tv3rdPlaceTitle, tv3rdPlaceCash, tv3rdPlaceScore;
    mcSavePrefs mcSDPrefs;
    SharedPreferences mcSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_screen_leaderboard);

        mcSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mcSDPrefs = new mcSavePrefs(mcSharedPrefs);

        fmAboutDialog = this.getFragmentManager();

        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        //setting up the text views for displaying the high scores, will be manipulated by the
        // shared preferences
        tv1stPlaceCash = (TextView) findViewById(R.id.tv1stPlaceCash);
        tv1stPlaceTitle = (TextView) findViewById(R.id.tv1stPlaceTitle);
        tv1stPlaceScore = (TextView) findViewById(R.id.tv1stPlaceScore);

        tv2ndPlaceTitle = (TextView) findViewById(R.id.tv2ndPlaceTitle);
        tv2ndPlaceCash = (TextView) findViewById(R.id.tv2ndPlaceCash);
        tv2ndPlaceScore = (TextView) findViewById(R.id.tv2ndPlaceScore);

        tv3rdPlaceTitle = (TextView) findViewById(R.id.tv3rdPlaceTitle);
        tv3rdPlaceScore = (TextView) findViewById(R.id.tv3rdPlaceScore);
        tv3rdPlaceCash = (TextView) findViewById(R.id.tv3rdPlaceCash);


        tv1stPlaceTitle.setText(mcSDPrefs.loadStringPreferences("1stFlightName"));
        tv1stPlaceCash.setText(Integer.toString(mcSDPrefs.loadIntPreferences("1stFlightCash")));
        tv1stPlaceScore.setText(Integer.toString(mcSDPrefs.loadIntPreferences("1stFlightScore")));

        tv2ndPlaceTitle.setText(mcSDPrefs.loadStringPreferences("2ndFlightName"));
        tv2ndPlaceCash.setText(Integer.toString(mcSDPrefs.loadIntPreferences("2ndFlightCash")));
        tv2ndPlaceScore.setText(Integer.toString(mcSDPrefs.loadIntPreferences("2ndFlightScore")));

        tv3rdPlaceTitle.setText(mcSDPrefs.loadStringPreferences("3rdFlightName"));
        tv3rdPlaceCash.setText(Integer.toString(mcSDPrefs.loadIntPreferences("3rdFlightCash")));
        tv3rdPlaceScore.setText(Integer.toString(mcSDPrefs.loadIntPreferences("3rdFlightScore")));
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
                Log.e("Debug", "quit pressed in top menu on options screen");
                finish();
                return true;

            case R.id.mAbout:
                Log.e("Debug", "about button pressed in top menu on options screen");
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
        switch(v.getId())
        {
            case R.id.btnReturn:
                //return to the main menu
                Log.e("Debug", "Return pressed in Options Menu");
                setResult(Activity.RESULT_OK);
                finish();
                break;

            default:
                break;
        }
    }
}