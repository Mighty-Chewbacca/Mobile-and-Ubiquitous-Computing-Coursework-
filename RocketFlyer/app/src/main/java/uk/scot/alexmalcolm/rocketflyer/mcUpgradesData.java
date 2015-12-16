package uk.scot.alexmalcolm.rocketflyer;

import java.io.Serializable;

/**
 * Created by Alex on 01/12/2015.
 */
public class mcUpgradesData implements Serializable
{
    private int upgradeLevel;
    private String upgradeName;
    private String upgradeDescription;
    private String upgradeChanges;
    private float upgradeModifications;
    private int upgradeCost;


    //public getters and setters for all variables
    public int getUpgradeLevel()
    {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel)
    {
        this.upgradeLevel = upgradeLevel;
    }

    public String getUpgradeName()
    {
        return upgradeName;
    }

    public void setUpgradeName(String upgradeName)
    {
        this.upgradeName = upgradeName;
    }

    public String getUpgradeDescription()
    {
        return upgradeDescription;
    }

    public void setUpgradeDescription(String upgradeDescription)
    {
        this.upgradeDescription = upgradeDescription;
    }

    public String getUpgradeChanges()
    {
        return upgradeChanges;
    }

    public void setUpgradeChanges(String upgradeChanges)
    {
        this.upgradeChanges = upgradeChanges;
    }

    public float getUpgradeModifications()
    {
        return upgradeModifications;
    }

    public void setUpgradeModifications(float upgradeModifications)
    {
        this.upgradeModifications = upgradeModifications;
    }

    public int getUpgradeCost()
    {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost)
    {
        this.upgradeCost = upgradeCost;
    }

    public String UpgradeToString()
    {
        String usersData;
        usersData = "Upgradelevel=" + upgradeLevel +
                ", UpgradeName=" + upgradeName +
                ", UpgradeDescription=" + upgradeDescription +
                ", UpgradeChanges=" + upgradeChanges +
                ", UpgradeModifications=" + upgradeModifications +
                ", UpgradeCost=" + upgradeCost + "]";
        return usersData;
    }

}
