package com.revolve44.firstrunsample;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;

import androidx.annotation.RequiresApi;

public class StatusBarUtils {
    private static int STATUS_BAR_HEIGHT = -1;

    public interface StatusBarHeightListener {
        void onStatusBarHeightFetched(int statusBarHeight);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static void getStatusBarHeight(final View view, final StatusBarHeightListener listener) {
        if (STATUS_BAR_HEIGHT > 0) {
            listener.onStatusBarHeightFetched(STATUS_BAR_HEIGHT);
        }

        view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                STATUS_BAR_HEIGHT = insets.getSystemWindowInsetTop();
                listener.onStatusBarHeightFetched(STATUS_BAR_HEIGHT);
                return insets;
            }
        });
    }
}