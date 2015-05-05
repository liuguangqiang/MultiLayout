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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

/**
 * Created by Eric on 15/4/29.
 */
public class TabItem {

    public enum Direction {
        TOP,
        BUTTOM,
        LEFT,
        RIGHT
    }

    private static final String TAG = TabItem.class.getSimpleName();

    private Context mContext;
    private View mView;
    private Direction mDirection = Direction.BUTTOM;

    private SpringSystem springSystem = SpringSystem.create();
    private Spring spring;

    private boolean isShow = false;

    public boolean isShow() {
        return isShow;
    }

    public TabItem(Context context) {
        mContext = context;
    }

    public TabItem(Context context, int resId) {
        mContext = context;
        setContent(resId);
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    public Direction getDirection() {
        return mDirection;
    }

    public View getView() {
        if (mView == null) throw new IllegalArgumentException("must set a content view");
        return mView;
    }

    public void setContent(int resId) {
        mView = LayoutInflater.from(mContext).inflate(resId, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mView.setLayoutParams(params);
        setVisibility(View.INVISIBLE);
        Log.i(TAG, "height:" + mView.getHeight());
    }

    public void setContent(View view) {
        mView = view;
        setVisibility(View.INVISIBLE);
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

    public void toggle() {
        if (isShow()) {
            hide();
        } else {
            show();
        }
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

        if (isShow) {
            spring.setCurrentValue(mView.getHeight());
        }

        spring.setEndValue(toShow ? 0 : mView.getHeight());
    }

}
