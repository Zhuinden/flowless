package flow;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public interface Bundleable {
    Bundle toBundle();

    void fromBundle(@Nullable Bundle bundle);
}
