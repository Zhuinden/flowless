package com.zhuinden.examplegithubclient.presentation.paths.repositorydetails;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.data.repository.RepositoryRepository;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.util.DaggerService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flowless.Flow;
import flowless.preset.FlowLifecycles;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class RepositoryDetailsView
        extends RelativeLayout
        implements FlowLifecycles.ViewLifecycleListener, RepositoryDetailsPresenter.ViewContract {
    public RepositoryDetailsView(Context context) {
        super(context);
        init();
    }

    public RepositoryDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RepositoryDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public RepositoryDetailsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Inject
    RepositoryDetailsPresenter repositoryDetailsPresenter;

    @Inject
    RepositoryRepository repositoryRepository;

    Repository selectedRepository;

    public void init() {
        if(!isInEditMode()) {
            RepositoryDetailsComponent repositoryDetailsComponent = DaggerService.getComponent(getContext());
            repositoryDetailsComponent.inject(this);
            RepositoryDetailsKey repositoryDetailsKey = Flow.getKey(this);
            selectedRepository = repositoryRepository.findByUrl(repositoryDetailsKey.url());
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void onViewRestored() {
        repositoryDetailsPresenter.attachView(this);
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        repositoryDetailsPresenter.detachView();
    }

    @Override
    public Repository getSelectedRepository() {
        return selectedRepository;
    }


    @BindView(R.id.repository_details_id)
    TextView repositoryDetailsid;

    @BindView(R.id.repository_details_name)
    TextView repositoryDetailsname;

    @BindView(R.id.repository_details_fullName)
    TextView repositoryDetailsfullName;

    @BindView(R.id.repository_details_owner)
    TextView repositoryDetailsowner;

    @BindView(R.id.repository_details__private)
    TextView repositoryDetails_private;

    @BindView(R.id.repository_details_htmlUrl)
    TextView repositoryDetailshtmlUrl;

    @BindView(R.id.repository_details_description)
    TextView repositoryDetailsdescription;

    @BindView(R.id.repository_details_fork)
    TextView repositoryDetailsfork;

    @BindView(R.id.repository_details_url)
    TextView repositoryDetailsurl;

    @BindView(R.id.repository_details_forksUrl)
    TextView repositoryDetailsforksUrl;

    @BindView(R.id.repository_details_keysUrl)
    TextView repositoryDetailskeysUrl;

    @BindView(R.id.repository_details_collaboratorsUrl)
    TextView repositoryDetailscollaboratorsUrl;

    @BindView(R.id.repository_details_teamsUrl)
    TextView repositoryDetailsteamsUrl;

    @BindView(R.id.repository_details_hooksUrl)
    TextView repositoryDetailshooksUrl;

    @BindView(R.id.repository_details_issueEventsUrl)
    TextView repositoryDetailsissueEventsUrl;

    @BindView(R.id.repository_details_eventsUrl)
    TextView repositoryDetailseventsUrl;

    @BindView(R.id.repository_details_assigneesUrl)
    TextView repositoryDetailsassigneesUrl;

    @BindView(R.id.repository_details_branchesUrl)
    TextView repositoryDetailsbranchesUrl;

    @BindView(R.id.repository_details_tagsUrl)
    TextView repositoryDetailstagsUrl;

    @BindView(R.id.repository_details_blobsUrl)
    TextView repositoryDetailsblobsUrl;

    @BindView(R.id.repository_details_gitTagsUrl)
    TextView repositoryDetailsgitTagsUrl;

    @BindView(R.id.repository_details_gitRefsUrl)
    TextView repositoryDetailsgitRefsUrl;

    @BindView(R.id.repository_details_treesUrl)
    TextView repositoryDetailstreesUrl;

    @BindView(R.id.repository_details_statusesUrl)
    TextView repositoryDetailsstatusesUrl;

    @BindView(R.id.repository_details_languagesUrl)
    TextView repositoryDetailslanguagesUrl;

    @BindView(R.id.repository_details_stargazersUrl)
    TextView repositoryDetailsstargazersUrl;

    @BindView(R.id.repository_details_contributorsUrl)
    TextView repositoryDetailscontributorsUrl;

    @BindView(R.id.repository_details_subscribersUrl)
    TextView repositoryDetailssubscribersUrl;

    @BindView(R.id.repository_details_subscriptionUrl)
    TextView repositoryDetailssubscriptionUrl;

    @BindView(R.id.repository_details_commitsUrl)
    TextView repositoryDetailscommitsUrl;

    @BindView(R.id.repository_details_gitCommitsUrl)
    TextView repositoryDetailsgitCommitsUrl;

    @BindView(R.id.repository_details_commentsUrl)
    TextView repositoryDetailscommentsUrl;

    @BindView(R.id.repository_details_issueCommentUrl)
    TextView repositoryDetailsissueCommentUrl;

    @BindView(R.id.repository_details_contentsUrl)
    TextView repositoryDetailscontentsUrl;

    @BindView(R.id.repository_details_compareUrl)
    TextView repositoryDetailscompareUrl;

    @BindView(R.id.repository_details_mergesUrl)
    TextView repositoryDetailsmergesUrl;

    @BindView(R.id.repository_details_archiveUrl)
    TextView repositoryDetailsarchiveUrl;

    @BindView(R.id.repository_details_downloadsUrl)
    TextView repositoryDetailsdownloadsUrl;

    @BindView(R.id.repository_details_issuesUrl)
    TextView repositoryDetailsissuesUrl;

    @BindView(R.id.repository_details_pullsUrl)
    TextView repositoryDetailspullsUrl;

    @BindView(R.id.repository_details_milestonesUrl)
    TextView repositoryDetailsmilestonesUrl;

    @BindView(R.id.repository_details_notificationsUrl)
    TextView repositoryDetailsnotificationsUrl;

    @BindView(R.id.repository_details_labelsUrl)
    TextView repositoryDetailslabelsUrl;

    @BindView(R.id.repository_details_releasesUrl)
    TextView repositoryDetailsreleasesUrl;

    @BindView(R.id.repository_details_deploymentsUrl)
    TextView repositoryDetailsdeploymentsUrl;

    @BindView(R.id.repository_details_createdAt)
    TextView repositoryDetailscreatedAt;

    @BindView(R.id.repository_details_updatedAt)
    TextView repositoryDetailsupdatedAt;

    @BindView(R.id.repository_details_pushedAt)
    TextView repositoryDetailspushedAt;

    @BindView(R.id.repository_details_gitUrl)
    TextView repositoryDetailsgitUrl;

    @BindView(R.id.repository_details_sshUrl)
    TextView repositoryDetailssshUrl;

    @BindView(R.id.repository_details_cloneUrl)
    TextView repositoryDetailscloneUrl;

    @BindView(R.id.repository_details_svnUrl)
    TextView repositoryDetailssvnUrl;

    @BindView(R.id.repository_details_homepage)
    TextView repositoryDetailshomepage;

    @BindView(R.id.repository_details_size)
    TextView repositoryDetailssize;

    @BindView(R.id.repository_details_stargazersCount)
    TextView repositoryDetailsstargazersCount;

    @BindView(R.id.repository_details_watchersCount)
    TextView repositoryDetailswatchersCount;

    @BindView(R.id.repository_details_language)
    TextView repositoryDetailslanguage;

    @BindView(R.id.repository_details_hasIssues)
    TextView repositoryDetailshasIssues;

    @BindView(R.id.repository_details_hasDownloads)
    TextView repositoryDetailshasDownloads;

    @BindView(R.id.repository_details_hasWiki)
    TextView repositoryDetailshasWiki;

    @BindView(R.id.repository_details_hasPages)
    TextView repositoryDetailshasPages;

    @BindView(R.id.repository_details_forksCount)
    TextView repositoryDetailsforksCount;

    @BindView(R.id.repository_details_mirrorUrl)
    TextView repositoryDetailsmirrorUrl;

    @BindView(R.id.repository_details_openIssuesCount)
    TextView repositoryDetailsopenIssuesCount;

    @BindView(R.id.repository_details_forks)
    TextView repositoryDetailsforks;

    @BindView(R.id.repository_details_openIssues)
    TextView repositoryDetailsopenIssues;

    @BindView(R.id.repository_details_watchers)
    TextView repositoryDetailswatchers;

    @BindView(R.id.repository_details_defaultBranch)
    TextView repositoryDetailsdefaultBranch;

    @Override
    public void setupView(Repository repository) {
        repositoryDetailsid.setText(Html.fromHtml("<strong>Id: </strong>" + String.valueOf(repository.getId())));
        repositoryDetailsname.setText(Html.fromHtml("<strong>Name: </strong>" + String.valueOf(repository.getName())));
        repositoryDetailsfullName.setText(Html.fromHtml("<strong>FullName: </strong>" + String.valueOf(repository.getFullName())));
        repositoryDetailsowner.setText(Html.fromHtml("<strong>Owner: </strong>" + (repository.getOwner() == null ? "" : repository.getOwner()
                .getLogin())));
        repositoryDetails_private.setText(Html.fromHtml("<strong>_Private: </strong>" + String.valueOf(repository.get_Private())));
        repositoryDetailshtmlUrl.setText(Html.fromHtml("<strong>HtmlUrl: </strong>" + String.valueOf(repository.getHtmlUrl())));
        repositoryDetailsdescription.setText(Html.fromHtml("<strong>Description: </strong>" + String.valueOf(repository.getDescription())));
        repositoryDetailsfork.setText(Html.fromHtml("<strong>Fork: </strong>" + String.valueOf(repository.getFork())));
        repositoryDetailsurl.setText(Html.fromHtml("<strong>Url: </strong>" + String.valueOf(repository.getUrl())));
        repositoryDetailsforksUrl.setText(Html.fromHtml("<strong>ForksUrl: </strong>" + String.valueOf(repository.getForksUrl())));
        repositoryDetailskeysUrl.setText(Html.fromHtml("<strong>KeysUrl: </strong>" + String.valueOf(repository.getKeysUrl())));
        repositoryDetailscollaboratorsUrl.setText(Html.fromHtml("<strong>CollaboratorsUrl: </strong>" + String.valueOf(repository.getCollaboratorsUrl())));
        repositoryDetailsteamsUrl.setText(Html.fromHtml("<strong>TeamsUrl: </strong>" + String.valueOf(repository.getTeamsUrl())));
        repositoryDetailshooksUrl.setText(Html.fromHtml("<strong>HooksUrl: </strong>" + String.valueOf(repository.getHooksUrl())));
        repositoryDetailsissueEventsUrl.setText(Html.fromHtml("<strong>IssueEventsUrl: </strong>" + String.valueOf(repository.getIssueEventsUrl())));
        repositoryDetailseventsUrl.setText(Html.fromHtml("<strong>EventsUrl: </strong>" + String.valueOf(repository.getEventsUrl())));
        repositoryDetailsassigneesUrl.setText(Html.fromHtml("<strong>AssigneesUrl: </strong>" + String.valueOf(repository.getAssigneesUrl())));
        repositoryDetailsbranchesUrl.setText(Html.fromHtml("<strong>BranchesUrl: </strong>" + String.valueOf(repository.getBranchesUrl())));
        repositoryDetailstagsUrl.setText(Html.fromHtml("<strong>TagsUrl: </strong>" + String.valueOf(repository.getTagsUrl())));
        repositoryDetailsblobsUrl.setText(Html.fromHtml("<strong>BlobsUrl: </strong>" + String.valueOf(repository.getBlobsUrl())));
        repositoryDetailsgitTagsUrl.setText(Html.fromHtml("<strong>GitTagsUrl: </strong>" + String.valueOf(repository.getGitTagsUrl())));
        repositoryDetailsgitRefsUrl.setText(Html.fromHtml("<strong>GitRefsUrl: </strong>" + String.valueOf(repository.getGitRefsUrl())));
        repositoryDetailstreesUrl.setText(Html.fromHtml("<strong>TreesUrl: </strong>" + String.valueOf(repository.getTreesUrl())));
        repositoryDetailsstatusesUrl.setText(Html.fromHtml("<strong>StatusesUrl: </strong>" + String.valueOf(repository.getStatusesUrl())));
        repositoryDetailslanguagesUrl.setText(Html.fromHtml("<strong>LanguagesUrl: </strong>" + String.valueOf(repository.getLanguagesUrl())));
        repositoryDetailsstargazersUrl.setText(Html.fromHtml("<strong>StargazersUrl: </strong>" + String.valueOf(repository.getStargazersUrl())));
        repositoryDetailscontributorsUrl.setText(Html.fromHtml("<strong>ContributorsUrl: </strong>" + String.valueOf(repository.getContributorsUrl())));
        repositoryDetailssubscribersUrl.setText(Html.fromHtml("<strong>SubscribersUrl: </strong>" + String.valueOf(repository.getSubscribersUrl())));
        repositoryDetailssubscriptionUrl.setText(Html.fromHtml("<strong>SubscriptionUrl: </strong>" + String.valueOf(repository.getSubscriptionUrl())));
        repositoryDetailscommitsUrl.setText(Html.fromHtml("<strong>CommitsUrl: </strong>" + String.valueOf(repository.getCommitsUrl())));
        repositoryDetailsgitCommitsUrl.setText(Html.fromHtml("<strong>GitCommitsUrl: </strong>" + String.valueOf(repository.getGitCommitsUrl())));
        repositoryDetailscommentsUrl.setText(Html.fromHtml("<strong>CommentsUrl: </strong>" + String.valueOf(repository.getCommentsUrl())));
        repositoryDetailsissueCommentUrl.setText(Html.fromHtml("<strong>IssueCommentUrl: </strong>" + String.valueOf(repository.getIssueCommentUrl())));
        repositoryDetailscontentsUrl.setText(Html.fromHtml("<strong>ContentsUrl: </strong>" + String.valueOf(repository.getContentsUrl())));
        repositoryDetailscompareUrl.setText(Html.fromHtml("<strong>CompareUrl: </strong>" + String.valueOf(repository.getCompareUrl())));
        repositoryDetailsmergesUrl.setText(Html.fromHtml("<strong>MergesUrl: </strong>" + String.valueOf(repository.getMergesUrl())));
        repositoryDetailsarchiveUrl.setText(Html.fromHtml("<strong>ArchiveUrl: </strong>" + String.valueOf(repository.getArchiveUrl())));
        repositoryDetailsdownloadsUrl.setText(Html.fromHtml("<strong>DownloadsUrl: </strong>" + String.valueOf(repository.getDownloadsUrl())));
        repositoryDetailsissuesUrl.setText(Html.fromHtml("<strong>IssuesUrl: </strong>" + String.valueOf(repository.getIssuesUrl())));
        repositoryDetailspullsUrl.setText(Html.fromHtml("<strong>PullsUrl: </strong>" + String.valueOf(repository.getPullsUrl())));
        repositoryDetailsmilestonesUrl.setText(Html.fromHtml("<strong>MilestonesUrl: </strong>" + String.valueOf(repository.getMilestonesUrl())));
        repositoryDetailsnotificationsUrl.setText(Html.fromHtml("<strong>NotificationsUrl: </strong>" + String.valueOf(repository.getNotificationsUrl())));
        repositoryDetailslabelsUrl.setText(Html.fromHtml("<strong>LabelsUrl: </strong>" + String.valueOf(repository.getLabelsUrl())));
        repositoryDetailsreleasesUrl.setText(Html.fromHtml("<strong>ReleasesUrl: </strong>" + String.valueOf(repository.getReleasesUrl())));
        repositoryDetailsdeploymentsUrl.setText(Html.fromHtml("<strong>DeploymentsUrl: </strong>" + String.valueOf(repository.getDeploymentsUrl())));
        repositoryDetailscreatedAt.setText(Html.fromHtml("<strong>CreatedAt: </strong>" + String.valueOf(repository.getCreatedAt())));
        repositoryDetailsupdatedAt.setText(Html.fromHtml("<strong>UpdatedAt: </strong>" + String.valueOf(repository.getUpdatedAt())));
        repositoryDetailspushedAt.setText(Html.fromHtml("<strong>PushedAt: </strong>" + String.valueOf(repository.getPushedAt())));
        repositoryDetailsgitUrl.setText(Html.fromHtml("<strong>GitUrl: </strong>" + String.valueOf(repository.getGitUrl())));
        repositoryDetailssshUrl.setText(Html.fromHtml("<strong>SshUrl: </strong>" + String.valueOf(repository.getSshUrl())));
        repositoryDetailscloneUrl.setText(Html.fromHtml("<strong>CloneUrl: </strong>" + String.valueOf(repository.getCloneUrl())));
        repositoryDetailssvnUrl.setText(Html.fromHtml("<strong>SvnUrl: </strong>" + String.valueOf(repository.getSvnUrl())));
        repositoryDetailshomepage.setText(Html.fromHtml("<strong>Homepage: </strong>" + String.valueOf(repository.getHomepage())));
        repositoryDetailssize.setText(Html.fromHtml("<strong>Size: </strong>" + String.valueOf(repository.getSize())));
        repositoryDetailsstargazersCount.setText(Html.fromHtml("<strong>StargazersCount: </strong>" + String.valueOf(repository.getStargazersCount())));
        repositoryDetailswatchersCount.setText(Html.fromHtml("<strong>WatchersCount: </strong>" + String.valueOf(repository.getWatchersCount())));
        repositoryDetailslanguage.setText(Html.fromHtml("<strong>Language: </strong>" + String.valueOf(repository.getLanguage())));
        repositoryDetailshasIssues.setText(Html.fromHtml("<strong>HasIssues: </strong>" + String.valueOf(repository.getHasIssues())));
        repositoryDetailshasDownloads.setText(Html.fromHtml("<strong>HasDownloads: </strong>" + String.valueOf(repository.getHasDownloads())));
        repositoryDetailshasWiki.setText(Html.fromHtml("<strong>HasWiki: </strong>" + String.valueOf(repository.getHasWiki())));
        repositoryDetailshasPages.setText(Html.fromHtml("<strong>HasPages: </strong>" + String.valueOf(repository.getHasPages())));
        repositoryDetailsforksCount.setText(Html.fromHtml("<strong>ForksCount: </strong>" + String.valueOf(repository.getForksCount())));
        repositoryDetailsmirrorUrl.setText(Html.fromHtml("<strong>MirrorUrl: </strong>" + String.valueOf(repository.getMirrorUrl())));
        repositoryDetailsopenIssuesCount.setText(Html.fromHtml("<strong>OpenIssuesCount: </strong>" + String.valueOf(repository.getOpenIssuesCount())));
        repositoryDetailsforks.setText(Html.fromHtml("<strong>Forks: </strong>" + String.valueOf(repository.getForks())));
        repositoryDetailsopenIssues.setText(Html.fromHtml("<strong>OpenIssues: </strong>" + String.valueOf(repository.getOpenIssues())));
        repositoryDetailswatchers.setText(Html.fromHtml("<strong>Watchers: </strong>" + String.valueOf(repository.getWatchers())));
        repositoryDetailsdefaultBranch.setText(Html.fromHtml("<strong>DefaultBranch: </strong>" + String.valueOf(repository.getDefaultBranch())));
    }   
}
