/*
 * Copyright 2015 Eric Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liuguangqiang.tablayout.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

/**
 * Created by Eric on 15/4/29.
 */
public class TabItem {

    private static final String TAG = TabItem.class.getSimpleName();

    private Context mContext;
    private View mView;

    private SpringSystem springSystem = SpringSystem.create();
    private Spring spring;

    public TabItem(Context context) {
        mContext = context;
    }

    public TabItem(Context context, int resId) {
        mContext = context;
        setContent(resId);
    }

    public View getView() {
        if (mView == null) throw new IllegalArgumentException("must set a content view");
        return mView;
    }

    public void setContent(int resId) {
        mView = LayoutInflater.from(mContext).inflate(resId, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mView.setLayoutParams(params);
        setVisibility(View.GONE);
    }

    public void setContent(View view) {
        mView = view;
        setVisibility(View.GONE);
    }

    public void show() {
        mView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mView.setVisibility(View.GONE);
    }

    public void setVisibility(int visibility) {
        mView.setVisibility(visibility);
    }

}
