package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.presentation.paths.repositorydetails.RepositoryDetailsKey;
import com.zhuinden.examplegithubclient.util.BundleFactory;
import com.zhuinden.examplegithubclient.util.DaggerService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flowless.Bundleable;
import flowless.Flow;
import flowless.preset.FlowLifecycles;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class RepositoriesView
        extends RelativeLayout
        implements FlowLifecycles.ViewLifecycleListener, RepositoriesPresenter.ViewContract, Bundleable {
    public RepositoriesView(Context context) {
        super(context);
        init();
    }

    public RepositoriesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RepositoriesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public RepositoriesView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        if(!isInEditMode()) {
            RepositoriesComponent repositoriesComponent = DaggerService.getComponent(getContext());
            repositoriesComponent.inject(this);
        }
    }

    @BindView(R.id.repositories_recycler)
    RecyclerView recyclerView;

    @Inject
    RepositoriesPresenter repositoriesPresenter;

    RepositoriesAdapter repositoriesAdapter;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()) {
            ButterKnife.bind(this);
            repositoriesAdapter = new RepositoriesAdapter(getContext());
            recyclerView.setAdapter(repositoriesAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if(!recyclerView.canScrollVertically(1)) {
                        onScrolledToBottom();
                    }
                }

                public void onScrolledToBottom() {
                    repositoriesPresenter.downloadMore();
                }
            });
        }
    }

    @Override
    public void onViewRestored() {
        repositoriesPresenter.attachView(this);
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        repositoriesPresenter.detachView();
    }

    @Override
    public void updateRepositories(List<Repository> repositories) {
        repositoriesAdapter.updateRepositories();
    }

    @Override
    public void openRepository(String url) {
        Flow.get(this).set(RepositoryDetailsKey.create(Flow.getKey(this), url));
    }

    @Override
    public Bundle toBundle() {
        Bundle bundle = BundleFactory.create();
        bundle.putBundle("PRESENTER_STATE", repositoriesPresenter.toBundle());
        return bundle;
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        if(bundle != null) {
            repositoriesPresenter.fromBundle(bundle.getBundle("PRESENTER_STATE"));
        }
    }
}
