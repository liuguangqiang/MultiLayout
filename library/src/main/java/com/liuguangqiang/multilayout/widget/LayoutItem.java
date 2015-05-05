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

package com.liuguangqiang.multilayout.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

/**
 * Created by Eric on 15/4/29.
 */
public class LayoutItem {

    public enum Gravity {
        TOP,

        BOTTOM
    }

    private static final String TAG = LayoutItem.class.getSimpleName();

    private Context mContext;
    private View mView;
    private Gravity mGravity = Gravity.BOTTOM;

    private SpringSystem springSystem = SpringSystem.create();
    private Spring spring;
    private LayoutParams mParams;

    private boolean isShow = false;

    public boolean isShow() {
        return isShow;
    }

    public LayoutItem(Context context) {
        mContext = context;
    }

    public LayoutItem(Context context, int resId) {
        mContext = context;
        setContent(resId);
    }

    public LayoutItem(Context context, View view) {
        mContext = context;
        setContent(view);
    }

    public void setGravity(Gravity Gravity) {
        this.mGravity = Gravity;
    }

    public Gravity getGravity() {
        return mGravity;
    }

    public View getView() {
        if (mView == null) throw new IllegalArgumentException("must set a content view");
        return mView;
    }

    public void setContent(int resId) {
        setContent(LayoutInflater.from(mContext).inflate(resId, null));
    }

    public void setContent(View view) {
        mView = view;
        mParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mParams.addRule(getParamsRule());
        mView.setLayoutParams(mParams);
        setVisibility(View.INVISIBLE);
    }

    public LayoutParams getLayoutParams() {
        return mParams;
    }

    private int getParamsRule() {
        return mGravity == Gravity.BOTTOM
                ? RelativeLayout.ALIGN_PARENT_BOTTOM
                : RelativeLayout.ALIGN_PARENT_TOP;
    }

    public void show() {
        mView.setVisibility(View.VISIBLE);
        toggle(true);
    }

    public void hide() {
        toggle(false);
    }

    public void setVisibility(int visibility) {
        mView.setVisibility(visibility);
    }

    private void toggle(boolean toShow) {
        isShow = toShow;
        if (spring == null) {
            spring = springSystem.createSpring();
            spring.setOvershootClampingEnabled(false);
            spring.addListener(new SimpleSpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    float value = (float) spring.getCurrentValue();
                    mView.setTranslationY(value);
                }
            });
        }

        if (mGravity == Gravity.BOTTOM) {
            animBottom(spring, toShow);
        } else {
            animTop(spring, toShow);
        }
    }

    private void animBottom(Spring spring, boolean toShow) {
        if (isShow) {
            spring.setCurrentValue(mView.getHeight());
        }

        spring.setEndValue(toShow ? 0 : mView.getHeight());
    }

    private void animTop(Spring spring, boolean toShow) {
        if (isShow) {
            spring.setCurrentValue(-mView.getHeight());
        }

        spring.setEndValue(toShow ? 0 : -mView.getHeight());
    }

}
