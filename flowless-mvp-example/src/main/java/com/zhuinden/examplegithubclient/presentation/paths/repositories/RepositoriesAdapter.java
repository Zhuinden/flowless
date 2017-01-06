package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.GithubRepo;
import com.zhuinden.examplegithubclient.util.DaggerService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Zhuinden on 2016.12.18..
 */

public class RepositoriesAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Inject
    RepositoriesPresenter repositoriesPresenter;

    static final int ROW = 0;
    static final int LOAD_MORE = 1;

    public RepositoriesAdapter(Context context) {
        RepositoriesComponent repositoriesComponent = DaggerService.getComponent(context);
        repositoriesComponent.inject(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ROW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repositories_row, parent, false));
        } else if(viewType == LOAD_MORE) {
            return new LoadMoreHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_repositories_load_more_row, parent, false));
        }
        throw new IllegalArgumentException("Invalid view type [" + viewType + "]");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder abstractHolder, int position) {
        if(abstractHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) abstractHolder;
            holder.bind(repositoriesPresenter.getRepositories().get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (repositoriesPresenter.getRepositories() == null ? 0 : repositoriesPresenter.getRepositories()
                .size()) + (repositoriesPresenter.didDownloadAll() ? 0 : 1);
    }

    @Override
    public int getItemViewType(int position) {
        if((repositoriesPresenter.getRepositories() == null && position == 0) || (position == repositoriesPresenter.getRepositories()
                .size()) && !repositoriesPresenter.didDownloadAll()) {
            return LOAD_MORE;
        }
        return ROW;
    }

    public void updateRepositories() {
        notifyDataSetChanged();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {
        @Inject
        RepositoriesPresenter repositoriesPresenter;

        @BindView(R.id.repositories_row_text)
        TextView row;

        private GithubRepo githubRepo;

        @OnClick(R.id.repositories_row)
        public void rowClicked() {
            repositoriesPresenter.openRepository(githubRepo);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            RepositoriesComponent repositoriesComponent = DaggerService.getComponent(itemView.getContext());
            repositoriesComponent.inject(this);
        }

        public void bind(GithubRepo githubRepo) {
            this.githubRepo = githubRepo;
            row.setText(githubRepo.getName());
        }
    }

    public static class LoadMoreHolder
            extends RecyclerView.ViewHolder {
        public LoadMoreHolder(View itemView) {
            super(itemView);
        }
    }
}
