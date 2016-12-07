# Flow(less)

_"Name-giving will be the foundation of our science."_ - Linnaeus

_"Memory is the treasury and guardian of all things._" - Cicero

_"It's better if you're good at one thing than if you're bad at many things just because you're trying too hard. Especially if you're a backstack library._" - Zhuinden

**Flow(less) gives names to your Activity's UI states, navigates between them, and remembers where it's been.**

This used to be a fork of Flow 1.0-alpha by Square, with the "resource management" aspect removed.
Now it provides more than that, both in terms of bug fixes and some additional features (specifically the dispatcher lifecycle integration) alike.
Also, you can't file issues on forks, which makes it not palatable on the long run.

## Features

Navigate between UI states. Support the back button easily without confusing your users with surprising results.

Remember the UI state, and its history, as you navigate and across configuration changes and process death.

Manage resources. UI states can create and share resources, and you can dispose of them when no longer needed.

Manage all types of UIs-- complex master-detail views, multiple layers, and window-based dialogs are all simple to manage.


## Using Flow(less)

In order to use Flow(less), you need to add jitpack to your project root gradle:

    buildscript {
        repositories {
            // ...
            maven { url "https://jitpack.io" }
        }
        // ...
    }
    allprojects {
        repositories {
            // ...
            maven { url "https://jitpack.io" }
        }
        // ...
    }


and add the compile dependency to your module level gradle.

    compile 'com.github.Zhuinden:flowless:1.0-alpha1.16'


Then, install Flow into your Activity:

```java
public class MainActivity {
    @BindView(R.id.main_root)
    ViewGroup root;

    ExampleDispatcher flowDispatcher; // extends SingleRootDispatcher

    @Override
    protected void attachBaseContext(Context newBase) {
        flowDispatcher = new ExampleDispatcher(this);
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
    public void onBackPressed() {
        if(!flowDispatcher.onBackPressed()) {
            super.onBackPressed();
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        flowDispatcher.preSaveViewState(); // optional
        super.onSaveInstanceState(outState);
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

public final class HomeKey extends flowless.ClassKey {
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
public interface LayoutKey
        extends Parcelable {
    @LayoutRes int layout();

    FlowAnimation animation();
}

@AutoValue
public abstract class CalendarEventKey implements LayoutKey {
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

``` java
History history = Flow.get(this).getHistory();
Flow.get(this).setHistory(history.buildUpon().pop(2).push(SomeKey.create()).build(), Direction.BACKWARD);
```

See the [Flow](https://github.com/Zhuinden/flowless/blob/master/flowless-library/src/main/java/flowless/Flow.java) class for other convenient operators.

As you navigate the app, Flow keeps track of where you've been. And Flow makes it easy to save view state (and any other state you wish) so that when your users go back to a place they've been before, it's just as they left it.

### Controlling UI
Navigation only counts if it changes UI state. Because every app has different needs, Flow lets you plug in [your own logic](https://github.com/Zhuinden/flowless/blob/master/flowless-library/src/main/java/flowless/Dispatcher.java) for responding to navigation and updating your UI.

The Dispatcher has the following tasks when a new state is set:
- Check for short-circuit if new state is same as the old (`DispatcherUtils.isPreviousKeySameAsNewKey()`), and if true, callback and return
- Inflate the new view with Flow's internal context using `LayoutInflater.from(traversal.createContext(...))`
- Persist the current view (`DispatcherUtils.persistViewToStateAndNotifyRemoval()`)
- Restore state to new view (`DispatcherUtils.restoreViewFromState()`)
- Optionally animate the two views (with `TransitionManager` or `AnimatorSet`)
- Remove the current view
- Add the new view
- Signal to Flow that the traversal is complete (`callback.onTraversalCompleted()`)

### Surviving configuration changes and process death
Android is a hostile environment. One of its greatest challenges is that your Activity or even your process can be destroyed and recreated under a variety of circumstances. Flow makes it easy to weather the storm, by automatically remembering your app's state and its history. 

You [supply the serialization](https://github.com/Zhuinden/flowless/blob/master/flowless-library/src/main/java/flowless/KeyParceler.java) for your keys, and Flow does the rest. The default parceler uses Parcelable objects. Flow automatically saves and restores your History (including any state you've saved), taking care of all of the Android lifecycle events so you don't have to worry about them.

**Note:** If you use the `ContainerDispatcherRoot`, you must call `ForceBundler.saveToBundle(activity, view)` manually in the `preSaveViewState()` method on the child you wish to persist in your container, because this cannot be handled automatically.

## Pre-set dispatchers for common use-cases

Two use-cases are supported out of the box. Both of them provide (optional) life-cycle hooks for easier usage within your custom viewgroups.

First is the `SingleRootDispatcher`, which works if your Activity has a *single root*, meaning you're changing the screens within a single ViewGroup within your Activity. This base class provides the default event delegation to the view inside your root. The `dispatch()` method has to be implemented by the user.

Second is the `ContainerRootDispatcher`. Its purpose is to delegate the `dispatch()` call and all other lifecycle method calls to your defined custom viewgroup. The Root provided to a container root dispatcher must implement `Dispatcher`. It must also delegate the lifecycle method call to its children. For easier access to all lifecycle methods, the `FlowContainerLifecycleListener` interface is introduced, and the `FlowLifecycleProvider` class tries to make delegation as simple as possible. Of course, delegation of the `FlowLifecycles` lifecycle methods are optional, and you can choose to delegate only what you actually need. An example is provided for this setup in the [Master-Detail example](https://github.com/Zhuinden/flowless/blob/master/flowless-sample-master-detail/src/main/java/com/zhuinden/flow_alpha_master_detail/MasterDetailContainer.java#L37).

## Example Custom View Group

The most typical setup for a custom view group would look like so, using the `Bundleable` interface and listening to state restoration with `ViewLifecycleListener`.

``` java
public class FirstView
        extends RelativeLayout
        implements Bundleable, FlowLifecycles.ViewLifecycleListener {
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
        }
    }

    @OnClick(R.id.first_button)
    public void firstButtonClick(View view) {
        Flow.get(view).set(SecondKey.create());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        // persist state here
        return bundle;
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        if(bundle != null) {
            // restore state here
        }
    }

    @Override
    public void onViewRestored() {
        // view was created and state has been restored
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        // view is about to be destroyed, either by Activity death 
        // or the Flow dispatcher has removed it
    }
}
```

The view is created based on the key:

``` java
@AutoValue
public abstract class FirstKey
        implements LayoutKey {
    public static FirstKey create() {
        return new AutoValue_FirstKey(R.layout.path_first, FlowAnimation.NONE);
    }
}
```

And it's inflated based on the following XML:

``` xml
<?xml version="1.0" encoding="utf-8"?>
<com.zhuinden.flowless_dispatcher_sample.FirstView 
             xmlns:android="http://schemas.android.com/apk/res/android"
             android:layout_width="match_parent"
             android:layout_height="match_parent">
    <Button
        android:id="@+id/first_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="@string/go_to_second_view"/>
</com.zhuinden.flowless_dispatcher_sample.FirstView>
```


### Managing resources (optional)
You can manage resources shared through your context manually using the `ServiceProvider`, which you can obtain through `Flow.services(Context)` or `Flow.services(View)`.

This way, you can bind services you need when you initialize your View in its constructor (before `onFinishInflate()` is called) or before it's inflated in the Dispatcher itself, while also sharing them to additional views that belong to the same Context.

Here is a rather barebones implementation that creates services for elements that are currently within the history of keys.

``` java
ServiceProvider serviceProvider = Flow.services(newContext);

// destroyNotIn()
Iterator<Object> aElements = traversal.origin != null ? traversal.origin.reverseIterator() : Collections.emptyList().iterator();
Iterator<Object> bElements = traversal.destination.reverseIterator();
while(aElements.hasNext() && bElements.hasNext()) {
    BaseKey aElement = (BaseKey) aElements.next();
    BaseKey bElement = (BaseKey) bElements.next();
    if(!aElement.equals(bElement)) {
        serviceProvider.unbindServices(aElement);  // returns map of bound services
        break;
    }
}
while(aElements.hasNext()) {
    BaseKey aElement = (BaseKey) aElements.next();
    serviceProvider.unbindServices(aElements.next()); // returns map of bound services
}
// end destroyNotIn

// create service for keys
for(Object destination : traversal.destination) {
    if(!serviceProvider.hasService(destination, DaggerService.TAG) {
        serviceProvider.bindService(destination, DaggerService.TAG, ((BaseKey) destination).createComponent());
    }
}
```

Which can now share the following service:

``` java
public class DaggerService {
    public static final String TAG = "DaggerService";

    @SuppressWarnings("unchecked")
    public static <T> T getComponent(Context context) {
        //noinspection ResourceType
        return (T) Flow.services(context).getService(Flow.getKey(context), TAG);
    }
}
```

And can be obtained like so:

``` java
private void init(Context context) {
    if(!isInEditMode()) {
        LoginComponent loginComponent = DaggerService.getComponent(context);
        loginComponent.inject(this);
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
