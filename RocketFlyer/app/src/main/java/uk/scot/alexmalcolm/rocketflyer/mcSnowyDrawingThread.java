package uk.scot.alexmalcolm.rocketflyer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.view.SurfaceHolder;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by rla on 29/10/2014.
 */
public class mcSnowyDrawingThread extends Thread
{
    private int canvasWidth;
    private int canvasHeight;
    private boolean run = false;

    private SurfaceHolder shSnowyHolder;
    private Paint paintBio;
    private mcSnowyDrawingSurfaceView drawingSf;

    public mcSnowyDrawingThread(SurfaceHolder surfaceHolder, mcSnowyDrawingSurfaceView bioSurfV) {
        this.shSnowyHolder = surfaceHolder;
        this.drawingSf = bioSurfV;
        paintBio = new Paint();
    }

    public void doStart()
    {
        synchronized (shSnowyHolder)
        {

        }
    }

    public void run() {
        while (run) {
            Canvas c = null;
            try {
                c = shSnowyHolder.lockCanvas(null);
                synchronized (shSnowyHolder) {
                    svDraw(c);
                }
            } finally {
                if (c != null) {
                    shSnowyHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public void setRunning(boolean b) {
        run = b;
    }
    public void setSurfaceSize(int width, int height) {
        synchronized (shSnowyHolder) {
            canvasWidth = width;
            canvasHeight = height;
            doStart();
        }
    }


    private void svDraw(Canvas canvas) {
        if(run) {
            canvas.save();
            canvas.restore();
            canvas.drawColor(Color.BLACK);
            paintBio.setStyle(Paint.Style.FILL);
            drawSnowyScene(canvas);
        }
    }

    public void drawSnowyScene(Canvas theCanvas)
    {
        paintBio.setStyle(Paint.Style.FILL);
        paintBio.setColor(Color.DKGRAY);
        theCanvas.drawCircle(canvasWidth / 4, (canvasHeight / 4) * 3, 250.0f, paintBio);

        //left top right bottom
        paintBio.setColor(Color.WHITE);
        theCanvas.drawRect(1600, 0, canvasWidth, canvasHeight, paintBio);
        for (int i = 0; i < 100; i++) //draw the snowflakes
        {
            int randomx = (int )(Math.random() * canvasWidth + 1);
            int randomy = (int )(Math.random() * canvasHeight + 1);
            paintBio.setColor(Color.WHITE);
            theCanvas.drawCircle(randomx, randomy, 5.0f, paintBio);
        }
    }


}

