package com.hefeibus.www.hefeibus.base;

public interface IPresenter<V extends IView> {

    /**
     * When activity created,the Presenter layer should
     * contains View interface to repost Action;
     *
     * @param view current View interface reference;
     */
    void onAttach(V view);

    /**
     * When activity is destroyed,release the presenter layer
     * reference.
     */
    void onDetach();

}
