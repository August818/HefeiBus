package com.hefeibus.www.hefeibus.view.mian_framework;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.base.BaseMvpActivity;
import com.hefeibus.www.hefeibus.base.TransferHandler;
import com.hefeibus.www.hefeibus.entity.Wrapper;
import com.hefeibus.www.hefeibus.fragment.history.HistoryFragment;
import com.hefeibus.www.hefeibus.fragment.search.SearchFragment;
import com.hefeibus.www.hefeibus.fragment.setting.SettingFragment;
import com.hefeibus.www.hefeibus.fragment.transfer.TransferFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity
 * Created by cx on 2018/3/17.
 */

public class MainActivity extends BaseMvpActivity<IMainPresenter> implements IMainView {
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private MyPagerAdapter adapter;

    @Override
    protected void init() {

    }

    @Override
    protected IMainPresenter onCreatePresenter() {
        return new MainPresenter();
    }

    /**
     * 设置控件属性
     */
    @Override
    protected void setAttributes() {

        //设置 Tablayout 的 title
        mTablayout.addTab(mTablayout.newTab().setText("查询"));
        mTablayout.addTab(mTablayout.newTab().setText("换乘"));
        mTablayout.addTab(mTablayout.newTab().setText("历史"));
        mTablayout.addTab(mTablayout.newTab().setText("设置"));

        //联动 viewpager 和 tablayout
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
        mTablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //初始化 viewpager 内容
        List<Fragment> list = new ArrayList<>();
        list.add(new SearchFragment());
        list.add(new TransferFragment());
        list.add(new HistoryFragment());
        list.add(new SettingFragment());
        adapter = new MyPagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        mTablayout = (TabLayout) findViewById(R.id.activity_main_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.activity_main_content);
    }

    /**
     * 设置 Activity 布局
     */
    @Override
    protected int setLayoutView() {
        return R.layout.activity_main;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public void requestTransferOutSide(Wrapper wrapper) {
        mViewPager.setCurrentItem(1, true);
        TransferHandler handler = (TransferHandler) adapter.getItem(1);
        handler.requestFromOutside(wrapper.getStart(), wrapper.getStop());
    }

    /**
     * pagerAdapter 内部类
     */
    class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;

        MyPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }


        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            if (list == null) {
                return 0;
            }
            return list.size();
        }

    }

}
