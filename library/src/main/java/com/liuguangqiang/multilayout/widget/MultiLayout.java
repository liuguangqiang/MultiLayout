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
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 15/4/29.
 */
public class MultiLayout extends RelativeLayout {

    private static final String TAG = LayoutItem.class.getSimpleName();

    private int current = -1;

    private List<LayoutItem> tabItems = new ArrayList<>();

    public MultiLayout(Context context) {
        this(context, null);
    }

    public MultiLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addLayout(LayoutItem layoutItem) {
        tabItems.add(layoutItem);
        addView(layoutItem.getView());
    }

    public void addLayout(int resId) {
        addLayout(resId, null);
    }

    public void addLayout(int resId, LayoutItem.Gravity gravity) {
        LayoutItem tabItem = new LayoutItem(getContext(), resId);
        if (gravity != null) tabItem.setGravity(gravity);
        tabItems.add(tabItem);
        addView(tabItem.getView());
    }

    public void show(int position) {
        LayoutItem item = tabItems.get(position);

        if (item.isShow()) {
            item.hide();
            current = -1;
        } else {
            hideCurrent();
            item.show();
            current = position;
        }
    }

    public void hide(int position) {
        LayoutItem item = tabItems.get(position);
        item.hide();
        if (position == current) current = -1;
    }

    private void hideCurrent() {
        if (current != -1) {
            tabItems.get(current).hide();
        }
    }

}
