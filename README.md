# Flow(less)

_"Name-giving will be the foundation of our science."_ - Linnaeus

_"The winds and waves are always on the side of the ablest navigators."_ - Gibbon

_"Memory is the treasury and guardian of all things._" - Cicero

_"It's better if you're good at one thing than if you're bad at many things just because you're trying too hard. Especially if you're a backstack library._" - Zhuinden

**Flow(less) gives names to your Activity's UI states, navigates between them, and remembers where it's been; and that's all it does.**

This is a fork of Flow 1.0-alpha by Square, with the "resource management" aspects completely removed.

## Features

Navigate between UI states. Support the back button easily without confusing your users with surprising results.

Remember the UI state, and its history, as you navigate and across configuration changes and process death.

~~Manage resources with set-up/tear-down hooks invoked for each UI state. UI states can easily share resources, and they'll be disposed when no longer needed.~~

Manage all types of UIs-- complex master-detail views, multiple layers, and window-based dialogs are all simple to manage.


## Using Flow

Currently **Flowless** is not set up to be added through Gradle, so you'd have to copy the sources.
I'm working on that quite soon, now that the presets are complete. Soon!

Then, install Flow into your Activity:

```java
public class MainActivity {
    @BindView(R.id.main_root)
    ViewGroup root;

    SingleRootDispatcher flowDispatcher;

    @Override
    protected void attachBaseContext(Context newBase) {
        flowDispatcher = new SingleRootDispatcher(newBase, this);
        newBase = Flow.configure(newBase, this) //
                .defaultKey(FirstKey.create()) //
                .dispatcher(flowDispatcher) //
                .install(); //
        flowDispatcher.setBaseContext(newBase);
        super.attachBaseContext(newBase);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        flowDispatcher.getRootHolder().setRoot(root);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        flowDispatcher.onActivityCreated(this, savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        boolean didGoBack = flowDispatcher.onBackPressed();
        if(didGoBack) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        flowDispatcher.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        flowDispatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
```

### Defining UI states with key objects

Your Activity's UI states are represented in Flow by Objects, which Flow refers to as "keys". Keys are typically [value objects][valueobject] with just enough information to identify a discrete UI state.

Flow relies on a key's [equals][equals] and [hashCode][hashcode] methods for its identity. Keys should be immutable-- that is, their `equals` and `hashCode` methods should always behave the same.

To give an idea of what keys might look like, here are some examples:

```java
public enum TabKey {
  TIMELINE,
  NOTIFICATIONS,
  PROFILE
}

public final class HomeKey extends flow.ClassKey {
}

public final class ArticleKey {
  public final String articleId;

  public ArticleKey(String articleId) {
    this.articleId = articleId;
  }

  public boolean equals(Object o) {
    return o instanceof ArticleKey
        && articleId.equals(((ArticleKey) o).articleId);
  }
  
  public int hashCode() {
    return articleId.hashCode();
  }
}
```

But if you want to be really cool, you can use [Auto-Parcel](https://github.com/frankiesardo/auto-parcel) to generate Parcelable immutable objects to define your keys.

``` java
@AutoValue
public abstract class CalendarEventKey
        implements LayoutPath, Parcelable {
    abstract long eventId();

    public static CalendarEventKey create(long eventId) {
        return new AutoValue_CalendarEventKey(R.layout.path_calendarevent, FlowAnimation.SEGUE, eventId);
    }
}
```

### Navigation and History
Flow offers simple commands for navigating within your app.

`Flow#goBack()` -- Goes back to the previous [key][keys]. Think "back button".

`Flow#set(key)` -- Goes to the requested key. Goes back or forward depending on whether the key is already in the History.

Flow also lets you rewrite history safely and easily.

`Flow#setHistory(history, direction)` -- Change history to whatever you want.

To modify the history, you ought to use the operators provided by History. Here is an example:

    History history = Flow.get(this).getHistory();
    Flow.get(this).setHistory(history.buildUpon().pop(2).push(SomeKey.create()).build(), Direction.BACKWARD);

See the [Flow](https://github.com/Zhuinden/flowless/blob/master/flow/src/main/java/flow/Flow.java) class for other convenient operators.

As you navigate the app, Flow keeps track of where you've been. And Flow makes it easy to save view state (and any other state you wish) so that when your users go back to a place they've been before, it's just as they left it.

### Controlling UI
Navigation only counts if it changes UI state. Because every app has different needs, Flow lets you plug in [your own logic](https://github.com/Zhuinden/flowless/blob/master/flow/src/main/java/flow/Dispatcher.java) for responding to navigation and updating your UI.

The Dispatcher has the following tasks when a new state is set:
- Inflate the new view with Flow's internal context using `LayoutInflater.from(context)`
- Persist the current view
- Restore state to new view
- Optionally animate the two views
- Remove the current view
- Add the new view
- Signal to Flow that the traversal is complete

### ~~Managing resources~~
~~Your app requires different resources when it's in different states; sometimes those resources are shared between states. Flow makes it easy to associate resources with keys so they're set up when needed and torn down (only) when they're not anymore.~~

### Surviving configuration changes and process death
Android is a hostile environment. One of its greatest challenges is that your Activity or even your process can be destroyed and recreated under a variety of circumstances. Flow makes it easy to weather the storm, by automatically remembering your app's state and its history. 

You [supply the serialization](https://github.com/Zhuinden/flowless/blob/master/flow/src/main/java/flow/KeyParceler.java) for your keys, and Flow does the rest. Flow  automatically saves and restores your History (including any state you've saved), taking care of all of the Android lifecycle events so you don't have to worry about them.

## Pre-set dispatchers for common use-cases

Two use-cases are supported out of the box. Both of them provide (optional) life-cycle hooks for easier usage within your custom viewgroups.

First is the `SingleRootDispatcher`, which works if your Activity has a *single root*, meaning you're changing the screens within a single ViewGroup within your Activity. 

Second is the `ContainerRootDispatcher`. Its purpose is to delegate the `dispatch()` call and all other lifecycle method calls to your defined custom viewgroup. The Root provided to a container root dispatcher must implement `Dispatcher`. It must also delegate the lifecycle method call to its children. For easier access to all lifecycle methods, the `FlowContainerLifecycleListener` interface is introduced, and the `FlowLifecycleProvider` class tries to make delegation as simple as possible. Of course, delegation of the `FlowLifecycles` lifecycle methods are optional, and you can choose to delegate only what you actually need. An example is provided for this setup in the Master-Detail example.

## Example Custom View Group

The most typical setup for a custom view group would look like so, using the `Bundleable` interface and listening to state restoration with `ViewLifecycleListener`.

``` java
public class FirstView
        extends RelativeLayout
        implements Bundleable, SingleRootDispatcher.ViewLifecycleListener {
    private static final String TAG = "FirstView";

    public FirstView(Context context) {
        super(context);
        init();
    }

    public FirstView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FirstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public FirstView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    FirstKey firstKey;

    public void init() {
        if(!isInEditMode()) {
            firstKey = Flow.getKey(this);
            Log.i(TAG, "init()");
        }
    }

    @OnClick(R.id.first_button)
    public void firstButtonClick(View view) {
        Flow.get(view).set(SecondKey.create());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG, "onFinishInflate()");
        ButterKnife.bind(this);
    }

    @Override
    public Bundle toBundle() {
        Log.i(TAG, "toBundle()");
        return new Bundle();
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        Log.i(TAG, "fromBundle()");
        if(bundle != null) {
            Log.i(TAG, "fromBundle() with bundle");
        }
    }

    @Override
    public void onViewRestored(boolean forcedWithBundler) {
        Log.i(TAG, "onViewRestored()");
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        Log.i(TAG, "onViewDestroyed(" + removedByFlow + ")");
    }
}
```


## License

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[equals]: http://developer.android.com/reference/java/lang/Object.html#equals(java.lang.Object)
[hashcode]: http://developer.android.com/reference/java/lang/Object.html#hashCode()
[keys]: #defining-ui-states-with-key-objects
[valueobject]: https://en.wikipedia.org/wiki/Value_object
