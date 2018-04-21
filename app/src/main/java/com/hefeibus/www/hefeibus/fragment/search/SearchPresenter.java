package com.hefeibus.www.hefeibus.fragment.search;

import android.support.annotation.NonNull;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.GroupDetail;
import com.hefeibus.www.hefeibus.entity.Line;
import com.hefeibus.www.hefeibus.sqlite.DatabaseDal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class SearchPresenter extends BaseMvpPresenter<ISearchView> implements ISearchPresenter {

    /**
     * 数据库操作层
     */
    private DatabaseDal mDal;

    public SearchPresenter() {
        mDal = new DatabaseDal();

    }

    @Override
    public void loadGroupLineData() {
        ifViewAttached(new ViewAction<ISearchView>() {
            @Override
            public void run(@NonNull ISearchView view) {
                view.showLoadingLayout();
            }
        });

        final HashMap<String, GroupDetail> map = new HashMap<>();
        GroupDetail groupDetail1 = new GroupDetail("城市线路", 10);
        GroupDetail groupDetail2 = new GroupDetail("开发区线路", 8);
        GroupDetail groupDetail3 = new GroupDetail("地铁", 2);
        GroupDetail groupDetail4 = new GroupDetail("旅游线路", 4);

        List<Line> lines = new ArrayList<>();

        lines.add(new Line("1路"));
        lines.add(new Line("2路"));
        lines.add(new Line("3路"));
        lines.add(new Line("4路"));
        lines.add(new Line("5路"));
        lines.add(new Line("6路"));

        groupDetail1.setLineList(lines);
        groupDetail2.setLineList(lines);
        groupDetail3.setLineList(lines);
        groupDetail4.setLineList(lines);

        map.put(groupDetail1.getGroupName(), groupDetail1);
        map.put(groupDetail2.getGroupName(), groupDetail2);
        map.put(groupDetail3.getGroupName(), groupDetail3);
        map.put(groupDetail4.getGroupName(), groupDetail4);

        final List<String> groupIndex = new ArrayList<>();

        groupIndex.add("城市线路");
        groupIndex.add("开发区线路");
        groupIndex.add("地铁");
        groupIndex.add("旅游线路");

        ifViewAttached(new ViewAction<ISearchView>() {
            @Override
            public void run(@NonNull ISearchView view) {
                view.restoreLayout();
                view.setGroupListDetail(map, groupIndex);
            }
        });

    }
}
