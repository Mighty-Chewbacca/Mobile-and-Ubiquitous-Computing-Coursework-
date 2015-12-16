package uk.scot.alexmalcolm.rocketflyer;

import java.io.Serializable;

/**
 * Created by Alex on 01/12/2015.
 */
public class mcUserData implements Serializable
{
    private int engineUpgradeLevel;
    private int fuelTankUpgradeLevel;
    private int hullUpgradeLevel;
    private String userName;

    private int currentCash;

    //previous flight 1 stuff
    private int previousFlight1Cash;
    private int previousFlight1Score;

    //previous flight 2 stuff
    private int previousFlight2Cash;
    private int previousFlight2Score;

    //previous flight 3 stuff
    private int previousFlight3Cash;
    private int previousFlight3Score;

    //previous flight 4 stuff
    private int previousFlight4Cash;
    private int previousFlight4Score;

    //previous flight 5 stuff
    private int previousFlight5Cash;
    private int previousFlight5Score;

    //public getters and setters for all variables
    public int getEngineUpgradeLevel()
    {
        return engineUpgradeLevel;
    }

    public void setEngineUpgradeLevel(int engineUpgradeLevel)
    {
        this.engineUpgradeLevel = engineUpgradeLevel;
    }

    public int getCurrentCash()
    {
        return currentCash;
    }

    public void setCurrentCash(int currentCash)
    {
        this.currentCash = currentCash;
    }

    public int getFuelTankUpgradeLevel()
    {
        return fuelTankUpgradeLevel;
    }

    public void setFuelTankUpgradeLevel(int fuelTankUpgradeLevel)
    {
        this.fuelTankUpgradeLevel = fuelTankUpgradeLevel;
    }

    public int getHullUpgradeLevel()
    {
        return hullUpgradeLevel;
    }

    public void setHullUpgradeLevel(int hullUpgradeLevel)
    {
        this.hullUpgradeLevel = hullUpgradeLevel;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getPreviousFlight1Cash()
    {
        return previousFlight1Cash;
    }

    public void setPreviousFlight1Cash(int previousFlight1Cash)
    {
        this.previousFlight1Cash = previousFlight1Cash;
    }

    public int getPreviousFlight1Score()
    {
        return previousFlight1Score;
    }

    public void setPreviousFlight1Score(int previousFlight1Score)
    {
        this.previousFlight1Score = previousFlight1Score;
    }

    public int getPreviousFlight2Cash()
    {
        return previousFlight2Cash;
    }

    public void setPreviousFlight2Cash(int previousFlight2Cash)
    {
        this.previousFlight2Cash = previousFlight2Cash;
    }

    public int getPreviousFlight2Score()
    {
        return previousFlight2Score;
    }

    public void setPreviousFlight2Score(int previousFlight2Score)
    {
        this.previousFlight2Score = previousFlight2Score;
    }

    public int getPreviousFlight3Cash()
    {
        return previousFlight3Cash;
    }

    public void setPreviousFlight3Cash(int previousFlight3Cash)
    {
        this.previousFlight3Cash = previousFlight3Cash;
    }

    public int getPreviousFlight3Score()
    {
        return previousFlight3Score;
    }

    public void setPreviousFlight3Score(int previousFlight3Score)
    {
        this.previousFlight3Score = previousFlight3Score;
    }

    public int getPreviousFlight4Cash()
    {
        return previousFlight4Cash;
    }

    public void setPreviousFlight4Cash(int previousFlight4Cash)
    {
        this.previousFlight4Cash = previousFlight4Cash;
    }

    public int getPreviousFlight4Score()
    {
        return previousFlight4Score;
    }

    public void setPreviousFlight4Score(int previousFlight4Score)
    {
        this.previousFlight4Score = previousFlight4Score;
    }

    public int getPreviousFlight5Cash()
    {
        return previousFlight5Cash;
    }

    public void setPreviousFlight5Cash(int previousFlight5Cash)
    {
        this.previousFlight5Cash = previousFlight5Cash;
    }

    public int getPreviousFlight5Score()
    {
        return previousFlight5Score;
    }

    public void setPreviousFlight5Score(int previousFlight5Score)
    {
        this.previousFlight5Score = previousFlight5Score;
    }

    public String userDataToString()
    {
        String usersData;
        usersData = "Username=" + userName +
                ", CurrentCash=" + currentCash +
                ", EngineUpgradeLevel=" + engineUpgradeLevel +
                ", FuelTankUpgradelevel=" + fuelTankUpgradeLevel +
                ", HullUpgradeLevel=" + hullUpgradeLevel +
                ", PreviousFlight1Score=" + previousFlight1Score +
                ", PreviousFlight1Cash=" + previousFlight1Cash +
                ", PreviousFlight2Score=" + previousFlight2Score +
                ", PreviousFlight2Cash=" + previousFlight2Cash +
                ", PreviousFlight3Score=" + previousFlight3Score +
                ", PreviousFlight3Cash=" + previousFlight3Cash +
                ", PreviousFlight4Score=" + previousFlight4Score +
                ", PreviousFlight4Cash=" + previousFlight4Cash +
                ", PreviousFlight5Score=" + previousFlight5Score +
                ", PreviousFlight5Cash=" + previousFlight5Cash + "]";
        return usersData;
    }

}
