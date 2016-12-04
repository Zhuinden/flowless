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
 * A screen that is willing to render in any orientation.
 */
final class LooseScreen
        extends OrientationSampleScreen
        implements Parcelable {
    static final LooseScreen INSTANCE = new LooseScreen();

    @Override
    public
    @LayoutRes
    int getLayoutId() {
        return R.layout.loose_screen;
    }

    private LooseScreen() {
    }

    // parcelable
    protected LooseScreen(Parcel in) {
    }

    public static final Creator<LooseScreen> CREATOR = new Creator<LooseScreen>() {
        @Override
        public LooseScreen createFromParcel(Parcel in) {
            return new LooseScreen(in);
        }

        @Override
        public LooseScreen[] newArray(int size) {
            return new LooseScreen[size];
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
