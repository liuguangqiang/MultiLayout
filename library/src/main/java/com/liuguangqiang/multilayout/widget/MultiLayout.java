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
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.liuguangqiang.tablayout.R;

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

    private ImageView ivBackground;

    public void setBackgroundResource(int resId) {
        ivBackground.setBackgroundResource(resId);
    }

    public void setBackgroundColor(int color) {
        ivBackground.setBackgroundColor(color);
    }

    private boolean enableBackground = false;

    public void setEnableBackground(boolean enable) {
        enableBackground = enable;
    }

    public void setOnPageChangedListener(OnPageChangedListener listener) {
        changedListener = listener;
    }

    public MultiLayout(Context context) {
        this(context, null);
    }

    public MultiLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        addBackground(context);
    }

    private void addBackground(Context context) {
        ivBackground = new ImageView(context);
        ivBackground.setVisibility(View.GONE);
        ivBackground.setBackgroundColor(context.getResources().getColor(R.color.black_p50));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(ivBackground, params);
    }

    public void showBackground() {
        if (ivBackground.getVisibility() == View.GONE) {
            ivBackground.setVisibility(View.VISIBLE);
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(300);
            ivBackground.startAnimation(anim);
        }
    }

    public void hideBackground() {
        if (ivBackground.getVisibility() == View.VISIBLE) {
            AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
            anim.setDuration(300);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ivBackground.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ivBackground.startAnimation(anim);
        }
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
            item.close();
            current = -1;
            if (enableBackground)
                hideBackground();
        } else {
            closeCurrent();
            item.open();
            current = position;
            if (enableBackground)
                showBackground();
        }
    }

    public void close(int position) {
        pageList.get(position).close();
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

    public Page getPage(int position) {
        return pageList.get(position);
    }

    @Override
    public void onOpen(int position) {
        if (changedListener != null) changedListener.onOpen(position);
    }

    @Override
    public void onClose(int position) {
        if (changedListener != null) changedListener.onClose(position);
    }

}
