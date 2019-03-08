package fr.kounecorp.bettercallsam.game2_noname;

import android.support.v7.widget.AppCompatTextView;

/*
 * The Android chronometer widget revised so as to count milliseconds
 */

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import java.text.DecimalFormat;

public class ChronometerMillis extends AppCompatTextView {
    @SuppressWarnings("unused")
    private static final String TAG = "Chronometer";
    private static final int TICK_WHAT = 2;
    private long mBase;
    private boolean mVisible;
    private boolean mStarted;
    private boolean mRunning;
    private OnChronometerTickListener mOnChronometerTickListener;
    private long timeElapsed;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message m) {
            if (mRunning) {
                updateText(SystemClock.elapsedRealtime());
                dispatchChronometerTick();
                sendMessageDelayed(Message.obtain(this , TICK_WHAT),
                        10);
            }
        }
    };

    public ChronometerMillis(Context context) {
        this (context, null, 0);
    }

    public ChronometerMillis(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public ChronometerMillis(Context context, AttributeSet attrs, int defStyle) {
        super (context, attrs, defStyle);
        init();
    }

    private void init() {
        mBase = SystemClock.elapsedRealtime();
        updateText(mBase);
    }

    public long getBase() {
        return mBase;
    }

    public void setBase(long base) {
        mBase = base;
        dispatchChronometerTick();
        updateText(SystemClock.elapsedRealtime());
    }

    public OnChronometerTickListener getOnChronometerTickListener() {
        return mOnChronometerTickListener;
    }

    public void setOnChronometerTickListener(
            OnChronometerTickListener listener) {
        mOnChronometerTickListener = listener;
    }

    public void start() {
        mStarted = true;
        updateRunning();
    }

    public void stop() {
        mStarted = false;
        updateRunning();
    }


    public void setStarted(boolean started) {
        mStarted = started;
        updateRunning();
    }

    @Override
    protected void onDetachedFromWindow() {
        super .onDetachedFromWindow();
        mVisible = false;
        updateRunning();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super .onWindowVisibilityChanged(visibility);
        mVisible = visibility == VISIBLE;
        updateRunning();
    }

    private synchronized void updateText(long now) {
        timeElapsed = now - mBase;

        DecimalFormat df = new DecimalFormat("00");

        int hours = (int)(timeElapsed / (3600 * 1000));
        int remaining = (int)(timeElapsed % (3600 * 1000));

        int minutes = (remaining / (60 * 1000));
        remaining = (remaining % (60 * 1000));

        int seconds = (remaining / 1000);

        int milliseconds = (((int)timeElapsed % 1000) / 10);

        String text = "";

        if (hours > 0) {
            text += df.format(hours) + ":";
        }
        if (minutes > 0) {
            text += df.format(minutes) + ":";
        }
        text += df.format(seconds) + ":";
        text += df.format(milliseconds);

        setText(text);
    }

    private void updateRunning() {
        boolean running = mVisible && mStarted;
        if (running != mRunning) {
            if (running) {
                updateText(SystemClock.elapsedRealtime());
                dispatchChronometerTick();
                mHandler.sendMessageDelayed(Message.obtain(mHandler,
                        TICK_WHAT), 10);
            } else {
                mHandler.removeMessages(TICK_WHAT);
            }
            mRunning = running;
        }
    }

    void dispatchChronometerTick() {
        if (mOnChronometerTickListener != null) {
            mOnChronometerTickListener.onChronometerTick(this);
        }
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public double getTimeElapsedInSeconds() {
        double milliseconds = ((int)timeElapsed % 1000) / 10;
        double seconds = (timeElapsed / 1000) + (milliseconds / 100);
        return seconds;
    }

    public interface OnChronometerTickListener {

        void onChronometerTick(ChronometerMillis chronometer);
    }

}
