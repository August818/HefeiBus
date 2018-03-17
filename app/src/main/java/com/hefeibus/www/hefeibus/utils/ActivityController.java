package com.hefeibus.www.hefeibus.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity Controller Manager
 * Created by cx on 2018/3/17.
 */

public class ActivityController {
    private static List<Activity> activityList;

    private static ActivityController controller;

    private ActivityController() {
        activityList = new ArrayList<>();
    }

    public static ActivityController getInstance() {
        if (controller == null) {
            controller = new ActivityController();
        }
        return controller;
    }

    public void add(Activity activity) {
        activityList.add(activity);
    }

    public void remove(Activity activity) {
        activityList.remove(activity);
    }

    public void quit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
