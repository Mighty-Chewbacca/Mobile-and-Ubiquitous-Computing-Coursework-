package uk.scot.alexmalcolm.rocketflyer;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.io.IOException;

/**
 * Created by Alex on 16/11/2015.
 * this menu will contain the play and upgrade buttons for the actual game loop
 */
public class PlayMenu_ScreenMain extends AppCompatActivity implements View.OnClickListener
{
    Button btnReturn, btnUpgradeScreen, btnLaunch, btnPastFlights;
    FragmentManager fmAboutDialog;
    RocketDBMgr dbMgr;
    mcSavePrefs mcSDPrefs;
    SharedPreferences mcSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_menu_screen_main);
        fmAboutDialog = this.getFragmentManager();

        mcSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mcSDPrefs = new mcSavePrefs(mcSharedPrefs);

        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        btnUpgradeScreen = (Button) findViewById(R.id.btnUpgradeScreen);
        btnUpgradeScreen.setOnClickListener(this);

        btnPastFlights = (Button) findViewById(R.id.btnPastFlights);
        btnPastFlights.setOnClickListener(this);

        btnLaunch = (Button) findViewById(R.id.btnLaunch);
        btnLaunch.setOnClickListener(this);

        dbMgr = new RocketDBMgr(this, "RocketUpgradesDB3.s3db", null, 1);
        try
        {
            dbMgr.dbCreate();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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
                Log.e("Main Screen", "quit pressed in top menu on play menu screen");
                finish();
                return true;

            case R.id.mAbout:
                Log.e("Main Screen", "about button pressed in top menu on play menu screen");
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
                Log.e("Main Screen", "return pressed from the play menu");
                setResult(Activity.RESULT_OK);
                finish();
                break;

            case R.id.btnUpgradeScreen:
                //return to the main menu
                Log.e("Main Screen", "return pressed from the play menu");
                Intent MainMenu_ScreenUpgrade = new Intent(getApplicationContext(), uk.scot.alexmalcolm.rocketflyer.PlayMenu_ScreenUpgrade.class);
                startActivity(MainMenu_ScreenUpgrade);
                break;

            case R.id.btnLaunch:
                //return to the main menu
                Log.e("Main Screen", "return pressed from the play menu");
                Intent PlayScreen_Main = new Intent(getApplicationContext(), uk.scot.alexmalcolm.rocketflyer.PlayScreen_Main.class);
                startActivity(PlayScreen_Main);
                break;

            case R.id.btnPastFlights:
                //return to the main menu
                Log.e("Main Screen", "return pressed from the play menu");
                Intent PastFlights = new Intent(getApplicationContext(), uk.scot.alexmalcolm.rocketflyer.PlayScreen_Leaderboard.class);
                startActivity(PastFlights);
                break;

            default:
                break;
        }
    }
}
