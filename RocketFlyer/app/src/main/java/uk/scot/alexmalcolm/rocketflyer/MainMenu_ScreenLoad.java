package uk.scot.alexmalcolm.rocketflyer;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Alex on 16/11/2015.
 * this screen will contain the load options to load the players data from a choice of 3 save files
 * it will have return access to the main menu and a button which will start the game passing in the
 * information from the respective load game chosen
 */
public class MainMenu_ScreenLoad extends AppCompatActivity implements View.OnClickListener
{
    Button btnReturn, btnLoad1, btnLoad2, btnLoad3;
    FragmentManager fmAboutDialog;
    TextView tvLoad1, tvLoad2, tvLoad3;
    RocketDBMgr dbMgr;
    mcUserData user1, user2, user3;
    mcSavePrefs mcSDPrefs;
    SharedPreferences mcSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_screen_load);

        mcSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mcSDPrefs = new mcSavePrefs(mcSharedPrefs);

        fmAboutDialog = this.getFragmentManager();

        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        btnLoad1 = (Button) findViewById(R.id.btnLoad1);
        btnLoad1.setOnClickListener(this);

        btnLoad2 = (Button) findViewById(R.id.btnLoad2);
        btnLoad2.setOnClickListener(this);

        btnLoad3 = (Button) findViewById(R.id.btnLoad3);
        btnLoad3.setOnClickListener(this);

        tvLoad1 = (TextView) findViewById(R.id.tvLoad1);
        tvLoad2 = (TextView) findViewById(R.id.tvLoad2);
        tvLoad3 = (TextView) findViewById(R.id.tvLoad3);

        dbMgr = new RocketDBMgr(this, "RocketUpgradesDB3.s3db", null, 1);
        try
        {
            dbMgr.dbCreate();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        user1 = dbMgr.getSaveFile(1);
        user2 = dbMgr.getSaveFile(2);
        user3 = dbMgr.getSaveFile(3);

        String user1Details = user1.getUserName() + "   Current Cash:" + user1.getCurrentCash();
        String user2Details = user2.getUserName() + "   Current Cash:" + user2.getCurrentCash();
        String user3Details = user3.getUserName() + "   Current Cash:" + user3.getCurrentCash();

        tvLoad1.setText(user1Details);
        tvLoad2.setText(user2Details);
        tvLoad3.setText(user3Details);

        Log.e("Load Screen", tvLoad1.getText().toString());
        Log.e("Load Screen", tvLoad2.getText().toString());
        Log.e("Load Screen", tvLoad3.getText().toString());

        //mcSDPrefs = new mcSavePrefs();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mc_menu, menu);
        return true;
    }

    @Override
    public void onRestart()
    {
        super.onRestart();

        recreate();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.mQuit:
                Log.e("Load Screen", "quit pressed in top menu on load screen");
                finish();
                return true;

            case R.id.mAbout:
                Log.e("Load Screen", "about button pressed in top menu on load screen");
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
        Intent PlayMenu_ScreenMain = new Intent(getApplicationContext(), uk.scot.alexmalcolm.rocketflyer.PlayMenu_ScreenMain.class);

        switch(v.getId())
        {
            case R.id.btnReturn:
                //return to the main menu
                Log.e("Load Screen", "Return pressed in load Menu");
                setResult(Activity.RESULT_OK);
                finish();
                break;

            case R.id.btnLoad1:
                if (dbMgr.getSaveFile(1).getUserName().equals("No Saved Data"))
                {
                    setUserName(1);

                }
                else
                {
                    Log.e("Load Screen", "pressed load game 1");
                    mcSDPrefs.savePreferences("UsersSaveFile", 1);
                    startActivity(PlayMenu_ScreenMain);
                }
                break;

            case R.id.btnLoad2:
                if (dbMgr.getSaveFile(2).getUserName().equals("No Saved Data"))
                {
                    setUserName(2);
                }
                else
                {
                    Log.e("Load Screen", "pressed load game 2");
                    mcSDPrefs.savePreferences("UsersSaveFile", 2);
                    startActivity(PlayMenu_ScreenMain);
                }
                break;

            case R.id.btnLoad3:
                if (dbMgr.getSaveFile(3).getUserName().equals("No Saved Data"))
                {
                    setUserName(3);
                }
                else
                {
                    Log.e("Load Screen", "pressed load game 3");
                    mcSDPrefs.savePreferences("UsersSaveFile", 3);
                    startActivity(PlayMenu_ScreenMain);
                }
                break;

            default:
                break;
        }
    }

    private void setUserName (final int userFileNumber)
    {
        final EditText nameTxt = new EditText(this);
        // Set the default text to a link of the Queen
        new AlertDialog.Builder(this)
                .setTitle("Name Input")
                .setMessage("Please Enter your Username")
                .setView(nameTxt)
                .setPositiveButton("Enter", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        String name = nameTxt.getText().toString();
                        mcUserData userFile = new mcUserData();
                        userFile.setUserName(name);
                        dbMgr.OverwriteSavedData(userFile, userFileNumber);
                        dbMgr.getSaveFile(userFileNumber);

                        mcSDPrefs.savePreferences("UsersSaveFile", userFileNumber);
                        Intent PlayMenu_ScreenMain = new Intent(getApplicationContext(), uk.scot.alexmalcolm.rocketflyer.PlayMenu_ScreenMain.class);
                        startActivity(PlayMenu_ScreenMain);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
            }
        }).show();
    }

}