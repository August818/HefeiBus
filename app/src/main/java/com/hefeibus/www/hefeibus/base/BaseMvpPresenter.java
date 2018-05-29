package com.hefeibus.www.hefeibus.base;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

public abstract class BaseMvpPresenter<V extends IView> implements IPresenter<V> {


    protected WeakReference<V> weakView;

    @Override
    public void onAttach(V view) {
        weakView = new WeakReference<>(view);
    }

    @Override
    public void onDetach() {
        weakView.clear();
    }

    protected final void ifViewAttached(ViewAction<V> action) {
        final V view = weakView == null ? null : weakView.get();
        if (view != null) {
            action.run(view);
        }
    }

    public interface ViewAction<V> {
        /**
         * This method will be invoked to run the action. Implement this method to interact with the view.
         *
         * @param view The reference to the view. Not null.
         */
        void run(@NonNull V view);
    }
}
