package flow.preset;

import android.content.Intent;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public interface FlowActivityCallbacks {
    boolean onBackPressed();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}
