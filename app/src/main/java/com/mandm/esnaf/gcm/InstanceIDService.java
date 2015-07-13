package com.mandm.esnaf.gcm;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;
import com.mandm.esnaf.R;

import java.io.IOException;

/**
 * Created by mcgoncu on 6/17/15.
 *
 */
public class InstanceIDService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {

        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            //TODO :
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
