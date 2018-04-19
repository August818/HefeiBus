package com.hefeibus.www.hefeibus.basemvp;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment base Activity
 * Created by xyw-mac on 2018/3/18.
 */

public abstract class BaseMvpFragment extends Fragment {

    protected View invokeMe(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(setLayoutView(), container, false);

        findViews(view);

        setAttributes();

        return view;
    }

    protected <T extends View> T findID(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * init some content within views
     * eg: setAdapter addListener;
     */
    protected abstract void setAttributes();

    /**
     * find Views in the layout and set into the members
     *
     * @param view the View that contains the widgets
     */
    protected abstract void findViews(View view);

    /**
     * setting the layout resource of the Activity
     *
     * @return the resource id of layout resource
     */
    protected abstract int setLayoutView();
}
