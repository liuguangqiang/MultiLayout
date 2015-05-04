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
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 15/4/29.
 */
public class TabLayout extends RelativeLayout {

    private static final String TAG = "TabLayout";

    private int current = -1;

    private List<TabItem> tabItems = new ArrayList<>();

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addTab(TabItem tabItem) {
        tabItems.add(tabItem);
        addView(tabItem.getView());
    }

    public void addTab(int resId) {
        TabItem tabItem = new TabItem(getContext(), resId);
        tabItems.add(tabItem);
        addView(tabItem.getView());
    }

    public void showTab(int position) {
        TabItem item = tabItems.get(position);

        if (item.isShow()) {
            item.hide();
            current = -1;
        } else {
            hideCurrent();
            item.show();
            current = position;
        }
    }

    private void hideCurrent() {
        if (current != -1) {
            tabItems.get(current).hide();
        }
    }

}
