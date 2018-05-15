package com.hefeibus.www.hefeibus.fragment.transfer;

import com.hefeibus.www.hefeibus.basemvp.IPresenter;

interface ITransferPresenter extends IPresenter<ITransferView> {
    void onDestroy();

    void commitQuery(String start, String stop);

    void onCancel();
}
