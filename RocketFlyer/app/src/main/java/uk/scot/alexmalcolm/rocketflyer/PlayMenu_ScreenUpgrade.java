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
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.IOException;

/**
 * Created by Alex on 16/11/2015.
 * This screen will contain the upgrades for the player to purchase during playing
 */
public class PlayMenu_ScreenUpgrade extends AppCompatActivity implements View.OnClickListener
{
    Button btnReturn, btnEngineUpgrades, btnHullUpgrades, btnFuelUpgrades, btnReturnUpgrade, btnUpgrade;
    TextView tvUpgradeTitle, tvUpgradeModification, tvUpgradeName,
    tvUpgradeChanges, tvUpgradeCost;
    FragmentManager fmAboutDialog;
    RocketDBMgr dbMgr;
    mcSavePrefs mcSDPrefs;
    SharedPreferences mcSharedPrefs;
    int currentLevel, nextLevel, currentUser;
    mcUpgradesData nextUpgrade;
    mcUserData theUser;
    String currentUpgrade;

    private ViewSwitcher vsUpgradeSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_menu_upgrade);

        mcSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mcSDPrefs = new mcSavePrefs(mcSharedPrefs);

        fmAboutDialog = this.getFragmentManager();

        tvUpgradeTitle = (TextView) findViewById(R.id.tvTitle);
        tvUpgradeModification = (TextView) findViewById(R.id.tvUpgradeModification);
        tvUpgradeName = (TextView) findViewById(R.id.tvUpgradeName);
        tvUpgradeChanges = (TextView) findViewById(R.id.tvUpgradeChanges);
        tvUpgradeCost = (TextView) findViewById(R.id.tvUpgradeCost);

        btnEngineUpgrades = (Button) findViewById(R.id.btnEngineUpgrades);
        btnEngineUpgrades.setOnClickListener(this);

        btnHullUpgrades = (Button) findViewById(R.id.btnHullUpgrades);
        btnHullUpgrades.setOnClickListener(this);

        btnFuelUpgrades = (Button) findViewById(R.id.btnFuelUpgrades);
        btnFuelUpgrades.setOnClickListener(this);

        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        btnUpgrade = (Button) findViewById(R.id.btnUpgrade);
        btnUpgrade.setOnClickListener(this);

        btnReturnUpgrade = (Button) findViewById(R.id.btnReturnUpgrade);
        btnReturnUpgrade.setOnClickListener(this);

        vsUpgradeSwitcher = (ViewSwitcher) findViewById(R.id.vsUpgradeSwitcher);

        vsUpgradeSwitcher.setDisplayedChild(0);

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
        Log.e("Upgrade Screen", "Loaded save file is: " + currentUser);
        Log.e("Upgrade Screen", "Loaded current user data successfully:  "  + dbMgr.getSaveFile(currentUser).userDataToString());
        theUser = dbMgr.getSaveFile(currentUser);
        currentUpgrade = " no upgrade selected";
    }

    private void SwitchView(String upgradeType)
    {
        String temp;
        switch (upgradeType)
        {
            case "Engine":
                Log.e("UpgradeScreen", "engine was pressed, switching to engine upgrades in switcher");
                currentUpgrade = "Engine";
                currentLevel = theUser.getEngineUpgradeLevel(); ///this needs changed to be the current users file - not a specific one
                nextLevel = currentLevel + 1;
                nextUpgrade = dbMgr.GetEngineVariables(nextLevel);
                tvUpgradeName.setText(nextUpgrade.getUpgradeName());
                tvUpgradeChanges.setText(nextUpgrade.getUpgradeChanges());
                tvUpgradeCost.setText(Integer.toString(nextUpgrade.getUpgradeCost()));
                temp = Float.toString(nextUpgrade.getUpgradeModifications());
                tvUpgradeModification.setText(temp);
                tvUpgradeTitle.setText("Engine Upgrades");
                btnUpgrade.setText("Purchase Engine Upgrade");
                vsUpgradeSwitcher.setDisplayedChild(1);
                break;

            case "FuelTank":
                Log.e("UpgradeScreen", "fuel tank was pressed, switching to fuel tank upgrades in switcher");
                currentUpgrade = "Fuel Tank";
                currentLevel = theUser.getFuelTankUpgradeLevel(); ///this needs changed to be the current users file - not a specific one
                nextLevel = currentLevel + 1;
                nextUpgrade = dbMgr.GetFuelTankVariables(nextLevel);
                tvUpgradeName.setText(nextUpgrade.getUpgradeName());
                tvUpgradeChanges.setText(nextUpgrade.getUpgradeChanges());
                tvUpgradeCost.setText(Integer.toString(nextUpgrade.getUpgradeCost()));
                temp = Float.toString(nextUpgrade.getUpgradeModifications());
                tvUpgradeModification.setText(temp);
                tvUpgradeTitle.setText("Fuel Tank Upgrades");
                btnUpgrade.setText("Purchase Fuel Tank Upgrade");
                vsUpgradeSwitcher.setDisplayedChild(1);
                break;

            case "Hull":
                Log.e("UpgradeScreen", "hull was pressed, switching to hull upgrades in switcher");
                currentUpgrade = "Hull";
                currentLevel = theUser.getHullUpgradeLevel(); ///this needs changed to be the current users file - not a specific one
                nextLevel = currentLevel + 1;
                    nextUpgrade = dbMgr.GetHullVariables(nextLevel);
                    tvUpgradeName.setText(nextUpgrade.getUpgradeName());
                    tvUpgradeChanges.setText(nextUpgrade.getUpgradeChanges());
                    tvUpgradeCost.setText(Integer.toString(nextUpgrade.getUpgradeCost()));
                    temp = Float.toString(nextUpgrade.getUpgradeModifications());
                    tvUpgradeModification.setText(temp);
                    tvUpgradeTitle.setText("Hull Upgrades");
                    btnUpgrade.setText("Purchase Hull Upgrade");
                    vsUpgradeSwitcher.setDisplayedChild(1);
                break;

            case "Return":
                Log.e("UpgradeScreen", "return was pressed in switcher, switching to child 0 in switcher");
                vsUpgradeSwitcher.setDisplayedChild(0);
                break;

            default:
                break;
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
                Log.e("UpgradeScreen", "quit pressed in top menu on upgrades screen");
                finish();
                return true;

            case R.id.mAbout:
                Log.e("UpgradeScreen", "about button pressed in top menu on upgrades screen");
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
                Log.e("UpgradeScreen", "Return pressed in upgrades Menu");
                setResult(Activity.RESULT_OK);
                finish();
                break;

            case R.id.btnHullUpgrades:
                //return to the main menu
                Log.e("UpgradeScreen", "hull upgrades pressed in upgrades menu");
                SwitchView("Hull");
                break;

            case R.id.btnFuelUpgrades:
                //return to the main menu
                Log.e("UpgradeScreen", "fuel tank upgrades pressed in upgrades Menu");
                SwitchView("FuelTank");
                break;

            case R.id.btnEngineUpgrades:
                //return to the main menu
                Log.e("UpgradeScreen", "engine upgrades pressed in upgrades Menu");
                SwitchView("Engine");
                break;

            case R.id.btnReturnUpgrade:
                //return to the main menu
                Log.e("UpgradeScreen", "Return pressed in upgrades Menu");
                SwitchView("Return");
                break;

            case R.id.btnUpgrade:
                //check if the user has enough money to buy
                //if they do -- buy upgrade
                //if they dont -- send toast message and do nothing else.

                if (theUser.getCurrentCash() >= nextUpgrade.getUpgradeCost())
                {
                    //buy the upgrade -- remove the money and increase the upgrade level stored in file
                    theUser.setCurrentCash(theUser.getCurrentCash() - nextUpgrade.getUpgradeCost());

                    //check which upgrade we need to increment if the purchase was successful
                    if (currentUpgrade == "Hull")
                    {
                        theUser.setHullUpgradeLevel(theUser.getHullUpgradeLevel() + 1);
                        Toast.makeText(getApplicationContext(), "Hull Upgraded", Toast.LENGTH_SHORT).show();
                    }
                    else if (currentUpgrade == "Fuel Tank")
                    {
                        theUser.setFuelTankUpgradeLevel(theUser.getFuelTankUpgradeLevel() + 1);
                        Toast.makeText(getApplicationContext(), "Fuel Tank Upgraded", Toast.LENGTH_SHORT).show();
                    }
                    else if (currentUpgrade == "Engine")
                    {
                        theUser.setEngineUpgradeLevel(theUser.getEngineUpgradeLevel() + 1);
                        Toast.makeText(getApplicationContext(), "Engine Upgraded", Toast.LENGTH_SHORT).show();
                    }

                    dbMgr.OverwriteSavedData(theUser, currentUser);
                    Log.e("Upgrade Screen", "Overwritten the save data:  " + dbMgr.getSaveFile(currentUser).userDataToString());
                }
                else
                {
                    //make toast to tell user they couldnt buy the upgrade
                    Toast.makeText(getApplicationContext(), "Insufficient Funds", Toast.LENGTH_SHORT).show();
                }

                vsUpgradeSwitcher.setDisplayedChild(0);
                break;

            default:
                break;
        }
    }
}