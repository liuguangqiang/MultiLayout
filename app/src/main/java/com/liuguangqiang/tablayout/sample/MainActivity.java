package com.liuguangqiang.tablayout.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.liuguangqiang.tablayout.widget.TabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        tabLayout.addTab(R.layout.layout_tab1);
        tabLayout.addTab(R.layout.layout_tab2);
    }

    @OnClick(R.id.btn_tab1)
    public void onClickTab1() {
        tabLayout.showTab(0);
    }

    @OnClick(R.id.btn_tab2)
    public void onClickTab2() {
        tabLayout.showTab(1);
    }

}
