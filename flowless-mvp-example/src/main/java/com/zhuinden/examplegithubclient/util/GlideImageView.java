package com.zhuinden.examplegithubclient.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhuinden.examplegithubclient.R;

import flowless.ViewUtils;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class GlideImageView
        extends ImageView {
    public GlideImageView(Context context) {
        super(context);
        init(context, null, -1);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    int drawableResource = 0;

    private void init(Context context, AttributeSet attributeSet, int defStyle) {
        TypedArray a = null;
        if(defStyle != -1) {
            a = getContext().obtainStyledAttributes(attributeSet, R.styleable.GlideImageView, defStyle, 0);
        } else {
            a = getContext().obtainStyledAttributes(attributeSet, R.styleable.GlideImageView);
        }
        drawableResource = a.getResourceId(R.styleable.GlideImageView_image_resource, 0);
        a.recycle();

        ViewUtils.waitForMeasure(this, (view, width, height) -> {
            if(!isInEditMode()) {
                if(drawableResource != 0) {
                    Glide.with(getContext()).load(drawableResource).dontAnimate().into(GlideImageView.this);
                }
            } else {
                setImageResource(drawableResource);
            }
        });
    }
}
