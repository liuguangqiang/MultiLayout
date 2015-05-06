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
public class MultiLayout extends RelativeLayout implements OnPageChangedListener {

    private static final String TAG = Page.class.getSimpleName();

    private int current = -1;

    private List<Page> pageList = new ArrayList<>();

    private OnPageChangedListener changedListener;

    public void setOnPageChangedListener(OnPageChangedListener listener) {
        changedListener = listener;
    }

    public MultiLayout(Context context) {
        this(context, null);
    }

    public MultiLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addPage(int resId) {
        addPage(resId, null);
    }

    public void addPage(int resId, Page.Gravity gravity) {
        Page page = new Page(getContext(), resId);
        addPage(page, gravity);
    }

    public void addPage(Page layoutItem) {
        addPage(layoutItem, null);
    }

    public void addPage(Page page, Page.Gravity gravity) {
        if (gravity != null) page.setGravity(gravity);
        pageList.add(page);
        page.setPosition(pageList.size() - 1);
        page.setOnPageChangedListener(this);

        addView(page.getView());
    }

    public void open(int position) {
        Page item = pageList.get(position);

        if (item.isOpened()) {
            item.open();
            current = -1;
        } else {
            closeCurrent();
            item.open();
            current = position;
        }
    }

    public void close(int position) {
        Page item = pageList.get(position);
        item.close();
        if (position == current) current = -1;
    }

    private void closeCurrent() {
        if (current != -1) {
            pageList.get(current).close();
        }
    }

    public boolean isOpen() {
        return current != -1;
    }

    public int getCurrentPosition() {
        return current;
    }

    @Override
    public void onOpen(int position) {
        if (changedListener != null) changedListener.onOpen(position);
    }

    @Override
    public void onClose(int position) {
        if (changedListener != null) changedListener.onClose(position);
    }

    @Override
    public void onFinished(int position, boolean isOpen) {
        if (changedListener != null) changedListener.onFinished(position, isOpen);
    }
}
