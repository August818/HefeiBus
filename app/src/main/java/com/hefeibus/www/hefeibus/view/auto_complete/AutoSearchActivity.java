package com.hefeibus.www.hefeibus.view.auto_complete;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.AutoSearchListAdapter;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpActivity;
import com.hefeibus.www.hefeibus.entity.Item;

import java.util.List;

public class AutoSearchActivity extends BaseMvpActivity<IAutoSearchPresenter> implements IAutoSearchView {
    private static final String TAG = "AutoSearchActivity";
    private EditText editText;
    private ListView listView;
    private AutoSearchListAdapter adapter = new AutoSearchListAdapter(this);
    private Toolbar toolbar;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_auto_search;
    }

    @Override
    protected IAutoSearchPresenter onCreatePresenter() {
        return new AutoSearchPresenter();
    }

    @Override
    protected void initViews() {
        //隐藏
        findViewById(R.id.cache_switcher).setVisibility(View.INVISIBLE);
        editText = (EditText) findViewById(R.id.search_box);
        listView = (ListView) findViewById(R.id.list_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoSearchActivity.this.onBackPressed();
            }
        });
    }

    @Override
    protected void setAttributes() {
        listView.setAdapter(adapter);
        setToolbar();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s);
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void init() {
        List<Item> resultSet = presenter.getResultSet();
        adapter.setResultSet(resultSet);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public AutoSearchActivity getCurrentActivity() {
        return this;
    }
}
