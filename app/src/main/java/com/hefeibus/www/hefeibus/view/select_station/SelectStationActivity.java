package com.hefeibus.www.hefeibus.view.select_station;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.OnItemClickListener;
import com.hefeibus.www.hefeibus.adapter.SelectStationListAdapter;
import com.hefeibus.www.hefeibus.base.BaseMvpActivity;
import com.hefeibus.www.hefeibus.utils.Parameters;

import java.util.List;

public class SelectStationActivity extends BaseMvpActivity<ISelectStationPresenter> implements ISelectStationView {
    private static final String TAG = "SelectStationActivity";
    private EditText editText;
    private ListView listView;
    private SelectStationListAdapter adapter = new SelectStationListAdapter(this);


    @Override
    protected int setLayoutView() {
        return R.layout.activity_auto_search;
    }

    @Override
    protected ISelectStationPresenter onCreatePresenter() {
        return new SelectStationPresenter(mAppDatabase);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.cache_switcher).setVisibility(View.INVISIBLE);
        editText = (EditText) findViewById(R.id.search_box);
        listView = (ListView) findViewById(R.id.list_view);
    }

    @Override
    protected void setAttributes() {
        listView.setAdapter(adapter);
        adapter.setListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(String itemName) {
                hintKeyboard();
                Intent intent = new Intent();
                intent.putExtra(Parameters.INTENT_STATION_KEY, itemName);
                SelectStationActivity.this.setResult(RESULT_OK, intent);
                SelectStationActivity.this.finish();
            }
        });
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

    private void hintKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void init() {
        List<String> resultSet = presenter.getStationSet();
        adapter.setResultSet(resultSet);
    }

    @Override
    public SelectStationActivity getCurrentActivity() {
        return this;
    }


}
