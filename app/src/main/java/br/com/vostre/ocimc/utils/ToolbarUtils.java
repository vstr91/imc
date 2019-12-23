package br.com.vostre.ocimc.utils;

import android.app.Activity;
import android.view.Menu;

import android.view.View;

import br.com.vostre.ocimc.R;

/**
 * Created by Almir on 16/12/2015.
 */
public class ToolbarUtils {

    public static void preparaMenu(Menu menu, Activity activity, View.OnClickListener listener) {
        activity.getMenuInflater().inflate(R.menu.main, menu);
    }

    public static void onMenuItemClick(View v, final Activity activity) {
        switch (v.getId()) {
            case android.R.id.home:
                activity.onBackPressed();
                break;
        }
    }

}
