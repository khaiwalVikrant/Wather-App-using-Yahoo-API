package com.showers.utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.showers.R;

/**
 * Created by vikrant on 03/06/16.
 */

public class Progressdialog extends Dialog {
    private static Handler dialogHandler;
    private Runnable runner;

    static {
        dialogHandler = new Handler();
    }

    public Progressdialog (Context c)
    {
        super(c);
    }

    public static  Dialog show (Context c)
    {
        final Progressdialog pd = new Progressdialog(c);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pd.setContentView(R.layout.customdialog);
        final ImageView imagetorotate = (ImageView) pd.findViewById(R.id.progresswrench);
        final Animation rotation = AnimationUtils.loadAnimation(c, R.anim.rotate_progress);
        rotation.setRepeatCount(Animation.INFINITE);
        pd.setCancelable(false);
        pd.runner = new Runnable() {

            public void run() {
                try {
                    pd.show();
                    imagetorotate.startAnimation(rotation);
                }
                catch (Exception e) {
                    /* do nothing */
                }
            }
        };
        dialogHandler.postDelayed(pd.runner, 0);
        return pd;
    }

    @Override
    public void cancel()
    {
        dialogHandler.removeCallbacks (runner);
        super.cancel();
    }
}