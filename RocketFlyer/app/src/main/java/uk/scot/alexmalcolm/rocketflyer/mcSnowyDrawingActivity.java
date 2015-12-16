package uk.scot.alexmalcolm.rocketflyer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class mcSnowyDrawingActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mc_snowy_drawing_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(new mcSnowyDrawingSurfaceView(this));
    }
}

