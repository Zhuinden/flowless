Change Log
==========

Flowless Version 1.0-alpha1.20 *(2016-12-18)*
---------------------------------------------
* BREAKING CHANGE: `ForceBundler.saveToBundle` finally no longer takes `Activity` parameter
* Added `Installer.globalKey()` to register global key, that can have state bound to it which never gets cleared
* Enhancement: Global key is available from `baseContext` via `Flow.getKey()`
* Change: `KeyManager` is exposed in Flow via `Flow.getStates()` to allow persisting global key state

Flowless Version 1.0-alpha1.17 *(2016-12-11)*
---------------------------------------------
* BREAKING CHANGE: `Flow.services(Context)` and `Flow.services(View)` are now `flow.getServices()`
* Fix: InternalContextWrapper should no longer throw NPE if InternalLifecycleIntegration is detached or not yet attached (returns null instead)
* Enhancement: null checks for ServiceProvider

Flowless Version 1.0-alpha1.16 *(2016-12-07)*
---------------------------------------------
* BREAKING CHANGE: `FlowLifecycles.ViewStatePersistenceListener`'s `preSaveViewState()` is now detached into two interfaces with following methods: `preSaveViewState()` and `onSaveInstanceState()`.
  * delegation of `preSaveViewState()` is manual, but `onSaveInstanceState()` is automatic. This enables `Bundleable` to work even without calling `preSaveViewState()`.

Flowless Version 1.0-alpha1.15 *(2016-12-04)*
---------------------------------------------
* BREAKING CHANGE: `FlowLifecycleListener.onViewRestored()` no longer receives `forcedWithBundler` boolean, it never really had a purpose.

Flowless Version 1.0-alpha1.14 *(2016-12-03)*
---------------------------------------------
* **API QUAKE!**
* BREAKING CHANGE: `SingleRootDispatcher` is now **abstract**, the `dispatch()` method *must be implemented by the user!* Its previous implementation and supporting elements were moved to `ExampleDispatcher` in the samples. (This change is to support annotations for metadata, and transition animations)
  * Moved following to samples: `DispatcherUtils.addViewToGroupForKey()`, `DispatcherUtils.createAnimatorForViews()`, `DispatcherUtils.selectAnimatedKey()` to remove forced `AnimatorSet` and `FlowAnimation` usage (to support transitions)
  * Moved following to samples: `DispatcherUtils.createViewFromKey()` to remove forced dependency on `LayoutKey` interface (to support using annotations)
  * Removed `DispatcherUtils.removeFromViewGroup()` and `DispatcherUtils.finishTraversal()` (because it cluttered the API even though it's just a null check / single-line method call)
  * Moved following to samples: `LayoutKey` (to support annotation-based layout specification)
  * Moved following to samples: `FlowAnimation` (to support transitions)
* BREAKING(?) CHANGE: `FlowLifecycleProvider` is now a utility class (static methods) because it doesn't make much sense to redefine any of its behavior
* **Enhancement:** added new sample `flowless-sample-transitions`

Flowless Version 1.0-alpha1.13 *(2016-11-20)*
---------------------------------------------
* BREAKING CHANGE: `DispatcherUtils.persistViewToState()` is now two methods: `persistViewToStateAndNotifyRemoval()` and `persistViewToStateWithoutNotifyRemoval()`  
* Added `ServiceProvider.hasService()`, `getService()` now works as documented and should be used with `hasService()`
* `RootHolder` now has `getRoot()` method (without which making it public didn't make much sense)

Flowless Version 1.0-alpha1.12 *(2016-08-29)*
---------------------------------------------
* After realizing the power of `Context.getSystemService()`, added `ServiceProvider`

Flowless Version 1.0-alpha1.11 *(2016-08-04)*
---------------------------------------------
* Removed `InternalContextThemeWrapper` because it wasn't needed
* Added delegation of `Activity` and `ContextThemeWrapper` methods to the inner Activity 

Flowless Version 1.0-alpha1.9 *(2016-07-31)*
--------------------------------------------
* Added `InternalContextThemeWrapper` to allow using Design Support Library

Flowless Version 1.0-alpha1.7 *(2016-07-30)*
--------------------------------------------
* Added hook `configure()` and `onTraversalCompleted()` to dispatcher
* Removed by Flow is called before waiting for measure

Flowless Version 1.0-alpha1.6 *(2016-07-27)*
--------------------------------------------
* `onDestroy()` callback called from `onDestroyView()` of `InternalLifecycleIntegration` (was not called previously)
* Flow's `PendingTraversal` is now forced to execute if the Activity is destroyed (this prevents the `state == DISPATCHED` freeze) 

Flowless Version 1.0-alpha1.5 *(2016-07-26)*
--------------------------------------------
* Animations no longer called if `previousView == null`

Flowless Version 1.0-alpha1.2 *(2016-07-22)*
--------------------------------
* Re-added tests
* Fixed possible freeze-up due to failing `dispatcherSetInMidFlightWaitsForBootstrap()` test`

Flowless Version 1.0-alpha1.1 *(2016-07-04)*
--------------------------------
* BREAKING CHANGE: Renamed `LayoutPath` to `LayoutKey`
* BREAKING CHANGE: Due to dispatchers no longer being bound to `Application.ActivityCallbacks`, `preSaveViewState()` must be called manually from the activity
* `onCreate` callback now relies on `InternalLifecycleIntegration`'s `onActivityCreated()` callback (Activity no longer has to do this manually)
* Dispatchers rely on the InternalLifecycleIntegration's callback events instead of being an `Application.ActivityCallbacks` (simplifies delegation in `SinglePaneContainer`)
* Removed `FlowActivityCallbacks`, it was the same as `FlowLifecycles.BackPressListener` and the like and shouldn't have existed in the first place

Flowless Version 1.0-alpha *(2016-07-01)*
--------------------------------
* Updated `SingleRootDispatcher` sample
* Added `ContainerRootDispatcher` preset, updated master-detail sample
* Simplified writing new Dispatchers by adding DispatcherUtil and FlowLifecycles (see `preset` for more details)
* Rewrote pending traversal management in Flow to reduce possible risk of freeze up

Flowless Version 1.0-alpha *(2016-06-28)*
--------------------------------
* Added `SingleRootDispatcher` preset and sample

Flowless Version 1.0-alpha *(2016-06-17, 2016-06-19)*
--------------------------------
* Fixed `Flow.addHistoryToIntent()` state restoration

Flowless Version 1.0-alpha *(2016-05-27)*
--------------------------------
* Added `ActivityUtils` class to obtain `Activity` from Flow Contexts
* Fixed calling bootstrap traversal on `onResume()` even in case the View still exists

Flowless Version 1.0-alpha *(2016-05-05)*
--------------------------------
* Completely removed `Flow.Services` and resource management, and all relevant classes.
* MultiKeys and TreeKeys no longer exist, KeyChanger and KeyDispatcher no longer exist.
* Removed default dispatcher, and the provided default key `"Hello, world"`.
* Added default KeyParceler (using Parcelable).
* Added ForceBundler and Bundleable to preserve custom view state to Bundle in `onSaveInstanceState()`.
* Added a simple and a master-detail example using Flowless.

Version 1.0-alpha *(2016-02-18)*
--------------------------------
Presented for review and feedback. API should still be considered unstable, docs incomplete, and functionality buggy. All of the above should be mostly resolved before beta.

1.0 brings major functional improvements and API changes. 

* Activity integration has been rewritten and is much simpler. One line to configure and install; one optional line to handle the back button; one optional line to handle new Intent. Flow handles lifecycle internally.
* Resource management (including shared resources) is now natively supported via TreeKeys-- Path has effectively been absorbed and simplified. Contexts are now managed internally and there's much less nesting of wrappers. 
* Multiple simultaneous states are now supported via MultiKeys-- Flow now works natively with UIs composed of dialogs, sheets, master-detail views, etc. MultiKeys can be composed of TreeKeys for resource sharing.
* Persistence has been expanded and simplified. You can now save a Bundle along with view state, and Flow takes care of all the lifecycle.
* Nested/queued traversals are much safer and more efficient.
* The `goBack` operation in particular is safer and more predictable.
* Save state and view state are managed internally and orthogonally to History; you no longer have to take care to avoid losing state when changing History.

Version 0.12 *(2015-08-13)*
------
* Fix: History.Builder#pop is nullable again, and adds History.Builder#isEmpty.

Version 0.11 *(2015-08-11)*
------
* Fix: No longer persists an empty list of states if the filter excludes everything.

Version 0.10 *(2015-05-01)*
------
* Fix: The Builder returned by `History#buildUpon()` is now safer to use. See
  javadoc for detail.

Version 0.9 *(2015-04-24)*
------
A large number of breaking changes have been made in the interest of focusing 
the library.

* Backstack is now called History and has some new method names.
* The `resetTo`, `goTo`, `replaceTo`, `forward`, and `backward` operations are 
  all gone. In their place are two simple methods: `set(Object)` and 
  `set(History, Direction)`.
* `HasParent` and `goUp` are gone. "Up" navigation is left as an exercise to app
  authors who need it, at least for the time being.
* The `@Layout` annotation has been removed. You can find it in the sample if
  you want a copy.
* Listener is now called Dispatcher, and can be set on a Flow after
  construction. Dispatcher gets more information than Listener did.

There are also some new features, and more are coming. 

* Added a Context service for easily obtaining the Flow.
* Added `FlowDelegate` for easier integration into an Activity.
* Added the flow-path module. Paths generally represent states/screens in an app, and
  are associated with Contexts which can be created by a user-supplied factory.
  PathContainer helps with switching views while maintaining view state.

Version 0.8 *(2014-09-17)*
-------
* API break: The Listener now gets a Callback, which it *must* call when it has
  completed a transition.
* Flow now supports reentry.  While a Listener is executing, calls to Flow which modify
  the backstack are enqueued.
* Beefed up sample app, including demonstration of providing view persistence via
  the back stack

Version 0.7 *(2014-05-16)*
-------
* replaceTo and goUp keep original screens for a matching prefix of the stack.
* Fix waitForMeasureLoop in example code.

Version 0.6 *(2014-04-21)*
-------
* API break: replaceTo() now has a new Direction associated with it, `REPLACE`.
  This is logically more correct because the incumbent backstack is not
  consulted, and convenient because a replace transition is typically
  different from a forward or backward transition.

Version 0.5 *(2014-04-15)*
-------
* Keep original screen on stack if found by resetTo.

Version 0.4 *(2014-01-28)*
-------
* API break: @Screen(layout=R.layout.foo) > @Layout(R.layout.foo), and Screens > Layouts.
  Support for view class literals is gone. They break theming and the fix isn't worth
  the bother.

Version 0.3 *(2014-01-21)*
-------
* New: Backstack#fromUpChain(Object), allows backstack to be created from a HasParent
  screen.

Version 0.2 *(2013-11-12)*
-------
Initial release.
