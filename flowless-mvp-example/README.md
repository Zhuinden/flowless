(Taken from [Zhuinden/ExampleGithubClient](https://github.com/Zhuinden/ExampleGithubClient/))

# Flowless MVP Example: "Example Github Client"

In reality, login just waits 3 seconds, and the only repository you download is "square" repository.

This is a sample to show data-domain-presentation layering, subscoping via Dagger2, and MVP architecture; in a single-Activity setup provided by [flowless](https://github.com/Zhuinden/flowless/).

Some tests are included, although they aren't complete.

----------------------

**Data layer:**

- data layer is reactive, currently in-memory and doesn't actually persist itself anywhere. It should be replaced with SqlBrite + RxJava, or Realm

- data is held by the DataSource, it is manipulated via the "Repository"

----------------------

**Domain layer:**

- the Service that communicates with the Github API, provided via Retrofit

- threading is provided by [Bolts-Android](https://github.com/BoltsFramework/Bolts-Android)

- the manipulation of data is done in the interactors

----------------------

**Presentation layer:**

- contains the presenters which store state and tell the view what to do when an event occurs

- also contains the views that delegate all callbacks to their presenters

---------------------

**Utils:**

- contains anything else that is not domain specific

- also contains `TransitionDispatcher`, which is what the application uses to determine what to do on an application state change

----------------

----------------

The application is driven by the keys.

For example,

``` java
@AutoValue
@Title(R.string.title_login)
@Layout(R.layout.path_login)
@ComponentFactory(LoginComponentFactory.class)
@LeftDrawerEnabled(false)
@ToolbarButtonVisibility(false)
public abstract class LoginKey
        implements Parcelable {
    public static LoginKey create() {
        return new AutoValue_LoginKey();
    }
}
```

The dispatcher creates the view and the scoped component, the view injects itself, and attaches itself to the presenter.

``` java
public class RepositoriesView
        extends RelativeLayout
        implements FlowLifecycles.ViewLifecycleListener, RepositoriesPresenter.ViewContract {

    // constructors
        
    public void init() {
        if(!isInEditMode()) {
            RepositoriesComponent repositoriesComponent = DaggerService.getComponent(getContext());
            repositoriesComponent.inject(this);
        }
    }

    @Inject
    RepositoriesPresenter repositoriesPresenter;
        
    @Override
    public void onViewRestored() {
        repositoriesPresenter.attachView(this);
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        repositoriesPresenter.detachView();
    }
}
```

The presenter provides a view contract based on which it can call the callbacks inside the view, to bring it "up-to-date" or to navigate.

``` java
@KeyScope(RepositoriesKey.class)
public class RepositoriesPresenter
        extends BasePresenter<RepositoriesPresenter.ViewContract> {
        
    @Inject
    public RepositoriesPresenter() {
    }
        
    public interface ViewContract
            extends Presenter.ViewContract {
        void updateRepositories(List<Repository> repositories);

        void openRepository(String url);
    }

    @Override
    protected void initializeView(ViewContract view) {
        if(repositories == null || repositories.isEmpty()) {
            downloadPage();
        } else {
            updateRepositoriesInView();
        }
    }
}
```

The interactors download the data via the service, and save it to the model. The presenters are subscribed to changes inside the model.

``` java
@ActivityScope
public class GetRepositoriesInteractorImpl
        implements GetRepositoriesInteractor {
    @Inject
    GithubService githubService;

    @Inject
    RepositoryRepository repositoryRepository;

    @Inject
    public GetRepositoriesInteractorImpl() {
    }

    @Override
    public Task<List<Repository>> getRepositories(final String user, int page) {
        return githubService.getRepositories(user, page).continueWith(task -> {
            if(task.isFaulted()) {
                throw task.getError();
            }
            return repositoryRepository.saveOrUpdate(task.getResult());
        });
    }
}
```
