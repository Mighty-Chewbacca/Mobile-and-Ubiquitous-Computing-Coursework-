package uk.scot.alexmalcolm.rocketflyer;

/**
 * Created by Alex on 05/12/2015.
 * this class will calculate the distance that the rocket has flown.
 * this is down to the users upgrades and the weather
 */
public class mcCalculateScore
{
    int engineLevel, hullLevel, fuelTankLevel, weatherModifier, totalDistance;
    float basicSpeed = 10.0f, basicFlightLength = 15.0f, engineModifier, hullModifier, fuelTankModifier;

    public mcCalculateScore(mcUserData userData, RocketDBMgr dbMgr)
    {
        engineLevel = userData.getEngineUpgradeLevel();
        hullLevel = userData.getHullUpgradeLevel();
        fuelTankLevel = userData.getFuelTankUpgradeLevel();

        hullModifier = dbMgr.GetHullVariables(hullLevel).getUpgradeModifications();
        engineModifier = dbMgr.GetEngineVariables(engineLevel).getUpgradeModifications();
        fuelTankModifier = dbMgr.GetFuelTankVariables(fuelTankLevel).getUpgradeModifications();
    }


    public int CalculateDistance() // will eventually need the weather
    {
        //the equation for calculating the distance flown is
        // distance = weathermodifier(engine X hull X basic speed(fueltank X basic flight length))
        //without the weather atm, the weather modifier will be ignored.

        totalDistance = Math.round(CalculateOverallSpeed() * CalculateOverallFlightLength());
        // take it as an int, no need in this scope to be too exact

        return totalDistance;
    }

    private float CalculateOverallSpeed()
    {
        //final speed is engine mod X hull mod X basic speed
        float finalSpeed = 0;

        finalSpeed = engineModifier * hullModifier * basicSpeed;

        return finalSpeed;
    }

    private float CalculateOverallFlightLength()
    {
        //final length is fuel tank mod X basic length
        float finalFlightLength = 0;

        finalFlightLength = fuelTankModifier * basicFlightLength;

        return finalFlightLength;
    }
}
