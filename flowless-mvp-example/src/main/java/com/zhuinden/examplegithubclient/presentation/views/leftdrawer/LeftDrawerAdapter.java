package com.zhuinden.examplegithubclient.presentation.views.leftdrawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainComponent;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainKey;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainPresenter;
import com.zhuinden.examplegithubclient.util.DaggerService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flowless.Flow;
import flowless.ServiceProvider;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class LeftDrawerAdapter
        extends RecyclerView.Adapter<LeftDrawerAdapter.LeftDrawerViewHolder> {
    @Override
    public LeftDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LeftDrawerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_left_drawer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(LeftDrawerViewHolder holder, int position) {
        holder.bind(LeftDrawerItems.values()[position]);
    }

    @Override
    public int getItemCount() {
        return LeftDrawerItems.values().length;
    }

    public static class LeftDrawerViewHolder
            extends RecyclerView.ViewHolder {
        private final Context context;

        @BindView(R.id.left_drawer_item_text)
        TextView leftDrawerText;

        @BindView(R.id.left_drawer_item_picture)
        ImageView imageView;

        LeftDrawerItems leftDrawerItem;

        @OnClick(R.id.left_drawer_item)
        public void onClickDrawerItem(View view) {
            Object newKey = leftDrawerItem.getKeyCreator().createKey();
            MainComponent component = ServiceProvider.get(context)
                    .getService(MainKey.KEY, DaggerService.TAG);
            MainPresenter mainPresenter = component.mainPresenter();
            mainPresenter.goToKey(Flow.get(view), newKey);
        }

        public LeftDrawerViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(LeftDrawerItems leftDrawerItem) {
            this.leftDrawerItem = leftDrawerItem;
            leftDrawerText.setText(leftDrawerItem.getLabelId());
            Glide.with(context).load(leftDrawerItem.getImageId()).dontAnimate().into(imageView);
        }
    }
}
