package com.liuguangqiang.tablayout.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.liuguangqiang.multilayout.widget.MultiLayout;
import com.liuguangqiang.multilayout.widget.OnPageChangedListener;
import com.liuguangqiang.multilayout.widget.Page;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.tabLayout)
    MultiLayout multiLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        multiLayout.addPage(R.layout.layout_tab1);
        multiLayout.addPage(R.layout.layout_tab2);

        Page layoutItem = new Page(this);
        layoutItem.setGravity(Page.Gravity.BOTTOM);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_tab3, null);
        layoutItem.setContent(view);
        multiLayout.addPage(layoutItem);
        multiLayout.setOnPageChangedListener(new OnPageChangedListener() {
            @Override
            public void onOpen(int position) {
                Log.i("MultiLayout", "onOpen:" + position);
            }

            @Override
            public void onClose(int position) {
                Log.i("MultiLayout", "onClose:" + position);
            }
        });
    }

    @OnClick(R.id.btn_tab1)
    public void onClickTab1() {
        multiLayout.open(0);
    }

    @OnClick(R.id.btn_tab2)
    public void onClickTab2() {
        multiLayout.open(1);
    }

    @OnClick(R.id.btn_tab3)
    public void onClickTab3() {
        multiLayout.open(2);
    }

}
