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

import java.io.IOException;

/**
 * Created by Alex on 18/11/2015.
 */
public class PlayScreen_Leaderboard extends AppCompatActivity implements View.OnClickListener
{
    Button btnReturn;
    TextView tvFlight1Score, tvFlight1Cash, tvFlight2Score, tvFlight2Cash, tvFlight3Score, tvFlight3Cash,
            tvFlight4Score, tvFlight4Cash, tvFlight5Score, tvFlight5Cash;
    FragmentManager fmAboutDialog;
    mcUserData theUser;
    RocketDBMgr dbMgr;
    mcSavePrefs mcSDPrefs;
    SharedPreferences mcSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_leaderboard);

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
        theUser = dbMgr.getSaveFile(mcSDPrefs.loadIntPreferences("UsersSaveFile"));

        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        //setting up all of the text views that will be edited to show the previous scores.
        tvFlight1Cash = (TextView) findViewById(R.id.tvFlight1Cash);
        tvFlight1Score = (TextView) findViewById(R.id.tvFlight1Score);

        tvFlight2Cash = (TextView) findViewById(R.id.tvFlight2Cash);
        tvFlight2Score = (TextView) findViewById(R.id.tvFlight2Score);

        tvFlight3Cash = (TextView) findViewById(R.id.tvFlight3Cash);
        tvFlight3Score = (TextView) findViewById(R.id.tvFlight3Score);

        tvFlight4Cash = (TextView) findViewById(R.id.tvFlight4Cash);
        tvFlight4Score = (TextView) findViewById(R.id.tvFlight4Score);

        tvFlight5Cash = (TextView) findViewById(R.id.tvFlight5Cash);
        tvFlight5Score = (TextView) findViewById(R.id.tvFlight5Score);

        getLeaderboards();
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

    private void getLeaderboards()
    {
        tvFlight1Score.setText(Integer.toString(theUser.getPreviousFlight1Score()));
        tvFlight1Cash.setText(Integer.toString(theUser.getPreviousFlight1Cash()));

        tvFlight2Score.setText(Integer.toString(theUser.getPreviousFlight2Score()));
        tvFlight2Cash.setText(Integer.toString(theUser.getPreviousFlight2Cash()));

        tvFlight3Score.setText(Integer.toString(theUser.getPreviousFlight3Score()));
        tvFlight3Cash.setText(Integer.toString(theUser.getPreviousFlight3Cash()));

        tvFlight4Score.setText(Integer.toString(theUser.getPreviousFlight4Score()));
        tvFlight4Cash.setText(Integer.toString(theUser.getPreviousFlight4Cash()));

        tvFlight5Score.setText(Integer.toString(theUser.getPreviousFlight5Score()));
        tvFlight5Cash.setText(Integer.toString(theUser.getPreviousFlight5Cash()));
    }
}
