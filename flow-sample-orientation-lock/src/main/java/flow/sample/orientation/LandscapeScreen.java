/*
 * Copyright 2016 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package flow.sample.orientation;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;

/**
 * Screen that requires landscape orientation.
 */
final class LandscapeScreen
        extends OrientationSampleScreen
        implements Parcelable {
    static final LandscapeScreen INSTANCE = new LandscapeScreen();

    private LandscapeScreen() {
    }

    @Override
    public
    @LayoutRes
    int getLayoutId() {
        return R.layout.landscape_screen;
    }

    @Override
    boolean requiresLandscape() {
        return true;
    }

    // parcelable
    protected LandscapeScreen(Parcel in) {
    }

    public static final Creator<LandscapeScreen> CREATOR = new Creator<LandscapeScreen>() {
        @Override
        public LandscapeScreen createFromParcel(Parcel in) {
            return new LandscapeScreen(in);
        }

        @Override
        public LandscapeScreen[] newArray(int size) {
            return new LandscapeScreen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
