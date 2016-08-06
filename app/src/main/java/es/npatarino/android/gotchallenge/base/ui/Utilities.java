package es.npatarino.android.gotchallenge.base.ui;

import android.content.Context;

public class Utilities {

    public static int dp(Context context, float value) {
        if (value == 0) {
            return 0;
        }

        float density = context.getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }

}
