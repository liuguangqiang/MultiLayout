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
public class Page {

    public enum Gravity {
        TOP,

        BOTTOM
    }

    private static final String TAG = Page.class.getSimpleName();

    private Context mContext;
    private View mView;
    private Gravity mGravity = Gravity.BOTTOM;

    private SpringSystem springSystem = SpringSystem.create();
    private Spring spring;
    private LayoutParams mParams;

    private boolean isOpened = false;

    public boolean isOpened() {
        return isOpened;
    }

    private int mPosition = 0;

    private OnPageChangedListener changedListener;

    public Page(Context context) {
        mContext = context;
    }

    public Page(Context context, int resId) {
        mContext = context;
        setContent(resId);
    }

    public Page(Context context, View view) {
        mContext = context;
        setContent(view);
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public void setOnPageChangedListener(OnPageChangedListener listener) {
        changedListener = listener;
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
        mView.setEnabled(false);
        setVisibility(View.INVISIBLE);
    }

    public void setEnable(boolean enable) {
        if (mView != null) mView.setEnabled(enable);
    }

    public LayoutParams getLayoutParams() {
        return mParams;
    }

    private int getParamsRule() {
        return mGravity == Gravity.BOTTOM
                ? RelativeLayout.ALIGN_PARENT_BOTTOM
                : RelativeLayout.ALIGN_PARENT_TOP;
    }

    public void open() {
        if (mView != null) {
            mView.setVisibility(View.VISIBLE);
            mView.setEnabled(true);
            toggle(true);

            if (changedListener != null) {
                changedListener.onOpen(mPosition);
            }
        }
    }

    public void close() {
        if (mView != null) {
            mView.setEnabled(false);
            toggle(false);

            if (changedListener != null) {
                changedListener.onClose(mPosition);
            }
        }
    }

    public void setVisibility(int visibility) {
        mView.setVisibility(visibility);
    }

    private void toggle(final boolean toOpen) {
        isOpened = toOpen;
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
            animBottom(spring, toOpen);
        } else {
            animTop(spring, toOpen);
        }
    }

    private void animBottom(Spring spring, boolean toOpen) {
        if (isOpened) {
            spring.setCurrentValue(mView.getHeight());
        }

        spring.setEndValue(toOpen ? 0 : mView.getHeight());
    }

    private void animTop(Spring spring, boolean toOpen) {
        if (isOpened) {
            spring.setCurrentValue(-mView.getHeight());
        }

        spring.setEndValue(toOpen ? 0 : -mView.getHeight());
    }

}
