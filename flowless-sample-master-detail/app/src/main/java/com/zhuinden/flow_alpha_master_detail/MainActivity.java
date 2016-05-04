package com.zhuinden.flow_alpha_master_detail;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhuinden.flow_alpha_master_detail.paths.FirstKey;

import java.util.Iterator;

import flow.Bundleable;
import flow.Dispatcher;
import flow.Flow;
import flow.ForceBundler;
import flow.History;
import flow.KeyParceler;
import flow.State;
import flow.Traversal;
import flow.TraversalCallback;

public class MainActivity
        extends AppCompatActivity
        implements Dispatcher {

    private RelativeLayout root;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = (RelativeLayout) findViewById(R.id.root);
    }

    @Override
    public void onBackPressed() {
        if(!Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void attachBaseContext(Context baseContext) {
        Log.i(TAG, "Attaching base context");
        baseContext = Flow.configure(baseContext, this) //
                .keyParceler(new KeyParceler() { //
                    @Override
                    public Parcelable toParcelable(Object key) {
                        return (Parcelable) key;
                    }

                    @Override
                    public Object toKey(Parcelable parcelable) {
                        return parcelable;
                    }
                }) //
                .defaultKey(new FirstKey()) //
                .dispatcher(this) //
                .install(); //
        super.attachBaseContext(baseContext);
    }


    protected void persistViewToState(Traversal traversal, View view) {
        if(view != null) {
            State outgoingState = traversal.getState(Flow.getKey(view.getContext()));
            if(outgoingState != null) {
                Log.i(TAG, "Persisting outgoing state for [" + view + "]");
                outgoingState.save(view);
                if(view instanceof Bundleable) {
                    Log.i(TAG, "Persisting state to bundle for " + view);
                    outgoingState.setBundle(((Bundleable) view).toBundle());
                }
            }
        }
    }

    protected void restoreViewFromState(Traversal traversal, View view) {
        Log.i(TAG, "Restoring view state for " + view);
        State incomingState = traversal.getState(Flow.getKey(view.getContext()));
        if(incomingState != null) {
            incomingState.restore(view);
            if(view instanceof Bundleable) {
                Log.i(TAG, "Restoring state from bundle for " + view);
                ((Bundleable) view).fromBundle(incomingState.getBundle());
            }
        }
    }

    @Override
    public void dispatch(Traversal traversal, TraversalCallback callback) {
        History destination = traversal.destination;
        History origin = traversal.origin;
        if(origin != null && origin.top() != null && origin.top().equals(destination.top())) { //short circuit on same key
            callback.onTraversalCompleted();
            return;
        }

        Object destTop = destination.top();
        if(!(destTop instanceof IsFullScreen || destTop instanceof IsMaster || destTop instanceof IsDetail)) {
            throw new IllegalArgumentException("The destination [" + destTop + "] does not implement either of the mandatory marker interfaces.");
        }
        IsLayoutKey destKey = (IsLayoutKey) destTop;

        View previousView = root.getChildAt(0);
        if(previousView != null) {
            if(previousView instanceof SinglePaneContainer) {
                SinglePaneContainer singlePaneContainer = (SinglePaneContainer) previousView;
                if(singlePaneContainer.getChildAt(0) != null) {
                    persistViewToState(traversal, singlePaneContainer.getChildAt(0));
                }
            } else if(previousView instanceof MasterDetailContainer) {
                MasterDetailContainer masterDetailContainer = (MasterDetailContainer) previousView;
                RelativeLayout masterContainer = masterDetailContainer.getMasterContainer();
                RelativeLayout detailContainer = masterDetailContainer.getDetailContainer();
                if(destKey instanceof IsMaster || destKey instanceof IsFullScreen) {
                    persistViewToState(traversal, masterContainer.getChildAt(0));
                }
                if(destKey instanceof IsMaster || destKey instanceof IsFullScreen || destKey instanceof IsDetail) {
                    persistViewToState(traversal, detailContainer.getChildAt(0));
                }
            } else {
                persistViewToState(traversal, previousView);
            }
        }

        Context destContext = traversal.createContext(destKey, this);
        View destView = LayoutInflater.from(destContext).inflate(destKey.getLayout(), root, false);

        restoreViewFromState(traversal, destView);

        if(destKey instanceof IsFullScreen) {
            if(previousView != null) {
                root.removeView(previousView);
            }
            root.addView(destView);
            callback.onTraversalCompleted();
        } else {
            View currentContainer = root.getChildAt(0);
            if(!(currentContainer instanceof MasterDetailContainer || currentContainer instanceof SinglePaneContainer)) {
                if(currentContainer != null) {
                    root.removeView(currentContainer);
                }
                currentContainer = LayoutInflater.from(this).inflate(R.layout.layout_master_detail_container, root, false);
                root.addView(currentContainer);
            }

            if(currentContainer instanceof MasterDetailContainer) {
                MasterDetailContainer masterDetailContainer = (MasterDetailContainer) currentContainer;
                masterDetailContainer.getDetailContainer().removeAllViews();
                if(destKey instanceof IsMaster) {
                    masterDetailContainer.getMasterContainer().removeAllViews();
                    masterDetailContainer.getMasterContainer().addView(destView);
                } else if(destKey instanceof IsDetail) {
                    Iterator<IsLayoutKey> destinationHistory = destination.reverseIterator();
                    IsMaster currentMaster = null;
                    while(destinationHistory.hasNext()) {
                        IsLayoutKey iterHistory = destinationHistory.next();
                        if(iterHistory instanceof IsMaster) {
                            currentMaster = (IsMaster) iterHistory;
                        }
                    }
                    if(currentMaster == null) { //TODO: ensure that adding a detail without previous master will still open master!
//                      Flow flow = Flow.get(getBaseContext());
//                      flow.replaceTop(((IsDetail) destKey).getMaster(), Direction.FORWARD);
//                      flow.set(destKey);
//                      return;
                        throw new IllegalStateException("Cannot open directly without adding its master [" + ((IsDetail) destKey).getMaster() + " first!");
                    }
                    View currentMasterView = masterDetailContainer.getMasterContainer().getChildAt(0);
                    if(currentMasterView == null) {
                        Context masterContext = traversal.createContext(currentMaster, this);
                        View masterView = LayoutInflater.from(masterContext)
                                .inflate(currentMaster.getLayout(), masterDetailContainer.getMasterContainer(), false);
                        restoreViewFromState(traversal, masterView);
                        masterDetailContainer.getMasterContainer().addView(masterView);
                    }
                    masterDetailContainer.getDetailContainer().removeAllViews();
                    masterDetailContainer.getDetailContainer().addView(destView);
                }
            } else if(currentContainer instanceof SinglePaneContainer) {
                SinglePaneContainer singlePaneContainer = (SinglePaneContainer)currentContainer;
                singlePaneContainer.removeAllViews();
                singlePaneContainer.addView(destView);
            }
            callback.onTraversalCompleted();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(root != null) {
            View currentView = root.getChildAt(0);
            if(currentView instanceof MasterDetailContainer) {
                MasterDetailContainer masterDetailContainer = (MasterDetailContainer) currentView;
                ForceBundler.saveToBundle(this, masterDetailContainer.getMasterContainer().getChildAt(0), masterDetailContainer.getDetailContainer().getChildAt(0));
            } else if(currentView instanceof SinglePaneContainer) {
                SinglePaneContainer singlePaneContainer = (SinglePaneContainer)currentView;
                ForceBundler.saveToBundle(this, singlePaneContainer.getChildAt(0));
            } else {
                ForceBundler.saveToBundle(this, currentView);
            }
        }
    }
}
