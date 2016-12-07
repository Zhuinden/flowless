package com.zhuinden.flowless_viewpager_example;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import flowless.Bundleable;
import flowless.preset.FlowLifecycles;

/**
 * Created by Zhuinden on 2016.07.17..
 */
public abstract class FlowlessPagerAdapter
        extends PagerAdapter {
    final Bundle[] bundles = new Bundle[getCount()]; // TODO: change to list like FragmentStatePagerAdapter
    final View[] views = new View[getCount()]; // TODO: change to list like FragmentStatePagerAdapter

    final boolean[] didCallDestroy = new boolean[getCount()]; // TODO: fix hack for multiple saveState calls (possibly through delegation of event from view...? -_-)

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = getItem(position);
        if(bundles[position] != null) {
            Bundle savedState = bundles[position];
            view.restoreHierarchyState(savedState.getSparseParcelableArray("viewState"));
            if(view instanceof Bundleable) {
                ((Bundleable) view).fromBundle(savedState.getBundle("bundle"));
            }
        }
        if(view instanceof FlowLifecycles.ViewLifecycleListener) {
            ((FlowLifecycles.ViewLifecycleListener) view).onViewRestored();
        }
        container.addView(view);
        views[position] = view;
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View)object;
        if(bundles[position] == null) {
            bundles[position] = new Bundle();
        }
        Bundle savedState = bundles[position];
        saveStateForView(view, savedState);
        if(view instanceof FlowLifecycles.ViewLifecycleListener) {
            ((FlowLifecycles.ViewLifecycleListener) view).onViewDestroyed(false);
        }
        views[position] = null;
        container.removeView(view);
    }

    @Override
    public Parcelable saveState() {
        Bundle pagerState = new Bundle();
        for(int i = 0; i < getCount(); i++) {
            if(views[i] != null) {
                if(bundles[i] == null) {
                    bundles[i] = new Bundle();
                }
                View view = views[i];
                Bundle savedState = bundles[i];
                saveStateForView(view, savedState);
                if(view instanceof FlowLifecycles.ViewLifecycleListener) {
                    if(!didCallDestroy[i]) {
                        didCallDestroy[i] = true;
                        ((FlowLifecycles.ViewLifecycleListener) view).onViewDestroyed(false);
                    }
                }
            }
            if(bundles[i] != null) {
                Bundle viewState = bundles[i];
                pagerState.putBundle("viewState_" + i, viewState);
            }
        }
        return pagerState;
    }

    private void saveStateForView(View view, Bundle savedState) {
        if(view instanceof FlowLifecycles.ViewStatePersistenceListener) {
            ((FlowLifecycles.ViewStatePersistenceListener) view).onSaveInstanceState(savedState);
        }
        SparseArray<Parcelable> viewState = new SparseArray<>();
        view.saveHierarchyState(viewState);
        savedState.putSparseParcelableArray("viewState", viewState);
        if(view instanceof Bundleable) {
            savedState.putBundle("bundle", ((Bundleable) view).toBundle());
        }
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        Bundle pagerState = (Bundle)state;
        for(int i = 0; i < getCount(); i++) {
            Bundle viewState = pagerState.getBundle("viewState_" + i);
            if(viewState != null) {
                bundles[i] = viewState;
            }
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public abstract View getItem(int position);
}
