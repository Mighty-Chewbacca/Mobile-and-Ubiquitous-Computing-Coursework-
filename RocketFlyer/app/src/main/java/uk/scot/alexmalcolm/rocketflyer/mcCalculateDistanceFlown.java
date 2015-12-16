package uk.scot.alexmalcolm.rocketflyer;

/**
 * Created by Alex malcolm on 27/10/2015.
 */
public class mcCalculateDistanceFlown
{
    private int initialSpeed;
    private int initialFlightLength;
    private float engineUpgradeModifier;
    private float hullUpgradeModifier;
    private int fuelTankModifier;
    private float weatherModifier;

    public mcCalculateDistanceFlown(float engine, float hull, int fuel, float weather)
    {
        initialSpeed = 10; //10 metres a second before any hull or engine upgrades
        initialFlightLength = 30; //30 seconds initial flight length before all upgrades

        engineUpgradeModifier = engine;
        fuelTankModifier = fuel;
        hullUpgradeModifier = hull;
        weatherModifier = weather;
    }

    public float CalculateDistance()
    {
        float distance;
        //to calculate the distance
        //distance = initial flight length X flight length modifier
        //distance = initial speed X engine upgrade modifier X hull upgrade modifier X distance
        //distance = weather modifier X distance

        float flightLength = initialFlightLength * fuelTankModifier; //length of the flight in seconds
        float totalDistance = initialSpeed * engineUpgradeModifier * hullUpgradeModifier * flightLength; //total distance travelled before the weather is taken into account
        distance = weatherModifier * totalDistance; //the final distance that the rocket has travelled, the distance multiplied by the weather variable.

        return distance;
    }


    //getters and setters for the variables just in case
    public int getInitialSpeed() {
        return initialSpeed;
    }

    public void setInitialSpeed(int initialSpeed) {
        this.initialSpeed = initialSpeed;
    }

    public float getWeatherModifier() {
        return weatherModifier;
    }

    public void setWeatherModifier(float weatherModifier) {
        this.weatherModifier = weatherModifier;
    }

    public int getFuelTankModifier() {
        return fuelTankModifier;
    }

    public void setFuelTankModifier(int fuelTankModifier) {
        this.fuelTankModifier = fuelTankModifier;
    }

    public float getHullUpgradeModifier() {
        return hullUpgradeModifier;
    }

    public void setHullUpgradeModifier(float hullUpgradeModifier) {
        this.hullUpgradeModifier = hullUpgradeModifier;
    }

    public float getEngineUpgradeModifier() {
        return engineUpgradeModifier;
    }

    public void setEngineUpgradeModifier(float engineUpgradeModifier) {
        this.engineUpgradeModifier = engineUpgradeModifier;
    }

    public int getInitialFlightLength() {
        return initialFlightLength;
    }

    public void setInitialFlightLength(int initialFlightLength) {
        this.initialFlightLength = initialFlightLength;
    }
}
