package uk.scot.alexmalcolm.rocketflyer;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by Alex
 * This is the main menu screen, it allows access to the help screen, options screen, leaderboards
 * and the load menu
 */

public class MainMenu_ScreenMain  extends AppCompatActivity implements View.OnClickListener
{
    Button btnHelp;
    Button btnPlay;
    Button btnOptions;
    Button btnLeaderboard;

    mcSavePrefs mcSDPrefs;
    SharedPreferences mcSharedPrefs;
    FragmentManager fmAboutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu__screen_main);

        mcSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mcSDPrefs = new mcSavePrefs(mcSharedPrefs);

        fmAboutDialog = this.getFragmentManager();

        btnHelp = (Button) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(this);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);

        btnOptions = (Button) findViewById(R.id.btnOptions);
        btnOptions.setOnClickListener(this);

        btnLeaderboard = (Button) findViewById(R.id.btnLeaderBoards);
        btnLeaderboard.setOnClickListener(this);

        RocketDBMgr dbMgr = new RocketDBMgr(this, "RocketUpgradesDB3.s3db", null, 1);
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
                Log.e("Main Screen", "quit pressed in top menu on main screen");
                finish();
                return true;

            case R.id.mAbout:
                Log.e("Main Screen", "about pressed in top menu on main screen");
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
            case R.id.btnOptions:
                Log.e("Main Screen", "options button pressed in main menu");
                //change to the options intent
                Intent MainMenu_ScreenOptions = new Intent(getApplicationContext(), uk.scot.alexmalcolm.rocketflyer.MainMenu_ScreenOptions.class);
                //give the new intent any extras its going to need here
                startActivity(MainMenu_ScreenOptions);
                break;

            case R.id.btnHelp:
                Log.e("Main Screen", "help button pressed in main menu");
                //change to the help intent
                Intent MainMenu_ScreenHelp = new Intent(getApplicationContext(), uk.scot.alexmalcolm.rocketflyer.MainMenu_ScreenHelp.class);
                //give the new intent any extras its going to need here
                startActivity(MainMenu_ScreenHelp);
                break;

            case R.id.btnPlay:
                Log.e("Main Screen", "play button pressed in main menu");
                //change to the play intent
                Intent MainMenu_ScreenLoad = new Intent(getApplicationContext(), uk.scot.alexmalcolm.rocketflyer.MainMenu_ScreenLoad.class);
                startActivity(MainMenu_ScreenLoad);
                break;

            case R.id.btnLeaderBoards:
                Log.e("Main Screen", "play button pressed in main menu");
                //change to the play intent
                Intent MainMenu_ScreenLeaderboard = new Intent(getApplicationContext(), uk.scot.alexmalcolm.rocketflyer.MainMenu_ScreenLeaderboard.class);
                startActivity(MainMenu_ScreenLeaderboard);
                break;

            default:
                break;
        }
    }
}
