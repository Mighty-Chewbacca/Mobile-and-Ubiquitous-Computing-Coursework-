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
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;

public class MainMenu_ScreenOptions extends AppCompatActivity implements View.OnClickListener
{
    Button btnReturn, btnResetLeaderBoards, btnResetUserFiles;
    ToggleButton tbSoundToggle, tbUserFile1, tbUserFile2, tbUserFile3;
    FragmentManager fmAboutDialog;
    boolean user1Selected = false, user2Selected = false, user3Selected = false, soundMuted = false;
    RocketDBMgr dbMgr;
    mcSavePrefs mcSDPrefs;
    SharedPreferences mcSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_screen_options);

        //load the sound muted from the prefs

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

        soundMuted = mcSDPrefs.loadBooleanPreferences("soundState");

        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        btnResetLeaderBoards = (Button) findViewById(R.id.btnResetLeaderBoards);
        btnResetLeaderBoards.setOnClickListener(this);

        btnResetUserFiles = (Button) findViewById(R.id.btnResetUserFiles);
        btnResetUserFiles.setOnClickListener(this);


        tbUserFile1 = (ToggleButton) findViewById(R.id.tbUserFile1);
        tbUserFile1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    Log.e("Debug", "userfile1 selected");
                    user1Selected = true;
                }
                else
                {
                    Log.e("Debug", "userfile1 unselected");
                    user1Selected = false;
                }
            }
        });

        tbUserFile2 = (ToggleButton) findViewById(R.id.tbUserFile2);
        tbUserFile2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    Log.e("Debug", "userfile2 selected");
                    user2Selected = true;
                }
                else
                {
                    Log.e("Debug", "userfile2 unselected");
                    user2Selected = false;
                }
            }
        });

        tbUserFile3 = (ToggleButton) findViewById(R.id.tbUserFile3);
        tbUserFile3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    Log.e("Debug", "userfile3 selected");
                    user3Selected = true;
                }
                else
                {
                    Log.e("Debug", "userfile3 unselected");
                    user3Selected = true;
                }
            }
        });
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

            case R.id.btnResetLeaderBoards:
                //return to the main menu
                Log.e("Debug", "Return pressed in Options Menu");
                //reset the main menu leaderboard - not the save file flight trackers

                mcSDPrefs.ResetLeaderboardPrefs();

                Toast.makeText(getApplicationContext(), "Leaderboard Reset", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnResetUserFiles:
                //return to the main menu
                Log.e("Debug", "Return pressed in Options Menu");
                //reset only the selected user files
                if (user1Selected)
                {
                    dbMgr.ResetUserData(1);
                    tbUserFile1.setChecked(false);
                }
                if(user2Selected)
                {
                    dbMgr.ResetUserData(2);
                    tbUserFile2.setChecked(false);
                }
                if(user3Selected)
                {
                    dbMgr.ResetUserData(3);
                    tbUserFile3.setChecked(false);
                }
                Toast.makeText(getApplicationContext(), "Selected User Files Reset", Toast.LENGTH_SHORT).show();
                //should only have reset ones that were on true
                break;

            default:
                break;
        }
    }
}
