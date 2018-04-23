package com.hefeibus.www.hefeibus.view.line_detail;

import android.support.annotation.NonNull;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.Line;
import com.hefeibus.www.hefeibus.sqlite.HefeiBusDatabase;

class LineDetailPresenter extends BaseMvpPresenter<ILineDetailView> implements ILineDetailPresenter {

    @Override
    public void queryLineDetail(String lineName) {
        HefeiBusDatabase mDal = new HefeiBusDatabase(weakView.get().getCurrentActicity());
        final Line line = mDal.queryLineDetail(lineName);
        ifViewAttached(new ViewAction<ILineDetailView>() {
            @Override
            public void run(@NonNull ILineDetailView view) {
                view.showLineInfo(line);
            }
        });
        mDal.close();
    }
}
