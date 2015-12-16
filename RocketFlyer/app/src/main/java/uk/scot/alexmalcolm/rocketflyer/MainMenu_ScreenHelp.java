package uk.scot.alexmalcolm.rocketflyer;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

/**
 * Created by Alex
 * this is the games help screen, it explains the game and what to do.
 * it gives only return access to the main menu.
 */

public class MainMenu_ScreenHelp extends AppCompatActivity implements View.OnClickListener
{
    Button btnReturn;
    FragmentManager fmAboutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_screen_help);

        fmAboutDialog = this.getFragmentManager();

        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);
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
                Log.e("Debug", "Quit pressed in top menu on help screen");
                finish();
                return true;

            case R.id.mAbout:
                Log.e("Debug", "About pressed in top menu on help screen");
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
                Log.e("Debug", "Return pressed in Help Menu");
                setResult(Activity.RESULT_OK);
                finish();
                break;

            default:
                break;
        }
    }
}
