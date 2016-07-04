Change Log
==========

Flowless Version 1.0-alpha2 *(2016-07-04)*
--------------------------------
* BREAKING CHANGE: Renamed `LayoutPath` to `LayoutKey`
* `onActivityResult` and `onPermissionResult` callbacks added to `InternalLifecycleIntegration` (Activity no longer has to do this manually)
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
