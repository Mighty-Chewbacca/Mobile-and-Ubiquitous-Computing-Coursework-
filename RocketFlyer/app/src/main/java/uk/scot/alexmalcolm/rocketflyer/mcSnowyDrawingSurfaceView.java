package uk.scot.alexmalcolm.rocketflyer;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by rla on 29/10/2014.
 */
public class mcSnowyDrawingSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder shDrawingHolder;

    mcSnowyDrawingThread drawingThread = null;


    public mcSnowyDrawingSurfaceView(Context context)
    {
        super(context);
        shDrawingHolder = getHolder();
        shDrawingHolder.addCallback(this);
        drawingThread = new mcSnowyDrawingThread(getHolder(), this);
        setFocusable(true);

    }

    public mcSnowyDrawingThread getThread()
    {
        return drawingThread;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {

        drawingThread.setRunning(true);
        drawingThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        drawingThread.setSurfaceSize(width,height);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        drawingThread.setRunning(false);
        while(retry)
        {
            try {
                drawingThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

