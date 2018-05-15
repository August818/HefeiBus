package com.hefeibus.www.hefeibus.fragment.transfer;

import com.hefeibus.www.hefeibus.basemvp.IView;
import com.hefeibus.www.hefeibus.entity.ProgramStatus;
import com.hefeibus.www.hefeibus.entity.TransferData;

import java.util.List;

interface ITransferView extends IView {
    TransferFragment getCurrentActivity();

    void repostTransferData(List<TransferData> transferData, String start, String stop);

    void makeToast(String msg);

    void setStatus(ProgramStatus status);
}
